package com.ztravel.product.back.piece.visa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.CareerType;
import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.common.BasicInfo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaDetailInfo;
import com.ztravel.product.po.pieces.visa.VisaMaterial;
import com.ztravel.product.po.pieces.visa.VisaProcess;
import com.ztravel.product.po.pieces.visa.VisaProduct;


@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = {PieceProductBackTestConfig.class})
public class VisaDaoImplTest extends AbstractJUnit4SpringContextTests{

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaDaoImplTest.class);

	@Resource
	private IVisaProductDao visaProductDaoImpl;
	@Test
	public void saveTest(){

		PieceProduct product = buildVisaProduct();
		try {
			visaProductDaoImpl.save(product);
		} catch (Exception e) {
			LOGGER.error("error:{[]}", e);
			e.printStackTrace();
		}
	}

//	@Test
	public void search(){
		@SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
		map.put("basicInfo.pname", "test1");
		try {
			List<VisaProduct> products = visaProductDaoImpl.selectByMap(map);
			if(null != products){
				System.out.println(products.size());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void delete(){
		@SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
		map.put("basicInfo.pname", "test1");
		try {
			Boolean res = visaProductDaoImpl.delete(Nature.VISA, "56f0fdcce4b0a10f67d67bee");
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	@Test
	public VisaProduct buildVisaProduct(){
		VisaProduct product = new VisaProduct();

		BasicInfo basic = new BasicInfo();
		basic.setPname("测试用手动构造数据");
		basic.setType(PieceType.VISA);
		List<String> toCity = new ArrayList<>();
		List<String> toCountry = new ArrayList<>();
		List<String> toCon = new ArrayList<>();
		toCon.add("亚洲");
		toCountry.add("日本");
		toCity.add("东京");
		basic.setToCity(toCity);
		basic.setToContinent(toCon);
		basic.setToCountry(toCountry);
		product.setBasicInfo(basic);

		VisaDetailInfo detail = new VisaDetailInfo();
		List<String> images = new ArrayList<>();
		images.add("569f32e8e4b0d9e9e7c3f9bc");
		detail.setImages(images);
		detail.setIsInterview(true);
		detail.setScope("测试用范围啦啦啦啦");
		detail.setStayTime("测试只能停留2天");
		detail.setTimes("最多三次");
		detail.setValidate("有效期至2016年6月1日");
		product.setDetailInfo(detail);
		List<PriceInfo> price = new ArrayList<>();
		PriceInfo priceInfo = new PriceInfo();
		priceInfo.setAdultCost(10.00);
		priceInfo.setChildCost(5.00);
		priceInfo.setAdultPrice(25.00);
		priceInfo.setChildPrice(10.00);
		priceInfo.setHasChildPrice(true);
		priceInfo.setId("test123456");
		price.add( priceInfo);
		product.setPriceInfos(price);
		Map<String,List<VisaMaterial>> materies = new HashMap<>();
		List<VisaMaterial> visms1 = new ArrayList<>();
		VisaMaterial ma1 = new VisaMaterial();
		ma1.setContent("cecececece");
		ma1.setImages(images);
		ma1.setTitle("在校学生测试用签证材料");
		visms1.add(ma1);
		materies.put(CareerType.STUDENT.name(), visms1);
		VisaMaterial ma2 = new VisaMaterial();
		List<VisaMaterial> visms2 = new ArrayList<>();
		ma2.setContent("cecececece");
		ma2.setImages(images);
		ma2.setTitle("自由职业测试用签证材料");
		visms2.add(ma2);
		materies.put(CareerType.FREE.name(), visms2);
		VisaMaterial ma3 = new VisaMaterial();
		List<VisaMaterial> visms3 = new ArrayList<>();
		ma3.setContent("cecececece");
		ma3.setImages(images);
		ma3.setTitle("学龄前儿童测试用签证材料");
		visms3.add(ma3);
		materies.put(CareerType.CHILD.name(), visms3);
		VisaMaterial ma4 = new VisaMaterial();
		List<VisaMaterial> visms4 = new ArrayList<>();
		ma4.setContent("cecececece");
		ma4.setImages(images);
		ma4.setTitle("在职测试用签证材料");
		visms4.add(ma4);
		materies.put(CareerType.EMPLOYED.name(), visms4);
		VisaMaterial ma5 = new VisaMaterial();
		List<VisaMaterial> visms5 = new ArrayList<>();
		ma5.setContent("cecececece");
		ma5.setImages(images);
		ma5.setTitle("退休人员签证材料");
		visms5.add(ma5);
		materies.put(CareerType.RETIRE.name(), visms5);
		product.setMaterias(materies);




		List<VisaProcess> process = new ArrayList<VisaProcess>();
		VisaProcess pro = new VisaProcess();
		pro.setContent("cecececece流程");
		pro.setImages(images);
		pro.setTitle("下单支付");
		process.add(pro);

		VisaProcess pro1 = new VisaProcess();
		pro1.setContent("cecececece流程");
		pro1.setImages(images);
		pro1.setTitle("准备材料");
		process.add(pro1);

		VisaProcess pro2 = new VisaProcess();
		pro2.setContent("cecececece流程");
		pro2.setImages(images);
		pro2.setTitle("出签及配送");
		process.add(pro2);
		product.setProcesses(process);

		Map<AdditionalRule, String> additionalInfos = new HashMap<>();
		additionalInfos.put(AdditionalRule.FEE_INCLUDE, "费用包含测试信息");
		additionalInfos.put(AdditionalRule.FEE_NOT_INCLUDE, "费用不包含测试信息");
		product.setAdditionalInfos(additionalInfos);

		product.setPid("VS0001");
		product.setStatus(ProductStatus.RELEASED);
		product.setNature(Nature.VISA);
		product.setDestinationType(DestinationType.INTERNATIONAL);
		product.setCreator("zpftest");

		return product;
	}

	public UnVisaProduct buildUnVisaProduct(){
		return new UnVisaProduct();
	}
}
