<%@page import="com.soft.pharmacy.model.Customer"%>
<%@page import="com.soft.pharmacy.model.SysUserSession"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
SysUserSession uss = (SysUserSession)session.getAttribute(session.getId());
if( uss != null){%>
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
<%
						Customer customer = (Customer)request.getAttribute("customer");
						
%>
<body>
	<div id="wrapper">
		<%@include file="../menu.jsp" %>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header">View Customer</h4>
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

								<form name="customeredit" role="form" method="post" action="/POS/customer/list" style="padding: 10px 10px">
									
                                    
									<div class="col-lg-6">
										<label>First Name</label> <input class="form-control" type="text" value="<%=customer.getFirstName() %>"
											name="FirstName" id="FirstName" placeholder="Enter First Name" disabled="disabled">
									</div>
									<input type="hidden" name="EnterpriseID" id="EnterpriseID" value="<%=us.getSysuser().getsysUserID()%>">
									<input type="hidden" name="CustomerID" id="CustomerID" value="<%=customer.getCustomerID()%>">
									<div class="col-lg-6">
										<label>Last Name</label> <input type="text" value="<%=customer.getFirstName() %>"
											class="form-control" placeholder="Enter Last Name" name="LastName" id="LastName" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Email</label> <input class="form-control" type="email" value="<%=customer.getEmail() %>"
											name="Email" id="Email" placeholder="Enter Email" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Phone Number</label> <input type="text" value="<%=customer.getPhoneNumber() %>"
											class="form-control" placeholder="Enter Phone Number" name="PhoneNumber" id="PhoneNumber" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Address 1</label> <textarea class="form-control" name="Address1" id="Address1" rows="10" cols="10" disabled="disabled"><%=customer.getAddress1()%></textarea> 
									</div>
									<div class="col-lg-6">
										<label>Address 2</label> <textarea class="form-control" name="Address2" id="Address2" rows="10" cols="10" disabled="disabled"><%=customer.getAddress2()%></textarea> 
									</div>
									<div class="col-lg-6">
										<label>State/Province</label> <input class="form-control" type="text" value="<%=customer.getState() %>"
											name="State" id="State" placeholder="Enter State" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Zip</label> <input type="text" value="<%=customer.getZip() %>"
											class="form-control" placeholder="Enter Zip" name="Zip" id="Zip" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Country</label> <input class="form-control" type="text" value="<%=customer.getCountry() %>"
											name="Country" id="Country" placeholder="Enter Country" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Comments</label> <input type="text" value="<%=customer.getComments() %>"
											class="form-control" placeholder="Enter Comments" name="Comments" id="Comments" disabled="disabled">
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
</body>

</html>
<%
}else{
	response.sendRedirect("/POS/index.jsp?Message=You are not authorize to login that page");
	}%>
