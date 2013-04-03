<?php	
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	
	$uid = $_GET['uid'];
	$form = $_GET['form'];
	
	if($form == "change_email")
	{
		$password = $_POST['password'];
		$new_email = $_POST['emailaddress'];
		
		$errors = 0;
		$empty = 2;
		
		//check if username/email has @
		$checker = '/^[a-zA-Z]+[(-|\' )a-zA-Z]*[a-zA-Z]$/';
		
		
		$username = $_POST['emailaddress'];		
		
		if($username!="")
		{
			$empty--;
			$checker = '/^[^\W][a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\@[a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\.[a-zA-Z]{2,4}$/';
			if(preg_match($checker, $username)==0)
			{
				$errors++;	
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
					$errors++;
				}
			}
		}
		
		$myResultSet = mysql_query("SELECT getUsingUID('s', $uid) as sf_guard_user_id", $myConnection);
		$record = mysql_fetch_array($myResultSet);
		$sf_guard_user_id = $record['sf_guard_user_id'];

		$myResultSet = mysql_query("SELECT getPass('s', $sf_guard_user_id) as salt, getPass('p', $sf_guard_user_id) as password", $myConnection);
		$record = mysql_fetch_array($myResultSet);
		$salt = $record['salt'];
		$real_password = $record['password'];
			
		if($password != "")
		{
			$empty--;
			if((sha1($salt . $password)) != $real_password)
			{
				$errors++;												
			}
		}

		echo "!!empty: " .$empty. " errors: " .$errors;
		if(!$empty && $errors <= 0)
		{
			mysql_query("UPDATE sf_guard_user SET username = '$new_email' WHERE id = $sf_guard_user_id", $myConnection);
			mysql_query("UPDATE users SET email_address = '$new_email' WHERE id = $uid", $myConnection);
			
			 header('Location: enter_login.php?action=change');
		}
		else
		{
			 header('Location: edit_account.php?uid='.$uid.'&action=error2');
		}		
	}
	
	if($form == "change_password")
	{
		$oldpassword = $_POST['oldpassword'];
		$newpassword1 = $_POST['newpassword1'];
		$newpassword2 = $_POST['newpassword2'];
		
		$empty = 2;
		$errors = 0;
		
		if($newpassword1 != "")
		{		
			$checker = '/[A-Za-z0-9]/';
				
		
			if(strcmp($newpassword1,$newpassword2)==0)
			{			
				$empty--;
			}
			
			if(preg_match($checker, $newpassword1))
			{
				if(strlen($newpassword1) > 5 && strlen($newpassword1) <= 12)
				{
					$empty--;				
				}
				else
				{
					$errors++;
				}
			}
			else
			{		
				$errors++;
			}
		}
		
		if(!$empty && $errors <= 0)
		{
			$myResultSet = mysql_query("SELECT getUsingUID('s', $uid) as sf_guard_user_id", $myConnection);
			$record = mysql_fetch_array($myResultSet);
			$sf_guard_user_id = $record['sf_guard_user_id'];

			$myResultSet = mysql_query("SELECT getPass('s', $sf_guard_user_id) as salt, getPass('p', $sf_guard_user_id) as password", $myConnection);
			$record = mysql_fetch_array($myResultSet);
			$salt = $record['salt'];
			$real_password = $record['password'];
			
			if((sha1($salt . $oldpassword)) == $real_password)
			{
				 $new_encrypted_pw = sha1($salt . $newpassword1);
				 mysql_query("UPDATE sf_guard_user SET password = '$new_encrypted_pw' WHERE id = $sf_guard_user_id", $myConnection);
			
				header('Location: account.php?uid='.$uid.'&action=change');					
			}
			else
			{
				header('Location: edit_account.php?uid='.$uid.'&action=error1');				
			}
		}
		else
		{
			header('Location: edit_account.php?uid='.$uid.'&action=error1');			
		}
	}
	mysql_close($myConnection);
?>