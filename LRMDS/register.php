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
		$role = $_POST['role'];
		$action = $_GET['action'];
		
		switch($role)
		{
			case "teacher": $group_id = 13;
							break;
			case "adviser": $group_id = 14;
							break;
			default: $group_id = 0;
		}
		
		if($action=="error")
		{
			$message = "<div class=\"warning\"><img src=\"images/icon/warning.png\" /> There was an error in your registration.</div>";
		}
		else
		{
			$message = "";
		}
		
	}
?>

<html>
	<head>
		<title>LRMDS - Register as <?php echo $role; ?></title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />
		<link href="css/login.css" type="text/css" rel="stylesheet" />
		
		<script src="prototype.js" type="text/javascript" /></script>
		<!-- This is to restrict the Office ID input into numbers only -->
		<SCRIPT TYPE="text/javascript">
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

		//-->
		</SCRIPT>
		<script>
		
			window.onload = periodicCheck();
			function periodicCheck()
			{
				var timer = setInterval(function(){checkField('all')},3000);
			}
			
			function stopCheck()
			{
				clearInterval(timer);
			}
			
			function checkField(fieldname) {
				if(fieldname=='all')
				{
					new Ajax.Updater('check-all', 'verify_register.php?fieldname=' +fieldname+ '&gid=' +<?php echo $group_id; ?>, { method: 'post', parameters: $('register').serialize() });
				}
				
				else
				{
					new Ajax.Updater('check-'+fieldname, 'verify_register2.php?fieldname=' +fieldname, { method: 'post', parameters: $('register').serialize() });
				}
			}
			
			function loadSubjects(choice)
			{
				var xmlhttp;
				if (choice=="")
				{
					document.getElementById("subjects").innerHTML="";
					return;
				}
				if (window.XMLHttpRequest)
				{ xmlhttp = new XMLHttpRequest(); }
				
				else { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); }
				
				xmlhttp.onreadystatechange=function()
				{
					if (xmlhttp.readyState==4 && xmlhttp.status==200)
					{
						document.getElementById("subjects").innerHTML=xmlhttp.responseText;
					}
				}
				
				xmlhttp.open("GET","list_subjects.php?g_id="+choice,true);
				xmlhttp.send();
				
				checkField("specialization");			
				
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
				<h1 class="login">Register as</h1>
				<div id="login-box">
					<h3 class="login">
						<?php
							if($role == "teacher")
							{
								echo "<img src=\"images/icon/teacher.png\" /> ";
							}
							if($role == "adviser")
							
							{
								echo "<img src=\"images/icon/adviser.png\" /> ";
							}
						 echo $role;
						 ?>
					</h3>
					
					<div id="check-all">
						<?php echo $message; ?>
					</div>					
							<form name=register method=POST onsubmit=stopCheck() action="process_registration.php?group_id=<?php echo $group_id; ?>" id="register" accept-charset="UTF-8">
							<table cellspacing=10 cellpadding=5 class="centered" =1>
								<tr>
									<td><label class="login">Last Name:</label></td>
									<td><input class="logintext" type="text" name="lname"  onkeyup=checkField("lname") /></td>
									<td width><div class="check"><div id="check-lname"></div></div></td>
								</tr>
								<tr>
									<td><label class="login">First Name:</label></td>
									<td><input class="logintext" type="text" name="fname"  onkeyup=checkField("fname") /></td>
									<td><div class="check"><div id="check-fname"></div></div></td>
								</tr>
								<tr>
									<td><label class="login">Middle Name:</label></td>
									<td><input class="logintext" type="text" name="mname" onkeyup=checkField("mname") /></td>
									<td><div class="check"><div id="check-mname"></div></div></td>
								</tr>
								<tr>
									<td><label class="login">E-mail Address:</label></td>
									<td><input class="logintext" type="text" name="username" onkeyup=checkField("username") /></td>
									<td><div class="check"><div id="check-username"></div></div></td>
								</tr>
								<tr>
									<td><label class="login">Password:</label></td>
									<td><input class="logintext" type="password" name="password1" onkeyup=checkField("password1") ><br />
									<td><div class="check"><div id="check-password1"></div></td>
								</tr>
								<tr>
									<td><label class="login">Confirm Password:</label></td>
									<td><input class="logintext" type="password" name="password2" onkeyup=checkField("password2") autocomplete="off"></td>
									<td><div class="check"><div id="check-password2"></div></div>
								</tr>
								<tr>
									<td><label class="login">School ID:</label></td>
									<td><input class="logintext" type="text" onKeyPress="return numbersonly(this, event)" maxlength=6 size=6 name="school_id"></td>
									<td>&nbsp;</td>
								</tr>
							
								<?php 
									if($role == "teacher"){
								?>
									<tr>
										<td colspan=2><label class="login">Subject Specialization:</label></td>
											<td>
												<div class="check"></div>
											</td>
									</tr>
								
									<tr>
										<td colspan=2>
											<table class="centered" cellspacing=0 cellpadding=0>
												<tr>
													<td><label class="login2">Grade Level: </label></td>
													<td>
														<?php								
															echo "<select name=\"grade_type\" onchange=loadSubjects(this.value)>";
															echo "<option value=\"0\">Select Grade Level</option>";
																$q_gradelevel = mysql_query("SELECT grade_types.id as id, grade_types.name as g_name FROM grade_types", $myConnection);
																while($gradelevel = mysql_fetch_array($q_gradelevel))
																{
																	echo "<option value=\"";
																	echo $gradelevel['id'];
																	echo "\">";
																	echo $gradelevel['g_name'];
																	echo "</option>";
																}
															
															echo"</select>";
														?>
													</td>
												</tr>
												<tr>
													<td colspan=2>
														<div class="right"><div id="subjects">
															<select style="visibility: hidden;" name="curriculum_area_type" onchange=checkField("subjects")>
																<option value=0>Select Subject</option>
															</select>
														</div>
													</td>
												</tr>
											</table>
										</td>
										<td>
											<table>
												<tr>
													<td>
														<div class="check"><div id="check-specialization">
														</div></div>
													</td>
												</tr>
												
													<td><div class="check">
														<div id="check-subjects"></div></div>
													</td>
												</tr>
											</table>
										</td>
									</tr>
											
										<?php
										}
										?>
										<tr>
											<td colspan=2>
												<div class="login-buttons">
													<input class="submit-light" type="submit" value="Register" />
													<input class="clearforms" type="reset" value="Clear Fields" />
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