<?php
	$connectUser = mysql_connect("$db", "$db_user", "$db_password");
	mysql_select_db("$database", $connectUser);
	
	$q_acct = mysql_query("SELECT getUsingUID('f', $uid) AS first_name, getUsingUID('l', $uid) AS last_name, getUsingUID('g', $uid) AS type", $connectUser);
			
	$acct = mysql_fetch_array($q_acct);
	switch ($acct['type']){
		case 12: $name = 'Student'; break;
		case 13: $name = 'Teacher'; break;
		case 14: $name = 'Adviser'; break;
	}
	mysql_close($connectUser);
?>

<div id="user-box" style="display: none;">
	<div id="user-details">
		<table id="user-table" cellpadding="4px" cellspacing="4px">
			<tr>
				<td colspan=2>
					<div id="account-name"><?php echo $acct['first_name']; ?></div>
				</td>
			</tr>
			<tr>
				<td colspan=2>
					<div id="account-type"><?php echo $name; ?></div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="userbox-tool" style="border-right-style: dotted; border-right-width: 1px;" ><img src="images/icon/userbox_type.png" /><a class="userbox" href="account.php?uid=<?php echo $uid; ?>&action=view">View profile</a></div>
				</td>
				<td>
					<div id="userbox-tool"><img src="images/icon/userbox_edit.png" /><a class="userbox" href="edit_account.php?uid=<?php echo $uid; ?>&action=edit">Edit account</a></div>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
				<div class="right">
					<a class="submit-light" href="enter_login.php">Logout</a>
				</div>
				</td>
			</tr>
		</table>
	</div>
</div>