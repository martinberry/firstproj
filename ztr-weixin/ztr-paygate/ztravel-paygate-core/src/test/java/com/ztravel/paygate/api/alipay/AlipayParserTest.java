package com.ztravel.paygate.api.alipay;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import com.ztravel.paygate.api.alipay.model.QueryResultModel;
import com.ztravel.paygate.api.alipay.model.RefundResultModel;

public class AlipayParserTest {

	@Test
	public void testParseRefundResultDetails() throws Exception {
		String details = "2014030512961512^0.01^SUCCESS^false^null$bycoa@10106266.com^2088101964737404^0.00^SUCCESS";
		List<RefundResultModel> resultList = AlipayParser.parserRefundResultDetails(details);
		Assert.isTrue(resultList.size() == 1);
		RefundResultModel model = resultList.get(0);
		Assert.isTrue("2014030512961512".equals(model.getTraceNum()));
		Assert.isTrue(1 == model.getAmount());
		Assert.isTrue("SUCCESS".equals(model.getTransRetMsg()));
		Assert.isTrue("bycoa@10106266.com".equals(model.getSellerEmail()));
		Assert.isTrue("2088101964737404".equals(model.getSellerId()));
		Assert.isTrue(0 == model.getRefundAmount());
		Assert.isTrue("SUCCESS".equals(model.getRefundRetMsg()));
	}

	@Test
	public void testParseQuerySuccessRequest() throws Exception {
		Resource resource = new ClassPathResource("/alipay/query-success-result.xml");
		StringWriter strWriter = new StringWriter();
		IOUtils.copy(resource.getInputStream(), strWriter);
		QueryResultModel model = AlipayParser.parseQueryResult(strWriter.toString());
		Assert.isTrue("T".equals(model.is_success));
		Assert.isTrue("MD5".equals(model.sign_type));
		Assert.isNull(model.error);
		Assert.isTrue(model.requestDatas().size() == 3);
		Assert.isTrue(model.tradeDatas().size() == 19);
		Assert.isTrue("2010073000030344".equals(model.requestDatas().get("trade_no")));
		Assert.isTrue("1280463992953".equals(model.tradeDatas().get("out_trade_no")));
	}

	@Test
	public void testParseQueryFailResult() throws Exception {

		Resource resource = new ClassPathResource("/alipay/query-fail-result.xml");
		StringWriter strWriter = new StringWriter();
		IOUtils.copy(resource.getInputStream(), strWriter);
		QueryResultModel model = AlipayParser.parseQueryResult(strWriter.toString());
		Assert.isTrue("F".equals(model.is_success));
		Assert.isTrue("TRADE_IS_NOT_EXIST".equals(model.error));
		Assert.isNull(model.sign_type);
		Assert.isTrue(model.requestDatas().size() == 0);
		Assert.isTrue(model.tradeDatas().size() == 0);
	}

}
