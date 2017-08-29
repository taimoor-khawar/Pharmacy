<%@page import="com.soft.pharmacy.model.Purchase"%>
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

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<title>POS</title>
<script >
function getpurchase(PurchaseID,EnterprisedID){
	 	 $.getJSON("/POS/order/getpurchase?PurchaseID="+PurchaseID+"&EnterprisedID="+EnterprisedID, null, 
					function(data) {
			 			$("#EnterprisedID").val(EnterprisedID);
			 			$("#ProductID").val(data.ProductID);
			 			$("#PurchaseID").val(PurchaseID);
			 			$("#SupplierID").val(data.SupplierID);
			 			$("#PurchaseID").val(PurchaseID);
			 			$("#OLDQuantity").val(data.OLDQuantity);
			 			$("#Quantity").val(data.Quantity);
			 			$("#edit").modal('show');
			 			
			});
	
}
</script>
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
						
						ArrayList purhaseorderlist = null;
						purhaseorderlist = (ArrayList)request.getAttribute("purhaseorderlist");
						
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
								href="/POS/order/newpurchase?EnterprisedID=<%=us.getSysuser().getsysUserID()%>">New Order</a>
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
										<th>Supplier Name</th>
										<th>Quantity</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>
								<%if (purhaseorderlist != null && purhaseorderlist.size() > 0){  %>
								<%for(int count=0;count<purhaseorderlist.size();count++){
							      	Purchase pr=(Purchase)purhaseorderlist.get(count);%>
									<tr class="odd gradeX">
										<td><a href=""><%=pr.getProductName()%></a></td>
										<td><%=pr.getSupplierName() %></td>
										<td><%=pr.getQuantity()%></td>
										<td align="center"><a onclick="getpurchase(<%=pr.getPurchaseID()%>,<%=pr.getEnterprisedID() %>)" class="btn btn-primary btn-xs"><span class="glyphicon glyphicon-pencil"></span></a></td>
    									<td align="center"><a href="/POS/order/deletepurchase?EnterprisedID=<%=pr.getEnterprisedID()%>&PurchaseID=<%=pr.getPurchaseID()%>" class="btn btn-danger btn-xs"><span class="glyphicon glyphicon-trash"></span></a></td>
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
					<h4 class="modal-title custom_align" id="Heading">Update Product</h4>
				</div>
				<div class="modal-body">
					<form name="purchaseedit" id="purchaseedit" role="form" method="POST" action="/POS/order/editpurchase"
						style="padding: 10px 10px">
						<input type="hidden" name="EnterprisedID" id="EnterprisedID" value="">
						<input type="hidden" name="ProductID" id="ProductID" value="">
						<input type="hidden" name="OLDQuantity" id="OLDQuantity" value="">
						<input type="hidden" name="SupplierID" id="SupplierID" value="">
						<input type="hidden" name="PurchaseID" id="PurchaseID" value="">
						<table>
						<tr>
						<!-- <td>
							<b>Choose the file To Upload:</b>
						</td> -->
						<tr>
							<tr>
								<td><label>Quantity</label><!-- </td>
								<td style="margin-left: 10px;">  --><input class="form-control"
											type="text" name="Quantity" id="Quantity"
											placeholder="Enter Quantity" onkeypress="onlynumbers(this)" value=""></td>
								<td><input type="submit" class="btn btn-primary"
											style="float: right; margin-top: 25px; margin-left: 10px;" value="Save"></td>
								<!-- <button type="submit" >Save</button>
										<button type="reset" class="btn btn-default"
											style="float: right;margin-right: 10px;">Reset</button> -->
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
    <script>
/* $( document ).ready( function () {
	$( "#purchaseedit" ).validate( {
		rules: {
			  Quantity:{
		    	  required:true
		      }
		},
		messages: {
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
}); */

function onlynumbers(field){
	 if (!String.fromCharCode(event.keyCode).match('[0-9.]') || (field.value.match('[.]') && String.fromCharCode(event.keyCode) == '.'))
        event.preventDefault();
	 
}
</script>
</body>

</html>