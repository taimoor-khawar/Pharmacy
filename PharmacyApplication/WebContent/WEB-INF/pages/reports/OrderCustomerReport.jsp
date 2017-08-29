<%@page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@page import="java.io.*"%>
<%@page import="com.soft.pharmacy.model.Bill"%>
<%@page import="com.soft.pharmacy.model.Customer"%>
<%@page import="com.soft.pharmacy.model.Product"%>
<%@page import="com.soft.pharmacy.model.Supplier"%>
<%@page import="com.soft.pharmacy.model.ProductType"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.apache.poi.hssf.usermodel.HSSFWorkbook" %>
<%@page import="org.apache.poi.hssf.usermodel.HSSFRow" %>
<%@page import="com.soft.pharmacy.model.SysUserSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%
SysUserSession uss = (SysUserSession)session.getAttribute(session.getId());
if( uss != null){%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd" >
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>POS</title>

<!-- Bootstrap Core CSS -->
<link href="/POS/resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="/POS/resources/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="/POS/resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="/POS/resources/vendor/morrisjs/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="/POS/resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<script type="text/javascript"
	src="http://ajax.aspnetcdn.com/ajax/jquery/jquery-1.4.4.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css" />

</head>
<%
				ArrayList customerslist = null;
				customerslist = (ArrayList)request.getAttribute("customerslist");
				
				ArrayList orderslist = null;
				orderslist = (ArrayList)request.getAttribute("orderslist");
				
				
				String customerID = (String)request.getAttribute("CustomerID");
				if(customerID==null)customerID="0";
				customerID = customerID.replaceAll("[^\\d.]", "");
				long CustomerID = 0;
				 try{
					CustomerID = Long.parseLong(customerID);
				}catch(Exception e){
					CustomerID = 0;
				} 
				 
				String FromDate = (String)request.getAttribute("FromDate");
				if(FromDate==null)FromDate="0";
				
				String ToDate = (String)request.getAttribute("ToDate");
				if(ToDate==null)ToDate="0";
				
%>
<body>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header">Order Report</h4>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div></div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="row">
							
								<form name="newOrder" id="newOrder" role="form"
									method="post" action="/POS/report/orderbycustomer" style="padding: 20px 20px">
									<input type="hidden" name="EnterprisedID" id="EnterprisedID" value="<%=uss.getSysuser().getsysUserID()%>">
									
									
									<div class="col-lg-3">
									<label>Customer</label>
										<select name="CustomerID"
											id="CustomerID" class="form-control selectpicker" data-show-subtext="true" data-live-search="true">
											<option value="0">Select Customer</option>
											<%for(int count=0;count<customerslist.size();count++){
										      	Customer cus=(Customer)customerslist.get(count);%>
											<option value="<%=cus.getCustomerID()%>" <%=cus.getCustomerID()==CustomerID?"selected":"" %>><%=cus.getFirstName()%>-<%=cus.getLastName() %></option>
											<%} %>
										</select>
										</div>
									
									<div class="col-lg-3">
										<label>From Date</label>
										<input class="form-control" type="date" id="fromdate" name="fromdate" value="<%=FromDate%>">
										</div>
									
									<div class="col-lg-3">
										<label>To Date</label>
										<input class="form-control" type="date" id="todate" name="todate" value="<%=ToDate%>">
										</div>
						        
									<input type="hidden" id="dtp_input1" value="" /><br/>
					           
									<div class="col-lg-3">
									<input type="submit" class="btn btn-primary" style="float: right;margin-left: 10px;" value="Search"/>
										<a href="/POS/report/orderlistexport?EnterprisedID=<%=uss.getSysuser().getsysUserID()%>&CustomerID=<%=CustomerID %>&fromdate=<%=FromDate %>&todate=<%=ToDate %>" style="float: right;" class="btn btn-primary" style="float: right;">Export CSV</a>
									</div>
									
									<div class="panel-body" style="margin-top: 70px;">

							<table width="100%" 
								class="table table-striped table-bordered table-hover"
								>
								<thead>
									<tr>
										<th>Customer Name</th>
										<th>Bill Name</th>
										<th>Bill Amount</th>
										<th>Bill Discount</th>
										<th>Bill Due Amount</th>
									</tr>
								</thead>
								<tbody>
									<%double OrderTotal = 0;
									if (orderslist != null && orderslist.size() > 0){  
									
									
									
									for(int count=0;count<orderslist.size();count++){
								      	Bill bill=(Bill)orderslist.get(count);
								      	OrderTotal+=bill.getBillDueAmount();
								      	
								      	
									%>
								   	<tr class="odd gradeX">
										<td><a href=""><%=bill.getCustomerName()%></a></td>
										<td><%=bill.getBillName() %></td>
										<td><%=bill.getBillAmount()%></td>
										<td><%=bill.getBillDiscount()%></td>
										<td><%=bill.getBillDueAmount()%></td>
										
									</tr>
									<%}
									//fw.flush();
									//fw.close();
									} %>
								</tbody>
							</table>
							</div>
							<div class="col-lg-3" style="margin-top: 10px;" ></div>
									<div class="col-lg-3" style="margin-top: 10px;float: right;" >
										<label>Total :</label> <label ><%=OrderTotal %></label>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.row -->

			<!-- /#page-wrapper -->
			<!-- /#wrapper -->
		</div>
	</div>
	<!-- jQuery -->
	<script src="/POS/resources/vendor/jquery/jquery.min.js"></script>
	<script type="text/javascript"
		src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.7/jquery.validate.min.js"></script>
	<!-- Bootstrap Core JavaScript -->
	<script src="/POS/resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="/POS/resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="/POS/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="/POS/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
	<script
		src="/POS/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="/POS/resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	

     <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/js/bootstrap-select.min.js"></script>
</body>

</html>
<%
}else{
	response.sendRedirect("/POS/index.jsp?Message=You are not authorize to login that page");
	}%>
