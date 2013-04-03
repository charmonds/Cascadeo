<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	include("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	$page_id = 1;
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];
		$action = $_GET['action'];
		
		$uname = mysql_query("SELECT getUsingUID('u', $uid) AS username, getUsingUID('g', $uid) AS type", $myConnection);	
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
		switch ($uname1['type']){
		case 12: $name = 'Student'; break;
		case 13: $name = 'Teacher'; break;
		case 14: $name = 'Adviser'; break;
	}
		
		if($action == "view")
		{
			$message = "";
		}
		else
		{
			if($action=="add_class")
			{
				$c_name = $_GET['c_name'];
				$message = "<div name=\"message\" class=\"success\"><img src=\"images/icon/success.png\" /> Successfully created new section <b>\"" .$c_name. "\"</b> to your classes.</div>";
			}
			
			if($action=="already_exists")
			{
				$gtype_id_add = $_GET['gtype_id_add'];
				$c_name = $_GET['c_name'];
				
				$message = "<div name=\"message\" class=\"warning\"><img src=\"images/icon/warning.png\" /> Error: <b>\"" .$c_name. "\"</b> already exists.</div>";
				
			}
			
			if($action=="remove")
			{
				$c_name = $_GET['c_name'];
				
				$message = "<div name=\"message\" class=\"success\"><img src=\"images/icon/success.png\" /> Successfully removed <b>\"" .$c_name. "\"</b> from your classes.</div>";
				
			}
			
			if($action=="edit")
			{
								
				$message = "<div name=\"message\" class=\"success\"><img src=\"images/icon/success.png\" /> Successfully modified section from your classes.</div>";
				
			}
		}
		
				
	}
	
	mysql_close($myConnection);
?>

<html>
	<head>
		<title>LRMDS - <?php echo $username; ?></title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />		
		<link href="css/account.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />	
		<link href="css/userbox.css" type="text/css" rel="stylesheet" />				
		<script src="prototype.js" type="text/javascript" /></script>
		<script>
			function loadEducationList(choice, url, ind)
			{
				var xmlhttp;
				<?php
					echo "var uid = '$uid';\n";
				?>
				if (choice=="")
				{
					document.getElementById("requests-content"+ind).innerHTML="";
					return;
				}
				if (window.XMLHttpRequest)
				{ xmlhttp = new XMLHttpRequest(); }
				
				else { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); }
				
				xmlhttp.onreadystatechange=function()
				{
					if (xmlhttp.readyState==4 && xmlhttp.status==200)
					{
						document.getElementById("requests-content"+ind).innerHTML=xmlhttp.responseText;
					}
				}
				
				xmlhttp.open("GET",url,true);
				xmlhttp.send();
				
			} 
			
			function showSubmit()
			{
				document.getElementById("submitthis").style.display = "block";
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
					<h1>Classes Overview</h1>
				</div>
				
				<?php 
										
					include("db_connect.g");
					$collectSections = mysql_connect("$db", "$db_user", "$db_password");
					mysql_select_db("$database", $collectSections);
					
					$q_levels = mysql_query("SELECT classes.id as id, grade_types.name as level, classes.name as section FROM classes JOIN grade_types
					ON classes.grade_type_id = grade_types.id
					WHERE adviser_id = $uid ORDER BY grade_type_id", $collectSections);
					
					$num_levels = mysql_num_rows($q_levels);
					
					
						echo "<div class=\"function-group1\">";
						echo $message;
						if($num_levels > 0)
						{
							echo "<div class=\"centered\">";
							echo "<table width=\"100%\">";
							echo "<tr>";
							echo "<td class=\"account-label\">";
							echo "Section Name";
							echo "</td>";
							echo "<td class=\"account-label\">";
							echo "Level";
							echo "</td>";
							echo "<td></td>";
							echo "</tr>";
							while($class = mysql_fetch_array($q_levels))
							{
								echo "<tr>";
								echo "<td class=\"account-info\">";
								echo "<a href=\"view_section.php?uid=";
								echo $uid;
								echo "&cid=";
								echo $class['id'];
								echo "&action=view\">";
								echo $class['section'];
								echo "</a>";
								echo "</td>";
								echo "<td class=\"account-info\">";
								echo $class['level'];
								echo "</td>";
						
								echo "<td><div class=\"remove\"><a class=\"tool\" href=\"modify_class.php?uid=" .$uid. "&cid=" .$class['id']. "&action=remove\">Remove</a></div></td>";
								echo "</tr>";
							}
							
							echo "</table>";
						}
						else
						{
							echo "<div class=\"instruction\">No sections added yet.</div>";
						}
						
						if($name == "Adviser")
						{
							echo "<div class=\"tool\"><a class=\"tool\" href=\"#grade\" onClick=loadEducationList(1,\"add_class_education_type.php?uid=" .$uid. "\",0)>Add section</a></div>";
						}
						
						echo "</div> <!-- .centered -->";
					
					?>
				
				
				<div id="requests-content0">
					
				</div>
					
				</div>
				
				
				<?php	mysql_close($collectSections); ?>
					
			</div> <!-- #page content -->
		</div> <!-- #main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>
