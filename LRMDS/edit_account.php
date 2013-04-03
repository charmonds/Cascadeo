<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	$page_id = 3;
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];
		$action = $_GET['action'];
		$message1 = "";
		$message2 = "";
		
		$uname = mysql_query("SELECT getUsingUID('u', $uid) AS username, getUsingUID('f', $uid) AS first_name", $myConnection);	
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
		
		$resultSet = mysql_query("SELECT getUsingUID('l', $uid) AS last_name, getUsingUID('f', $uid) AS first_name, getUsingUID('d', $uid) AS description", $myConnection);
			
		$account = mysql_fetch_array($resultSet);
		$fetch_subject_area = mysql_query("
		SELECT grade_types.name as level,
		curriculum_area_types.name as subject_name
		
		FROM grade_types JOIN education_types
		ON grade_types.education_type_id = education_types.id
		JOIN curriculum_area_types
		ON grade_types.id = curriculum_area_types.grade_type_id
		JOIN users
		ON users.curriculum_area_type_id = curriculum_area_types.id
		WHERE users.id = $uid;		
		", $myConnection);
		
		$subject_area = mysql_fetch_array($fetch_subject_area);
		
		if($action=="error1")
		{
			$message1 = "<div class=\"warning\"><img src=\"images/icon/warning.png\" /> There was an error in your request to change password. 	Please try again.</div>";
		}
		
		if($action=="error2")
		{
			$message2 = "<div class=\"warning\"><img src=\"images/icon/warning.png\" /> There was an error in your request to change e-mail address. Please try again.</div>";
		}
		
	}
?>

<html>
	<head>
		<title>LRMDS - <?php echo $uname1['username'] ; ?> -  Account Settings</title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />		
		<link href="css/account.css" type="text/css" rel="stylesheet" />			
		<link href="css/userbox.css" type="text/css" rel="stylesheet" />			
		<link href="css/login.css" type="text/css" rel="stylesheet" />
		<script src="prototype.js" type="text/javascript" /></script>
		<script>
			function periodicCheck(fieldname,formname)
			{
				if(formname == "change_password")
				{
					var timer1 = setInterval(function(){checkField(fieldname,formname)},500);
					setTimeout(stopCheck(formname),10000);
				}

				if(formname == "change_email")
				{
					var timer2 = setInterval(function(){checkField(fieldname,formname)},500);
					setTimeout(stopCheck(formname),10000);
				}
			}
			
			function stopCheck(formname)
			{
				if(formname == "change_password")
				{
					clearInterval(timer2);
				}
				
				if(formname == "change_email")
				{
					clearInterval(timer1);
				}
				
				if(formname == "all")
				{
					clearInterval(timer1);
					clearInterval(timer2);
				}
			}
			
			function checkField(fieldname, formname) {
				if(formname == "change_password")
				{
					new Ajax.Updater('check-'+fieldname, 'verify_password_change.php?fieldname=' +fieldname+ '&uid=<?php echo $uid; ?>&action=check', { method: 'post', parameters: $('change_password').serialize() });
				}
				
				if(formname == "change_email")
				{
					new Ajax.Updater('check-'+fieldname, 'verify_email_change.php?fieldname=' +fieldname+ '&uid=<?php echo $uid; ?>&action=check', { method: 'post', parameters: $('change_email').serialize() });
				}
			}
			
			function showUserBox()
			{
				var userBox = document.getElementById('user-box');
				var accountBox = document.getElementById('account-box');
				
				if(userBox.style.display == "block")
				{
					userBox.style.display = "none";
				}
				else
				{
					userBox.style.display = "block";
				}
			}
		</script>
	</head>
	<body>
	<?php include('userbox.php'); ?>
		<div id="logo">
		
		</div>
		
		<div id="tagline">
			Learning Resource Management and Development System				
		</div>
		
		<div id="account-box" onClick="showUserBox()">				
			<img src="images/icon/user.png" title="user_icon" class="icon" />
			<?php echo $username; ?>
		</div>
		
		<div id="header">
		
		</div>
		
		<div id="main-container">
			
			<div id="page-content">			
				
				<div id="menu-bar">
					<?php include('menu.g'); ?>
				</div>
				
				<div id="status-bar">				
					Today is <?php $date = date('l, F j, Y', time()); echo $date; ?>.
					<h1>Edit Account</h1>
				</div>
				
				<div class="function-group1">
					<?php echo $message1; ?>
					<div class="centered">
						<form name="change_password" onsubmit=stopCheck("all") method="POST" action="process_change.php?uid=<?php echo $uid;?>&form=change_password" id="change_password">
							<table cellspacing=10 cellpadding=5 class="centered">
								<tr>
									<td><label class="login">Old Password:</td>
									<td width="5px">&nbsp;</td>
									<td><input type="password" name="oldpassword" onkeyup=periodicCheck("oldpassword","change_password") /></td>
									<td><div class="check"><div id="check-oldpassword"></div></div></td>
								</tr>
								<tr>
									<td><label class="login">New Password:</td>
									<td>&nbsp;</td>
									<td><input type="password" name="newpassword1" onkeyup=periodicCheck("newpassword1","change_password")  /></td>
									<td><div class="check"><div id="check-newpassword1"></div></div></td>
								</tr>
								<tr>
									<td><label class="login">Confirm New Password:</td>
									<td>&nbsp;</td>
									<td><input type="password" name="newpassword2" onkeyup=periodicCheck("newpassword2","change_password")  /></td>
									<td><div class="check"><div id="check-newpassword2"></div></div></td>
								</tr>
							</table>
							
								<input class="submit-light" type="submit" value="Change Password" />
								<input class="clearforms" type="reset" value="Clear Fields" />
							
						</form>
					</div>
					<div class="centered">
					<?php echo $message2; ?>
						<form name=change_email onsubmit=stopCheck("all") method=POST action="process_change.php?uid=<?php echo $uid;?>&form=change_email" id="change_email">
							<table cellspacing=10 cellpadding=5 class="centered">
								<tr>
									<td><label class="login">Password:</td>
									<td>&nbsp;</td>
									<td><input type="password" name="password" onkeyup=periodicCheck("password","change_email") /></td>
									<td><div class="check"><div id="check-password"></div></div></td>
								</tr>						
								<tr>
									<td><label class="login">New E-mail Address:</td>
									<td>&nbsp;</td>
									<td><input type="text" name="emailaddress" onkeyup=periodicCheck("emailaddress","change_email") ></td>
									<td><div class="check"><div id="check-emailaddress"></div></div></td>
								</tr>						
							</table>
							
								<input class="submit-light" type="submit" value="Update E-mail Address" />
								<input class="clearforms" type="reset" value="Clear Fields" />
							
						</form>
					</div>
				</div> <!-- # function group -->
				
				<div id="requests-content">
				</div>
				
			</div> <!-- # page content -->
		</div> <!-- # main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>
