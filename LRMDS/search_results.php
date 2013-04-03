<?php
	require_once("db_connect.g");
	$myConnection = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $myConnection);
	
	if (!$myConnection)
	{
		die("Failed to connect: " . mysql_error());
	}
	else
	{
		$uid = $_GET['uid'];	
		$search = mysql_real_escape_string($_POST['searchq']);

		$query_allres = "SELECT title, id FROM learning_resources WHERE is_public = 1 AND title LIKE '%$search%' LIMIT 0,20;";
		$myResultSet = mysql_query($query_allres, $myConnection);
		$num_records = mysql_num_rows($myResultSet);
		
		
		if(mysql_num_rows($myResultSet) == 0)
		{
			echo "Search returned no results.";
		}
		else
		{
			echo "<div class=\"function1\">";
			echo "<h2 class=\"function1\">Resources containing ";	
			echo "\"" . $search ."\"";
			echo " - (" .$num_records. " results )";
			echo "</h2><br><br>";
			
			
			echo "<div class=\"rounded-corners1\">\n";
			echo "<table cellspacing=0 cellpadding=3><tr>";		
			
			$listnumber = 1;
			while($record = mysql_fetch_assoc($myResultSet)) {
				echo "<td align=\"right\" class=\"list-number-icon1\">" .$listnumber. "</td>";
				echo "<td class=\"width-spacer\">&nbsp;</td>";
				if ($listnumber % 2 == 0)
				{ echo "<td valign=\"middle\" class=\"list-even1\">"; }
				else
				{ echo "<td valign=\"middle\" class=\"list-odd1\">"; }
					
				echo "<a href=\"view_resource.php?rid=";	
				echo $record['id'];	
				echo "&uid=";
				echo $uid;	
				echo "\" class =\"list-item1\">";
				$id = $record['title'];
				echo "$id";
				echo "</a></td>";
				echo "</tr>";
				$listnumber++;
			}  
			echo "</table>";
			echo "</div> <!-- .round-corners1 -->";	
			echo "</div> <!-- .function1 -->";
		}
		
	}
?>
