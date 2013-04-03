<?php	
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	
	$uid = $_GET['uid'];
	$action = $_GET['action'];
	
	$fieldname = $_GET['fieldname'];
	$oldpassword = $_POST['oldpassword'];
	$newpassword1 = $_POST['newpassword1'];
	$newpassword2 = $_POST['newpassword2'];
	
	if($fieldname == "newpassword1")
	{
		if($newpassword1 != "")
			{		
				$checker = '/[A-Za-z0-9]/';
				if(preg_match($checker, $newpassword1))
				{
					if(strlen($newpassword1) > 5 && strlen($newpassword1) <= 12)
					{
						echo "<img src=\"images/icon/success.png\" />";
					}
					else
					{
						
						echo "<img src=\"images/icon/warning.png\" />Password must contain between 5 to 12 characters.";
					}
				}
				else
				{		
					echo "<img src=\"images/icon/warning.png\" />Password consists of only alphanumeric characters, and must not contain spaces";
				}
			}
	}
	
	if($fieldname == "newpassword2")
	{
		if($newpassword1=="")
		{
			echo "";
		}
		else
		{
			if(strcmp($newpassword1,$newpassword2)==0)
			{
				
				echo "<img src=\"images/icon/success.png\" /> Passwords match.";
			}
			else
			{
				echo "<img src=\"images/icon/warning.png\" /> Passwords do not match.";
			}
		}
	}
	
	if($fieldname=="oldpassword")
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
			
				// $new_encrypted_pw = sha1($salt . $newpassword1);
				// mysql_query("UPDATE sf_guard_user SET password = '$new_encrypted_pw' WHERE id = $sf_guard_user_id", $myConnection);
			
				echo "<img src=\"images/icon/success.png\" />";	
		}
	
		else echo "<img src=\"images/icon/warning.png\" /> Old password wrong.";

	}
?>
