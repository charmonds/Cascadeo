<?php
	$student = 12;
	$teacher = 13;
	$adviser = 14;
	
	$connection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $connection);
	$q_login = mysql_query("
		SELECT getUsingUID('g', $uid) AS type");
		
	$login = mysql_fetch_array($q_login);
	
	switch($login['type'])
	{
		case $student:	include('menu_student.php');
						break;
		case $teacher:	include('menu_teacher.php');
						break;
		case $adviser:	include('menu_adviser.php');
						break;
		default:
			echo "<a href=\"account.php?uid=" .$uid. "&action=view\">";
			echo "<div class=\"menu-selected\">";
			echo "<img src=\"images/menu_account_sel.png\" />";
			echo "Account";
			echo "</div>";
			echo "</a>";
			break;
						
	}
	
	mysql_close($connection);
?>