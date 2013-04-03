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
		function newSalt()
		{
			return chr(rand(0,255)).chr(rand(0,255)).chr(rand(0,255)).chr(rand(0,255));
		}
		
		$username = $_POST['username'];
		$group_id = $_GET['group_id'];
		if($group_id == 13)
		{
			$ctype_id = $_POST['curriculum_area_type'];
			$grade_type = $_POST['grade_type'];
		}
		$lname = $_POST['lname'];
		$fname = $_POST['fname'];
		$mname = $_POST['mname'];
		$password1 = $_POST['password1'];
		$password2 = $_POST['password2'];
		$school_id = $_POST['school_id'];
			
		$errors = 0;
		$empty = 0;
		
		if($password1=="")
		{
			$empty++;
		} 
		
		if($password2=="")
		{
			$empty++;
		} 
		
		if($password1!="" && $password2 !="")
		{
			if($_POST['password1'] != $_POST['password2'])
			{ 
				$errors++;				
			}
		}
		
		//check if is blank
		if($username=="")
		{
			$empty++;
		}			
		else
		{
			$checker = '/^[^\W][a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\@[a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\.[a-zA-Z]{2,4}$/';
			if(preg_match($checker, $username)) 
			{ 
				$test = mysql_query("SELECT verifyAccount('$username') as username", $myConnection);
				$fetch = mysql_fetch_array($test);
				if($fetch['username']!=NULL)
				{
					$errors++;
					 
				}
			}
			else 
			{
				 
				$errors++;
			}
			
		}
		
		//check name if it has no numbers
		$checker = '/^[a-zA-Z]+[(-|\' )a-zA-Z]*[a-zA-Z]$/';
		if($lname=="")
		{
			$empty++;
		}
		else
		{
			if(!preg_match($checker, $lname)) { $errors++; } 
		}
		if($fname=="")
		{
			$empty++;
		}
		else
		{
			if(!preg_match($checker, $fname)) { $errors++; } 
		}
		if($mname=="")
		{
			$empty++;
		}
		else
		{
			if(!preg_match($checker, $mname)) { $errors++; } 
		}

		if($school_id=="")
		{
			$empty++;
		}
		
		if($group_id == 13)
		{
			if($ctype_id == 0 && ($grade_type != 7 && $grade_type != 8))
			{
				$empty++;
				$errors++;
			}
				
			if($grade_type == 0)
			{
				$empty++;
				$errors++;
			}
		}
				
		if($empty)
		{
			echo "";
		}
		
		if(!$empty)
		{	
			if($errors <= 0)
			{					
				//IF EVERYTHING IS VALID
				$salt = sha1(newSalt());
				$encrypt_password = sha1($salt . $_POST['password1']);

				$created_at = date('Y-m-d H:i:s', time());
				//insert into sf_guard_user
				mysql_query("INSERT INTO sf_guard_user(username, algorithm, salt, password, created_at, last_login, is_active, is_super_admin) VALUES ('$username', 'sha1', '$salt', '$encrypt_password', '$created_at', NULL, 1, 0)", $myConnection);
				
				//get the sf_guard_user_id
				$additionalInfo1 = mysql_query("
				SELECT account('s', '$username', 'null') as sf_guard_user_id
				", $myConnection);

				$temp = mysql_fetch_array($additionalInfo1);
				$sf_guard_user_id = $temp['sf_guard_user_id'];

					
				//insert into the users table
				if($group_id == 13){
					if($grade_type == 7 || $grade_type == 8)
					{
					mysql_query("INSERT INTO users (last_name, first_name, middle_name, sf_guard_user_id, email_address, school_id, curriculum_area_type_id, created_at) VALUES('$lname', '$fname', '$mname', $sf_guard_user_id, '$username', $school_id, NULL, '$created_at')", $myConnection);
					}
					else mysql_query("INSERT INTO users (last_name, first_name, middle_name, sf_guard_user_id, email_address, school_id, curriculum_area_type_id, created_at) VALUES('$lname', '$fname', '$mname', $sf_guard_user_id, '$username', $school_id, $ctype_id, '$created_at')", $myConnection);
				
				}
				
				else
				mysql_query("INSERT INTO users (last_name, first_name, middle_name, sf_guard_user_id, email_address, school_id, created_at) VALUES('$lname', '$fname', '$mname', $sf_guard_user_id, '$username', $school_id, '$created_at')", $myConnection);


				//insert into sf_guard_user_group
				mysql_query("INSERT INTO sf_guard_user_group VALUES($sf_guard_user_id, $group_id)", $myConnection);
				
				header('Location: enter_login.php?action=success');

				mysql_close($myConnection);
			}

			else
				header('Location: register.php?action=error');
		}
		
	}
?>