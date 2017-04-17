package com.ztravel.reuse.order.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.util.DateUtils;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.PieceType;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.order.service.IPieceProductBookConvertReuseService;
import com.ztravel.reuse.product.entity.ProductBookVo;
import com.ztravel.reuse.product.service.IUnvisaProductReuseService;
import com.ztravel.reuse.product.service.IVisaProductReuseService;


@Service
public class PieceProductBookConvertReuseService implements IPieceProductBookConvertReuseService {

	@Resource
	private IVisaProductDao visaProductDaoImpl;

	@Resource
	private IUnVisaProductDao unVisaProductDaoImpl;
	
	@Resource
	private IVisaProductReuseService visaProductReuseService;
	
	@Resource
	private IUnvisaProductReuseService unvisaProductReuseService;
	
	

	private static DecimalFormat  moneyFormat = new DecimalFormat("#####0.00");



	@Override
	public ProductBookVo pieceProduct2BookVo(DetailToOrderCriteria criteria)throws Exception {
		ProductBookVo bookVo = null;
		if(criteria.getProductNature().equals(PieceType.VISA.name())){
			VisaProduct visaProduct = visaProductDaoImpl.getProductById(criteria.getProductId());
			bookVo = visaProduct2BookVo(criteria, visaProduct);
		}else{
			UnVisaProduct unVisaProduct = unVisaProductDaoImpl.getProductById(criteria.getProductId());
			bookVo = unVisaProduct2BookVo(criteria, unVisaProduct);
		}
		return bookVo;
	}

	public ProductBookVo visaProduct2BookVo(DetailToOrderCriteria criteria,VisaProduct product)throws Exception{
		ProductBookVo bookVo = new ProductBookVo();
		Assert.isTrue(product != null, "产品不存在");
		basicInfoConvert(bookVo,product,criteria);
		bookVo.setProductTitle(product.getBasicInfo().getPname());
		setAdultAndChildPriceInfo(bookVo,criteria,product.getPriceInfos());
		bookVo.setAdditionalInfos(product.getAdditionalInfos());
		//设置产品第一张图片ID
		if(!CollectionUtils.isEmpty(product.getDetailInfo().getImages())){
			bookVo.setFirstImageId(product.getDetailInfo().getImages().get(0));
		}
		return bookVo;
	}
	public ProductBookVo unVisaProduct2BookVo(DetailToOrderCriteria criteria,UnVisaProduct product)throws Exception{
		ProductBookVo bookVo = new ProductBookVo();
		Assert.isTrue(product != null, "产品不存在");
		basicInfoConvert(bookVo,product,criteria);
		bookVo.setProductTitle(product.getBasicInfo().getPname());
		setAdultAndChildPriceInfo(bookVo,criteria,product.getPriceInfos());
		bookVo.setAdditionalInfos(product.getAdditionalInfos());
		//设置产品第一张图片ID
		if(!CollectionUtils.isEmpty(product.getDetailInfo().getImages())){
			bookVo.setFirstImageId(product.getDetailInfo().getImages().get(0));
		}
		return bookVo;
	}

	public void basicInfoConvert(ProductBookVo bookVo,PieceProduct product,DetailToOrderCriteria criteria) throws Exception{
		//设置产品信息
		bookVo.setProductId(product.getId().toString());
		bookVo.setProductNo(product.getPid());
		bookVo.setNature(product.getBasicInfo().getType().name());
		bookVo.setProductType(product.getNature().getName());//产品性质
		bookVo.setDepartDay(criteria.getBookDate());
		bookVo.setIsBedTips(false);
		Integer tripDays = 0;
		bookVo.setIsDomestic(DestinationType.DOMESTIC.getName());
		bookVo.setReturnDay(DateUtils.addDate(criteria.getBookDate(), 3, 1));//非自由行产品返程日期随意设置
		bookVo.setTripDays(tripDays);
		bookVo.setCostPriceId(criteria.getCostPriceId());
		PriceInfo price;
		if(criteria.getProductNature().equals(PieceType.VISA.name())){
		  price = visaProductReuseService.getPriceByPidAndPriceId(product.getPid(), criteria.getCostPriceId());
		}else{
		 price = unvisaProductReuseService.getPriceByPidAndPriceId(product.getPid(),criteria.getCostPriceId());	
		}
		
		Assert.isTrue(null != price, "产品["+product.getPid()+"]销售价格["+criteria.getCostPriceId()+"]信息不存在");
		bookVo.setCostPriceName(price.getName());
		bookVo.setIsContainFlight(false);
		bookVo.setIsContainHotel(false);
	}
	private void setAdultAndChildPriceInfo(ProductBookVo bookVo, DetailToOrderCriteria criteria, List<PriceInfo> priceInfo ) throws Exception{
		Assert.isTrue(!CollectionUtils.isEmpty(priceInfo), "产品{"+criteria.getProductId()+"}未设置销售价信息");
		PriceInfo dayInfo = null;
		for(PriceInfo price : priceInfo){
			if(price.getId().equals(criteria.getCostPriceId())){
				dayInfo = price;
				break;
			}
		}
		Assert.isTrue(null != priceInfo, "产品{"+criteria.getProductId()+"}的销售价格信息{"+criteria.getCostPriceId()+"}不存在");
		if(null!=dayInfo)
		bookVo.setCostPriceName(dayInfo.getName());
		Double totalAdultPrice = 0d;
        Double totalChildPrice = 0d;
        Double totalSinglePrice = 0d;
        bookVo.setAdultNum(criteria.getAdultNum() == null ? 0 : criteria.getAdultNum());
        bookVo.setChildNum(criteria.getChildNum() == null ? 0 : criteria.getChildNum());
        bookVo.setSingleNum(bookVo.getAdultNum() % 2);
        if(null != dayInfo){
            Double adultPrice = dayInfo.getAdultPrice() == null ? 0d : dayInfo.getAdultPrice();
            Double childPrice = dayInfo.getChildPrice() == null ? 0d : dayInfo.getChildPrice();
            totalAdultPrice = adultPrice* bookVo.getAdultNum();
            totalChildPrice = childPrice * bookVo.getChildNum();
            bookVo.setAdultPrice(moneyFormat.format(adultPrice));
            bookVo.setChildrenPrice(moneyFormat.format(childPrice));
        }
        bookVo.setTotalAdultPrice(moneyFormat.format(totalAdultPrice));
        bookVo.setTotalChildPrice(moneyFormat.format(totalChildPrice));
        bookVo.setTotalSingleDiff(moneyFormat.format(totalSinglePrice));
        bookVo.setTotalPrice(moneyFormat.format(totalAdultPrice + totalChildPrice));
	}
	public DetailToOrderCriteria getDetailToOrderCriteria(ProductBookItem productBookItem){
		DetailToOrderCriteria criteria = new DetailToOrderCriteria();
		criteria.setProductId(productBookItem.getProductId());
		criteria.setBookDate(productBookItem.getBookDate());
        criteria.setChildNum(productBookItem.getChildrenNum());
        criteria.setAdultNum(productBookItem.getAdaultNum());
		criteria.setPackageId(productBookItem.getPackageId());
		criteria.setProductNature(productBookItem.getNature());
		criteria.setCostPriceId(productBookItem.getPriceId());
		return  criteria;
	}


}
