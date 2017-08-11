<%@page import="com.soft.pharmacy.model.Customer"%>
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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

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
						
						ArrayList customerlist = null;
						customerlist = (ArrayList)request.getAttribute("customerslist");
						System.out.println(customerlist.size());
						
						
						
						
%>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Customer</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading" style="height: 50px;">
							Customer List <a class="btn btn-primary" style="float: right;"
								href="/POS/customer/add">Add Customer</a>
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
										<th>First Name</th>
										<th>Last Name</th>
										<th>Email</th>
										<th>Phone Number</th>
										<th>Country</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
								<%if (customerlist != null && customerlist.size() > 0){  %>
								<%for(int count=0;count<customerlist.size();count++){
							      	Customer cus=(Customer)customerlist.get(count);%>
									<tr class="odd gradeX">
										<td><a href="/POS/customer/view?EnterprisedID=<%=cus.getEnterpriseID()%>&CustomerID=<%=cus.getCustomerID() %>"><%=cus.getFirstName()%></a></td>
										<td><%=cus.getLastName()%></td>
										<td><%=cus.getEmail()%></td>
										<td><%=cus.getPhoneNumber()%></td>
										<td><%=cus.getCountry() %></td>
										<td align="center"><a href="/POS/customer/edit?EnterprisedID=<%=cus.getEnterpriseID()%>&CustomerID=<%=cus.getCustomerID() %>" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span></a></td>
    									<td align="center"><a href="/POS/customer/delete?EnterprisedID=<%=cus.getEnterpriseID()%>&CustomerID=<%=cus.getCustomerID() %>" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span></a></td>
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