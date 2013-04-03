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
			
			$myResultSet = mysql_query("
			SELECT DISTINCT 
				learning_resources.title as lr_title, 
				classes.name as c_name, 
				resource_shelf.is_shown as is_shown, 
				learning_resources.id as lr_id, 
				resource_shelf.class_id as class_id 
			FROM resource_shelf
				JOIN learning_resources ON resource_shelf.learning_resource_id = learning_resources.id
				LEFT JOIN classes ON resource_shelf.class_id = classes.id
			WHERE resource_shelf.teacher_id=$uid 
			ORDER BY learning_resources.title", $connection2);		

			$num_records = mysql_num_rows($myResultSet);
	
			
			

			if($num_records)
			{				
				echo "<table><tr>";
				echo "<td class=\"resource-label\">Learning Resource Title</td>";
				
				if($login['type'] == $teacher)
				{
					echo "<td class=\"resource-label\">Is Shown to Class</td>";
					echo "<td class=\"resource-label\">Class Name/s</td>";
					echo "<td class=\"resource-label\">&nbsp;</td>";
				}
				echo "</tr>";
				
				$listnumber = 1;
				while($record = mysql_fetch_array($myResultSet)){
					
					$rid = $record['lr_id'];
					$cid = $record['class_id'];
					//$a = 0;
					

					$list_classes = mysql_query("
					SELECT 
						resource_shelf.class_id as class_id, 
						resource_shelf.is_shown as is_shown, 
						classes.name as class_name 
					FROM classes 
						JOIN resource_shelf ON resource_shelf.class_id = classes.id 
					WHERE resource_shelf.learning_resource_id = $rid", $connection2);
					
					$num_of_classes = mysql_query("
					SELECT COUNT(*) as count 
					FROM resource_shelf 
					WHERE resource_shelf.learning_resource_id = $rid", $connection2);
					
					$temp = mysql_fetch_array($num_of_classes);
					$rowspan = $temp['count'];

					if($listnumber % 2 == 0)
					{
						$td_class = "resource-info";
					}
					else
					{
						$td_class = "resource-info2";
					}
				
					//first column: learning resource title			
					echo "<tr><td class=\"" .$td_class. "\" rowspan=\"$rowspan\">";
					echo "<a href = \"view_resource.php?rid=" .$rid. "&uid=" .$uid. "\">" . $record['lr_title'] . "</a>";
					
					if($login['type']==$teacher)
					{
						for($i=1; $i<=$rowspan; $i++){
						$info = mysql_fetch_array($list_classes);
						//second column: shared / not shared
						echo "</td><td class=\"" .$td_class. "\">";
							if($info['is_shown'] == 1)
							{
								echo "shared";
								if($info['class_name'] != NULL)
								{
									echo "<div class=\"unshare\">";
									echo "<a class=\"unshare\" href=\"add_to_shelf.php?rid=";
									echo $rid;
									echo "&uid=";
									echo $uid;
									echo "&cid=";
									echo $info['class_id'];
									echo "&action=unshare\">";
									echo "unshare</a>";
									echo "</div>";
								}
							}
							else
							{
								echo "not shared";
								if($info['class_name'] != NULL)
								{
									echo "&nbsp;&nbsp;&nbsp;&nbsp;";
									echo "<div class=\"share\">";
									echo "<a class=\"share\" href=\"add_to_shelf.php?rid=";
									echo $rid;
									echo "&uid=";
									echo $uid;
									echo "&cid=";
									echo $info['class_id'];
									echo "&action=share\">";
									echo "share</a>";
									echo "</div>";
								}
							}
						//third column: if there is no class name yet, display dropdown. If there is, display the class name
						echo"</td><td class=\"" .$td_class. "\">";
						if($info['class_name'] == NULL){
							
							
							$getschoolid = mysql_query("
							SELECT school_id as school_id 
							FROM users 
							WHERE id = $uid", $connection2);
							
							$getschoolid2 = mysql_fetch_array($getschoolid);
							$school_id = $getschoolid2['school_id'];

							echo "<form name=\"select_class\" method=\"POST\" action=\"add_to_shelf.php?rid=";
							echo $record['lr_id'];
							echo "&uid=";
							echo $uid;
							echo "&action=add";
							echo "\">";
							echo "
							<select name =\"c_id\">
							<option>Select Class</option>";
							for ( $gType = 1; $gType < 18; $gType++){
								$myResultSet2 = mysql_query("
								SELECT classes.name as c_name, classes.id as c_id, grade_types.name as g_name
								FROM classes 
									JOIN users ON classes.adviser_id = users.id
									JOIN grade_types ON grade_types.id = classes.grade_type_id
								WHERE users.school_id = $school_id 
									AND classes.grade_type_id = $gType
								ORDER BY classes.grade_type_id", $connection2);
								if ($myResultSet2 != NULL){ 
									$record2 = mysql_fetch_array($myResultSet2);
									if($record2['c_name'] != NULL){
										echo "<option>--";
										echo $record2['g_name'];
										echo "--</option>";
									}
									do{
										$check_if_shared = 0;
										//get classes where the resource is already shared to
										$getclass = mysql_query("SELECT class_id as class_id from resource_shelf where learning_resource_id = $rid", $connection2);
										while($record3 = mysql_fetch_array($getclass)){
											if($record2['c_id'] == $record3['class_id']){
												$check_if_shared = 1;
											}
										}
										//the only sections on the menu are those sections the resource is not yet shared to
										if($check_if_shared == 0) {
											echo "<option value=";
											echo $record2['c_id'];
											echo ">";
											echo $record2['c_name'];
											echo "</option>";
										}	
									}while($record2 = mysql_fetch_array($myResultSet2));
								}
							}
							echo "</select><input type=\"submit\" value=\"Save\">";
							echo"</form>";
						}			
						else {
							echo $info['class_name'];
							if($i == $rowspan){
								echo "<br><a href=\"add_to_shelf.php?rid=";
								echo $rid;
								echo "&uid=";
								echo $uid;
								echo "&cid=";
								echo $cid;
								echo "&action=put";
								echo "\"><img src=\"images/icon/add.png\" width=\"15px\"></a>";
							}
							
						}
						echo "</td>";
						
						//fourth column: remove button
						echo "<td class=\"" .$td_class. "\">";
						echo "<div class=\"remove\">";
						echo "<a class=\"remove\" href=\"add_to_shelf.php?rid=";
						echo $rid;
						echo "&uid=";
						echo $uid;
						echo "&cid=";
						echo $info['class_id'];
						echo "&action=remove\">";
						echo "Remove</a>";
						echo "</div>";
						echo "</td></tr>";
						
						
					}
				}
				$listnumber++;
				for($x=2;$x<=$rowspan;$x++) $mover = mysql_fetch_array($myResultSet);	
							
				}
				
				echo"</table>";
			}
			
			else
			{
				echo "<div class=\"instruction\">No learning resources added yet.</div>";
			}
			
			if($login['type']==$teacher)
			{
				echo "<div class=\"tool\">";
				echo "<a href=\"resources.php?uid=";
				echo $uid;
				echo "\" class=\"tool\">Add resource</a>";
				echo "</div>";
				/* cancel button: removes rows na wala pang class_name; goes back to template.php
				echo"<a href=\"template.php?action=cancel&uid=";
				echo $uid;
				echo "\"> Cancel </a>";
				*/
			}
		}
		else
		{
			$cid = $_GET['cid'];
			$rid = $_GET['rid'];
			// if clicked from the resource page
			if($action == "put")
			{	
				mysql_query("INSERT INTO resource_shelf VALUES($rid, $uid, NULL, 0)", $connection2);
				header('Location: my_resources.php?action=shelved&rid=' .$rid. "&uid=" .$uid);
			}
			
			// if remove is clicked
			if($action == "remove")
			{
				mysql_query("DELETE FROM resource_shelf WHERE learning_resource_id = $rid AND teacher_id = $uid AND class_id = $cid", $connection2);
				header('Location: my_resources.php?action=remove&rid=' .$rid. "&uid=" .$uid. "&cid=" . $cid);
			}
				
			// if a class name is selected and saved
			if($action == "add"){
				$cid = $_POST['c_id'];
				
				if($cid > 0)
				{
					mysql_query("UPDATE resource_shelf SET class_id = $cid WHERE learning_resource_id = $rid AND teacher_id = $uid AND isnull(class_id) = 1",$connection2);
				header('Location: my_resources.php?action=add&rid=' .$rid. "&uid=" .$uid. "&cid=" .$cid);
				}
				else echo 
				header('Location: my_resources.php?action=no_class&rid=' .$rid. "&uid=" .$uid);
			}
			
			//if share button is clicked
			if ($action == "share")
			{
				mysql_query("UPDATE resource_shelf SET is_shown = 1 WHERE learning_resource_id = $rid AND teacher_id = $uid AND class_id = $cid", $connection2);
				header('Location: my_resources.php?action=share&rid=' .$rid. "&uid=" .$uid. "&cid=" .$cid);
			}

			//if unshare button is clicked
			if($action == "unshare")
			{
				mysql_query("UPDATE resource_shelf SET is_shown = 0 WHERE learning_resource_id = $rid AND teacher_id = $uid AND class_id = $cid", $connection2);
				header('Location: my_resources.php?action=unshare&rid=' .$rid. "&uid=" .$uid. "&cid=" .$cid);
			}

			
			
		}
		
		mysql_close($connection2);
	}
?>
