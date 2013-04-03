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
		$message = "";
		
		$uname = mysql_query("
		SELECT getUsingUID('u', $uid) AS username, 
		getUsingUID('f', $uid) AS first_name",
		$myConnection);	
		
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
		
		$resultSet = mysql_query("
		SELECT getUsingUID('l', $uid) AS last_name, 
		getUsingUID('f', $uid) AS first_name, 
		getUsingUID('d', $uid) AS description, 
		getUsingUID('g', $uid) as gid,
		getUsingUID('n', $uid) as section,
		getUsingUID('t', $uid) as grade_type", 
		$myConnection);
			
		$account = mysql_fetch_array($resultSet);
		$fetch_subject_area = mysql_query("
		SELECT grade_types.name as level,
			curriculum_area_types.name as subject_name
		
		FROM grade_types 
			JOIN education_types ON grade_types.education_type_id = education_types.id
			JOIN curriculum_area_types ON grade_types.id = curriculum_area_types.grade_type_id
			JOIN users ON users.curriculum_area_type_id = curriculum_area_types.id
		WHERE users.id = $uid;		
		", $myConnection);
		
		$subject_area = mysql_fetch_array($fetch_subject_area);
		
		if($action=="change")
		{
			$message = "<div class=\"success\"><img src=\"images/icon/success.png\" />Successfully changed account details.</div>";
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
			function loadResourcesForLevel(choice)
			{
				var xmlhttp;
				<?php
					echo "var uid = '$uid';\n";
				?>
				if (choice=="")
				{
					document.getElementById("requests-content").innerHTML="";
					return;
				}
				if (window.XMLHttpRequest)
				{ xmlhttp = new XMLHttpRequest(); }
				
				else { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); }
				
				xmlhttp.onreadystatechange=function()
				{
					if (xmlhttp.readyState==4 && xmlhttp.status==200)
					{
						document.getElementById("requests-content").innerHTML=xmlhttp.responseText;
					}
				}
				
				xmlhttp.open("GET","search_results2.php?uid=" +uid+ "&grade_type=" +choice,true);
				xmlhttp.send();
				
			}     

			function sendRequest() {
				new Ajax.Updater('requests-content', <?php echo "'search_results.php?uid=$uid'";?>, { method: 'post', parameters: $('searchform').serialize() });
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
					<h1>Account Settings</h1>
				</div>
				
				
				<div class="function-group2">
				<?php echo $message; ?>
					<table>
						<tr>
							<td class="account-label">Name:</td>
							<td width="5px">&nbsp;</td>
							<td class="account-info"><?php echo $account['first_name'] . " " . $account['last_name']; ?></td>
						</tr>
						<tr>
							<td class="account-label">Account Type:</td>
							<td>&nbsp;</td>
							<td class="account-info"><?php echo $account['description']; ?></td>
						</tr>
						<tr>
							<td class="account-label">Username:</td>
							<td>&nbsp;</td>
							<td class="account-info"><?php echo $username; ?></td>
						</tr>
						<tr>
							<td class="account-label">Password:</td>
							<td>&nbsp;</td>
							<td class="account-info">< not shown ></td>
						</tr>
					<?php if($account['gid']==13) /* teacher account */
					{
					?>
						<tr>
							<td class="account-label">Level:</td>
							<td>&nbsp;</td>
							<td class="account-info"><?php echo $subject_area['level']; ?></td>
						</tr>
						<tr>
							<td class="account-label">Subject Area:</td>
							<td>&nbsp;</td>
							<td class="account-info"><?php echo $subject_area['subject_name']; ?></td>
						</tr>
					<?php
					}
					?>
					<?php if($account['gid']==12) /* student account */
					{
					?>
						<tr>
							<td class="account-label">Level:</td>
							<td>&nbsp;</td>
							<td class="account-info"><?php echo $account['grade_type']; ?></td>
						</tr>
						<tr>
							<td class="account-label">Section:</td>
							<td>&nbsp;</td>
							<td class="account-info"><?php echo $account['section']; ?></td>
						</tr>
					<?php
					}
					?>
					<tr>
							<td colspan=2></td>
							<td><div class="right"><a href="edit_account.php?uid=<?php echo $uid; ?>&action=edit" class="submit-light">Edit Account</a></div></td>
						</tr>
					</table>
					
					
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
