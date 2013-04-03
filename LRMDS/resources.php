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
		$uname = mysql_query("SELECT getUsingUID('u', $uid) AS username", $myConnection);	
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
	}
?>

<html>
	<head>
		<title>LRMDS - <?php echo $username; ?></title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />	
		<link href="css/userbox.css" type="text/css" rel="stylesheet" />				
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
					<h1>Browse All Resources</h1>
				</div>
				
				<div class="function-group1">
					<table style="margin: auto;"><tr>					
					<td>
						<div id="function-box1">
							<form method ="POST" onsubmit="return false;" id="searchform">
								<label class="function1">Search by keyword</label><br />
								<input autocomplete="off" id="searchbox" name="searchq" onkeyup="sendRequest()" type="textbox"  />
							</form>
						</div>
					</td>
					<td>
						<div id="function-box2">
							<form name="grade_type_select">
								<label class="function2">Select all resources per grade level:</label><br />
								<select class="function2" name="grade_type" onchange="loadResourcesForLevel(this.value)">
									<option class="form-instruction">Select Grade Type</option>
									<?php
										$action = $_GET['action'];

										if (!$myConnection) { echo "Connection failed."; }
										{
											//this part is for the cancel button in shelf
											if($action == "cancel")
											{	
												mysql_query("DELETE FROM resource_shelf WHERE class_id is null", $myConnection);		
											}
											//end of that part
										}
										
										$myResultSet = mysql_query("
											SELECT grade_types.name as gtypename,
											grade_types.id as gtypeid
											FROM grade_types", $myConnection);
							
										$gtypeid = $record['gtypeid'];

										while($record = mysql_fetch_array($myResultSet)){
											echo "<option class=\"function2\" value=\"";
											echo $record['gtypeid'];
											echo "\">";
											echo $record['gtypename'];
											echo "</option>";
										}
									?>
								</select>
							</form>
						</div> <!-- #function box -->
					</td>
					</tr></table>
				</div> <!-- .left indent group -->
				
				<div id="requests-content">
				</div>
				
			</div> <!-- #page content -->
		</div> <!-- #main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>
