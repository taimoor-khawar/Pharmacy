package com.soft.pharmacy.excel;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.soft.pharmacy.model.Bill;
import com.soft.pharmacy.model.Billline;
import com.soft.pharmacy.model.Customer;
import com.soft.pharmacy.model.Product;
import com.soft.pharmacy.model.Purchase;

public class CreatePDF {
	
	private static Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD,BaseColor.WHITE);
	private static Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);
	private static Font TIME_ROMAN_INVOICE = new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD,BaseColor.BLACK);
	private static Font TIME_ROMAN_BILLINE = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD,BaseColor.WHITE);
	private static Font TIME_INVOICE = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK);
	private static Font THANK_YOU_NOTE = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD,BaseColor.BLACK);
	
	static Font invTitleFont = new Font(Font.FontFamily.HELVETICA, 18,Font.BOLD,BaseColor.BLACK);
	static Font advertisementFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.WHITE) ;
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
    
    static BaseColor headingColor = new BaseColor (10, 44, 99) ;
    static BaseColor PaymentheadingBgColor = new BaseColor (195,240,199 ) ;
    static BaseColor PaymentheadingBorderColor = new BaseColor (46,139,87 ) ;
    static BaseColor InfobgColor = new BaseColor (216,191,216) ;
    
    public static final String LOGOIMG = "E:/Repository/Pharmacy/PharmacyApplication/WebContent/resources/images/rsz_image1.png";
    
    
    public static String COMPANY_NAME = "ITEGLOBE";
    public static String INFO_NOTE = "If you have any issue, Please contact";
    public static String INFO_NOTE_2= "[ Taimoor Khawar, 03239970124, taimoor.khawar47@hotmail.com ]";
    public static String THANK_YOU= "THANK YOU For Your Business !";
	private static Product pur;

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static Document createPDFBill(String file,ArrayList<Billline> billlines,Bill bill,HttpServletRequest request) throws MalformedURLException, IOException {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			addMetaDataBill(document);
			//addTitlePage(document);
			createTableBill(document,billlines,bill,request);
			document.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;

	}

	private static void addMetaDataBill(Document document) {
		document.addTitle("Generate Bill PDF");
		document.addSubject("Generate Bill PDF");
		document.addAuthor(COMPANY_NAME);
		document.addCreator(COMPANY_NAME);
	}

	/*private static void addTitlePage(Document document)
			throws DocumentException {

		Paragraph preface = new Paragraph();
		creteEmptyLine(preface, 1);
		preface.add(new Paragraph("Bill Report", TIME_ROMAN));

		creteEmptyLine(preface, 1);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		preface.add(new Paragraph("Report created on "
				+ simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
		
		document.add(preface);
		
		

	}*/

	private static void creteEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	private static void createTableBill(Document document,ArrayList<Billline> billlines,Bill bill,
			HttpServletRequest request) throws DocumentException, MalformedURLException, IOException {
		
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		
		PdfPTable headertable = new PdfPTable(2);
		headertable.setWidthPercentage(100);
		headertable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		Image img = Image.getInstance(LOGOIMG);
		PdfPCell ImageCell = new PdfPCell(img, false);
        ImageCell.setBorder(Rectangle.NO_BORDER);
        ImageCell.setVerticalAlignment(Element.ALIGN_TOP);
        headertable.addCell(ImageCell);
		
        PdfPCell InvoiceCell = new PdfPCell(new Phrase("INVOICE",TIME_ROMAN_INVOICE));
        InvoiceCell.setBorder(Rectangle.NO_BORDER);
        InvoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        InvoiceCell.setVerticalAlignment(Element.ALIGN_TOP);
        headertable.addCell(InvoiceCell);
        
        document.add(headertable);
        document.add(paragraph);
		
        // Company Info Table
        
        PdfPTable companyinfoheadertable = new PdfPTable(4);
        companyinfoheadertable.setWidthPercentage(100);
		
        PdfPCell CompanyInfoCell = new PdfPCell(new Phrase("Company ",TIME_ROMAN));
        CompanyInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CompanyInfoCell.setFixedHeight((float) 20.0);
        CompanyInfoCell.setBackgroundColor(headingColor);
        companyinfoheadertable.addCell(CompanyInfoCell);
       
        PdfPCell EmptyCell = new PdfPCell(new Phrase(""));
        EmptyCell.setBorder(Rectangle.NO_BORDER);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
         
        PdfPCell CompanyNameCellValue = new PdfPCell(new Phrase("ITEGLOBE ",TIME_ROMAN_SMALL));
        CompanyNameCellValue.setBorder(Rectangle.NO_BORDER);
        CompanyNameCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
        companyinfoheadertable.addCell(CompanyNameCellValue);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        
        
        PdfPCell CompanyAddressCellValue = new PdfPCell(new Phrase("Lahore ",TIME_ROMAN_SMALL));
        CompanyAddressCellValue.setBorder(Rectangle.NO_BORDER);
        CompanyAddressCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
        companyinfoheadertable.addCell(CompanyAddressCellValue);
        
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        
        document.add(companyinfoheadertable);
        document.add(paragraph);
		
        
        // Bill Table
        
		PdfPTable billtable = new PdfPTable(4);
		billtable.setWidthPercentage(100);
		billtable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		creteEmptyLine(paragraph, 1);
		
		PdfPCell bc1 = new PdfPCell(new Phrase("Bill To",TIME_ROMAN));
		bc1.setFixedHeight((float) 20.0);
		bc1.setBorder(Rectangle.NO_BORDER);
		bc1.setBackgroundColor(headingColor);
		bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
		billtable.addCell(bc1);
		
		billtable.addCell(EmptyCell);
		billtable.addCell(EmptyCell);
		billtable.addCell(EmptyCell);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 0);
		document.add(paragraph);
		
		bc1 = new PdfPCell(new Phrase(bill.getCustomerName(),TIME_ROMAN_SMALL));
		bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
		bc1.setBorder(Rectangle.NO_BORDER);
		//bc1.disableBorderSide(0);
		billtable.addCell(bc1);
		
		billtable.addCell(EmptyCell);
		billtable.addCell(EmptyCell);
		billtable.addCell(EmptyCell);
		
		bc1 = new PdfPCell(new Phrase(bill.getCustomerAddress(),TIME_ROMAN_SMALL));
		bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
		bc1.setBorder(Rectangle.NO_BORDER);
		//bc1.disableBorderSide(0);
		billtable.addCell(bc1);
		
		
		
		billtable.addCell(EmptyCell);
		billtable.addCell(EmptyCell);
		billtable.addCell(EmptyCell);
	
		document.add(billtable);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 0);
		document.add(paragraph);
		
		
		
		// Bill Line Table
		PdfPTable billinetable = new PdfPTable(5);
		billinetable.setWidthPercentage(100);
		billinetable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 1);
		document.add(paragraph);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Product Name",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		billinetable.addCell(c1);

		c1 = new PdfPCell(new Phrase("Quantity",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		billinetable.addCell(c1);

		c1 = new PdfPCell(new Phrase("Amount",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		
		billinetable.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Discount",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		billinetable.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Due Amount",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		billinetable.addCell(c1);
		
		int TotalQuantity = 0;
		double TotalBilllineAmount = 0;
		double TotalBilllineDiscount = 0;
		double TotalBilllineDueAmount = 0;
		
		billinetable.setHeaderRows(1);

		for (int i = 0; i < billlines.size(); i++) {
			Billline bl = (Billline)billlines.get(i);
			
			 TotalQuantity += bl.getProductQuantity();
			 TotalBilllineAmount += bl.getBilllineAmount();
			 TotalBilllineDiscount += bl.getBilllineDiscount();
			 TotalBilllineDueAmount += bl.getBilllineDueAmount();
			
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
		
		
		// Total Table
		PdfPTable billinetotaltable = new PdfPTable(5);
		billinetotaltable.setWidthPercentage(100);
		billinetotaltable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		document.add(paragraph);
		
		PdfPCell TotalInfoCell = new PdfPCell(new Phrase("Total ",TIME_ROMAN));
		TotalInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		TotalInfoCell.setColspan(5);
		TotalInfoCell.setFixedHeight((float) 20.0);
		TotalInfoCell.setBackgroundColor(headingColor);
		billinetotaltable.addCell(TotalInfoCell);
		
		billinetotaltable.addCell(EmptyCell);
		billinetotaltable.addCell(EmptyCell);
		billinetotaltable.addCell(EmptyCell);
		billinetotaltable.addCell(EmptyCell);
		billinetotaltable.addCell(EmptyCell);
		
		PdfPCell ProductInfoCell = new PdfPCell(new Phrase(""));
		ProductInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		billinetotaltable.addCell(ProductInfoCell);
		
		PdfPCell QuantityInfoCell = new PdfPCell(new Phrase(""+TotalQuantity,TableDataFont));
		QuantityInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		billinetotaltable.addCell(QuantityInfoCell);
		
		PdfPCell BilllineAmountInfoCell = new PdfPCell(new Phrase(""+TotalBilllineAmount,TableDataFont));
		BilllineAmountInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		billinetotaltable.addCell(BilllineAmountInfoCell);
		
		PdfPCell BilllineDiscountInfoCell = new PdfPCell(new Phrase(""+TotalBilllineDiscount,TableDataFont));
		BilllineDiscountInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		billinetotaltable.addCell(BilllineDiscountInfoCell);
		
		PdfPCell BilllineDueAmountInfoCell = new PdfPCell(new Phrase(""+TotalBilllineDueAmount,TableDataFont));
		BilllineDueAmountInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		billinetotaltable.addCell(BilllineDueAmountInfoCell);
		
		document.add(billinetotaltable);
		document.add(paragraph);
		
		
		// Notes Table
		PdfPTable notetotaltable = new PdfPTable(1);
		notetotaltable.setWidthPercentage(100);
		notetotaltable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell NoteInfoCell = new PdfPCell(new Phrase("Make all checks payable to "+COMPANY_NAME,TIME_INVOICE));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		NoteInfoCell = new PdfPCell(new Phrase(INFO_NOTE,TIME_ROMAN_SMALL));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		NoteInfoCell = new PdfPCell(new Phrase(INFO_NOTE_2,TIME_ROMAN_SMALL));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		notetotaltable.addCell(EmptyCell);
		notetotaltable.addCell(EmptyCell);
		
		NoteInfoCell = new PdfPCell(new Phrase(THANK_YOU,THANK_YOU_NOTE));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		document.add(notetotaltable);
	}
	
	//Customer Report PDF
	public static Document createPDFCustomerReport(String file,ArrayList<Customer> customerlist,HttpServletRequest request) throws MalformedURLException, IOException {

		Document document = null;

		try {
			document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();
			addMetaDataCustomerReport(document);
			//addTitlePage(document);
			createTableCustomerReport(document,customerlist,request);
			document.close();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;

	}
	
	private static void addMetaDataCustomerReport(Document document) {
		document.addTitle("Generate Customer Report PDF");
		document.addSubject("Generate Customer Report PDF");
		document.addAuthor(COMPANY_NAME);
		document.addCreator(COMPANY_NAME);
	}
	
	
	private static void createTableCustomerReport(Document document,ArrayList<Customer> customerslist,
			HttpServletRequest request) throws DocumentException, MalformedURLException, IOException {
		
		Paragraph paragraph = new Paragraph();
		creteEmptyLine(paragraph, 2);
		document.add(paragraph);
		
		PdfPTable headertable = new PdfPTable(2);
		headertable.setWidthPercentage(100);
		headertable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		Image img = Image.getInstance(LOGOIMG);
		PdfPCell ImageCell = new PdfPCell(img, false);
        ImageCell.setBorder(Rectangle.NO_BORDER);
        ImageCell.setVerticalAlignment(Element.ALIGN_TOP);
        headertable.addCell(ImageCell);
		
        PdfPCell InvoiceCell = new PdfPCell(new Phrase("Customer Report",TIME_ROMAN_INVOICE));
        InvoiceCell.setBorder(Rectangle.NO_BORDER);
        InvoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        InvoiceCell.setVerticalAlignment(Element.ALIGN_TOP);
        headertable.addCell(InvoiceCell);
        
        document.add(headertable);
        document.add(paragraph);
		
        // Company Info Table
        
        PdfPTable companyinfoheadertable = new PdfPTable(4);
        companyinfoheadertable.setWidthPercentage(100);
		
        PdfPCell CompanyInfoCell = new PdfPCell(new Phrase("Company ",TIME_ROMAN));
        CompanyInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CompanyInfoCell.setFixedHeight((float) 20.0);
        CompanyInfoCell.setBackgroundColor(headingColor);
        companyinfoheadertable.addCell(CompanyInfoCell);
       
        PdfPCell EmptyCell = new PdfPCell(new Phrase(""));
        EmptyCell.setBorder(Rectangle.NO_BORDER);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
         
        PdfPCell CompanyNameCellValue = new PdfPCell(new Phrase("ITEGLOBE ",TIME_ROMAN_SMALL));
        CompanyNameCellValue.setBorder(Rectangle.NO_BORDER);
        CompanyNameCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
        companyinfoheadertable.addCell(CompanyNameCellValue);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        
        
        PdfPCell CompanyAddressCellValue = new PdfPCell(new Phrase("Lahore ",TIME_ROMAN_SMALL));
        CompanyAddressCellValue.setBorder(Rectangle.NO_BORDER);
        CompanyAddressCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
        companyinfoheadertable.addCell(CompanyAddressCellValue);
        
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        companyinfoheadertable.addCell(EmptyCell);
        
        document.add(companyinfoheadertable);
        document.add(paragraph);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 0);
		document.add(paragraph);
		
		
		
		// Customer data Table
		PdfPTable customerdatatable = new PdfPTable(5);
		customerdatatable.setWidthPercentage(100);
		customerdatatable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		paragraph = new Paragraph();
		creteEmptyLine(paragraph, 1);
		document.add(paragraph);
		
		PdfPCell c1 = new PdfPCell(new Phrase("First Name",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		customerdatatable.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Last Name",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		customerdatatable.addCell(c1);

		c1 = new PdfPCell(new Phrase("Email",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		customerdatatable.addCell(c1);

		c1 = new PdfPCell(new Phrase("Phone",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		
		customerdatatable.addCell(c1);
		
		c1 = new PdfPCell(new Phrase("Address",TIME_ROMAN_BILLINE));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setBackgroundColor(headingColor);
		customerdatatable.addCell(c1);
		
		customerdatatable.setHeaderRows(1);

		for (int i = 0; i < customerslist.size(); i++) {
			Customer cus = (Customer)customerslist.get(i);
			
			BaseColor RowBgColor;
			
			if(i%2==0)
				RowBgColor = BaseColor.WHITE;
			else
				RowBgColor = BaseColor.LIGHT_GRAY;
			
			
			PdfPCell c2 = new PdfPCell(new Phrase(cus.getFirstName(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			customerdatatable.addCell(c2);

			c2 = new PdfPCell(new Phrase(cus.getLastName(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			customerdatatable.addCell(c2);

			c2 = new PdfPCell(new Phrase(cus.getEmail(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			customerdatatable.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(cus.getPhoneNumber(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			customerdatatable.addCell(c2);
			
			c2 = new PdfPCell(new Phrase(cus.getAddress1(),TableDataFont));
			c2.setHorizontalAlignment(Element.ALIGN_CENTER);
			c2.setBackgroundColor(RowBgColor);
			customerdatatable.addCell(c2);
			
		}
		
		document.add(customerdatatable);
		
		// Notes Table
		PdfPTable notetotaltable = new PdfPTable(1);
		notetotaltable.setWidthPercentage(100);
		notetotaltable.setHorizontalAlignment(Element.ALIGN_LEFT);
		
		PdfPCell NoteInfoCell = new PdfPCell(new Phrase("Make all checks payable to "+COMPANY_NAME,TIME_INVOICE));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		NoteInfoCell = new PdfPCell(new Phrase(INFO_NOTE,TIME_ROMAN_SMALL));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		NoteInfoCell = new PdfPCell(new Phrase(INFO_NOTE_2,TIME_ROMAN_SMALL));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		notetotaltable.addCell(EmptyCell);
		notetotaltable.addCell(EmptyCell);
		
		NoteInfoCell = new PdfPCell(new Phrase(THANK_YOU,THANK_YOU_NOTE));
		NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		NoteInfoCell.setBorder(Rectangle.NO_BORDER);
		notetotaltable.addCell(NoteInfoCell);
		
		document.add(notetotaltable);
	}
	
	
	//Purchase Report PDF
		public static Document createPDFPurchaseReport(String file,ArrayList<Purchase> purchasess,Product pr,HttpServletRequest request) throws MalformedURLException, IOException {

			Document document = null;

			try {
				document = new Document();
				PdfWriter.getInstance(document, new FileOutputStream(file));
				document.open();
				addMetaDataPurchaseReport(document);
				//addTitlePage(document);
				createTablePurchaseReport(document,purchasess,pr,request);
				document.close();

			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			return document;

		}
		
		private static void addMetaDataPurchaseReport(Document document) {
			document.addTitle("Generate Purchase Report PDF");
			document.addSubject("Generate Purchase Report PDF");
			document.addAuthor(COMPANY_NAME);
			document.addCreator(COMPANY_NAME);
		}
		
		
		private static void createTablePurchaseReport(Document document,ArrayList<Purchase> purchaseslist,Product pr,
				HttpServletRequest request) throws DocumentException, MalformedURLException, IOException {
			
			Paragraph paragraph = new Paragraph();
			creteEmptyLine(paragraph, 1);
			document.add(paragraph);
			
			PdfPTable headertable = new PdfPTable(2);
			headertable.setWidthPercentage(100);
			headertable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			Image img = Image.getInstance(LOGOIMG);
			PdfPCell ImageCell = new PdfPCell(img, false);
	        ImageCell.setBorder(Rectangle.NO_BORDER);
	        ImageCell.setVerticalAlignment(Element.ALIGN_TOP);
	        headertable.addCell(ImageCell);
			
	        PdfPCell InvoiceCell = new PdfPCell(new Phrase("Purchase Report",TIME_ROMAN_INVOICE));
	        InvoiceCell.setBorder(Rectangle.NO_BORDER);
	        InvoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	        InvoiceCell.setVerticalAlignment(Element.ALIGN_TOP);
	        headertable.addCell(InvoiceCell);
	        
	        document.add(headertable);
	        document.add(paragraph);
			
	        // Company Info Table
	        
	        PdfPTable companyinfoheadertable = new PdfPTable(4);
	        companyinfoheadertable.setWidthPercentage(100);
			
	        PdfPCell CompanyInfoCell = new PdfPCell(new Phrase("Company ",TIME_ROMAN));
	        CompanyInfoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        CompanyInfoCell.setFixedHeight((float) 20.0);
	        CompanyInfoCell.setBackgroundColor(headingColor);
	        companyinfoheadertable.addCell(CompanyInfoCell);
	       
	        PdfPCell EmptyCell = new PdfPCell(new Phrase(""));
	        EmptyCell.setBorder(Rectangle.NO_BORDER);
	        companyinfoheadertable.addCell(EmptyCell);
	        companyinfoheadertable.addCell(EmptyCell);
	        companyinfoheadertable.addCell(EmptyCell);
	         
	        PdfPCell CompanyNameCellValue = new PdfPCell(new Phrase("ITEGLOBE ",TIME_ROMAN_SMALL));
	        CompanyNameCellValue.setBorder(Rectangle.NO_BORDER);
	        CompanyNameCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
	        companyinfoheadertable.addCell(CompanyNameCellValue);
	        companyinfoheadertable.addCell(EmptyCell);
	        companyinfoheadertable.addCell(EmptyCell);
	        companyinfoheadertable.addCell(EmptyCell);
	        
	        
	        PdfPCell CompanyAddressCellValue = new PdfPCell(new Phrase("Lahore ",TIME_ROMAN_SMALL));
	        CompanyAddressCellValue.setBorder(Rectangle.NO_BORDER);
	        CompanyAddressCellValue.setHorizontalAlignment(Element.ALIGN_LEFT);
	        companyinfoheadertable.addCell(CompanyAddressCellValue);
	        
	        companyinfoheadertable.addCell(EmptyCell);
	        companyinfoheadertable.addCell(EmptyCell);
	        companyinfoheadertable.addCell(EmptyCell);
	        
	        document.add(companyinfoheadertable);
	        document.add(paragraph);
			
	        
	        // Bill Table
	        
			PdfPTable billtable = new PdfPTable(4);
			billtable.setWidthPercentage(100);
			billtable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			creteEmptyLine(paragraph, 1);
			
			PdfPCell bc1 = new PdfPCell(new Phrase("Product",TIME_ROMAN));
			bc1.setFixedHeight((float) 20.0);
			bc1.setBorder(Rectangle.NO_BORDER);
			bc1.setBackgroundColor(headingColor);
			bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			billtable.addCell(bc1);
			
			billtable.addCell(EmptyCell);
			billtable.addCell(EmptyCell);
			billtable.addCell(EmptyCell);
			
			paragraph = new Paragraph();
			creteEmptyLine(paragraph, 0);
			document.add(paragraph);
			
			bc1 = new PdfPCell(new Phrase(pr.getProductName(),TIME_ROMAN_SMALL));
			bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			bc1.setBorder(Rectangle.NO_BORDER);
			//bc1.disableBorderSide(0);
			billtable.addCell(bc1);
			
			billtable.addCell(EmptyCell);
			billtable.addCell(EmptyCell);
			billtable.addCell(EmptyCell);
			
			bc1 = new PdfPCell(new Phrase(pr.getProducTypeName(),TIME_ROMAN_SMALL));
			bc1.setHorizontalAlignment(Element.ALIGN_LEFT);
			bc1.setBorder(Rectangle.NO_BORDER);
			//bc1.disableBorderSide(0);
			billtable.addCell(bc1);
			
			
			
			billtable.addCell(EmptyCell);
			billtable.addCell(EmptyCell);
			billtable.addCell(EmptyCell);
		
			document.add(billtable);
			
			paragraph = new Paragraph();
			creteEmptyLine(paragraph, 0);
			document.add(paragraph);
			
			
			
			// Bill Line Table
			PdfPTable purchaselinetable = new PdfPTable(5);
			purchaselinetable.setWidthPercentage(100);
			purchaselinetable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			paragraph = new Paragraph();
			creteEmptyLine(paragraph, 1);
			document.add(paragraph);
			
			PdfPCell c1 = new PdfPCell(new Phrase("Product Name",TIME_ROMAN_BILLINE));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(headingColor);
			purchaselinetable.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Supplier Name",TIME_ROMAN_BILLINE));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(headingColor);
			purchaselinetable.addCell(c1);

			c1 = new PdfPCell(new Phrase("Quantity",TIME_ROMAN_BILLINE));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(headingColor);
			purchaselinetable.addCell(c1);

			c1 = new PdfPCell(new Phrase("Amount",TIME_ROMAN_BILLINE));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(headingColor);
			
			purchaselinetable.addCell(c1);
			
			c1 = new PdfPCell(new Phrase("Total Amount",TIME_ROMAN_BILLINE));
			c1.setHorizontalAlignment(Element.ALIGN_CENTER);
			c1.setBackgroundColor(headingColor);
			purchaselinetable.addCell(c1);
			
			
			
			int TotalQuantity = 0;
			double TotalBuyingPrice = 0;
			double TotalPurchaseAmount = 0;
			double TotalAmount = 0;
			double TotalOrderAmount = 0;
			
			purchaselinetable.setHeaderRows(1);

			for (int i = 0; i < purchaseslist.size(); i++) {
				Purchase pur = (Purchase)purchaseslist.get(i);
				
				 TotalQuantity += pur.getQuantity();
				 TotalBuyingPrice += pur.getBuyingPrice();
				 TotalPurchaseAmount = pur.getQuantity() * pur.getBuyingPrice();
				 TotalAmount += TotalPurchaseAmount;
				 TotalOrderAmount += TotalAmount;
				
				BaseColor RowBgColor;
				
				if(i%2==0)
					RowBgColor = BaseColor.WHITE;
				else
					RowBgColor = BaseColor.LIGHT_GRAY;
				
				
				PdfPCell c2 = new PdfPCell(new Phrase(pur.getProductName(),TableDataFont));
				c2.setHorizontalAlignment(Element.ALIGN_CENTER);
				c2.setBackgroundColor(RowBgColor);
				purchaselinetable.addCell(c2);

				c2 = new PdfPCell(new Phrase(""+pur.getSupplierName(),TableDataFont));
				c2.setHorizontalAlignment(Element.ALIGN_CENTER);
				c2.setBackgroundColor(RowBgColor);
				purchaselinetable.addCell(c2);

				c2 = new PdfPCell(new Phrase(""+pur.getQuantity(),TableDataFont));
				c2.setHorizontalAlignment(Element.ALIGN_CENTER);
				c2.setBackgroundColor(RowBgColor);
				purchaselinetable.addCell(c2);
				
				c2 = new PdfPCell(new Phrase(""+pur.getBuyingPrice(),TableDataFont));
				c2.setHorizontalAlignment(Element.ALIGN_CENTER);
				c2.setBackgroundColor(RowBgColor);
				purchaselinetable.addCell(c2);
				
				c2 = new PdfPCell(new Phrase(""+TotalPurchaseAmount,TableDataFont));
				c2.setHorizontalAlignment(Element.ALIGN_CENTER);
				c2.setBackgroundColor(RowBgColor);
				purchaselinetable.addCell(c2);
				
			}
			
			document.add(purchaselinetable);
			
			
			// Total Table
			PdfPTable purchasetotaltable = new PdfPTable(5);
			purchasetotaltable.setWidthPercentage(100);
			purchasetotaltable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			document.add(paragraph);
			
			PdfPCell TotalInfoCell = new PdfPCell(new Phrase("Total ",TIME_ROMAN));
			TotalInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			TotalInfoCell.setColspan(5);
			TotalInfoCell.setFixedHeight((float) 20.0);
			TotalInfoCell.setBackgroundColor(headingColor);
			purchasetotaltable.addCell(TotalInfoCell);
			
			purchasetotaltable.addCell(EmptyCell);
			purchasetotaltable.addCell(EmptyCell);
			purchasetotaltable.addCell(EmptyCell);
			purchasetotaltable.addCell(EmptyCell);
			purchasetotaltable.addCell(EmptyCell);
			
			PdfPCell ProductInfoCell = new PdfPCell(new Phrase(""));
			ProductInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			purchasetotaltable.addCell(ProductInfoCell);
			
			PdfPCell SupplierInfoCell = new PdfPCell(new Phrase(""));
			SupplierInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			purchasetotaltable.addCell(SupplierInfoCell);
			
			PdfPCell QuantityInfoCell = new PdfPCell(new Phrase(""+TotalQuantity,TableDataFont));
			QuantityInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			purchasetotaltable.addCell(QuantityInfoCell);
			
			PdfPCell BilllineAmountInfoCell = new PdfPCell(new Phrase(""+TotalBuyingPrice,TableDataFont));
			BilllineAmountInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			purchasetotaltable.addCell(BilllineAmountInfoCell);
			
			PdfPCell BilllineDiscountInfoCell = new PdfPCell(new Phrase(""+TotalAmount,TableDataFont));
			BilllineDiscountInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			purchasetotaltable.addCell(BilllineDiscountInfoCell);
			
			/*PdfPCell BilllineDueAmountInfoCell = new PdfPCell(new Phrase(""+TotalBilllineDueAmount,TableDataFont));
			BilllineDueAmountInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			billinetotaltable.addCell(BilllineDueAmountInfoCell);*/
			
			document.add(purchasetotaltable);
			document.add(paragraph);
			
			
			// Notes Table
			PdfPTable notetotaltable = new PdfPTable(1);
			notetotaltable.setWidthPercentage(100);
			notetotaltable.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell TotalOrderInfoCell = new PdfPCell(new Phrase("Purchase Total: "+TotalOrderAmount,TIME_ROMAN_SMALL));
			TotalOrderInfoCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			TotalOrderInfoCell.setBorder(Rectangle.NO_BORDER);
			notetotaltable.addCell(TotalOrderInfoCell);
			
			PdfPCell NoteInfoCell = new PdfPCell(new Phrase("Make all checks payable to "+COMPANY_NAME,TIME_INVOICE));
			NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			NoteInfoCell.setBorder(Rectangle.NO_BORDER);
			notetotaltable.addCell(NoteInfoCell);
			
			NoteInfoCell = new PdfPCell(new Phrase(INFO_NOTE,TIME_ROMAN_SMALL));
			NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			NoteInfoCell.setBorder(Rectangle.NO_BORDER);
			notetotaltable.addCell(NoteInfoCell);
			
			NoteInfoCell = new PdfPCell(new Phrase(INFO_NOTE_2,TIME_ROMAN_SMALL));
			NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			NoteInfoCell.setBorder(Rectangle.NO_BORDER);
			notetotaltable.addCell(NoteInfoCell);
			
			notetotaltable.addCell(EmptyCell);
			notetotaltable.addCell(EmptyCell);
			
			NoteInfoCell = new PdfPCell(new Phrase(THANK_YOU,THANK_YOU_NOTE));
			NoteInfoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			NoteInfoCell.setBorder(Rectangle.NO_BORDER);
			notetotaltable.addCell(NoteInfoCell);
			
			document.add(notetotaltable);
		}

}
