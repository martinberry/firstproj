package com.ztravel.common.view;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.junit.Test;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ztravel.common.pdf.PDFUtil;
import com.ztravel.common.util.IOUtil;


public class TestPdf {
	
	@Test
	public void create() throws DocumentException, MalformedURLException, IOException{
		Document document = new Document(PageSize.A4, 20, 20, 20, 20);
		
		OutputStream os = new FileOutputStream("/Users/wanhaofan/tmp/1.pdf") ;
		
		PdfWriter.getInstance(document, os);

		document.open();
		Paragraph br = PDFUtil.getBr() ;
		PdfPCell blankCell = new PdfPCell() ;
		
		InputStream is = ClassLoader.getSystemResourceAsStream("com/ztravel/common/view/logo.png") ;
		Image logo = Image.getInstance(IOUtil.readAsByteArray(is));
		logo.scaleToFit(76f, 76f);
		logo.setAbsolutePosition(document.right() - 76f, document.top() - 30f);
		document.add(logo) ;
		
		Paragraph title = PDFUtil.getParagraphAlignCenter(new Chunk("行程确认单",PDFUtil.fontChinese_big_bold)) ;
		document.add(title);
		//换行
		document.add(br) ;
		document.add(br) ;
		
		//设置订单基本信息
		PdfPTable orderBasicTable = new PdfPTable(3) ;
		PdfPCell prdNameCell = new PdfPCell(new Phrase("上海－北京", PDFUtil.fontChinese_small_bold)) ;
		prdNameCell.setBorderWidth(0f);
		prdNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		orderBasicTable.addCell(prdNameCell);
		
		PdfPCell orderNoCell = new PdfPCell(new Phrase("订单号: 11111111", PDFUtil.fontChinese_small_bold)) ;
		orderNoCell.setBorderWidth(0f);
		orderNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		orderBasicTable.addCell(orderNoCell);
		
		PdfPCell orderCreateDateCell = new PdfPCell(new Phrase("下单日期: 2016-10-10", PDFUtil.fontChinese_small_bold)) ;
		orderCreateDateCell.setBorderWidth(0f);
		orderCreateDateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		orderBasicTable.addCell(orderCreateDateCell);
		
		orderBasicTable.completeRow();
		
		blankCell.setBorderWidth(0f);
		orderBasicTable.addCell(blankCell);
		orderBasicTable.addCell(blankCell);
		
		PdfPCell playDateCell = new PdfPCell(new Phrase("出游日期: 2016-10-10", PDFUtil.fontChinese_small_bold)) ;
		playDateCell.setBorderWidth(0f);
		playDateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		orderBasicTable.addCell(playDateCell);
		
		document.add(orderBasicTable) ;
		document.add(br) ;
		document.add(br) ;
		//设置订单基本信息结束
		
		//设置旅客信息开始
		Paragraph passengerInfo = PDFUtil.getParagraphAlignCenter(new Chunk("旅客信息",PDFUtil.fontChinese_small_bold)) ;
		document.add(passengerInfo) ;
		document.add(br) ;
		
		PdfPTable passengerTable = new PdfPTable(8) ;
		PdfPCell pNameCell = new PdfPCell(new Phrase("姓名", PDFUtil.fontChinese_small_bold)) ;
		pNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pNameCell);
		
		PdfPCell pENameCell = new PdfPCell(new Phrase("英文/拼音名", PDFUtil.fontChinese_small_bold)) ;
		pENameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pENameCell);
		
		PdfPCell pCountryCell = new PdfPCell(new Phrase("国籍", PDFUtil.fontChinese_small_bold)) ;
		pCountryCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pCountryCell);
		
		PdfPCell pTypeCell = new PdfPCell(new Phrase("乘客类型", PDFUtil.fontChinese_small_bold)) ;
		pTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pTypeCell);
		
		PdfPCell pZTypeCell = new PdfPCell(new Phrase("证件类型", PDFUtil.fontChinese_small_bold)) ;
		pZTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pZTypeCell);
		
		PdfPCell pZNoCell = new PdfPCell(new Phrase("证件号", PDFUtil.fontChinese_small_bold)) ;
		pZNoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pZNoCell);
		
		PdfPCell pZValidCell = new PdfPCell(new Phrase("证件有效期", PDFUtil.fontChinese_small_bold)) ;
		pZValidCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pZValidCell);
		
		PdfPCell pBirthCell = new PdfPCell(new Phrase("出生年月", PDFUtil.fontChinese_small_bold)) ;
		pBirthCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		passengerTable.addCell(pBirthCell);
		
		passengerTable.completeRow();
		
		document.add(passengerTable) ;
		document.add(br) ;
		document.add(br) ;
		//设置旅客信息结束

		//设置机票信息开始
		Paragraph flightInfo = PDFUtil.getParagraphAlignCenter(new Chunk("机票信息",PDFUtil.fontChinese_small_bold)) ;
		document.add(flightInfo) ;
		document.add(br) ;
		
		PdfPTable flightTable = new PdfPTable(6) ;
		PdfPCell fDayNumCell = new PdfPCell(new Phrase("天数", PDFUtil.fontChinese_small_bold)) ;
		fDayNumCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		flightTable.addCell(fDayNumCell);
		
		PdfPCell fStartDateCell = new PdfPCell(new Phrase("出发日期/时间", PDFUtil.fontChinese_small_bold)) ;
		fStartDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		flightTable.addCell(fStartDateCell);
		
		PdfPCell fStartPartCell = new PdfPCell(new Phrase("出发机场", PDFUtil.fontChinese_small_bold)) ;
		fStartPartCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		flightTable.addCell(fStartPartCell);
		
		PdfPCell fEndDateCell = new PdfPCell(new Phrase("到达时间", PDFUtil.fontChinese_small_bold)) ;
		fEndDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		flightTable.addCell(fEndDateCell);
		
		PdfPCell fEndPartCell = new PdfPCell(new Phrase("到达机场", PDFUtil.fontChinese_small_bold)) ;
		fEndPartCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		flightTable.addCell(fEndPartCell);
		
		PdfPCell fInfoCell = new PdfPCell(new Phrase("航班号/机型/航空公司", PDFUtil.fontChinese_small_bold)) ;
		fInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		flightTable.addCell(fInfoCell);
		
		flightTable.completeRow();
		
		document.add(flightTable) ;
		document.add(br) ;
		document.add(br) ;
		//设置机票信息结束
		
		//设置酒店信息开始
		Paragraph hotelInfo = PDFUtil.getParagraphAlignCenter(new Chunk("酒店信息",PDFUtil.fontChinese_small_bold)) ;
		document.add(hotelInfo) ;
		document.add(br) ;
		
		PdfPTable hotelTable = new PdfPTable(6) ;
		PdfPCell hCheckinCell = new PdfPCell(new Phrase("入住时间", PDFUtil.fontChinese_small_bold)) ;
		hCheckinCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		hotelTable.addCell(hCheckinCell);
		
		PdfPCell hCheckoutCell = new PdfPCell(new Phrase("离店时间", PDFUtil.fontChinese_small_bold)) ;
		hCheckoutCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		hotelTable.addCell(hCheckoutCell);
		
		PdfPCell hNameCell = new PdfPCell(new Phrase("入住酒店", PDFUtil.fontChinese_small_bold)) ;
		hNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		hotelTable.addCell(hNameCell);
		
		PdfPCell hTypeCell = new PdfPCell(new Phrase("房型", PDFUtil.fontChinese_small_bold)) ;
		hTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		hotelTable.addCell(hTypeCell);
		
		PdfPCell hBedTypeCell = new PdfPCell(new Phrase("床型", PDFUtil.fontChinese_small_bold)) ;
		hBedTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		hotelTable.addCell(hBedTypeCell);
		
		PdfPCell hDaysNumCell = new PdfPCell(new Phrase("入住天数", PDFUtil.fontChinese_small_bold)) ;
		hDaysNumCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		hotelTable.addCell(hDaysNumCell);
		
		hotelTable.completeRow();
		
		document.add(hotelTable) ;
		document.add(br) ;
		document.add(br) ;
		//设置酒店信息结束
		
		//设置附加产品信息开始
		Paragraph additionProductInfo = PDFUtil.getParagraphAlignCenter(new Chunk("附加产品",PDFUtil.fontChinese_small_bold)) ;
		document.add(additionProductInfo) ;
		document.add(br) ;
		
		PdfPTable additionProductTable = new PdfPTable(5) ;
		
		PdfPCell aNameCell = new PdfPCell(new Phrase("名称", PDFUtil.fontChinese_small_bold)) ;
		aNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		additionProductTable.addCell(aNameCell);
		
		PdfPCell aDateCell = new PdfPCell(new Phrase("日期", PDFUtil.fontChinese_small_bold)) ;
		aDateCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		additionProductTable.addCell(aDateCell);
		
		PdfPCell aPriceCell = new PdfPCell(new Phrase("单价", PDFUtil.fontChinese_small_bold)) ;
		aPriceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		additionProductTable.addCell(aPriceCell);
		
		PdfPCell aNumCell = new PdfPCell(new Phrase("数量", PDFUtil.fontChinese_small_bold)) ;
		aNumCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		additionProductTable.addCell(aNumCell);
		
		PdfPCell aSumCell = new PdfPCell(new Phrase("合计", PDFUtil.fontChinese_small_bold)) ;
		aSumCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		additionProductTable.addCell(aSumCell);
		
		additionProductTable.completeRow();
		
		document.add(additionProductTable) ;
		document.add(br) ;
		document.add(br) ;
		//设置附加产品信息结束
		
		//设置费用说明开始
		Paragraph priceInfo = PDFUtil.getParagraphAlignCenter(new Chunk("费用说明",PDFUtil.fontChinese_small_bold)) ;
		document.add(priceInfo) ;
		document.add(br) ;
		
		PdfPTable priceTable = new PdfPTable(2) ;
		
		PdfPCell pContainCell = new PdfPCell(new Phrase("费用包含", PDFUtil.fontChinese_small_bold)) ;
		pContainCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pContainCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pContainCell);
		priceTable.addCell(blankCell) ;
		priceTable.completeRow();
		
		PdfPCell pNotContainCell = new PdfPCell(new Phrase("费用不含", PDFUtil.fontChinese_small_bold)) ;
		pNotContainCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pNotContainCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pNotContainCell);
		priceTable.addCell(blankCell) ;
		priceTable.completeRow();
		
		PdfPCell pFreeCell = new PdfPCell(new Phrase("赠送项目", PDFUtil.fontChinese_small_bold)) ;
		pFreeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pFreeCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pFreeCell);
		priceTable.addCell(blankCell) ;
		priceTable.completeRow();
		
		PdfPCell pPoliceCell = new PdfPCell(new Phrase("退改政策", PDFUtil.fontChinese_small_bold)) ;
		pPoliceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pPoliceCell.setVerticalAlignment(Element.ALIGN_TOP);
		priceTable.addCell(pPoliceCell);
		priceTable.addCell(blankCell) ;
		priceTable.completeRow();
		
		document.add(priceTable) ;
		document.add(br) ;
		document.add(br) ;
		//设置费用说明结束
		
		//重要声明开始
		Paragraph importantInfo = PDFUtil.getParagraph(new Chunk("重要声明:",PDFUtil.fontChinese_normal_bold)) ;
		Paragraph importantInfo1 = PDFUtil.getParagraph(new Chunk("收到本行程确认单表示我们和您之间的合同已经成立。",PDFUtil.fontChinese_normal)) ;
		Paragraph importantInfo2 = PDFUtil.getParagraph(new Chunk("感谢您的理解,出行可能会因战争,政治,天气等原因发生改变。",PDFUtil.fontChinese_normal)) ;
		importantInfo.setIndentationLeft(55f);
		importantInfo1.setIndentationLeft(55f);
		importantInfo2.setIndentationLeft(55f);
		document.add(importantInfo) ;
		document.add(importantInfo1) ;
		document.add(importantInfo2) ;
		document.add(br) ;
		//重要声明结束
		
		document.close();
	}
}
