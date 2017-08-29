package com.soft.pharmacy.excel;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.soft.pharmacy.model.Bill;

public class ExcelView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Bill> orderslist = (ArrayList<Bill>) model.get("orderslist");
		//create a wordsheet
		HSSFSheet sheet = workbook.createSheet("Revenue Report");
		
		HSSFRow header = sheet.createRow(0);
		header.createCell(0).setCellValue("Sr #");
		header.createCell(1).setCellValue("Customer Name");
		header.createCell(2).setCellValue("Bill Name");
		header.createCell(3).setCellValue("Bill Amount");
		header.createCell(4).setCellValue("Bill Discount");
		header.createCell(5).setCellValue("Bill Due Amount");
		
		int rowNum = 1;
		int count=0;
		for (Bill bill : orderslist) {
			//create the row data
			count+=1;
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(count);
			row.createCell(1).setCellValue(bill.getCustomerName());
			row.createCell(2).setCellValue(bill.getBillName());
			row.createCell(3).setCellValue(bill.getBillAmount());
			row.createCell(4).setCellValue(bill.getBillDiscount());
			row.createCell(5).setCellValue(bill.getBillDueAmount());
			
        }
		
	}

}
