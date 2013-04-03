<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{
		
	}
?>

<html>
	<head>
		<title>LRMDS - Register for Account</title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />
		<link href="css/login.css" type="text/css" rel="stylesheet" />
		
		<!-- This is to restrict the Office ID input into numbers only -->
		<script type="text/javascript">
			<!--
			// copyright 1999 Idocs, Inc. http://www.idocs.com
			// Distribute this script freely but keep this notice in place
			function numbersonly(myfield, e, dec)	
			{
				var key;
				var keychar;
				
				if (window.event)
				   key = window.event.keyCode;
				else if (e)
				   key = e.which;
				else
				   return true;
				keychar = String.fromCharCode(key);
				
				// control keys
				if ((key==null) || (key==0) || (key==8) || 
					(key==9) || (key==13) || (key==27) )
				   return true;
				
				// numbers
				else if ((("0123456789").indexOf(keychar) > -1))
				   return true;
				
				// decimal point jump
				else if (dec && (keychar == "."))
				   {
				   myfield.form.elements[dec].focus();
				   return false;
				   }
				else
				   return false;
			}


		</script>
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
				<h1 class="login">Register</h1>		
				<div id="login-box">
					<h3 class="login">Select Account Type</h3>
					<form name="register_as" method="POST" action="register.php?action=new">
						<table width=250px cellspacing=0 cellpadding=0 class="centered">
							<tr>
								<td>
									<div class="select">
										<table>
											<tr>
												<td>
													<img src="images/icon/teacher.png" title="Teacher" class="centered" />
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" name="role" value="teacher" />
													<label class="login">Teacher</label>
												</td>
											</tr>
										</table>
									</div>
								</td>
								<td style="border-left: 1px dotted #5a7037;">
									<div class="select">
										<table width="100%">
											<tr>
												<td>
													<img src="images/icon/adviser.png" title="Adviser" class="centered" />
												</td>
											</tr>
											<tr>
												<td>
													<input type="radio" name="role" value="adviser" />
													<label class="login">Adviser</label>
												</td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan=2>									
									<div class="login-buttons">
										<input class="submit-light" type="submit" value="Select">
									</div>
								</td>
							</tr>
						</table>
					</form>				
				</div>				
				
				<div id="requests-content">
				</div>
				
			</div> <!-- # page content -->
		</div> <!-- # main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>

<?php mysql_close($myConnection); ?>