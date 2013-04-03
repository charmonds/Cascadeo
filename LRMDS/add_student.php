<?php
require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $myConnection);

	if (!$myConnection)
	{
		die("Failed to connect: " . mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];
		$cid = $_GET['cid'];
		
		//form to get information about the student to be added
		
		echo "<div class=\"add-box\">";
		echo "<h3 class=\"function2\">New Student Information:</h3><br />";
		echo "<form name=\"add_student\" method=\"POST\" action=\"view_section.php?uid=";
		echo $uid;
		echo "&cid=";
		echo $cid;
		echo "&action=add_student";
		echo "\">";
		echo " ";
		
		echo "<table><tr>";
		echo "<td><label class=\"function2\">Last Name:</label></td>";
		echo "<td width=\"20px\">&nbsp;</td>";
		echo "<td><input class=\"student\" type=\"text\" name=\"lname\" /></td>";
		echo "</tr><tr>";
		
		echo "<td><label class=\"function2\">First Name:</label></td>";
		echo "<td></td>";
		echo "<td><input class=\"student\" type=\"text\" name=\"fname\" /></td>";
		echo "</tr><tr>";
		
		echo "<td><label class=\"function2\">Middle Name:</label></td>";
		echo "<td></td>";
		echo "<td><input class=\"student\" type=\"text\" name=\"mname\" /></td>";
		echo "</tr><tr>";
		
		echo "<td><label class=\"function2\">Email Address:</label></td>";
		echo "<td></td>";
		echo "<td><input class=\"student\" type=\"text\" name=\"emailaddress\" /></td>";
		
		echo "</tr></table>";
		echo "<br /><br />";
		echo "<input class=\"submit-light\" type=\"submit\" value=\"Add student to class\" />";
		echo "<input class=\"clearforms\" type=\"reset\" value=\"Clear all\" />";
		echo "</form>";
		echo "</div>";
	}
?>

