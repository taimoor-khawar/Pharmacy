<%@page import="com.soft.pharmacy.model.Customer"%>
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
<script >
function getproduct(){
	 if($("#ProductID").val() != 0){
		 var TotalRow = parseInt($("#TotalRow").val());
		 $.getJSON("/POS/product/getproduct?ProductID=" + $("#ProductID").val(), null, 
					function(data) {
			 			var row=
			 			'<tr>'+
			 			'<input type="hidden" name="ProductCartID" id="ProductCartID" value="'+data.ProductID+'" />'+
			 			'<td>'+data.ProductName+'</td>'+
			 			'<td><input type="text" style=width:120px; name="ProductPrice' + data.ProductID + '" onkeyup="calculateRow();" value="'+data.SellingPrice+'" class="ProductPrice form-control" onkeypress="onlynumbers(this)"/></td>'+
			 			'<td><input style=width:120px; class="ProductQuantity form-control" type="text" name="ProductQuantity' + data.ProductID + '" value="'+data.ProductQuantity+'" onkeyup="calculateRow()" onkeypress="onlynumbers(this)"/></td>'+
			 			'<td><input style=width:120px; class="NetPrice form-control" type="text" name="NetPrice' + data.ProductID + '" onkeypress="onlynumbers(this)"/></td>'+
			 			'<td><input style=width:120px; class="Discount form-control" type="text" name="Discount' + data.ProductID + '" onkeyup="calculateRow()" value="0" onkeypress="onlynumbers(this)"/></td>'+
			 			'<td><input style=width:120px; class="TotalPrice form-control" type="text" name="TotalPrice' + data.ProductID + '" onkeypress="onlynumbers(this)"/></td></tr>';
			 			
			 			$("#ordertable").append(row);
				        TotalRow = parseInt(TotalRow + 1);
				        $("#TotalRow").val(TotalRow);
				        CalculateTotaaal();
			});
	}
}

  function CalculateTotaaal(ProductID){
	 var NetPrice = 0;
	    //iterate through each textboxes and add the values
	    $(".ProductPrice").each(function () {
	        //add only if the value is number
	        if (!isNaN(this.value) && this.value.length != 0) {
	        	NetPrice = parseFloat( $(".ProductPrice").val()) * parseFloat( $(".ProductQuantity").val());
	        	$(".NetPrice").val(NetPrice);
	        	$(".TotalPrice").val(NetPrice);
	        }
	    });
	    calculateSum();
}
 
 function calculateRow() {
		$('.ProductQuantity, .ProductPrice, .Discount').change(function () {
	        var cost = 0;
	        var $row = $(this).closest("tr");
	        var qty = parseFloat($row.find('.ProductQuantity').val());
	        var rate = parseFloat($(".ProductPrice").val());
	        var discount = parseFloat($row.find('.Discount').val());
	        cost = qty * rate;
	        var totalPrice = cost - discount;
	        //        alert($("#rate").val());

	        if (isNaN(cost)) {
	            $row.find('.NetPrice').val("Nix is");
	        } else {
	            $row.find('.NetPrice').val(cost);
	            $row.find('.TotalPrice').val(totalPrice);
	        }
	        calculateSum();
	    })
 }
 
 function calculateSum() {

	    var sum = 0;
	    //iterate through each textboxes and add the values
	    $(".TotalPrice").each(function () {
	        //add only if the value is number
	        if (!isNaN(this.value) && this.value.length != 0) {
	            sum += parseFloat(this.value);
	        }
	    });
	    //.toFixed() method will roundoff the final sum to 2 decimal places
	    $("#OrderTotal").val(sum.toFixed(2));
	}

 function onlynumbers(field){
	 if (!String.fromCharCode(event.keyCode).match('[0-9.]') || (field.value.match('[.]') && String.fromCharCode(event.keyCode) == '.'))
         event.preventDefault();
	 
 }
</script>
</head>
<%
				ArrayList productslist = null;
				productslist = (ArrayList)request.getAttribute("productslist");
				
				ArrayList customerslist = null;
				customerslist = (ArrayList)request.getAttribute("customerslist");
				
			
%>
<body>
	<div id="wrapper">
		<%@include file="../menu.jsp"%>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h4 class="page-header">New Order</h4>
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
									method="post" action="/POS/order/postorder" style="padding: 20px 20px">
									<input type="hidden" name="TotalRow" id="TotalRow" value="0">
									<input type="hidden" name="EnterprisedID" id="EnterprisedID" value="<%=uss.getSysuser().getsysUserID()%>">
									<div class="col-lg-6">
										<label>Product</label> <select name="ProductID"
											id="ProductID" class="selectpicker form-control" 
											data-show-subtext="true" data-live-search="true" onchange="getproduct()">
											<option value="0">Select Product</option>
											<% for(int count=0;count<productslist.size();count++){
							      				Product pr=(Product)productslist.get(count);%>
											<option value="<%=pr.getProductID()%>"><%=pr.getProductName()%></option>
											<%} %>
										</select>
									</div>
									
									<div class="col-lg-6">
										<label>Customer</label> <select name="CustomerID"
											id="CustomerID" class="form-control selectpicker" data-show-subtext="true" data-live-search="true">
											<option value="0">Select Customer</option>
											<%for(int count=0;count<customerslist.size();count++){
										      	Customer cus=(Customer)customerslist.get(count);%>
											<option value="<%=cus.getCustomerID()%>"><%=cus.getFirstName()%>-<%=cus.getLastName() %></option>
											<%} %>
										</select>
									</div>
									
									<div class="panel-body" style="margin-top: 70px;">

							<table width="100%" 
								class="table table-striped table-bordered table-hover"
								>
								<thead>
									<tr>
										<th>Product Name</th>
										<th>Product Price</th>
										<th>Quantity</th>
										<th>Net Price</th>
										<th>Discount</th>
										<th>Total Price</th>
									</tr>
								</thead>
								<tbody id="ordertable">
									
								</tbody>
							</table>
							</div>
							<div class="col-lg-6" style="margin-top: 10px;"></div>
									<div class="col-lg-6" style="margin-top: 10px;width: 300px;margin-left: 210px;">
										<label>Order Total</label> <input class="form-control" type="text"
											name="OrderTotal" id="OrderTotal" onkeypress="onlynumbers(this)"
											placeholder="OrderTotal" value="0">
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
	$( "#newOrder" ).validate( {
		rules: {
			ProductID:
		    	  {selectproduct : true},
		    	  CustomerID:{selectcustomer:true},
		},
		messages: {
		      ProductID: "Please Select Product",
		      CustomerID: "Please Select Customer",
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
	
	jQuery.validator.addMethod('selectproduct', function (value) {
        return (value != '0');
    }, "Product required");
	
	jQuery.validator.addMethod('selectcustomer', function (value) {
        return (value != '0');
    }, "Customer required");

});


</script>
</body>

</html>
<%
}else{
	response.sendRedirect("/POS/index.jsp?Message=You are not authorize to login that page");
	}%>
