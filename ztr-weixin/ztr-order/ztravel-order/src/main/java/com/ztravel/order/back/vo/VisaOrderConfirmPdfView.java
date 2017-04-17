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
import com.ztravel.common.html.HTMLUtil;
import com.ztravel.common.pdf.PDFUtil;
import org.codehaus.plexus.util.StringUtils;

public class VisaOrderConfirmPdfView extends AbstractPdfView4Itext5{
private VisaOrderConfirmPDFEntity entity ;

	private static final Paragraph br = PDFUtil.getBr() ;

	private static final PdfPCell blankCell = new PdfPCell() ;


	private static final String TITLE = "真旅行产品确认单" ;

	private static final String VISA_PRODUCT_NAME="产品名称";

	private static final String VISA_PRICE_TYPE="购买产品类型";

	private static final String VISA_PRICE_TITILE = "费用说明" ;

	private static final String VISA_REFUND_POLICY = "退改政策" ;

	private static final String VISA_PAY_TOTAL="应付总价";

	private static final String IMPORTANT_1 = "重要声明:" ;

	private static final String IMPORTANT_2 = "收到本行程确认单表示我们和您之间的合同已经成立。" ;

	private static final String IMPORTANT_3 = "感谢您的理解,出行可能会因战争,政治,天气等原因发生改变。" ;

	public VisaOrderConfirmPdfView(VisaOrderConfirmPDFEntity entity){
		super() ;
		this.entity = entity ;
	}


	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		Paragraph title = PDFUtil.getParagraphAlignCenter(new Chunk(TITLE,PDFUtil.fontChinese_big_bold)) ;
		document.add(title);
		//换行
		document.add(br) ;
		document.add(br) ;

		//设置订单基本信息
		createVisaBasicInfo(document) ;
		//设置订单基本信息结束

		//设置签证信息开始
		if(!StringUtils.isEmpty(entity.getOrderNo())){
			createInfo(document) ;
		}
		//设置签证信息结束



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

	private void createVisaBasicInfo(Document document) throws DocumentException{
		PdfPTable orderBasicTable = new PdfPTable(2) ;

		PdfPCell prdNameCell = new PdfPCell(new Phrase("亲爱的"+entity.getContactorName()+"您好:", PDFUtil.fontChinese_small_bold)) ;
		prdNameCell.setBorderWidth(0f);
		prdNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		orderBasicTable.addCell(prdNameCell);
		//orderBasicTable.addCell(blankCell);
		//orderBasicTable.addCell(blankCell);

		//orderBasicTable.completeRow();

		//blankCell.setBorderWidth(2f);


		//orderBasicTable.addCell(blankCell);
		//orderBasicTable.addCell(blankCell);
		PdfPCell orderNoCell = new PdfPCell(new Phrase("订单号: " + entity.getOrderNo(), PDFUtil.fontChinese_small_bold)) ;
		orderNoCell.setBorderWidth(0f);
		orderNoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		orderBasicTable.addCell(orderNoCell);
		
		orderBasicTable.completeRow();
		document.add(orderBasicTable) ;


		document.add(br) ;
		document.add(br) ;
	}


	private void createInfo(Document document) throws DocumentException{

		PdfPTable Table = new PdfPTable(2) ;

		PdfPCell productNameCell = new PdfPCell(new Phrase(VISA_PRODUCT_NAME, PDFUtil.fontChinese_small_bold)) ;
		productNameCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		productNameCell.setVerticalAlignment(Element.ALIGN_TOP);
		Table.addCell(productNameCell);
		Table.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getProductName()))) ;
		Table.completeRow();

		PdfPCell visaPriceTypeCell = new PdfPCell(new Phrase(VISA_PRICE_TYPE, PDFUtil.fontChinese_small_bold)) ;
		visaPriceTypeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		visaPriceTypeCell.setVerticalAlignment(Element.ALIGN_TOP);
		Table.addCell(visaPriceTypeCell);
		Table.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getVisaPriceType()))) ;
		Table.completeRow();

		PdfPCell feeDetailCell = new PdfPCell(new Phrase(VISA_PRICE_TITILE, PDFUtil.fontChinese_small_bold)) ;
		feeDetailCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		feeDetailCell.setVerticalAlignment(Element.ALIGN_TOP);
		Table.addCell(feeDetailCell);
		Table.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getFeeDetail()))) ;
		Table.completeRow();

		PdfPCell policeCell = new PdfPCell(new Phrase(VISA_REFUND_POLICY, PDFUtil.fontChinese_small_bold)) ;
		policeCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		policeCell.setVerticalAlignment(Element.ALIGN_TOP);
		Table.addCell(policeCell);
		Table.addCell(PDFUtil.getCellAlignLeftNormal(HTMLUtil.removeHtmlTag(entity.getRefundPolicy()))) ;
		Table.completeRow();


		PdfPCell pPoliceCell = new PdfPCell(new Phrase(VISA_PAY_TOTAL, PDFUtil.fontChinese_small_bold)) ;
		pPoliceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		pPoliceCell.setVerticalAlignment(Element.ALIGN_TOP);
		Table.addCell(pPoliceCell);
		Table.addCell(new Phrase(entity.getPayDetail().getPayPrice()+"元(订单总价:"+entity.getPayDetail().getOrderPrice()+"元"+" "+"网站优惠:"+entity.getPayDetail().getCouponPrice()+"元)", PDFUtil.fontChinese_small_bold)) ;
		Table.completeRow();


		document.add(Table) ;
		document.add(br) ;
		document.add(br) ;
	}

}
