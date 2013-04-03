<?php	
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	

	$uid = $_GET['uid'];
	$fieldname = $_GET['fieldname'];
	$password = $_POST['password'];
	$new_email = $_POST['emailaddress'];
	$action = $_GET['action'];
	
	$errors = 0;
	$empty = 0;
	//check if username/email has @
	$checker = '/@/';
	
	if($new_email == "")
	{
		$empty++;
	}
	
	if($fieldname=="emailaddress")
	{
		$username = $_POST['emailaddress'];		
		
		if($username=="")
		{
			$empty++;
		}
		else 
		{
			$checker = '/^[^\W][a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\@[a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\.[a-zA-Z]{2,4}$/';
			if(preg_match($checker, $username)==0)
			{
				$errors++;
				if($action=="check")					
					echo "<img src=\"images/icon/warning.png\" /> Invalid e-mail address.";	
			}
			else
			{
				$checkUsers = mysql_connect("$db", "$db_user", "$db_password");
				mysql_select_db("$database", $checkUsers);
	
				if (!$checkUsers)
				{
					die("Failed to connect: ". mysql_error());
				}
				
				$test = mysql_query("SELECT verifyAccount('$username') as username", $checkUsers);
				$result = mysql_fetch_array($test);
				
				mysql_close($checkUsers);
				
				if($result['username'])
				{		
					$error++;
					if($action=="check")
						echo "<img src=\"images/icon/warning.png\" /> Username already exists. Please use a different username or check if you already have an account.";													
				}
				else
				{
					if($action=="check")
						echo "<img src=\"images/icon/success.png\" /> <b>'" .$username. "'</b> is available.";	
				}
			}
		}
	}
	
	if($fieldname == "password")
	{
		$myResultSet = mysql_query("SELECT getUsingUID('s', $uid) as sf_guard_user_id", $myConnection);
		$record = mysql_fetch_array($myResultSet);
		$sf_guard_user_id = $record['sf_guard_user_id'];

		$myResultSet = mysql_query("SELECT getPass('s', $sf_guard_user_id) as salt, getPass('p', $sf_guard_user_id) as password", $myConnection);
		$record = mysql_fetch_array($myResultSet);
		$salt = $record['salt'];
		$real_password = $record['password'];
			
		if($password != "")
		{
			if((sha1($salt . $password)) == $real_password)
			{
				if($action=="check")
					echo "<img src=\"images/icon/success.png\" />";	
			}
		
			else
			{
				$errors++;
				if($action=="check")
					echo "<img src=\"images/icon/warning.png\" /> Incorrect password.";													
			}
		}
		else
		{
			$empty++;
			echo "";
		}
	}

	if(!$empty && $errors <= 0)
	{
		if($action=="check")
			echo "<img src=\"images/icon/success.png\" />";
		if($action=="send")
		{
			mysql_query("UPDATE sf_guard_user SET username = '$new_email' WHERE id = $sf_guard_user_id", $myConnection);
			mysql_query("UPDATE users SET email_address = '$new_email' WHERE id = $uid", $myConnection);
		}
	}
	
	mysql_close($myConnection);
?>
