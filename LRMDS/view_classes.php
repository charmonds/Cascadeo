<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	$page_id = 1;
	
	
	if (!$myConnection)
	{
		die("Failed to connect: " . mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];
		$uname = mysql_query("SELECT getUsingUID('u', $uid) AS username, getUsingUID('f', $uid) AS first_name", $myConnection);	
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
		$gtype_id = $_GET['gtype_id'];
		$action = $_GET['action'];

		//when the page came from add_class.php, the added class is inserted into the database
		if($action=="add_class")
		{
			$gtype_id_added = $_GET['gtype_id'];
			$c_name = $_POST['c_name'];
			mysql_query("INSERT INTO classes (name, adviser_id, grade_type_id) VALUES ('$c_name', $uid, $gtype_id_added)", $myConnection);
			$gtype_id = $gtype_id_added;
			
			echo $c_name;
		}
		
		$myResultSet = mysql_query("SELECT grade_types.name as gtype_name FROM grade_types WHERE grade_types.id = $gtype_id", $myConnection);
		$grades = mysql_fetch_array($myResultSet);
		$gradename = $grades['gtype_name'];
		
		//get the classes created by this adviser		
		$q_classesUnderAdviser = mysql_query("
		SELECT getUsingUID('n', $uid) AS  c_name,
		getUsingUID('i', $uid) AS  c_id", $myConnection);
		$num_records = mysql_num_rows($q_classesUnderAdviser);
		
?>

<html>
	<head>
		<title>LRMDS - <?php echo $uname1['username'] ; ?> -  Account Settings</title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />		
		<link href="css/account.css" type="text/css" rel="stylesheet" />
		<link href="css/classes.css" type="text/css" rel="stylesheet" />		
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
					<h1><?php echo $gradename; ?></h1>
				</div>
			
				<?php
					if($num_records)
					{
						echo "<div class=\"function1\">";
						echo "<h2 class=\"function1\">";
						echo $num_records;
						if($num_records == 1)
						{ echo " section"; }
						else
						{ echo " sections"; }
						echo "</h2><br><br>";
						
						echo "<div class=\"rounded-corners1\">\n";	
						
						$section_number = 1;
						$total_numstudents = 0;
						echo "<div class=\"section-row\">\n";
						while ($class = mysql_fetch_array($q_classesUnderAdviser)){
							$cid = $class['c_id'];
							$q_section = mysql_query("SELECT student_id FROM student_class JOIN classes on 
								student_class.class_id = classes.id where grade_type_id = $gtype_id", $myConnection);
							$num_students = mysql_num_rows($q_section);
							$total_numstudents += $num_students;
							
							echo "\t<div class=\"section-box\">\n";
							echo "\t\t<a class=\"section-title\" href=\"view_section.php?cid=";
							echo $class['c_id'];
							echo "&uid=";
							echo $uid;
							echo "&action=view\">";
							echo $class['c_name'];
							echo "</a>";
							echo "\n\t\t<div class=\"section-count\">\n";
							echo "\t\t\t(" .$num_students;
							if($num_students == 1)
							{ echo " student)\n"; }
							else
							{ echo " students)\n"; }
							echo "\t\t</div>\n"; /* section-count */	
							// multiple of 5 sections per row
							echo "\t</div> <!-- section-box -->";
							if($section_number == 3)
							{						
								echo "\t</div>\n";	/* section row */	
								echo "<div class=\"section-row\">\n\n";
								$section_number = 1;
							}
							else
							{
								$section_number++;
							}
						}
						echo "</div> <!-- last section-row -->";
						
						echo "<div class=\"section-row\">";
						echo "<div class=\"list-all\">";
						echo "<a class=\"list-all\">List all in one page</a>";
						echo "<div class=\"section-count\">";
						echo "(" .$total_numstudents;
						if($total_numstudents == 1)
						{ echo " student)"; }
						else
						{ echo " students)"; }
						echo "</div> <!-- section count -->";
						
						echo "</div> <!-- list-all -->";
						echo "</div> <!-- section-row -->";
						echo "</div> <!-- rounded-corners1 -->";
					}
					else
					{
						echo "<div class=\"function-group2\">\n";
						echo "<div class=\"instruction\">No sections added yet.</div>";
					}
					
					echo "<div class=\"tool\"><a class=\"tool\" href=\"add_class2.php?uid=";
					echo $uid;
					echo "&gtype_id=";
					echo $gtype_id;
					echo "\">";
					echo "Add section</a></div>"; /* tool */
					echo "</div>"; /* function-group2 */
				}
			?>
				<div id="requests-content">
				</div>
				
			</div> <!-- # page content -->
		</div> <!-- # main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>

