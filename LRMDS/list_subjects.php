<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $myConnection);
	
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{
		$g_id = $_GET['g_id'];
		
		$q_subject = mysql_query("
			SELECT curriculum_area_types.name as ctype_name, 
			curriculum_area_types.id as ctype_id 
			FROM curriculum_area_types JOIN grade_types 
			on curriculum_area_types.grade_type_id = grade_types.id where grade_types.id = $g_id;", $myConnection);
			
		echo "<label class=\"function1\">Subject:</label>";
		echo "<select name=\"curriculum_area_type\">";
		echo "<option value=0>Select Subject</option>";
		while($subject = mysql_fetch_array($q_subject))
		{
			echo "<option value=";
			echo $subject['ctype_id'];
			echo ">";
			echo $subject['ctype_name'];
			echo "</option>";
		}
		echo "</select>";
	}
	
	mysql_close($myConnection);
?>