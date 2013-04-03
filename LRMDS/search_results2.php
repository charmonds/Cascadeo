<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	$myConnection = mysql_select_db("$database", $myConnection);
		
	if (!$myConnection)
	{
		die("Failed to connect: ". mysql_error());
	}
	else
	{		
		$uid = $_GET['uid'];
		$gtypeid = $_GET['grade_type'];
	
		$q_allresourcespergrade = "SELECT learning_resources.title as lrtitle,
		learning_resources.id as lrid
		FROM learning_resources
		JOIN learning_resource_educationals ON learning_resource_educationals.learning_resource_id = learning_resources.id
		JOIN learning_resource_educationals_grade_type ON learning_resource_educationals_grade_type.learning_resource_educational_id = learning_resource_educationals.id
		JOIN grade_types ON learning_resource_educationals_grade_type.grade_type_id = grade_types.id
		WHERE is_public = 1 AND grade_types.id = '".$gtypeid."' ORDER BY learning_resources.title";		
		
		$resources = mysql_query($q_allresourcespergrade);
		$num_records = mysql_num_rows($resources);
		
		echo "<br /><br />";
		echo $resources['lrid'];
		
		$q_gradetype = "SELECT grade_types.name as gtypename FROM grade_types WHERE grade_types.id = '".$gtypeid."'";	
		$gtypename = mysql_query($q_gradetype);
		$grade = mysql_fetch_array($gtypename);
		
		echo "<div class=\"function2\">";
		if($gtypeid >= 1)
		{	
			echo "<h2 class=\"function2\">Resources for ";	
			echo $grade['gtypename'];
			echo " - (" .$num_records. " results )";
			echo "</h2><br><br>";
		}
		
		echo "<div class=\"rounded-corners2\">";
		echo "<table cellspacing=0 cellpadding=3><tr>";

		$listnumber = 1;
		while($record = mysql_fetch_assoc($resources))
		{
			echo "<td align=\"right\" class=\"list-number-icon2\">" .$listnumber. "</td>";
			
			echo "<td class=\"width-spacer\">&nbsp;</td>";
			if ($listnumber % 2 == 0)
			{ echo "<td valign=\"middle\" class=\"list-even2\">"; }
			else
			{ echo "<td valign=\"middle\" class=\"list-odd2\">"; }
			
			echo "<a href = \"view_resource.php?uid=";	
			echo $uid;
			echo "&rid=";
			echo $record['lrid'];	
			echo "\" class =\"list-item2\">";
			echo $record['lrtitle'];
			echo "</a></td>";
			echo "</tr>";
			$listnumber++;
		}  
		
		echo "</table>";
		echo "</div>";
		echo "</div>";
	}
?>