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

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.10.0/css/bootstrap-select.min.css" />

<title>POS</title>
</head>
<%
				ArrayList producttypeslist = null;
				producttypeslist = (ArrayList)request.getAttribute("producttypeslist");
				
				ArrayList supplierslist = null;
				supplierslist = (ArrayList)request.getAttribute("supplierslist");
				
				Product product = (Product)request.getAttribute("product");
%>
<body>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header">Edit Product</h4>
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

								<form name="productedit" id="productedit" role="form"
									method="post" action="/POS/product/editproduct" style="padding: 20px 20px">
									<div class="col-lg-6">
										<label>Product Name</label> <input class="form-control"
											type="text" value="<%=product.getProductName() %>" name="ProductName" id="ProductName"
											placeholder="Enter Product Name ">
									</div>
									<input type="hidden" name="EnterprisedID" id="EnterprisedID"
										value="<%=product.getEnterprisedID()%>">
									
									<input type="hidden" name="ProductID" id="ProductID"
										value="<%=product.getProductID()%>">
									
									<div class="col-lg-6">
										<label>Product Code</label> <input type="text"
											class="form-control" placeholder="Enter Code"
											name="ProductCode" id="ProductCode" value="<%=product.getProductCode() %>" onchange="checkproductcode()"> <label
											id="Codeinfo" class="alert alert-success"
											style="display: none;"></label>
									</div>
									<div class="col-lg-6">
										<label>Product Type</label> <select name="ProductTypeID"
											id="ProductTypeID" class="form-control selectpicker" data-show-subtext="true" data-live-search="true">
											<option value="0">Select Product Type</option>
											<% for(int count=0;count<producttypeslist.size();count++){
							      				ProductType pt=(ProductType)producttypeslist.get(count);%>
											<option value="<%=pt.getProductTypeID()%>" <%=pt.getProductTypeID()==product.getProductTypeID()?"selected":"" %>><%=pt.getProductTypeName()%></option>
											<%} %>
										</select>
									</div>
									<div class="col-lg-6">
										<label>Supplier</label> <select name="SupplierID"
											id="SupplierID" class="form-control selectpicker" data-show-subtext="true" data-live-search="true">
											<option value="0">Select Supplier</option>
											<%for(int count=0;count<supplierslist.size();count++){
										      	Supplier sup=(Supplier)supplierslist.get(count);%>
											<option value="<%=sup.getSupplierID()%>" <%=sup.getSupplierID()==product.getSupplierID()?"selected":"" %>><%=sup.getCode()%>-<%=sup.getFirstName()%></option>
											<%} %>
										</select>
									</div>

									<div class="col-lg-6">
										<label>Selling Price</label> <input type="text"
											class="form-control" placeholder="Enter Selling Price"
											name="SellingPrice" id="SellingPrice" value="<%=product.getSellingPrice()%>" onkeypress="onlynumbers(this)">
									</div>
									<div class="col-lg-6">
										<label>Buying Price</label> <input class="form-control" type="text"
											name="BuyingPrice" id="BuyingPrice" value="<%=product.getBuyingPrice()%>"
											placeholder="Enter Buying Price" onkeypress="onlynumbers(this)">
									</div>
									
									<div class="col-lg-6">
										<label>Quantity</label> <input class="form-control"
											type="text" name="ProductQuantity" id="ProductQuantity" value="<%=product.getProductQuantity()%>"
											placeholder="Enter Quantity" onkeypress="onlynumbers(this)">
									</div>
									<div class="col-lg-6">
										
									</div>
									<div class="col-lg-6" style="margin-top: 10px;"></div>
									<div class="col-lg-6" style="margin-top: 10px; float: right;">
										<button type="submit" class="btn btn-primary"
											style="float: right">Save</button>
										<button type="reset" class="btn btn-default"
											style="float: right;margin-right: 10px;">Reset</button>
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
		<script>
$( document ).ready( function () {
	$( "#productedit" ).validate( {
		rules: {
			ProductName: "required",
		      ProductCode: {
		        required: true,
		        minlength: 4
		      },
		      ProductTypeID:
		    	  {selectproducttype : true},
		      SupplierID:{selectsupplier:true},
		      ProductMarkUp:{
		    	  maxlength:2,
		    	  required: true
		      },
		      SellingPrice:{
		    	  required:true
		      },
		      BuyingPrice:{
		    	  required:true
		      },
		      Quantity:{
		    	  required:true
		      }
		},
		messages: {
			  ProductName: "Please enter Product Name",
		      ProductCode: {
		        required: "Please provide a Code",
		        minlength: "Your Code must be at least 4 characters long"
		      },
		      ProductTypeID: "Please Select Product Type",
		      SupplierID: "Please Select Supplier",
		      SellingPrice:"Enter Selling Price",
		      BuyingPrice:"Enter Buying Price",
		      Quantity:"Enter Quantity"
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


function checkproductcode(){
	$("#Codeinfo").css('display','none')
	if($("#ProductCode").val().length > 0){
		$.ajax({
			url : "/POS/product/productcodecheck",
			type : "GET",
			data :{
				productCode:$("#ProductCode").val()
			},
			success : function(response) {
				alert(response)
				$("#Codeinfo").html(response)
				$("#Codeinfo").css('display','block')
				$("#Codeinfo").delay(1000).fadeOut();
				
			},
			error : function(e) {
				alert(e);
			}
		});
	}
	
}

function onlynumbers(field){
	 if (!String.fromCharCode(event.keyCode).match('[0-9.]') || (field.value.match('[.]') && String.fromCharCode(event.keyCode) == '.'))
        event.preventDefault();
	 
}
</script>
</body>

</html>
<%
}else{
	response.sendRedirect("/POS/index.jsp?Message=You are not authorize to login that page");
	}%>
