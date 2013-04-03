<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $myConnection);
	
	if (!$myConnection)
	{
		die("Failed to connect: ");
	}
	else
	{
		$action = $_GET['action'];
		$message = "";
		
		if($action=="success")
		{
			$message = "<div class=\"success\"><img src=\"images/icon/success.png\" /> Successfully created account. </div>";
		}
		
		if($action=="change")
		{
			$message = "<div class=\"success\"><img src=\"images/icon/success.png\" /> Successfully changed account details. </div>";
		}
		
		if($action=="error")
		{
			$message = "<div class=\"warning\"><img src=\"images/icon/warning.png\" /> Invalid username/password.</div>";
		}
	}
?>
<html>
	<head>
		<title>LRMDS</title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />	
		<link href="css/login.css" type="text/css" rel="stylesheet" />			
		<script src="prototype.js" type="text/javascript" /></script>
	</head>
	<body id="login-bg">
		<div id="logo">
		
		</div>
		
		<div id="tagline">
			Learning Resource Management and Development System				
		</div>
		
		<div id="header">
		
		</div>
		
		<div id="main-container">
			<div id="login">		
				<h1 class="login">Login</h1>	
				<div id="login-box">
				
				<?php echo $message; ?>
				
					<form method="POST" action="login.php">
						<table width=100% cellspacing=10 cellpadding=5 class="centered">
							<tr>
								<td>
									<label class="login">Username:</label>
								</td>
								<td>
									<input type="text" name="username" class="logintext" />
								</td>
							</tr>
							<tr>
								<td>
									<label class="login">Password:</label>
								</td>
								<td>
									<input type="password" name="password" class="logintext" />
								</td>
							</tr>
							<tr>
								<td colspan=2>
									<div class="login-buttons">
										<input type="submit" class="submit-light" />
										<a class="tool" href="register_select.php">Register</a>
									</div>
								</td>
							</tr>
						</table>						
					</form>					
				
				</div>
			</div>
		</div>
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
	</body>
</html>

<?php mysql_close($myConnection); ?>