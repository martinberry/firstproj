package com.ztravel.paygate.wx.service.impl;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.GateType;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.HttpUtil;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.paygate.wx.client.entity.RefundQueryRequestEntity;
import com.ztravel.paygate.wx.client.entity.RefundQueryResponseEntity;
import com.ztravel.paygate.wx.converter.WxPayParmConvert;
import com.ztravel.paygate.wx.dao.IWxRefundDao;
import com.ztravel.paygate.wx.service.IWxRefundService;
import com.ztravel.payment.paygate.model.RefundResultBean;
import com.ztravel.payment.service.IThirdPaymentThriftService;

@Service
public class WxRefundServiceImpl implements IWxRefundService {

    private static final Logger logger = LoggerFactory.getLogger(WxRefundServiceImpl.class);

    @Resource
    IWxRefundDao wxRefundDao;

    @Resource(name="orderThriftClientServiceImpl")
    private IOrderThriftClientService orderThriftClientServiceImpl;

    @Resource
    private IThirdPaymentThriftService thirdPaymentThriftService;

    private static final String WX_REFUND_QUERY_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "refund_query_url", ConfScope.R) ;

    @Override
    public RefundQueryResponseEntity queryRefund(String refundId) throws Exception {
        logger.info("wechat refund query ---> refundId:{}", refundId);
        RefundQueryResponseEntity response;
        //生成查询退款接口的请求参数
        RefundQueryRequestEntity refundQuery = WxPayParmConvert.buildWxRefundQueryParam(refundId);
        String refundQueryXml = WxPayParmConvert.buildXMlParamByEntity(refundQuery, RefundQueryRequestEntity.class, "xml");
        logger.info("wechat refund query request ---> {}", refundQueryXml);

        //调用微信查询退款接口
        String xmlResponse = HttpUtil.postXml(WX_REFUND_QUERY_URL, refundQueryXml);
        logger.info("wechat refund query response ---> {}", xmlResponse);
        //调用微信查询退款接口时,需对返回的XML格式数据做转换
        String changeXmlResponse = parseXml(xmlResponse);
        logger.info("change wechat refund query response ---> {}", changeXmlResponse);
        //将转换后的XML数据转换成查询退款对象
        response = WxPayParmConvert.getEntityByXml(changeXmlResponse, RefundQueryResponseEntity.class);
        return response;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor=RuntimeException.class)
    public CommonResponse proceedForRefund(String refundId) throws Exception {

        CommonResponse commonResponse = new CommonResponse();

        RefundQueryResponseEntity oldRefundQueryResponse = preproceedForRefund(refundId);
        if (oldRefundQueryResponse != null && oldRefundQueryResponse.getRecord_status() != null && oldRefundQueryResponse.getRecord_status().equals("T")) {
            logger.info("order already cancelled :::{}, skip...", refundId);
            commonResponse.setSuccess(true);
            commonResponse.setErrMsg("该订单已经取消成功");
            return commonResponse;
        }

        //如果微信退款查询结果中退款状态已经成功，则不需要重复进行退款查询申请，只需对该订单走取消业务流程即可
        if (oldRefundQueryResponse != null && oldRefundQueryResponse.getRefund_status() != null && oldRefundQueryResponse.getRefund_status().equals("SUCCESS")) {
            dealRefundSuccessOrder(oldRefundQueryResponse, commonResponse);
            logger.info("wechat refund end, result:::{}", TZBeanUtils.describe(commonResponse));
            return commonResponse;
        }

        //主动向微信发送查询退款接口请求
        RefundQueryResponseEntity refundQueryResponse = queryRefund(refundId);
        logger.info("wechat query refund result:\n" + JSONObject.toJSONString(refundQueryResponse));

        //记录退款查询结果记录
        recordQueryRefundResult(oldRefundQueryResponse, refundQueryResponse);

        Boolean refundFail = false;

        String return_code = refundQueryResponse.getReturn_code();
        String result_code = refundQueryResponse.getResult_code();
        if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
            String refund_status = refundQueryResponse.getRefund_status();
            if (refund_status.equals("SUCCESS")) {
                dealRefundSuccessOrder(refundQueryResponse, commonResponse);
            } else if (refund_status.equals("FAIL")) {
                commonResponse.setSuccess(false);
                commonResponse.setErrMsg("退款失败");
                refundFail = true;
            } else if (refund_status.equals("PROCESSING")) {
                commonResponse.setSuccess(false);
                commonResponse.setErrMsg("退款处理中");
            } else if (refund_status.equals("NOTSURE")) {
                commonResponse.setSuccess(false);
                commonResponse.setErrMsg("未确定，需要商户原退款单号重新发起");
                refundFail = true;
            } else if (refund_status.equals("CHANGE")) {
                commonResponse.setSuccess(false);
                commonResponse.setErrMsg("转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款");
                refundFail = true;
            }

            //退款查询结果状态如果不是PROCESSING或SUCCESS，即将后台订单状态改为：退款失败
            if (refundFail) {
                commonResponse = orderThriftClientServiceImpl.updateOrderToRefundFailed(oldRefundQueryResponse.getOut_refund_no());
            }

        } else {
            commonResponse.setSuccess(false);
            commonResponse.setErrMsg("微信查询退款接口请求结果返回失败, transaction_id=" + refundQueryResponse.getTransaction_id());
        }

        logger.info("wechat refund end, result:::{}", TZBeanUtils.describe(commonResponse));
        return commonResponse ;
    }

    /**
     * 处理已成功退款的订单
     * @param refundQueryResponse
     * @param commonResponse
     * @throws Exception
     */
    private void dealRefundSuccessOrder(RefundQueryResponseEntity refundQueryResponse, CommonResponse commonResponse) throws Exception {

        String orderId = refundQueryResponse.getOut_refund_no();
        try {
            wxRefundDao.lock(refundQueryResponse);
            RefundResultBean refundResultBean = new RefundResultBean() ;
            refundResultBean.setOrderNum(orderId);
            refundResultBean.setGateType(GateType.WeChatpay.getGateCode());
            refundResultBean.setTraceNum(refundQueryResponse.getTransaction_id());
            refundResultBean.setRefundAmount(refundQueryResponse.getRefund_fee());
            logger.info("notify order start, request: {}", TZBeanUtils.describe(refundResultBean));
            boolean result = thirdPaymentThriftService.notifyOrderRefund(refundResultBean);
			logger.info("notify order end, response: {}", result);
			if(result){
				refundQueryResponse.setRecord_status("T");
                refundQueryResponse.setLast_modify_time(new DateTime());
                logger.info("update record confirm status, request: {}", TZBeanUtils.describe(refundQueryResponse));
                boolean upt = wxRefundDao.updateRecordStatus(refundQueryResponse);
                logger.info("update record confirm status, response: {}", upt);
                commonResponse.setSuccess(true);
                commonResponse.setErrMsg("");
			}else{
				commonResponse.setErrMsg("thirdPaymentThriftService notifyOrderRefund fail...");
				commonResponse.setSuccess(false);
				throw new Exception(commonResponse.getErrMsg());
			}
        } catch (Exception e) {
            logger.error(TZMarkers.p2, "wechat refund error", e);
            commonResponse.setSuccess(false);
            commonResponse.setErrMsg("微信退款成功,但业务更改出现异常");
        }
    }

    @Override
    public RefundQueryResponseEntity preproceedForRefund(String refundId) {
    	RefundQueryResponseEntity entity = wxRefundDao.selectByRefundId(refundId);
        return entity ;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED, rollbackFor=RuntimeException.class)
    private void recordQueryRefundResult(RefundQueryResponseEntity oldRefundQueryResponse, RefundQueryResponseEntity refundQueryResponse) {

        refundQueryResponse.setRecord_status("F");
        DateTime currentDateTime = new DateTime();
        refundQueryResponse.setLast_modify_time(currentDateTime);

        if (oldRefundQueryResponse == null) {
            refundQueryResponse.setCreate_time(currentDateTime);
            //退款记录表中没有数据记录时插入一条记录
            try {
                wxRefundDao.insert(refundQueryResponse) ;
            } catch (Exception e) {
                logger.error("insert wechat refund record into DB fail:::{}", refundQueryResponse.getRefund_id(), e);
            }
        } else {
            //退款记录表中有一条数据记录时判断记录状态,"T"表示该订单已退款成功,"F"表示该订单正在退款处理中
            logger.info("wechat refund already record, so update...");
            try {
                wxRefundDao.update(refundQueryResponse) ;
            } catch (Exception e) {
                logger.error("update wechat refund record into DB fail:::{}", refundQueryResponse.getRefund_id(), e);
            }
        }

    }

      /**
      * 解析微信响应的查询退款接口的数据包（XML）
     *
     * @param request
     * @return
     * @throws Exception
     */
     private String parseXml(String xmlResponse) throws Exception {

         StringReader stringReader = new StringReader(xmlResponse);
         InputSource inSource = new InputSource(stringReader);
         SAXReader saxReader = new SAXReader();
         Document document = saxReader.read(inSource);

         Element root = document.getRootElement();
        // 得到根元素的所有子节点
         List<Element> elementList = root.elements();

         System.out.println(elementList.toString());

         StringBuffer sBuffer = new StringBuffer();
         sBuffer.append("<xml>");

         List<String> keyList = new ArrayList<String>();
        // 遍历所有子节点
        for (Element e : elementList) {
            String key = e.getName();
            if (key.startsWith("out_refund_no_")) {
                key = "out_refund_no";
            } else if (key.startsWith("refund_fee_")) {
                key = "refund_fee";
            } else if (key.startsWith("refund_id_")) {
                key = "refund_id";
            } else if (key.startsWith("refund_status_")) {
                key = "refund_status";
            } else if (key.startsWith("refund_channel_")) {
                key = "refund_channel";
            }
            if(keyList.contains(key)){
                continue;
            }
            keyList.add(key);
            sBuffer.append("<").append(key).append(">");
            sBuffer.append(e.getText());
            sBuffer.append("</").append(key).append(">");
        }
         sBuffer.append("</xml>");
         System.out.println(sBuffer.toString());

        // 释放资源
        stringReader.close();
        stringReader = null;

        return sBuffer.toString();
     }

	@Override
	public List<RefundQueryResponseEntity> searchUnProceedRefundRecord() {
		return wxRefundDao.searchUnProceedRefundRecord() ;
	}
	
	
	public static void main(String args[]){
		RefundQueryRequestEntity refundQuery = WxPayParmConvert.buildWxRefundQueryParam("2005570785201512080095909628");
        String refundQueryXml = WxPayParmConvert.buildXMlParamByEntity(refundQuery, RefundQueryRequestEntity.class, "xml");
        logger.info("wechat refund query request ---> {}", refundQueryXml);

        //调用微信查询退款接口
        String xmlResponse = HttpUtil.postXml(WX_REFUND_QUERY_URL, refundQueryXml);
        System.out.println(xmlResponse) ;
	}

}
