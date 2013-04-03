<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $myConnection);
	$page_id = 2;
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{
		$rid = $_GET['rid'];
		$uid = $_GET['uid'];
		
		$uname = mysql_query("SELECT getUsingUID('u', $uid) AS username, getUsingUID('f', $uid) AS first_name", $myConnection);	
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
		
		$q_basicinfo = mysql_query("
		SELECT 
			getDetails('Gt', $rid) AS Title,
			getDetails('Gl', $rid) AS Language,
			getDetails('Gd', $rid) AS Description,
			getDetails('Gk', $rid) AS Keywords,
			getDetails('G?', $rid)AS Public,
			getDetails('Gi', $rid)  AS ID,
			getDetails('Gm', $rid) AS Media_Type,
			
			getDetails('Go', $rid) AS Original_Filename,
			getDetails('Gr', $rid) AS Resource_Location,
			getDetails('Ge', $rid) AS External_ID,
			getDetails('Gp', $rid) AS Published_On,
			getDetails('Gs', $rid) AS Primary_Storage
		", $myConnection);
		
		$resource = mysql_fetch_array($q_basicinfo);

		
		$q_techinfo = mysql_query("
		SELECT getDetails('Ts', $rid) AS File_Size,
			getDetails('Ta', $rid) AS Special_Requirements,
			getDetails('To', $rid) AS Operating_System,
			getDetails('Tw', $rid) AS Software,
			getDetails('Tn', $rid) AS Number_of_Pages,
			getDetails('Tf', $rid) AS File_Type
		;", $myConnection);
		
		
		if($technical = mysql_fetch_array($q_techinfo))
		{
					
		}

		function filter_blanks(&$recordarray)
		{		
			$no_data = "<div class=\"no-data\"> no data </div>";
			if(!empty($recordarray))
			{
				foreach($recordarray as &$filter)
				{
					if($filter == NULL || ctype_space($filter))
					{
						$filter = $no_data;
					}
				}
			}
			else
			{
				$recordarray = array_fill(0,12,$no_data);
			}
		}
		
		filter_blanks($resource);
		filter_blanks($technical);
		
		function print_info(&$fileinfo, $q_info, $start, $end)
		{
			if(!$q_info)
			{
				echo "<div class=\"no-info\">no data available</div>";
			}
			else
			{
				$no_data = "<div class=\"no-data\"> no data </div>";
				echo "<table>";
				for($col = $start; $col < $end; $col++)
				{
					$field = mysql_fetch_field($q_info, $col);
						$remove_underscores = str_replace("_"," ",$field->name);
						echo "<tr>";
						echo "<td class=\"resource-label\">".$remove_underscores.":</td>";
						echo "<td>&nbsp;</td>";
						echo "<td class=\"resource-info\">";
						
						
						if($col == 3)
						{
							if($fileinfo[$col]== $no_data)
								echo $no_data;
							else
							{
								$keyword = explode(',', $fileinfo[$field->name]);
								foreach($keyword as $word)
								{
									echo "<div class=\"keyword\">" .$word. "</div>";
								}
								unset($word);
							}
						}
						else echo $fileinfo[$col];
						
						echo "</td>";
						echo "</tr>";	
					
				}
				echo "</table>";
			}			
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
		<script>
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
					<h1>View Resource</h1>
				</div>
				
				<div class="function-group2">
					<h3 class="function1">Basic Information</h3>
					<?php
						print_info($resource,$q_basicinfo,0,6);
					?>
					
				</div>				
				<div class="function-group2">
					<h3 class="function1">File Information</h3>
					<?php
						print_info($resource,$q_basicinfo,7,12);
					?>					
				</div>
				<div class="function-group2">
					<h3 class="function1">Technical Information</h3>
					<?php
						print_info($technical,$q_techinfo,0,6);
					?>				
				</div>
				
				<div class="function-group2">
					<h3 class="function1">Education Information</h3>
					<table>
						<?php
							$myResultSet = mysql_query("
							SELECT getDetails('Em', $rid) AS material_type
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);							
							echo "<tr>";
							echo "<td class=\"resource-label\">Education Material Type: </td>";
							echo "<td class=\"resource-info\">";
							if($educational['material_type'] == 2) 
								echo "Curriculum";
							else if($educational['material_type'] == 1)
								echo "Professional Development";
							else echo "None";
							echo "</td>";
							echo "</tr>";	
							
							
							$myResultSet = mysql_query("
							SELECT getDetails('Ee', $rid) AS et_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							echo "<tr>";
							echo "<td class=\"resource-label\">Education Type: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['et_name']);
							echo "</td>";
							echo "</tr>";
							

							$myResultSet = mysql_query("
							SELECT getDetails('Eg', $rid) AS g_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);	
							echo "<tr>";
							echo "<td class=\"resource-label\">Grade: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['g_name']);
							echo "</td>";
							echo "</tr>";
							

							$myResultSet = mysql_query("
							SELECT getDetails('Ec', $rid) AS cat_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							echo "<tr>";
							echo "<td class=\"resource-label\">Curriculum Area: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['cat_name']);
							echo "</td>";
							echo "</tr>";
							
							
							$myResultSet = mysql_query("
							SELECT getDetails('Ed', $rid) AS dst_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							echo "<tr>";
							echo "<td class=\"resource-label\">Discipline: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['dst_name']);
							echo "</td>";
							echo "</tr>";

							$myResultSet = mysql_query("
							SELECT getDetails('Es', $rid) AS s_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);	
							echo "<tr>";
							echo "<td class=\"resource-label\">Strands: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['s_name']);
							echo "</td>";
							echo "</tr>";
							
							$myResultSet = mysql_query("
							SELECT getDetails('Eo', $rid) AS c_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);	
							echo "<tr>";
							echo "<td class=\"resource-label\">Competencies: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['c_name']);
							echo "</td>";
							echo "</tr>";
							
							$myResultSet = mysql_query("
							SELECT getDetails('Ep', $rid) AS sc_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							echo "<tr>";
							echo "<td class=\"resource-label\">Subcompetencies:</td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['sc_name']);
							echo "</td>";
							echo "</tr>";
							
							$myResultSet = mysql_query("
							SELECT getDetails('Et', $rid) AS related_topics, getDetails('Eb', $rid) AS objectives, getDetails('Er', $rid) As duration
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							
							echo "<tr>";
							echo "<td class=\"resource-label\">Related Topics: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['related_topics']);
							echo "</td>";
							echo "</tr>";
							
							echo "<tr>";
							echo "<td class=\"resource-label\">Objectives: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['objectives']);
							echo "</td>";
							echo "</tr>";
							
							echo "<tr>";
							echo "<td class=\"resource-label\">Duration: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['duration']);
							echo "</td>";
							echo "</tr>";
							
							
							
							$myResultSet = mysql_query("
							SELECT getDetails('Ei', $rid) AS iut_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);	
							echo "<tr>";
							echo "<td class=\"resource-label\">Intended Users: </td>";
							echo "<td class=\"resource-info\">";
							print($educational['iut_name']);
							echo "</td>";
							echo "</tr>";
							
							
							$myResultSet = mysql_query("
							SELECT getDetails('Ea', $rid) AS accessibility
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							echo "<tr>";
							echo "<td class=\"resource-label\">Accessibility: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['accessibility']);
							echo "</td>";
							echo "</tr>";
							

							$myResultSet = mysql_query("
							SELECT getDetails('Eu', $rid) AS eut_name
							", $myConnection);
							$educational = mysql_fetch_array($myResultSet);
							echo "<tr>";
							echo "<td class=\"resource-label\">Primary Education Use: </td>";
							echo "<td class=\"resource-info\">";
							print_data($educational['eut_name']);
							echo "</td>";
							echo "</tr>";		
							
							function print_data($str)
							{
							if(ctype_space($str) || $str == NULL)
								echo "<div class=\"no-data\"> no data </div>";
							else echo $str;
							}
						
						echo "</table>";
					?>
				</div>
				<div class="function-group2">
				<h3 class="function1">Rights</h3>
				<table>
					<?php
						$myResultSet = mysql_query("
						SELECT getDetails('Rc', $rid) AS copyright, 
						getDetails('Ro', $rid) AS copyright_owner, 
						getDetails('Ra', $rid) AS acquisition, 
						getDetails('Ru', $rid) AS conditions_of_use
						", $myConnection);
						$rights = mysql_fetch_array($myResultSet);
						
						echo "<tr>";
						echo "<td class=\"resource-label\">Copyright: </td>";
						echo "<td class=\"resource-info\">";
						print_data($rights['copyright']);
						echo "</td>";
						echo "</tr>";
						
						echo "<tr>";
						echo "<td class=\"resource-label\">Copyright: </td>";
						echo "<td class=\"resource-info\">";
						print_data($rights['copyright_owner']);
						echo "</td>";
						echo "</tr>";
						
						echo "<tr>";
						echo "<td class=\"resource-label\">Acquisition: </td>";
						echo "<td class=\"resource-info\">";
						print_data($rights['acquisition']);
						echo "</td>";
						echo "</tr>";
						
						echo "<tr>";
						echo "<td class=\"resource-label\">Conditions of Use: </td>";
						echo "<td class=\"resource-info\">";
						print_data($rights['conditions_of_use']);
						echo "</td>";
						echo "</tr>";
					
						echo "</table>";
					?>
				</div>
				
				<div id="requests-content">
				</div>
				
			</div>
			<!-- # page content -->
			
		</div> <!-- # main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>

<?php mysql_close($myConnection); ?>