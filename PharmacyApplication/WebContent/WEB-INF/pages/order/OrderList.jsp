<%@page import="com.soft.pharmacy.model.Bill"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>POS</title>
<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script> 
<script src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/plug-ins/9dcbecd42ad/integration/jqueryui/dataTables.jqueryui.js">
</script>
<link rel="stylesheet" 
href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" 

href="https://cdn.datatables.net/plug-ins/9dcbecd42ad/integration/jqueryui/dataTables.jqueryui.css">
<!-- Bootstrap Core CSS -->
<link href="../resources/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">

<!-- MetisMenu CSS -->
<link href="../resources/vendor/metisMenu/metisMenu.min.css"
	rel="stylesheet">

<!-- Custom CSS -->
<link href="../resources/dist/css/sb-admin-2.css" rel="stylesheet">

<!-- Morris Charts CSS -->
<link href="../resources/vendor/morrisjs/morris.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="../resources/vendor/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<title>POS</title>
</head>
<body>
	<%
						String message = (String)request.getAttribute("Message");
						if (message == null) message="";
						
						String result = (String)request.getParameter("result");
						if(result==null)result="0";
						int Result = 0;
						try{
							Result = Integer.parseInt(result);
						}catch(Exception e)
						{
							Result = 0;
						}
						
						ArrayList orderslist = null;
						orderslist = (ArrayList)request.getAttribute("orderslist");
						
%>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Orders</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" style="height: 50px;">
							Order List <a class="btn btn-primary" style="float: right;"
								href="/POS/order/neworder?EnterprisedID=<%=us.getSysuser().getsysUserID()%>">New Order</a>
						</div>
						<%if(Result==1 && message.length() > 0){ %>
							<div class="panel-body">
								<div class="alert alert-success" align="center">
                                	<%=message %>
                            	</div>
							</div>
						<%}else if(Result==0 && message.length() > 0){ %>
							<div class="panel-body">
								<div class="alert alert-warning" align="center">
                                	<%=message %>
                            	</div>
							</div>
						<%} %>
						<!-- /.panel-heading -->
						
						<div class="panel-body">
						
							<table width="100%"
								class="table table-striped table-bordered table-hover"
								id="dataTables-example">
								<thead>
									<tr>
										<th>Customer Name</th>
										<th>Bill Name</th>
										<th>Net Amount</th>
										<th>Discount</th>
										<th>Due Amount</th>
										<th>Edit</th>
										<th>Delete</th>
										<th>PDF</th>
									</tr>
								</thead>
								<tbody>
								<%if (orderslist != null && orderslist.size() > 0){  %>
								<%for(int count=0;count<orderslist.size();count++){
							      	Bill bill=(Bill)orderslist.get(count);%>
									<tr class="odd gradeX">
										<td><a href=""><%=bill.getCustomerName()%></a></td>
										<td><%=bill.getBillName() %></td>
										<td><%=bill.getBillAmount()%></td>
										<td><%=bill.getBillDiscount()%></td>
										<td><%=bill.getBillDueAmount()%></td>
										<td align="center"><a href="/POS/order/editorder?EnterprisedID=<%=bill.getEnterprisedID()%>&BillID=<%=bill.getBillID()%>" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil" style="font-size: 14px;"></i></a></td>
    									<td align="center"><a href="/POS/order/deleteorder?EnterprisedID=<%=bill.getEnterprisedID()%>&BillID=<%=bill.getBillID()%>" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-trash" style="font-size: 14px;"></i></a></td>
    									<td align="center"><a href="/POS/order/orderpdf?EnterprisedID=<%=bill.getEnterprisedID()%>&BillID=<%=bill.getBillID()%>"><i class="fa fa-download" style="color: red;font-size: 18px;"></i></a></td>
									</tr>
									<%} %>
									<%} %>
								</tbody>
							</table>
							<!-- /.table-responsive -->

						</div>
						
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->

			<!-- /#page-wrapper -->
			<!-- /#wrapper -->
		</div>
	</div>
	<!-- jQuery -->
	<script src="../resources/vendor/jquery/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="../resources/vendor/bootstrap/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script src="../resources/vendor/metisMenu/metisMenu.min.js"></script>

	<!-- DataTables JavaScript -->
	<script
		src="../resources/vendor/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="../resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
	<script
		src="../resources/vendor/datatables-responsive/dataTables.responsive.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="../resources/dist/js/sb-admin-2.js"></script>

	<!-- Page-Level Demo Scripts - Tables - Use for reference -->
	<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>
</body>

</html>