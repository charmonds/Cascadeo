<?php	
	include("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $myConnection);

	$myResultSet = mysql_query("SELECT education_types.id as ed_id, education_types.name as ed_name FROM education_types", $myConnection);

	$uid = $_GET['uid'];
	echo "<div class=\"function1\">";
	echo "<h2 class=\"function1\">Select Grade Level:</h2><br />";

	echo "<form method=\"GET\" name=\"select_education_type\" action=\"add_class_grade_type.php?uid=" .$uid. "\">";
	echo "<label class=\"function1\">Education Type</label><br />";
	echo "<select name=\"ed_id\" onchange=loadEducationList(this.value,\"add_class_grade_type.php?uid=" .$uid. "&ed_id=\"+this.value,1)>";
	echo "<option>Select</option>";
					
	while($record = mysql_fetch_array($myResultSet)){
		echo "<option value=";
		echo $record['ed_id'];
		echo ">";
		echo $record['ed_name'];
		echo "</option>";
	}
					
	echo "</select>";
	echo "</form>";
			
			
	echo "<div id=\"requests-content1\">";
			
	echo "</div>";
	echo "</form>";
	echo "</div>";
	echo "</div>";

	mysql_close($myConnection);
?>