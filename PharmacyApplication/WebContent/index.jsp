<html >
<head>
  <title>College Education File System</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <link href='https://fonts.googleapis.com/css?family=Passion+One' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Oxygen' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="./css/stylesheet.css">
</head>
<body>
<%
		String Message = "";//request.getParameter("Message");
		//if (Message == null) Message="";
%>
<div class="container" align="center" style="width: 900px;height: 350px;margin-top: 20px;background-color:#D3D3D3">
  <%if(Message != null && Message != ""){ %>
  	<h4 align="center" style="margin-top: 10px;color: red;"><%=Message %></h4>
  <%} %>
  <h2 align="center" style="margin-top: 50px;">College Education File System</h2>
  <h4 align="center" style="margin-top: 20px;"><b>Here is your VU ID</b></h4>
  <div align="center" style="margin-top: 20px;">
  		<a href="./login" style="font-size: 22px;font-weight: bold;"><span class="glyphicon glyphicon-log-in" style="padding-right: 5px;"></span>Login</a>  
  		<a href="./Register" style="font-size: 22px;font-weight: bold;"><span class="glyphicon glyphicon-user" style="padding-right: 5px;"></span>Register</a>
  </div>
</div>

</body>
</html>
