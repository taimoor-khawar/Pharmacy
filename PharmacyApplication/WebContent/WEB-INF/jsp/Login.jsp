<html>
<head>
  <title>College Education File System</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  function reset(){
	  document.getElementById("Email").value == "";
	  document.getElementById("Password").value == "";
	  document.getElementById("UserType").value == "0";
  }
  
  function checkdata(f){
	  if (document.getElementById("Email").value == "0"){
			alert("Please enter Email");
			return false;
		}else if(document.getElementById("Password").value == ""){
			alert("Please enter Password");
			return false;
		}else if(document.getElementById("UserType").value == "0"){
			alert("Please enter UserTypeID");
			return false;
		} else {
			return true;
		}  
	  
  };
  </script>
</head>
<body>
<div class="container" align="center" style="width: 700px;height: 300px;margin-top: 20px;">
  <h5>${model.admin.AName}</h5>
  <form class="form-horizontal" id="loginform" name="loginform" action="./mainlogin" method="post" onsubmit="return checkdata(loginform)">
    <div class="form-group">
      <label class="control-label col-sm-2" for="email">Email:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="Username" name="Username" placeholder="Enter Username" required="required">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pwd">Password:</label>
      <div class="col-sm-10">          
        <input type="password" class="form-control" id="Password" name="Password" placeholder="Enter password">
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10" align="left">
        <button type="submit" class="btn btn-success" ><span class="glyphicon glyphicon-ok" style="padding-right: 5px;"></span>Submit</button>
        <button type="button" class="btn btn-danger" onclick="reset()"><span class="glyphicon glyphicon-refresh" style="padding-right: 5px;"></span>Reset</button>
      </div>
    </div>
  </form>
</div>
</body>
</html>
