package com.ztravel.paygate.web.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.dao.rdbms.SequenceGenerator;
import com.ztravel.paygate.api.alipay.AlipayParser;
import com.ztravel.paygate.api.alipay.model.RefundProfitResultModel;
import com.ztravel.paygate.api.alipay.model.RefundResultModel;
import com.ztravel.paygate.core.dao.IAlipayDao;
import com.ztravel.paygate.core.dao.IAlipayRefundDao;
import com.ztravel.paygate.core.enums.PayState;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.enums.RefundState;
import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayExample;
import com.ztravel.paygate.core.po.gen.AlipayExample.Criteria;
import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.core.po.gen.AlipayRefundExample;
import com.ztravel.paygate.web.service.IAlipayService;
import com.ztravel.paygate.web.util.EnvArgs.Const;

/**
 * 支付宝支付接口
 * 
 * @author dingguangxian
 * 
 */
@Service("paygate_alipay_service")
public class AlipayService implements IAlipayService {
	private static Logger logger = LoggerFactory.getLogger(AlipayService.class);
	private static final String ALIPAY_SEQUENCE_NAME = "paygate_alipay_seq";

	@Resource
	private SequenceGenerator sequenceGenerator;
	@Resource
	private IAlipayDao entityDao;

	@Resource
	private IAlipayRefundDao refundDao;

	@Override
	public Alipay createEntity() throws Exception {
		Alipay entity = new Alipay();
		entity.setAlipayId(sequenceGenerator.getNextSeq(ALIPAY_SEQUENCE_NAME, 20));
		entity.setCreateTime(new Date());
		return entity;
	}

	@Override
	public Alipay findById(String id) throws SQLException {

		logger.info("findById:{}", id);
		AlipayExample example = new AlipayExample();
		Criteria criteria = example.createCriteria();
		criteria.andAlipayIdEqualTo(id);
		List<Alipay> list = entityDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			// 检索到多条记录的情况下,只返回签名成功的记录

			logger.error("more than one entity found[id={}].", id);
			throw new RuntimeException("more than one entity found[id=" + id + "].");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public Alipay insertSelective(Alipay entity) throws SQLException {
		entityDao.insertSelective(entity);
		return entity;
	}

	@Override
	public void updateSelectiveById(Alipay entity) throws SQLException {
		entityDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public Alipay searchByOrderNum(String orderNum) throws SQLException {
		logger.info("searchByOrderNum:{}", orderNum);
		AlipayExample example = new AlipayExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrderNumEqualTo(orderNum);
		List<Alipay> list = entityDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			// 检索到多条记录的情况下,只返回签名成功的记录
			for (Alipay record : list) {
				if (PaygateError.SUCCESS.code().equals(record.getSignRetCode())) {
					return record;
				}
			}

			logger.error("more than one entity found[orderNum={}].", orderNum);
			throw new RuntimeException("more than one entity found[orderNum=" + orderNum + "].");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public Alipay searchPaySuccessRecord(String orderNum) throws SQLException {
		logger.info("searchPaySuccessRecord:{}", orderNum);
		AlipayExample example = new AlipayExample();
		Criteria criteria = example.createCriteria();
		criteria.andOrderNumEqualTo(orderNum);
		criteria.andPayStateEqualTo(PayState.SUCCESS.name());
		criteria.andValsignRetCodeEqualTo(PaygateError.SUCCESS.code());
		List<Alipay> list = entityDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			logger.error("more than one entity found[orderNum={}].", orderNum);
			// throw new RuntimeException("more than one entity found[orderNum="
			// + orderNum + "].");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	public Alipay searchByOrderNumAndTraceNum(String orderNum, String traceNum) throws SQLException {
		logger.info("searchByOrderNumAndTransNum:{},{}", orderNum, traceNum);
		AlipayExample example = new AlipayExample();
		Criteria criteria = example.createCriteria();
		//criteria.andOrderNumEqualTo(orderNum);
		criteria.andTraceNumEqualTo(traceNum);
		List<Alipay> list = entityDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			// never cause.
			logger.error("more than one entity found[orderNum={},traceNum={}].", orderNum, traceNum);
			throw new RuntimeException("more than one entity found[orderNum=" + orderNum + ",traceNum=" + traceNum + "].");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public AlipayRefund createRefundEntity() throws Exception {
		AlipayRefund entity = new AlipayRefund();
		entity.setAlipayRefundId(sequenceGenerator.getNextSeq(ALIPAY_SEQUENCE_NAME, 20));
		entity.setCreateTime(new Date());
		return entity;
	}

	@Override
	public AlipayRefund findByRefundId(String refundId) throws SQLException {
		logger.info("findByRefundId:{}", refundId);
		AlipayRefundExample example = new AlipayRefundExample();
		com.ztravel.paygate.core.po.gen.AlipayRefundExample.Criteria criteria = example.createCriteria();
		criteria.andAlipayRefundIdEqualTo(refundId);
		List<AlipayRefund> list = refundDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			// 检索到多条记录的情况下,只返回签名成功的记录

			logger.error("more than one entity found[id={}].", refundId);
			throw new RuntimeException("more than one entity found[id=" + refundId + "].");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public AlipayRefund insertSelective(AlipayRefund entity) throws SQLException {
		refundDao.insertSelective(entity);
		return entity;
	}

	@Override
	public void updateSelectiveById(AlipayRefund entity) throws SQLException {
		refundDao.updateByPrimaryKeySelective(entity);
	}

	@Override
	public AlipayRefund searchByRefundNum(String refundNum) throws SQLException {
		logger.info("searchByRefundNum:{}", refundNum);
		AlipayRefundExample example = new AlipayRefundExample();
		AlipayRefundExample.Criteria criteria = example.createCriteria();
		criteria.andRefundNumEqualTo(refundNum);
		example.setOrderByClause("create_time desc");
		List<AlipayRefund> list = refundDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			// 检索到多条记录的情况下,只返回签名成功的记录
			for (AlipayRefund record : list) {
				if (PaygateError.SUCCESS.code().equals(record.getReqRetCode())) {
					return record;
				}
			}
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}

	@Override
	public AlipayRefund searchRefundSuccessRecord(String refundNum) throws SQLException {
		logger.info("searchRefundSuccessRecord:{}", refundNum);
		AlipayRefundExample example = new AlipayRefundExample();
		AlipayRefundExample.Criteria criteria = example.createCriteria();
		criteria.andRefundNumEqualTo(refundNum);
		criteria.andRefundStateEqualTo(RefundState.SUCCESS.name());
		criteria.andValsignRetCodeEqualTo(PaygateError.SUCCESS.code());
		List<AlipayRefund> list = refundDao.selectByExample(example);
		if (list != null && list.size() > 1) {
			logger.error("more than one entity found[refundNum={}].", refundNum);
			// throw new RuntimeException("more than one entity found[orderNum="
			// + orderNum + "].");
		}
		return list == null || list.size() == 0 ? null : list.get(0);
	}
	
	@Override
	public void recordRefundNotify(String alipayRefundId,
			Map<String, String> responseData) throws Exception{

		String refundDetails = responseData.get("result_details");
		List<RefundResultModel> list = AlipayParser.parserRefundResultDetails(refundDetails);
		RefundResultModel model = null;
		if (list != null && list.size() == 1) {
			model = list.get(0);
		}
		//model
		List<RefundProfitResultModel> profitList = model.getRefundProfits();
		if(profitList != null){
			for(RefundProfitResultModel m : profitList){
				if(!"SUCCESS".equals(m.getStatus())){
					//TODO 退分润失败，通知手动处理
					/*PaygateMonitor.sendMessage(getClass(), "退分润失败，请核查", null, refundDetails, TZBeanUtils.describe(m), responseData);*/
				}
			}
		}
		AlipayRefund record = new AlipayRefund();
		record.setAlipayRefundId(alipayRefundId);
		record.setTransRetMsg(model == null ? null : model.getTransRetMsg());
		record.setRefundRetMsg(model == null ? null : model.getRefundRetMsg());
		record.setSuccessNum(Short.parseShort(responseData.get("success_num")));
		record.setResultDetails(refundDetails);
		record.setUnfreezeDetails(responseData.get("unfreeze_details"));
		record.setRespTime(new Date());
		refundDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Alipay> searchUnProceedPayRecord() throws Exception {
		AlipayExample example = new AlipayExample();
		Criteria criteria = example.createCriteria();
		criteria.andPayStateEqualTo(PayState.SUCCESS.name()) ;
		criteria.andValsignRetCodeEqualTo(PaygateError.SUCCESS.code()) ;
		criteria.andConfirmResultNotEqualTo(Const.CLIENT_CONFIRM_SUCCESS_FLAG);
		List<Alipay> records = entityDao.selectByExample(example);
		return records ;
	}
	
	@Override
	public List<AlipayRefund> searchUnProceedRefundRecord() throws Exception {
		AlipayRefundExample example = new AlipayRefundExample();
		com.ztravel.paygate.core.po.gen.AlipayRefundExample.Criteria criteria = example.createCriteria();
		criteria.andRefundStateEqualTo(RefundState.SUCCESS.name()) ;
		criteria.andValsignRetCodeEqualTo(PaygateError.SUCCESS.code()) ;
		criteria.andConfirmResultNotEqualTo(Const.CLIENT_CONFIRM_SUCCESS_FLAG);
		List<AlipayRefund> records = refundDao.selectByExample(example);
		return records ;
	}

}
