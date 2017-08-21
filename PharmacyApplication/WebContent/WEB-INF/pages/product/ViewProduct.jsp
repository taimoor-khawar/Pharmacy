<%@page import="com.soft.pharmacy.model.Product"%>
<%@page import="com.soft.pharmacy.model.Supplier"%>
<%@page import="com.soft.pharmacy.model.ProductType"%>
<%@page import="java.util.ArrayList"%>
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

<title>POS</title>
</head>
<%
				Product product = (Product)request.getAttribute("product");
%>
<body>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header">View Product</h4>
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

								<form name="productadd" id="productadd" role="form"
									method="post" action="" style="padding: 20px 20px">
									<div class="col-lg-6">
										<label>Product Name</label> <input class="form-control"
											type="text" value="<%=product.getProductName() %>" name="ProductName" id="ProductName" disabled="disabled"
											placeholder="Enter Product Name ">
									</div>
									<input type="hidden" name="EnterpriseID" id="EnterpriseID"
										value="<%=us.getSysuser().getsysUserID()%>">

									<div class="col-lg-6">
										<label>Product Code</label> <input type="text"
											class="form-control" placeholder="Enter Code"
											name="ProductCode" id="ProductCode" value="<%=product.getProductCode() %>" disabled="disabled"> <label
											id="Codeinfo" class="alert alert-success"
											style="display: none;"></label>
									</div>
									<div class="col-lg-6">
										<label>Product Type</label>
											<input type="text"
											class="form-control" placeholder="Enter Code"
											name="" id="" value="<%=product.getProducTypeName()%>" disabled="disabled">
											
										
									</div>
									<div class="col-lg-6">
										<label>Supplier</label> 
										<input type="text"
											class="form-control" placeholder="Enter Code"
											name="" id="" value="<%=product.getSupplierName()%>" disabled="disabled">
									</div>

									<div class="col-lg-6">
										<label>Selling Price</label> <input type="text"
											class="form-control" placeholder="Enter Supply Price"
											name="SupplyPrice" id="SupplyPrice" value="<%=product.getSellingPrice()%>" disabled="disabled">
									</div>
									<div class="col-lg-6">
										<label>Buying Price</label> <input class="form-control" type="text"
											name="ProductMarkUp" id="ProductMarkUp" value="<%=product.getBuyingPrice()%>"
											placeholder="Enter Mark Up" disabled="disabled">
									</div>
									
									<div class="col-lg-6">
										<label>Quantity</label> <input class="form-control"
											type="text" name="ProductQuantity" id="ProductQuantity" value="<%=product.getProductQuantity()%>"
											placeholder="Enter Quantity" disabled="disabled">
									</div>
									<div class="col-lg-6">
										
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
	<script>
$( document ).ready( function () {
	$( "#productadd" ).validate( {
		rules: {
			ProductName: "required",
		      ProductCode: {
		        required: true,
		        minlength: 4
		      },
		      ProductTypeID:
		    	  {selectproducttype : true},
		      SupplierID:{selectsupplier:true}
		},
		messages: {
			  ProductName: "Please enter Product Name",
		      ProductCode: {
		        required: "Please provide a Code",
		        minlength: "Your Code must be at least 4 characters long"
		      },
		      ProductTypeID: "Please Select Product Type",
		      SupplierID: "Please Select Supplier"
		},
		errorElement: "em",
		errorPlacement: function ( error, element ) {
			// Add the `help-block` class to the error element
			error.addClass( "help-block" );
			if ( element.prop( "type" ) === "checkbox" ) {
				error.insertAfter( element.parent( "label" ) );
				
			} else {
				error.insertAfter( element );
			}
			
			if ( !element.next( "span" )[ 0 ] ) {
				$( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
			}
		},
		 highlight: function ( element, errorClass, validClass ) {
			 $( element ).parents( ".col-lg-6" ).addClass( "has-error" ).removeClass( "has-success" );
			 $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );
		},
		unhighlight: function (element, errorClass, validClass) {
			$( element ).parents( ".col-lg-6" ).addClass( "has-success" ).removeClass( "has-error" );
			$( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
		}
	});
	
	jQuery.validator.addMethod('selectproducttype', function (value) {
        return (value != '0');
    }, "Product Type required");
	
	jQuery.validator.addMethod('selectsupplier', function (value) {
        return (value != '0');
    }, "Supplier required");

});
</script>
</body>

</html>
<%
}else{
	response.sendRedirect("/POS/index.jsp?Message=You are not authorize to login that page");
	}%>
