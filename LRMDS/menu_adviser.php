<?php
	$img_addr = array("menu_dashboard","menu_myclasses"," ","menu_account");
	$div_on = "<div class=\"menu-selected\">";	
	$div_end = "</div>";
	$div_off = "<div class=\"menu-item\">";
	
	$menu_div = array($div_off, $div_off, " ", $div_off);
	
	switch($page_id)
	{
		case 0:	$img_addr[0] .= "_sel";
				$menu_div[0]	= $div_on;
				break;
		case 1:	$img_addr[1] .= "_sel";	
				$menu_div[1]	= $div_on;
				break;
		case 3:	$img_addr[3] .= "_sel";	
				$menu_div[3]	= $div_on;
				break;
		default:
			break;
	}
?>

<table cellspacing=0 cellpadding=0>
	<tr>
		<td>
			<a href="home.php?uid=<?php echo $uid; ?>">
				<?php echo $menu_div[0]; ?>	
				<img src="images/<?php echo $img_addr[0]; ?>.png" />			
					Home				
				<?php echo $div_end; ?>
			</a>
		</td>
		<td>
			<a href="classes.php?uid=<?php echo $uid; ?>&action=view">
				<?php echo $menu_div[1]; ?>
				<img src="images/<?php echo $img_addr[1]; ?>.png" />
					Classes
				<?php echo $div_end; ?>
			</a>
		</td>
		<td>
			<a href="account.php?uid=<?php echo $uid; ?>&action=view">
				<?php echo $menu_div[3]; ?>
				<img src="images/<?php echo $img_addr[3]; ?>.png" />
					Account
				<?php echo $div_end; ?>
			</a>
		</td>
	</tr>
</table>








