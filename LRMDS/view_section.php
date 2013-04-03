<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	$page_id = 1;
	
	function newSalt()
	  {
		return chr(rand(0,255)).chr(rand(0,255)).chr(rand(0,255)).chr(rand(0,255));
		}
	 

	function generatePassword($myConnection){
		$num = rand(2, 25);
		$password = strval($num);	
		$password = $password . "";
		
		$num = rand(1,25);
		$temp = mysql_query("SELECT getWord('a', $num) AS adjective", $myConnection);
		$temp1 = mysql_fetch_array($temp);
		$password = $password . $temp1['adjective'] . "";

		$num = rand(1,25);
		$temp = mysql_query("SELECT getWord('c', $num) AS color", $myConnection);
		$temp1 = mysql_fetch_array($temp);
		$password = $password . $temp1['color'] . "";
		

		$num = rand(1,25);
		$temp = mysql_query("SELECT getWord('n', $num) AS animal", $myConnection);
		$temp1 = mysql_fetch_array($temp);
		$password = $password . $temp1['animal'] . "";
		

		return $password;
	}



	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}		
	else
	{
		$uid = $_GET['uid'];
		$cid = $_GET['cid'];
		$valid = 1;
		$action = $_GET['action'];
		$message = "";

		$q_sectioninfo = mysql_query("SELECT getClassName($cid) AS c_name", $myConnection);
		$section = mysql_fetch_array($q_sectioninfo);
		
		$uname = mysql_query("
		SELECT getUsingUID('u', $uid) AS username, 
		getUsingUID('f', $uid) AS first_name",
		$myConnection);	
		
		$uname1 = mysql_fetch_array($uname);
		$username = $uname1['username'];
	
	
		if($action=="invite_student")
		{
			$sid = $_GET['sid'];
			$check_in_class = mysql_query("
			SELECT student_id as sid
				
			FROM student_class
			WHERE student_id = " .$sid. "
			AND student_class.class_id = $cid;
			", $myConnection);
				
			$in_class = mysql_fetch_array($check_in_class);
			
			if($in_class)
			{
				$message = "<div class=\"warning\"><img src=\"images/icon/warning.png\" /> Student already exists in this section.</div>";
			}
			
			else
			{				
				mysql_query("INSERT INTO student_class VALUES($cid, $sid)", $myConnection);			
				$message = "<div class=\"success\"><img src=\"images/icon/success.png\" /> Successfully added student to section.</div>";
			}
		}
		
		//when add a student button is clicked
		if($action == "add_student"){		
			$fname = $_POST['fname'];
			$lname = $_POST['lname'];
			$mname = $_POST['mname'];
			$emailaddress = $_POST['emailaddress'];



			//check if username already exists
			$test = mysql_query("SELECT verifyAccount('$emailaddress') as email_address", $myConnection);
			$test_if_null = mysql_fetch_array($test);
			if($test_if_null['email_address'] != NULL)
			{
				$message = "<div name=\"message\" class=\"success\"><img src=\"images/icon/warning.png\" /> There is already a student with that email address.</div>";
				$valid = 0;
			}	
		
			if($valid == 1)
			{
				//generate password for student		
				$password = generatePassword($myConnection);	
				
				//put the account information into a text file
				$myFile = "accountInformation.txt";
				if (file_exists($myFile)) $fh = fopen($myFile, 'a');
				else $fh = fopen($myFile, 'w');
				fwrite($fh, $fname . " " . $mname . " " . $lname);
				fwrite($fh, "\n");
				fwrite($fh, $emailaddress);
				fwrite($fh, "\n");
				fwrite($fh, $password);
				fwrite($fh, "\n\n");
				fclose($fh);


				$salt = sha1(newSalt());
				$encrypt_password = sha1($salt . $password);

				$created_at = date('Y-m-d H:i:s', time());
				//insert into sf_guard_user
				mysql_query("INSERT INTO sf_guard_user(username, algorithm, salt, password, created_at, last_login, is_active, is_super_admin) VALUES ('$emailaddress', 'sha1', '$salt', '$encrypt_password', '$created_at', NULL, 1, 0)", $myConnection);
			
				//get the sf_guard_user_id
				$additionalInfo1 = mysql_query("
				SELECT verifyAccount('$emailaddress') as sf_guard_user_id
				", $myConnection);

				$temp = mysql_fetch_array($additionalInfo1);
				$sf_guard_user_id = $temp['sf_guard_user_id'];
		
				//get the school id from the adviser's id
				$additionalInfo2 = mysql_query("
				SELECT getUsingUID('c', $uid) AS school_id", $myConnection);
		
				$temp = mysql_fetch_array($additionalInfo2);
				$school_id = $temp['school_id'];
			
				//insert into the users table
				mysql_query("INSERT INTO 				
				users (last_name, first_name, middle_name, sf_guard_user_id, email_address, school_id, created_at) 
				VALUES('$lname', '$fname', '$mname', $sf_guard_user_id, '$emailaddress', $school_id, '$created_at')", $myConnection);
			
				//get the id of the student
				$myResultSet = mysql_query("SELECT id as sid FROM users where first_name = '$fname' AND last_name= '$lname' AND middle_name= '$mname'", $myConnection);
				$temp = mysql_fetch_array($myResultSet);
				$sid = $temp['sid'];
	
				//insert into class
				mysql_query("INSERT INTO student_class VALUES($cid, $sid)", $myConnection);

				//insert into sf_guard_user_group
				mysql_query("INSERT INTO sf_guard_user_group VALUES($sf_guard_user_id, 12)", $myConnection);
		
				$message = "<div name=\"message\" class=\"success\"><img src=\"images/icon/success.png\" /> Successfully added <b>\"" .$lname. ", " .$fname. " " .$mname. "\"</b> to <b>\"" .$section['c_name']. "\"</b></div>";
			

			}// if($a == 1) closing
		}// if($action == add) closing

		if($action == "remove")
		{
			$sid = $_GET['sid'];
			mysql_query("DELETE FROM student_class where student_id = $sid", $myConnection);
			
			$message = "<div name=\"message\" class=\"success\"><img src=\"images/icon/success.png\" /> Successfully removed from <b>\"" .$section['c_name']. "\"</b></div>";
			
		}


		//FETCHING OF DATA
		$resultSet = mysql_query("SELECT getUsingUID('g', $uid) AS type", $myConnection);
		$account = mysql_fetch_array($resultSet);
		switch ($account['type'])
		{
			case 12: $role = 'Student'; break;
			case 13: $role = 'Teacher'; break;
			case 14: $role = 'Adviser'; break;
		}
	
		
		$q_section = mysql_query("
		SELECT student_id 
		
		FROM student_class JOIN classes on 
		student_class.class_id = classes.id

		WHERE classes.id = $cid", $myConnection);
								
		$num_students = mysql_num_rows($q_section);
							

		
		
	 mysql_close($myConnection);

	}

?>
<html>
	<head>
		<title>LRMDS - View Section: <?php echo $section['c_name']; ?></title>
		<link href="css/main.css" type="text/css" rel="stylesheet" />
		<link href="css/stylesheet.css" type="text/css" rel="stylesheet" />	
		<link href="css/account.css" type="text/css" rel="stylesheet" />		
		<link href="css/classes.css" type="text/css" rel="stylesheet" />
		<link href="css/userbox.css" type="text/css" rel="stylesheet" />
		<script src="prototype.js" type="text/javascript" /></script>
		<script>
			function loadPage(box,url)
			{
				var xmlhttp;
				if (url=="")
				{
					document.getElementById(box).innerHTML="";
					return;
				}
				if (window.XMLHttpRequest)
				{ xmlhttp = new XMLHttpRequest(); }
				
				else { xmlhttp=new ActiveXObject("Microsoft.XMLHTTP"); }
				
				xmlhttp.onreadystatechange=function()
				{
					if (xmlhttp.readyState==4 && xmlhttp.status==200)
					{
						document.getElementById(box).innerHTML=xmlhttp.responseText;
					}
				}
				
				xmlhttp.open("GET",url,true);
				xmlhttp.send();
				
			}    
			
			function checkField(fieldname) {
				new Ajax.Updater('check-'+fieldname, 'verify_register2.php?fieldname=' +fieldname, { method: 'post', parameters: $('add_student').serialize() });				
			}
			
			function sendSearchRequest(box,url,formname)
			{
				new Ajax.Updater(box, url, { method: 'post', parameters: $(formname).serialize() });
			}			
			
			function showUserBox()
			{
				var userBox = document.getElementById('user-box');
				
				if(userBox.style.display == "block")
				{
					userBox.style.display = "none";
				}
				else
				{
					userBox.style.display = "block";
				}
			}
			
			
			function showAddBox(showthis,hidethis)
			{
				var showBox = document.getElementById(showthis);
				var hideBox = document.getElementById(hidethis);
				
				if(showBox.style.display == "none")
				{
					showBox.style.display = "block";
					hideBox.style.display = "none";
				}
				else
				{
					showBox.style.display = "none";
					hideBox.style.display = "none";
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
					<h1>View Students</h1>
				</div>
				
				<div class="function-group1">
					<h2 class="function2">
						<?php 
							echo $section['c_name'] . " - " .$num_students;
							
							if($num_students == 1)
							{
								echo " student"; 
							}
							else
							{
								echo " students"; 
							}
						?>
					</h2><br />
					<?php
					
					include("db_connect.g");
					$connectStudents = mysql_connect("$db", "$db_user", "$db_password");
					mysql_select_db("$database", $connectStudents);
					
						$myResultSet = mysql_query("
						SELECT users.last_name as lname,
						users.first_name as fname, 
						users.middle_name as mname,
						users.email_address as emailaddress,
						users.id as sid
						FROM users
						JOIN student_class ON student_class.student_id = users.id
						WHERE student_class.class_id = $cid
						", $connectStudents);
						

						$temp = mysql_query("SELECT getClassName($cid) AS c_name", $connectStudents);
						$c_name = mysql_fetch_array($temp);
						
						echo $message;
						
						if($num_students)
						{
							echo "<table width=80%><tr>";
							echo "<td class=\"account-label\">Full Name</td>";
							echo "<td class=\"account-label\">Username</td>";
						
							$listnumber = 1;
							while ($record = mysql_fetch_array($myResultSet)){
								echo "<tr>";
								if($listnumber % 2 == 0)
								{ $td_class = "account-info"; }
								else
								{ $td_class = "account-info2"; }
								
								echo "<td class=\"" .$td_class. "\">";
								echo $record['lname'] . ", " . $record['fname'] . " " . $record['mname'];
								echo "</td><td class=\"" .$td_class. "\">";
								echo $record['emailaddress'];
								echo"</td>";
								
								if($role == "Adviser")
								{
									echo "<td>";
									echo "<a class=\"remove\" href=\"view_section.php?sid=";
									echo $record['sid'];
									echo "&cid=";
									echo $cid;
									echo "&uid=";
									echo $uid;
									echo "&action=remove\">";
									echo "<img src=\"images/icon/remove.png\" /> Remove from section";
									echo "</a>";
									echo "</td></tr>";
								}
								
								$listnumber++;
							}
							
							echo "</table>";
						}
						
						// num_levels = 0
						else
						{
							echo "<div class=\"instruction\">No students yet.</div>";								
						}
						
						if($role == "Adviser")
						{
						?>
							<div class="right">
							<table>
								<tr>
									<td>
										<div class="resource-tool">
											<a class="tool" onclick=showAddBox("invite-student","add-student")>
												<img src="images/icon/invite_small.png" />
												Invite student									
											</a>
										
										</div>
									</td>
									<td>
										<div class="resource-tool">
											<a class="tool" onclick=showAddBox("add-student","invite-student")>
												<img src="images/icon/add.png" />
												Create student account									
											</a>
										
										</div>
									</td>
								</tr>
							</table>
							</div>
						<?php
						}
						
						
					?>
				
				</div> <!-- .function group -->
				
				<div id="add-student" style="display:none;">
					<div class="add-box">
						<h3 class="function2">New Student Information:</h3><br />
						<form id="add_student" method="POST" <?php echo "action=\"view_section.php?uid=" .$uid. "&cid=" .$cid. "&action=add_student\""; ?>>				
							<table><tr>
							<td><label class="function2">Last Name:</label></td>
							<td width="20px">&nbsp;</td>
							<td><input class="student" type="text" name="lname" onkeyup=checkField("lname") /></td>
							<td><div class="check"><div id="check-lname"></div></div></td>
							</tr><tr>
							
							<td><label class="function2">First Name:</label></td>
							<td></td>
							<td><input class="student" type="text" name="fname" onkeyup=checkField("fname") /></td>							
							<td><div class="check"><div id="check-fname"></div></div></td>
							</tr><tr>
							
							<td><label class="function2">Middle Name:</label></td>
							<td></td>
							<td><input class="student" type="text" name="mname" onkeyup=checkField("mname") /></td>					
							<td><div class="check"><div id="check-mname"></div></div></td>
							</tr><tr>
							
							<td><label class="function2">Email Address:</label></td>
							<td></td>
							<td><input class="student" type="text" name="emailaddress" onkeyup=checkField("emailaddress") /></td>					
							<td><div class="check"><div id="check-emailaddress"></div></div></td>
							
							</tr></table>
							<input class="submit-light" type="submit" value="Add student to class" />
							<input class="clearforms" type="reset" value="Clear all" />
						</form>
					</div>
				</div>
					
				<div id="invite-student" style="display:none;">
					<div class="add-box">
						<h3 class="invite-student">Student Information:</h3><br />
						<form id="invite_student" method="POST" <?php echo "action=\"view_section.php?uid=" .$uid. "&cid=" .$cid. "&action=invite_student\""; ?>>
							<table>
								<tr>			
									<td><label class="function2">Last Name:</label></td>
										<td><label class="function2">First Name:</label></td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
								</tr>
								<tr>
									<td><input class="student" type="text" name="lname" /></td>
									<td><input class="student" type="text" name="fname" /></td>
									
									<td><a class="submit-light" onclick=sendSearchRequest("search-student",<?php echo "\"search_student.php?uid=".$uid."&cid=".$cid."\""; ?>,"invite_student") />Search</a></td>
									<td><input class="clearforms" type="reset" value="Clear all" /></td>
								</tr>
							</table>
						</form>
						
						<div id="search-student">
						</div>
					</div>
				</div>
				
				
			</div> <!-- #page content -->
		</div> <!-- #main container -->
		
		<div id="footer">
			Copyright &copy; 2012
		</div>
		
	</body>
</html>

<?php mysql_close($connectStudents); ?>
