<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	$page_id = 2;
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];
		$action = $_GET['action'];
		
		if($action=="view")
		{
			$message = "";
		}
		
		$uname = mysql_query("SELECT getUsingUID('u', $uid) AS username, getUsingUID('f', $uid) AS first_name", $myConnection);	
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
		
		mysql_close($myConnection);
	}
	
?>

<html>
	<head>
		<title>LRMDS - <?php echo $uname1['username'] ; ?> -  My Resources</title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />		
		<link href="css/account.css" type="text/css" rel="stylesheet" />	
		<link href="css/userbox.css" type="text/css" rel="stylesheet" />
		<script src="prototype.js" type="text/javascript" /></script>
		<script>
			function shelfDo(url)
			{
				var xmlhttp;
				
				if (url=="")
				{
					document.getElementById("message").innerHTML="";
					return;
				}
				if (window.XMLHttpRequest)
				{ xmlhttp = new XMLHttpRequest(); }
				
				else { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); }
				
				xmlhttp.onreadystatechange=function()
				{
					if (xmlhttp.readyState==4 && xmlhttp.status==200)
					{
						document.getElementById("message").innerHTML=xmlhttp.responseText;
					}
				}
				
				xmlhttp.open("GET",url,true);
				xmlhttp.send();				
			}   	
			function shelf(url)
			{
				var xmlhttp;
				
				if (url=="")
				{
					document.getElementById("shelf").innerHTML="";
					return;
				}
				if (window.XMLHttpRequest)
				{ xmlhttp = new XMLHttpRequest(); }
				
				else { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); }
				
				xmlhttp.onreadystatechange=function()
				{
					if (xmlhttp.readyState==4 && xmlhttp.status==200)
					{
						document.getElementById("shelf").innerHTML=xmlhttp.responseText;
					}
				}
				
				xmlhttp.open("GET",url,true);
				xmlhttp.send();				
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
	<body onload=shelf("student_resources.php?action=view&uid=<?php echo $uid; ?>")>
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
					<h1>My Resources</h1>
				</div>
				
				<div class="function-group2">
				<div id="message">
					<?php echo $message; ?>
				</div>
					<div id="shelf">
					
					</div>
				</div> <!-- function group -->
			
				
			</div> <!-- # page content -->
		</div> <!-- # main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>
