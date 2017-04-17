package com.ztravel.order.back.vo;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.CollectionUtils;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.entity.PassengerInfo;
import com.ztravel.common.html.HTMLUtil;
import com.ztravel.common.pdf.PDFUtil;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.reuse.product.entity.ProductFlightInfo;
import com.ztravel.reuse.product.entity.ProductHotelInfo;

/**
 * 
 * @author wanhaofan
 * implement of itext5
 *
 */
public class OpConfirmPdfView extends AbstractPdfView4Itext5 {
	
	private OpConfirmPDFEntity entity ;
	
	//private static Image logo = null ;
	private static final Paragraph br = PDFUtil.getBr() ;
	
	private static final PdfPCell blankCell = new PdfPCell() ;
	
	//private static final String LOGO = "/logo.png" ;
	
	private static final String TITLE = "行程确认单" ;
	
	private static final String PASSENGER_TITLE = "旅客信息" ;
	
	private static final String FLIGHT_TITLE = "机票信息" ;
	
	private static final String HOTEL_TITLE = "酒店信息" ;
	
	private static final String ADDITIONAL_TITLE = "附加产品" ;
	
	private static final String PRICE_TITILE = "费用说明" ;
	
	private static final String FEES_CONTAIN = "费用包含" ;
	
	private static final String FEES_NOT_CONTAIN = "费用不含" ;
	
	private static final String FREE_ITEM = "赠送项目" ;
	
	private static final String REFUND_POLICY = "退改政策" ;
	
	private static final String IMPORTANT_1 = "重要声明:" ;
	
	private static final String IMPORTANT_2 = "收到本行程确认单表示我们和您之间的合同已经成立。" ;
	
	private static final String IMPORTANT_3 = "感谢您的理解,出行可能会因战争,政治,天气等原因发生改变。" ;
	
	public OpConfirmPdfView(OpConfirmPDFEntity entity){
		super() ;
		this.entity = entity ;
	}
	
//	static{
//		InputStream is = OpConfirmPdfView.class.getResourceAsStream(LOGO) ;
//		try {
//			logo = Image.getInstance(IOUtil.readAsByteArray(is));
//		} catch (BadElementException | IOException e) {
//			LOGGER.error(e.getMessage(), e);
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				LOGGER.error(e.getMessage(), e);
//			}
//		}
//	}
	
	/**
	 * Subclasses must implement this method to build an iText PDF document,
	 * given the model. Called between {@code Document.open()} and
	 * {@code Document.close()} calls.
	 * <p>Note that the passed-in HTTP response is just supposed to be used
	 * for setting cookies or other HTTP headers. The built PDF document itself
	 * will automatically get written to the response after this method returns.
	 * @param model the model Map
	 * @param document the iText Document to add elements to
	 * @param writer the PdfWriter to use
	 * @param request in case we need locale etc. Shouldn't look at attributes.
	 * @param response in case we need to set cookies. Shouldn't write to it.
	 * @throws Exception any exception that occured during document building
	 * @see com.lowagie.text.Document#open()
	 * @see com.lowagie.text.Document#close()
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
//		logo.scaleToFit(76f, 76f);
//		logo.setAbsolutePosition(document.right() - 76f, document.top() - 30f);
//		document.add(logo) ;
		
		Paragraph title = PDFUtil.getParagraphAlignCenter(new Chunk(TITLE,PDFUtil.fontChinese_big_bold)) ;
		document.add(title);
		//换行
		document.add(br) ;
		document.add(br) ;
		
		//设置订单基本信息
		createBasicInfo(document) ;
		//设置订单基本信息结束
		
		//设置旅客信息开始
		if(!CollectionUtils.isEmpty(entity.getPassengers())){
			createPassengerInfo(document) ;
		}
		//设置旅客信息结束

		//设置机票信息开始
		if(!CollectionUtils.isEmpty(entity.getFlights())){
			createFlightInfo(document) ;
		}
		//设置机票信息结束
		
		//设置酒店信息开始
		if(!CollectionUtils.isEmpty(entity.getHotels())){
			createHotelInfo(document) ;
		}
		//设置酒店信息结束
		
		//设置附加产品信息开始
		if(!CollectionUtils.isEmpty(entity.getAdditionalProducts())){
			createAdditionalProductInfo(document) ;
		}
		//设置附加产品信息结束
		
		//设置费用说明开始
		createPriceInfo(document) ;
		//设置费用说明结束
		
		//重要声明开始
		Paragraph importantInfo = PDFUtil.getParagraph(new Chunk(IMPORTANT_1,PDFUtil.fontChinese_normal_bold)) ;
		Paragraph importantInfo1 = PDFUtil.getParagraph(new Chunk(IMPORTANT_2,PDFUtil.fontChinese_normal)) ;
		Paragraph importantInfo2 = PDFUtil.getParagraph(new Chunk(IMPORTANT_3,PDFUtil.fontChinese_normal)) ;
		importantInfo.setIndentationLeft(55f);
		importantInfo1.setIndentationLeft(55f);
		importantInfo2.setIndentationLeft(55f);
		document.add(importantInfo) ;
		document.add(importantInfo1) ;
		document.add(importantInfo2) ;
		document.add(br) ;
		//重要声明结束
	}
	
	private void createBasicInfo(Document document) throws DocumentException{
		PdfPTable orderBasicTable = new PdfPTable(3) ;
		PdfPCell prdNameCell = new PdfPCell(new Phrase(entity.getpName(), PDFUtil.fontChinese_small_bold)) ;
		prdNameCell.setBorderWidth(0f);
		prdNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		orderBasicTable.addCell(prdNameCell);
		
		PdfPCell orderNoCell = new PdfPCell(new Phrase("订单号: " + entity.getOrderNo(), PDFUtil.fontChinese_small_bold)) ;
		orderNoCell.setBorderWidth(0f);
		orderNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		orderBasicTable.addCell(orderNoCell);
		
		PdfPCell orderCreateDateCell = new PdfPCell(new Phrase("下单日期: " + entity.getOrderDate(), PDFUtil.fontChinese_small_bold)) ;
		orderCreateDateCell.setBorderWidth(0f);
		orderCreateDateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		orderBasicTable.addCell(orderCreateDateCell);
		
		orderBasicTable.completeRow();
		
		
		blankCell.setBorderWidth(0f);
		PdfPCell orderAmountCell = new PdfPCell(new Phrase("订单金额: ￥" + entity.getOrderAmount(), PDFUtil.fontChinese_small_bold)) ;
		orderAmountCell.setBorderWidth(0f);
		orderAmountCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		orderBasicTable.addCell(orderAmountCell);
		orderBasicTable.addCell(blankCell);
		
		PdfPCell playDateCell = new PdfPCell(new Phrase("出游日期: " + entity.getPlayDate(), PDFUtil.fontChinese_small_bold)) ;
		playDateCell.setBorderWidth(0f);
		playDateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		orderBasicTable.addCell(playDateCell);
		
		document.add(orderBasicTable) ;
		document.add(br) ;
		document.add(br) ;
	}
	
	private void createPassengerInfo(Document document) throws DocumentException{
		Paragraph passengerInfo = PDFUtil.getParagraphAlignCenter(new Chunk(PASSENGER_TITLE,PDFUtil.fontChinese_small_bold)) ;
		document.add(passengerInfo) ;
		document.add(br) ;
		
		PdfPTable passengerTable = new PdfPTable(8) ;
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("姓名"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("英文/拼音名"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("国籍"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("乘客类型"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("证件类型"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("证件号"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("证件有效期"));
		
		passengerTable.addCell(PDFUtil.getCellAlignLeftBold("出生年月"));
		
		passengerTable.completeRow();
		
		List<PassengerInfo> passengers = entity.getPassengers() ;
		
		for(int i=0;i<passengers.size();i++){
			PassengerInfo passenger = passengers.get(i) ;
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getPassengerName()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getPassengerEnName()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getCountry()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getType()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getCredentialType()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getCredentialNum()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getCredentialsDeadLine()));
			
			passengerTable.addCell(PDFUtil.getCellAlignLeftNormal(passenger.getBirthday()));
			passengerTable.completeRow();
		}
		
		document.add(passengerTable) ;
		document.add(br) ;
		document.add(br) ;
	}
	
	private void createFlightInfo(Document document) throws DocumentException{
		Paragraph flightInfo = PDFUtil.getParagraphAlignCenter(new Chunk(FLIGHT_TITLE,PDFUtil.fontChinese_small_bold)) ;
		document.add(flightInfo) ;
		document.add(br) ;
		
		PdfPTable flightTable = new PdfPTable(6) ;
		flightTable.addCell(PDFUtil.getCellAlignLeftBold("天数"));
		
		flightTable.addCell(PDFUtil.getCellAlignLeftBold("出发日期/时间"));
		
		flightTable.addCell(PDFUtil.getCellAlignLeftBold("出发机场"));
		
		flightTable.addCell(PDFUtil.getCellAlignLeftBold("到达时间"));
		
		flightTable.addCell(PDFUtil.getCellAlignLeftBold("到达机场"));
		
		flightTable.addCell(PDFUtil.getCellAlignLeftBold("航班号/机型/航空公司"));
		
		flightTable.completeRow();
		
		List<ProductFlightInfo> flights = entity.getFlights() ;
		
		for(int i=0;i<flights.size();i++){
			ProductFlightInfo flight = flights.get(i) ;
			flightTable.addCell(PDFUtil.getCellAlignLeftNormal(flight.getOffsetDays().toString()));
			
			flightTable.addCell(PDFUtil.getCellAlignLeftNormal(flight.getDepartDate() + "-" + flight.getDepartTime()));
			
			flightTable.addCell(PDFUtil.getCellAlignLeftNormal(flight.getDepartAirPort()));
			
			flightTable.addCell(PDFUtil.getCellAlignLeftNormal(flight.getArriveTime()));
			
			flightTable.addCell(PDFUtil.getCellAlignLeftNormal(flight.getArriveAirPort()));
			
			flightTable.addCell(PDFUtil.getCellAlignLeftNormal(flight.getFlightNum() + "/" + flight.getPlaneCode() + "/" + flight.getAirLineName()));
			
			flightTable.completeRow();
		}
		
		document.add(flightTable) ;
		document.add(br) ;
		document.add(br) ;
	}
	
	private void createHotelInfo(Document document) throws DocumentException{
		Paragraph hotelInfo = PDFUtil.getParagraphAlignCenter(new Chunk(HOTEL_TITLE,PDFUtil.fontChinese_small_bold)) ;
		document.add(hotelInfo) ;
		document.add(br) ;
		
		PdfPTable hotelTable = new PdfPTable(5) ;
		hotelTable.addCell(PDFUtil.getCellAlignLeftBold("入住时间"));
		
		hotelTable.addCell(PDFUtil.getCellAlignLeftBold("离店时间"));
		
		hotelTable.addCell(PDFUtil.getCellAlignLeftBold("入住酒店"));
		
		hotelTable.addCell(PDFUtil.getCellAlignLeftBold("房型"));
		
		hotelTable.addCell(PDFUtil.getCellAlignLeftBold("床型"));
		
		hotelTable.completeRow();
		
		List<ProductHotelInfo> hotels = entity.getHotels() ;
		
		for(int i=0;i<hotels.size();i++){
			ProductHotelInfo hotel = hotels.get(i) ;
			hotelTable.addCell(PDFUtil.getCellAlignLeftNormal(hotel.getCheckInDate()));
			
			hotelTable.addCell(PDFUtil.getCellAlignLeftNormal(hotel.getCheckOutDate()));
			
			hotelTable.addCell(PDFUtil.getCellAlignLeftNormal(hotel.getHotelName()));
			
			hotelTable.addCell(PDFUtil.getCellAlignLeftNormal(hotel.getRoomType()));
			
			hotelTable.addCell(PDFUtil.getCellAlignLeftNormal(hotel.getHotelType()));
			
			hotelTable.completeRow();
		}
		
		document.add(hotelTable) ;
		document.add(br) ;
		document.add(br) ;
	}
	
	private void createAdditionalProductInfo(Document document) throws DocumentException{
		Paragraph additionProductInfo = PDFUtil.getParagraphAlignCenter(new Chunk(ADDITIONAL_TITLE,PDFUtil.fontChinese_small_bold)) ;
		document.add(additionProductInfo) ;
		document.add(br) ;
		
		PdfPTable additionProductTable = new PdfPTable(5) ;
		
		additionProductTable.addCell(PDFUtil.getCellAlignLeftBold("名称"));
		
		additionProductTable.addCell(PDFUtil.getCellAlignLeftBold("单价"));
		
		additionProductTable.addCell(PDFUtil.getCellAlignLeftBold("数量"));
		
		additionProductTable.addCell(PDFUtil.getCellAlignLeftBold("合计"));
		
		additionProductTable.completeRow();
		
		
		List<AdditionalProduct> additionalProducts = entity.getAdditionalProducts() ;
		
		for(int i=0;i<additionalProducts.size();i++){
			AdditionalProduct additionalProduct = additionalProducts.get(i) ;
			additionProductTable.addCell(PDFUtil.getCellAlignLeftNormal(additionalProduct.getName()));
			
			MoneyCalculator price = new MoneyCalculator(additionalProduct.getPrice()) ;
			additionProductTable.addCell(PDFUtil.getCellAlignLeftNormal(price.fenToYuan().toString()));
			
			additionProductTable.addCell(PDFUtil.getCellAlignLeftNormal(additionalProduct.getNum()));
			
			MoneyCalculator totalPrice = new MoneyCalculator(additionalProduct.getTotalPrice()) ;
			additionProductTable.addCell(PDFUtil.getCellAlignLeftNormal(totalPrice.fenToYuan().toString()));
			
			additionProductTable.completeRow();
		}
		
		document.add(additionProductTable) ;
		document.add(br) ;
		document.add(br) ;
	}
	
	private void createPriceInfo(Document document) throws DocumentException{
		Paragraph priceInfo = PDFUtil.getParagraphAlignCenter(new Chunk(PRICE_TITILE,PDFUtil.fontChinese_small_bold)) ;
		document.add(priceInfo) ;
		document.add(br) ;
		
		PdfPTable priceTable = new PdfPTable(2) ;
		
		PdfPCell pContainCell = new PdfPCell(new Phrase(FEES_CONTAIN, PDFUtil.fontChinese_small_bold)) ;
		pContainCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pContainCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pContainCell);
		priceTable.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getFeesContain()))) ;
		priceTable.completeRow();
		
		PdfPCell pNotContainCell = new PdfPCell(new Phrase(FEES_NOT_CONTAIN, PDFUtil.fontChinese_small_bold)) ;
		pNotContainCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pNotContainCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pNotContainCell);
		priceTable.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getFeesNotContain()))) ;
		priceTable.completeRow();
		
		PdfPCell pFreeCell = new PdfPCell(new Phrase(FREE_ITEM, PDFUtil.fontChinese_small_bold)) ;
		pFreeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pFreeCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pFreeCell);
		priceTable.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getFreeItem()))) ;
		priceTable.completeRow();
		
		PdfPCell pPoliceCell = new PdfPCell(new Phrase(REFUND_POLICY, PDFUtil.fontChinese_small_bold)) ;
		pPoliceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pPoliceCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pPoliceCell);
		priceTable.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getRefundPolicy()))) ;
		priceTable.completeRow();
		
		document.add(priceTable) ;
		document.add(br) ;
		document.add(br) ;
	}
	
}
