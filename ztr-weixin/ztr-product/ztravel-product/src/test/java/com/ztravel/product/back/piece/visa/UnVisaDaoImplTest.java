package com.ztravel.product.back.piece.visa;

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
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.test.SpringJUnit4ClassRunnerWithLog;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.po.pieces.common.BasicInfo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;

@RunWith(SpringJUnit4ClassRunnerWithLog.class)
@ContextConfiguration(classes = {PieceProductBackTestConfig.class})
public class UnVisaDaoImplTest  extends AbstractJUnit4SpringContextTests{
private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaDaoImplTest.class);

	@Resource
	private IUnVisaProductDao unVisaProductDaoImpl;

//	@Test
	public void saveTest(){
		PieceProduct product = buildVisaProduct();
		try {
			unVisaProductDaoImpl.save(product);
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
			List<UnVisaProduct> products = unVisaProductDaoImpl.selectByMap(map);
			if(null != products){
				System.out.println(products.size());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void delete(){
		@SuppressWarnings("unchecked")
		Map<String,String> map = new HashedMap();
		map.put("basicInfo.pname", "test1");
		try {
			Boolean res = unVisaProductDaoImpl.delete(Nature.VISA, "56f10c53e4b012eebee7e810");
			System.out.println(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public UnVisaProduct buildVisaProduct(){
		UnVisaProduct product = new UnVisaProduct();
		BasicInfo basic = new BasicInfo();
		basic.setPname("test1");
		basic.setType(PieceType.VISA);
		product.setBasicInfo(basic);
		return product;
	}

}
