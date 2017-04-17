//package com.ztravel.order.front.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//import com.ztravel.common.entity.AdditionalProduct;
//import com.ztravel.common.entity.OpConfirmPDFEntity;
//import com.ztravel.common.entity.PassengerInfo;
//import com.ztravel.common.entity.ProductFlightInfo;
//import com.ztravel.common.enums.AdditionalRule;
//import com.ztravel.common.view.AbstractPdfView4Itext5;
//import com.ztravel.common.view.OpConfirmPdfView;
//import com.ztravel.order.entity.OrderPassenger;
//import com.ztravel.order.front.service.IOrderService;
//import com.ztravel.order.front.util.PriceUtil;
//import com.ztravel.order.front.wo.AdditionalProductWo;
//import com.ztravel.order.front.wo.OrderDetailWo;
//import com.ztravel.order.front.wo.OrderProductWo;
//import com.ztravel.order.front.wo.OrderWo;
//
//
//@Controller
//@RequestMapping("/opConfirm")
//public class OpConfirmController {
//	
//	//private static final Logger LOGGER = RequestIdentityLogger.getLogger(OpConfirmController.class);
//	
//	@Resource
//	private IOrderService orderService ;
//	
//	@RequestMapping("/download/{orderId}")
//	public ModelAndView download(@PathVariable String orderId, HttpServletResponse response) throws Exception{
//		ModelAndView ret = new ModelAndView() ;
//
//		OrderDetailWo orderDetail = orderService.selectOrderById(orderId, "") ;
//		List<OrderPassenger> passengers = orderService.getOrderPassenger(orderId);
//		OpConfirmPDFEntity entity = convertOrderDetailWo(orderDetail, passengers) ;
//		AbstractPdfView4Itext5 pdfView = new OpConfirmPdfView(entity) ;
//		ret.setView(pdfView);
//		return ret ;
//	}
//	
//	private static OpConfirmPDFEntity convertOrderDetailWo(OrderDetailWo orderDetail, List<OrderPassenger> passengers){
//		OpConfirmPDFEntity entity = new OpConfirmPDFEntity() ;
//		OrderWo order = orderDetail.getOrder() ;
//		OrderProductWo product = orderDetail.getProduct() ;
//		entity.setOrderNo(order.getOrderNo());
//		entity.setpName(product.getProductTitle());
//		entity.setOrderDate(order.getCreateDate());
//		entity.setPlayDate(product.getBookDate());
//		entity.setOrderAmount(order.getTotalPrice());
//		
//		List<PassengerInfo> ps = new ArrayList<PassengerInfo>() ;
//		for(int i=0;i<passengers.size(); i++){
//			PassengerInfo pi = new PassengerInfo() ;
//			OrderPassenger op = passengers.get(i) ;
//			pi.setBirthday(op.getBirthday());
//			pi.setCountry(op.getCountry());
//			pi.setCredentialNum(op.getCredentialNum());
//			pi.setCredentialsDeadLine(op.getCredentialDeadLine());
//			pi.setCredentialType(op.getCredentialType());
//			pi.setFirstName(op.getFirstName());
//			pi.setFirstNameEn(op.getFirstEnName());
//			pi.setGender(op.getGender());
//			pi.setLastName(op.getLastName());
//			pi.setLastNameEn(op.getLastEnName());
//			pi.setPassengerEnName(op.getFirstEnName() + "/" + op.getLastEnName());
//			pi.setPassengerName(op.getFirstName() + "/" + op.getLastName());
//			pi.setType(op.getPassengerType());
//			ps.add(pi) ;
//		}
//		entity.setPassengers(ps);
//		List<AdditionalProductWo> additionalProductWos = orderDetail.getAdditionalProducts() ;
//		
//		if(additionalProductWos != null){
//		List<AdditionalProduct> additionalProducts = new ArrayList<AdditionalProduct>();
//	        for (AdditionalProductWo additionalProductWo : additionalProductWos) {
//	            AdditionalProduct additionalProduct = new AdditionalProduct();
//	            additionalProduct.setName(additionalProductWo.getName());
//	            additionalProduct.setPrice(PriceUtil.yuan2cent(additionalProductWo.getPrice()));
//	            additionalProduct.setNum(additionalProductWo.getNum());
//	            additionalProduct.setTotalPrice(PriceUtil.yuan2cent(additionalProductWo.getTotalPrice()));
//	            additionalProducts.add(additionalProduct);
//	        }
//			
//			entity.setAdditionalProducts(additionalProducts);
//		}
//		
//		List<ProductFlightInfo> pfi = new ArrayList<ProductFlightInfo>() ;
//		if(product.getGoFlightList() != null){
//			pfi.addAll(product.getGoFlightList()) ;
//		}
//		if(product.getMidlFlightList() != null){
//			pfi.addAll(product.getMidlFlightList()) ;
//		}
//		if(product.getBackFlightList() != null){
//			pfi.addAll(product.getBackFlightList()) ;
//		}
//		
//		entity.setFlights(pfi);
//		
//		entity.setHotels(product.getHotelList());
//		Map<String, String> additionalInfo = product.getAdditionalInfos() ;
//		
//		entity.setFeesContain(additionalInfo.get(AdditionalRule.FEE_INCLUDE.toString()));
//		entity.setFeesNotContain(additionalInfo.get(AdditionalRule.FEE_NOT_INCLUDE.toString()));
//		entity.setFreeItem(additionalInfo.get(AdditionalRule.GIFT_ITEMS.toString()));
//		entity.setRefundPolicy(additionalInfo.get(AdditionalRule.REFUND_POLICY.toString()));
//		return entity ;
//	}
//}
