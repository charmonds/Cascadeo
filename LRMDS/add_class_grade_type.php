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
		$ed_id = $_GET['ed_id'];
		$myResultSet = mysql_query("SELECT grade_types.id as gtype_id, grade_types.name as gtype_name FROM grade_types WHERE education_type_id = $ed_id", $myConnection);

		//drop down menu
		//items depend on the selected education type from previous page
		
		echo "<br /><label class=\"function1\">Section</label><br />";
		echo "<form name=\"select_grade_type\" method=\"POST\" action=\"add_class2.php?uid=";
		echo $uid;
		echo "\">";
		
		echo "<select name=\"gtype_id\" ";
		echo " onchange=showSubmit(\"add_class2.php?uid=";
		echo $uid;
		echo "&gtype_id=\"+this.value+\"&action=view\")";
		echo ">";
		
		echo "<option name=\"grade\">Select Grade Level</option>";
		while($record = mysql_fetch_array($myResultSet)){
			echo "<option value=";
			echo $record['gtype_id'];
			echo ">";
			echo $record['gtype_name'];
			echo "</option>";
		}
		
		echo "</select>";
				
		echo "<br /><br /><br />";
		echo "<div id=\"submitthis\">";
		echo "<div class=\"add-box\" name=\"#sub\">";
		echo "<label class=\"function1\">Enter name for new section:</label><br />";
		echo "<input class=\"search-function1\" type=\"textbox\" name=\"c_name\" />";
		echo "<br />";
		echo "<input type=\"submit\" class=\"submit-function1\" value=\"Add Class\"/>";
		echo "<div id=\"error\"></div>";
		echo "</div>";
		echo "</div>";
		
		echo "</form>";
		
		mysql_close($myConnection);
	}
?>
