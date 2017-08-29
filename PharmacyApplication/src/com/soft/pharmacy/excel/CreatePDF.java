package com.soft.pharmacy.excel;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Billline;

public class CreatePDF {
	
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	static Font invTitleFont = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD,BaseColor.BLACK);
	static Font advertisementFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK) ;
    static Font MonogramFont = new Font(Font.FontFamily.HELVETICA, 10, 0, BaseColor.BLACK) ;
    static Font TableHeaderFont = new Font(Font.FontFamily.HELVETICA,  10, Font.BOLD, BaseColor.BLACK) ; 
    static Font TableDataFont = new Font(Font.FontFamily.HELVETICA, 8, 0, BaseColor.BLACK) ;
    static Font TableDataFontBold = new Font(Font.FontFamily.HELVETICA, 10,Font.BOLD,BaseColor.BLACK) ;
    static Font CustomerInfoFont = new Font(Font.FontFamily.HELVETICA, 10, 0, BaseColor.BLACK) ;
    static Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, 0) ;
    static Font HeadingInfoFont = new Font(Font.FontFamily.HELVETICA, 10, 0, BaseColor.BLACK) ;

    static Font TableDatFont = new Font(Font.FontFamily.HELVETICA, 10, 0, BaseColor.BLACK) ;
    static Font CustomerInfoFontBold = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK) ;
    static Font TableDataFont2 = new Font(Font.FontFamily.HELVETICA, 10, 0, BaseColor.BLACK) ;
    static Color headingBgColor = new Color ( 228 , 234 , 244 ) ;
    
    static BaseColor BankheadingBgColor = new BaseColor ( 255 , 219 , 253 ) ;
    static BaseColor PaymentheadingBgColor = new BaseColor (195,240,199 ) ;
    static BaseColor PaymentheadingBorderColor = new BaseColor (46,139,87 ) ;
    static BaseColor InfobgColor = new BaseColor (216,191,216) ;

	/**
	 * @param args
	 */
	public static Document createPDF(String file,ArrayList<Billline> billlines,Bill bill) {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			addMetaData(document);
			addTitlePage(document);
			createTable(document,billlines,bill);
			document.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;

	}

	private static void addMetaData(Document document) {
		document.addTitle("Generate PDF report");
		document.addSubject("Generate PDF report");
		document.addAuthor("Taimoor Khawar");
		document.addCreator("Taimoor Khawar");
	}

	private static void addTitlePage(Document document)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("Bill Report", TIME_ROMAN));

		creteEmptyLine(preface, 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		preface.add(new Paragraph("Report created on "
				+ simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
		
		document.add(preface);
		
		

	}

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static void createTable(Document document,ArrayList<Billline> billlines,Bill bill) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		
		PdfPTable billtable = new PdfPTable(1);
		billtable.setWidthPercentage(100);
		billtable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		creteEmptyLine(paragraph, 1);
		PdfPCell bc1 = new PdfPCell(new Phrase("Bill Detail",TIME_ROMAN));
		bc1.setBorder(Rectangle.NO_BORDER);
		bc1.setHorizontalAlignment(Element.ALIGN_CENTER);
		billtable.addCell(bc1);
		
		bc1 = new PdfPCell(new Phrase(""));
		bc1.setFixedHeight((float) 10.0);
		bc1.setBorder(Rectangle.NO_BORDER);
		billtable.addCell(bc1);
		
		bc1 = new PdfPCell(new Phrase("Customer Name: "+bill.getCustomerName()));
		bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
		bc1.setBorder(Rectangle.NO_BORDER);
		//bc1.disableBorderSide(0);
		billtable.addCell(bc1);
		
		bc1 = new PdfPCell(new Phrase("Bill Name: "+bill.getBillName()));
		bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
		bc1.setBorder(Rectangle.NO_BORDER);
		//bc1.disableBorderSide(0);
		billtable.addCell(bc1);
		
	
		document.add(billtable);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		
		PdfPTable billinetable = new PdfPTable(5);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 1);
		document.add(paragraph);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Product Name",TableHeaderFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		bc1.setBackgroundColor(PaymentheadingBorderColor);
		billinetable.addCell(c1);

		c1 = new PdfPCell(new Phrase("Quantity",TableHeaderFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		bc1.setBackgroundColor(PaymentheadingBorderColor);
		billinetable.addCell(c1);

		c1 = new PdfPCell(new Phrase("Amount",TableHeaderFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		bc1.setBackgroundColor(PaymentheadingBorderColor);
		
		billinetable.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Discount",TableHeaderFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		bc1.setBackgroundColor(PaymentheadingBorderColor);
		billinetable.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Due Amount",TableHeaderFont));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		bc1.setBackgroundColor(PaymentheadingBorderColor);
		billinetable.addCell(c1);
		
		billinetable.setHeaderRows(1);

		for (int i = 0; i < billlines.size(); i++) {
			Billline bl = (Billline)billlines.get(i);
			
			BaseColor RowBgColor;
			
			if(i%2==0)
				RowBgColor = BaseColor.WHITE;
			else
				RowBgColor = BaseColor.LIGHT_GRAY;
			
			billinetable.setWidthPercentage(100);
			PdfPCell c2 = new PdfPCell(new Phrase(bl.getProductName(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			billinetable.addCell(c2);

			c2 = new PdfPCell(new Phrase(""+bl.getProductQuantity(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			billinetable.addCell(c2);

			c2 = new PdfPCell(new Phrase(""+bl.getBilllineAmount(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			billinetable.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(""+bl.getBilllineDiscount(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			billinetable.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(""+bl.getBilllineDueAmount(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			billinetable.addCell(c2);
			
		}

		document.add(billinetable);
	}

}
