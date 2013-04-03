<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $myConnection);

	if (!$myConnection)
	{
		die("Failed to connect: " . mysql_error());
	}
	else
	{ 
		$un = $_POST['username'];
		$ps = $_POST['password'];
		$myResultSet = mysql_query("
		SELECT account('g', '$un', '$ps') AS id,
		account('l', '$un', '$ps') AS uid", $myConnection);
		
		$record = mysql_fetch_array($myResultSet);
		do
		{
			if ($record['id'] == NULL) 
			{
				header('Location: enter_login.php?action=error');
			}
			else
			{
				header('Location: home.php?uid='.$record['uid']);
			}
		} while ($record = mysql_fetch_array($myResultSet));
		
		mysql_close($myConnection);
}
