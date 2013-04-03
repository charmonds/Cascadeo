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
		$group_id = $_GET['gid'];	
		$username = $_POST['username'];
		$fname = $_POST['fname'];
		$lname = $_POST['lname'];
		$mname = $_POST['mname'];
		$password1 = $_POST['password1'];
		$password2 = $_POST['password2'];
		$school_id = $_POST['school_id'];
		
		if($group_id == 13)
		{
			$ctype_id = $_POST['curriculum_area_type'];
			$grade_type = $_POST['grade_type'];
		}
			
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
			if($ctype_id == 0)
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
		
		
		if(!$empty)
		{	
			if($errors <= 0)
				echo "<div class=\"success\"><img src=\"images/icon/success.png\" /> Account ready to be created.</div>";
			else
				echo "<div class=\"warning\"><img src=\"images/icon/warning.png\" /> Please check errors below.</div>";
		}
		
		if($empty)
		{
			echo "";
		}
			
	}
	mysql_close($myConnection);
?>
