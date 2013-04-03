<?php
	require_once("db_connect.g");
	$connection2 = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $connection2);
	
	if (!$connection2)
	{
		die("Failed to connect: ". mysql_error());
	}		
	else
	{
		$uid = $_GET['uid'];
		$action = $_GET['action'];
		
		$q_login = mysql_query("
				SELECT getUsingUID('g', $uid) AS type", $connection2);
		
		$login = mysql_fetch_array($q_login);
		$teacher = 13;
		
		if($action == "view")
		{
			//displays all resources on the shelf of the logged in teacher
			$resourceList = mysql_query("SELECT DISTINCT
				learning_resources.title as lr_title,
				learning_resources.id as lr_id,
				resource_shelf.teacher_id as teacher_id
				FROM resource_shelf 
				JOIN learning_resources ON resource_shelf.learning_resource_id = learning_resources.id
				JOIN classes ON resource_shelf.class_id = classes.id
				JOIN student_class ON student_class.class_id = classes.id
				JOIN users ON student_class.student_id = users.id
				WHERE users.id=$uid AND resource_shelf.is_shown = 1
				ORDER BY resource_shelf.teacher_id, learning_resources.title", $connection2);
			
			$num_records = mysql_num_rows($resourceList);
			
			if($num_records)
			{				
				echo "<table><tr>";
				echo "<td class=\"resource-label\">Learning Resource Title</td>";
				echo "<td class=\"resource-label\">Shared by</td>";
				echo "<td class=\"resource-label\">View Resource</td>";
				echo "</tr>";
				
				$listnumber = 1;
				while($record = mysql_fetch_array($resourceList)){
					$rid = $record['lr_id'];
					
					if($listnumber % 2 == 0)
					{
						$td_class = "resource-info";
					}
					else
					{
						$td_class = "resource-info2";
					}
				
					//first column: learning resource title			
					echo "<tr><td class=\"" .$td_class. "\">";
					echo $record['lr_title'];
					
					//second column: teacher who shared the document
					echo "</td><td class=\"" .$td_class. "\">";
					$teacher_id = $record['teacher_id'];
					$temp = mysql_query("SELECT getUsingUID('f', $teacher_id) AS teacher_fname, getUsingUID('l', $teacher_id) AS teacher_lname FROM users WHERE users.id = $teacher_id", $connection2);
					$temp2 = mysql_fetch_array($temp);	
					$teacher_lname = $temp2['teacher_lname'];
					$teacher_fname = $temp2['teacher_fname'];
					echo $teacher_fname . " " . $teacher_lname . "</td>";
					//third column: download resource/ for now view resource
					echo"</td><td class=\"" .$td_class. "\">";
					echo "<a href=\"view_resource_student.php?uid=";
					echo $uid;
					echo "&rid=";
					echo $record['lr_id'];
					echo "\">";		
					echo "View</a></td></tr>";
						
					$listnumber++;
					
				}
				echo"</table>";
			}
			
			else
			{
				echo "<div class=\"instruction\">No learning resources added yet.</div>";
			}
		}
		
		mysql_close($connection2);
	}
?>
