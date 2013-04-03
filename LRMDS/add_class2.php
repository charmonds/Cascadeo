<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
   "http://www.w3.org/TR/html4/strict.dtd">

<?php
	require_once("db_connect.g");
	$myConnection1 = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection1);
	$page_id = 1;
	
	if (!$myConnection1)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{		
		$uid = $_GET['uid'];
		$gtype_id_add = $_POST['gtype_id'];
		$c_name = $_POST['c_name'];
		
		$q_checkunique = mysql_query("SELECT * FROM classes WHERE adviser_id = $uid AND grade_type_id = $gtype_id_add AND name = '$c_name';", $myConnection1);
		$exists = mysql_num_rows($q_checkunique);		
		
		if($exists)
		{		
			header('Location: classes.php?uid=' .$uid. '&c_name=' .$c_name. '&action=already_exists');	
		}
		else
		{
			mysql_query("INSERT INTO classes (name, adviser_id, grade_type_id) VALUES ('$c_name', $uid, $gtype_id_add)", $myConnection1);			
			header('Location: classes.php?uid=' .$uid. '&c_name=' .$c_name. '&action=add_class');
		}
		
		
		mysql_close($myConnection1);
		
		
	}
	
					
?>