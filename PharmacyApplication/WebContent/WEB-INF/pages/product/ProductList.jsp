<%@page import="com.soft.pharmacy.model.Product"%>
<%@page import="com.soft.pharmacy.model.Supplier"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
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
<script 

src="https://code.jquery.com/jquery-1.11.1.min.js"></script> 

<script 

src="https://cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
<script 

src="https://cdn.datatables.net/plug-ins/9dcbecd42ad/integration/jqueryui/dataTables.jqueryui.js">
</script>
<link rel="stylesheet" 

href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" 

href="https://cdn.datatables.net/plug-ins/9dcbecd42ad/integration/jqueryui/dataTables.jqueryui.css">
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
						
						ArrayList productslist = null;
						productslist = (ArrayList)request.getAttribute("productslist");
												
%>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Product</h1>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<table>
								<tr width="100%">
									<td width="78%">Products List</td>
									<td><a class="btn btn-primary" style="float: right;"
										href="/POS/product/addproduct?EnterprisedID=<%=us.getSysuser().getsysUserID()%>">Add Product</a> 
										
										<a data-target="#edit" data-toggle="modal"
										class="btn btn-primary"
										> <span
											class="glyphicon glyphicon-pencil"></span> Import File
									</a></td>
								</tr>
							</table>
							<!-- <div>
							Supplier List </div>
							<div> <a class="btn btn-primary"
								href="/POS/config/supplier/add">Add Supplier</a>
							<a class="btn btn-primary" data-title="Edit" data-toggle="modal" data-target="#edit" >
							<span class="glyphicon glyphicon-pencil"></span> Import File</a></div>
						</div> -->
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
										<th>Product Name</th>
										<th>Product Code</th>
										<th>Type</th>
										<th>Supplier</th>
										<th>Buying Price</th>
										<th>Selling Price</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
									<%if (productslist != null && productslist.size() > 0){  %>
									<%for(int count=0;count<productslist.size();count++){
							      	Product pr=(Product)productslist.get(count);%>
									<tr class="odd gradeX">
										<td><a
											href="/POS/product/viewproduct?EnterprisedID=<%=pr.getEnterprisedID()%>&ProductID=<%=pr.getProductID()%>"><%=pr.getProductName()%></a></td>
										<td><%=pr.getProductCode()%></td>
										<td><%=pr.getProducTypeName()%></td>
										<td><%=pr.getSupplierName()%></td>
										<td><%=pr.getBuyingPrice() %></td>
										<td><%=pr.getSellingPrice() %></td>
										
										<td align="center"><a
											href="/POS/product/editproduct?EnterprisedID=<%=pr.getEnterprisedID()%>&ProductID=<%=pr.getProductID() %>"
											class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil" style="font-size: 14px;"></i></a></td>
										<td align="center"><a
											href="/POS/product/deleteproduct?EnterprisedID=<%=pr.getEnterprisedID()%>&ProductID=<%=pr.getProductID() %>"
											class="btn btn-danger btn-xs"><i
												class="glyphicon glyphicon-trash" style="font-size: 14px;"></i></a></td>
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
	<div class="modal fade" id="edit" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<h4 class="modal-title custom_align" id="Heading">Upload File</h4>
				</div>
				<div class="modal-body">
					<form name="supplierfile" role="form" method="POST" action="/POS/product/upload"
						style="padding: 10px 10px" enctype="multipart/form-data">
						<input type="hidden" name="EnterprisedID" id="EnterprisedID" value="<%=uss.getSysuser().getsysUserID()%>">
						<table>
						<tr>
						<td>
							<b>Choose the file To Upload:</b>
						</td>
						<tr>
							<tr>
								<td><INPUT NAME="file" TYPE="file"></td>
								<td >
									<p align="right">
										<INPUT TYPE="submit" class="btn btn-primary" VALUE="Upload">
									</p>
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- jQuery -->
	<script src="/POS/resources/vendor/jquery/jquery.min.js"></script>

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
	<script>
    $(document).ready(function() {
        $('#dataTables-example').DataTable({
            responsive: true
        });
    });
    </script>
</body>

</html>
<%
}else{
	response.sendRedirect("/POS/index.jsp?Message=You are not authorize to login that page");
	}%>