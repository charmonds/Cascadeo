<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);
	$page_id = "resources";
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}		
	else {
		
		//get the user id of the logged in user
		$uid = $_GET['uid'];

		//get the user information
		$myResultSet = mysql_query("
		SELECT getUsingUID('l', $uid) AS  lname,
		getUsingUID('f', $uid) AS  fname,
		getUsingUID('m', $uid) AS  mname,
		getUsingUID('i', $uid) AS  c_name,
		getUsingUID('u', $uid) AS  username", $myConnection);
		$record = mysql_fetch_array($myResultSet);

		
		//get the name of the class the student belongs in
		$c_name = $record['c_name'];
		$myResultSet2 = mysql_query("
		SELECT users.last_name as adviser_lname,
		users.first_name as adviser_fname
		FROM users
		JOIN classes ON classes.adviser_id = users.id
		WHERE classes.name = '$c_name'
		", $myConnection);
		$record2 = mysql_fetch_array($myResultSet2);
	}
?>

<html>
<head>
	<title>LRMDS</title>
</head>
<body>
	<table border=1 width="80%">
	<tr><td width="30%">Name</td><td><?php echo $record['lname'] . ", " . $record['fname'] . " " . $record['mname'] . "";?></td></tr>
	<tr><td>Username</td><td><?php echo $record['username'];?></td></tr>	
	<tr><td>Class/Section</td><td><?php echo $record['c_name'];?></td></tr>
	<tr><td>Adviser</td><td><?php echo $record2['adviser_fname'] . " " . $record2['adviser_lname'];?></td></tr>
	</table>

	<a href="student_home.php?uid=<?php echo $uid?>">Back</a>
</body>
</html>
