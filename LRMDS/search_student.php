<?php
require_once("db_connect.g");
	$connectStudents = mysql_connect("$db", "$db_user", "$db_password");
	$connection = mysql_select_db("$database", $connectStudents);

	if (!$connectStudents)
	{
		die("Failed to connect: " . mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];
		$cid = $_GET['cid'];
		
		$lname = $_POST['lname'];
		$fname = $_POST['fname'];
		
		$filterbyname = "";
		
		if($lname!="" && $fname != "")
		{
			$filterbyname = " users.first_name LIKE '%$fname%' AND users.last_name LIKE '%$lname%' AND ";
			echo "Both not empty";
		}
		else if($fname=="" && $lname != "")
		{
			$filterbyname = " users.last_name LIKE '%$lname%' AND ";
			echo "First name is empty";
		}
		else if($fname != "" && $lname == "")
		{
			$filterbyname = " users.first_name LIKE '%$fname%' AND ";
			echo "Last name is empty";
		}else{
			
			$filterbyname = " users.first_name LIKE '%$fname%' OR users.last_name LIKE '%$lname%' AND ";

		}
		
		
		$search_student = mysql_query("
			SELECT users.id AS sid, 
			users.first_name AS fname, 
			users.middle_name AS mname, 
			users.last_name AS lname

			FROM users JOIN sf_guard_user 
			ON users.sf_guard_user_id = sf_guard_user.id
			JOIN sf_guard_user_group 
			ON sf_guard_user_group.user_id = sf_guard_user.id

			WHERE $filterbyname
			CONVERT( users.school_id USING utf8 ) = getUsingUID('c', $uid)
			AND sf_guard_user_group.group_id = 12
			 
			ORDER BY users.last_name",
			$connectStudents);
			 
		$num_results = mysql_num_rows($search_student);
		
		echo "<div class=\"resource-box\">";

		if($num_results > 0)
		{
			echo "<table cellspacing=0 cellpadding=3>";
			
			$listnumber = 1;
			while($student = mysql_fetch_array($search_student))
			{
			?>
				<tr>
				<td align="right" class="list-number-icon2"><?php echo $listnumber; ?> </td>
			
			<?php
				if ($listnumber % 2 == 0)
				{ 
					$td_class = "list-even2"; 
				}
				else
				{ 
					$td_class= "list-odd2";
				}				
			
				echo "<td valign=\"middle\" class=\"" .$td_class. "\">";
				echo $student['lname']. ", " .$student['fname']. " " .$student['mname'];
				echo "</td>";
				
				echo "<td valign=\"middle\" class=\"" .$td_class. "\" >";
				$check_in_class = mysql_query("
				SELECT student_id as sid
				
				FROM student_class
				WHERE student_id = " .$student['sid']. "
				AND student_class.class_id = $cid;
				", $connectStudents);
				
				$in_class = mysql_fetch_array($check_in_class);
				
				if($in_class)
				{
					echo "<img src=\"images/icon/success.png\" /> Already in section.";
				}
				else
				{
					echo "<a href=\"view_section.php?uid=" .$uid. "&cid=" .$cid. "&sid=" .$student['sid']. "&action=invite_student\"";
					echo " class=\"list-item2\">";
					echo "<img src=\"images/icon/add2.png\" /> Add student.";
					echo "</a>";
				}
				
				echo "</td>";
				echo "</tr>";
				
				$listnumber++;
			}
			?>
			
			</table>
	<?php
		}
		else
		{
			echo "<div class=\"instruction\">No record yet for student name.";
			echo "<a onclick=showAddBox(\"add-student\",\"invite-student\"); /> Create student acccount.</a></div>";
		}
		 
		echo "</div>";
		
		mysql_close($connectStudents);
	}
	
