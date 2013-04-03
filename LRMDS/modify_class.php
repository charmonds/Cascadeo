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
		$action = $_GET['action'];
		
		$cid = $_GET['cid'];
		$q_section = mysql_query("SELECT getClassName($cid) AS name", $myConnection1);
		$section = mysql_fetch_array($q_section);
		
		if($action == "remove")
		{
			$q_remove = mysql_query("DELETE FROM classes WHERE id = $cid", $myConnection1);
			header('Location: classes.php?uid=' .$uid. "&c_name=" .$section['name'] . "&action=remove#message");
		}
		
		
		mysql_close($myConnection1);		
	}
	
					
?>