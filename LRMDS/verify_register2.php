<?php	
	require_once("db_connect.g");
	
	
	$fieldname = $_GET['fieldname'];
	
	switch($fieldname)
	{
		
		case "lname":
		case "fname":
		case "mname":
		
		$input = $_POST[$fieldname];
		
		$checker = '/^[a-zA-Z]+[(-|\' )a-zA-Z]*[a-zA-Z]$/';
		
		if($input=="")
		{
			echo "";
		}
		else
		{
			if(preg_match($checker, $input))
			{ 
				echo "<img src=\"images/icon/success.png\" />";
			} 
			else
			{
				echo "<img src=\"images/icon/warning.png\" /> Name contains invalid characters.";
			}
		}
				
			break;
	}
	
	if($fieldname=="username")
	{
		$username = $_POST['username'];
		
		
		if($username=="")
		{
			echo "";
		}
		else 
		{
			$checker = '/^[^\W][a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\@[a-zA-Z0-9_]+(\.[a-zA-Z0-9_]+)*\.[a-zA-Z]{2,4}$/';
			if(preg_match($checker, $username)==0)
			{
				echo "<img src=\"images/icon/warning.png\" /> Invalid e-mail address.";													
			}
			else
			{
				$checkUsers = mysql_connect("$db", "$db_user", "$db_password");
				mysql_select_db("$database", $checkUsers);
	
				if (!$checkUsers)
				{
					die("Failed to connect: ". mysql_error());
				}
				
				$test = mysql_query("SELECT verifyAccount('$username') as username", $checkUsers);
				$result = mysql_fetch_array($test);
				
		
				mysql_close($checkUsers);
				
				if($result['username'])
				{
					echo "<img src=\"images/icon/warning.png\" /> Username already exists. Please use a different username or check if you already have an account.";													
				}
				else
				{
					echo "<img src=\"images/icon/success.png\" /> <b>'" .$username. "'</b> is available.";	
				}
			}
		}
	}
	
	if($fieldname=="password1")
	{
		$password1 = $_POST['password1'];
		
		if($password1=="")
		{
			echo "";
		}
		
		if($password1!="")
		{		
			$checker = '/[A-Za-z0-9]/';
			if(preg_match($checker, $password1))
			{
				if(strlen($password1) > 5 && strlen($password1) <= 12)
				{
					echo "<img src=\"images/icon/success.png\" />";
				}
				else
				{
					echo "<img src=\"images/icon/warning.png\" />Password must contain between 5 to 12 characters.";
				}
			}
			else
			{
				echo "<img src=\"images/icon/warning.png\" />Password consists of only alphanumeric characters, and must not contain spaces";
			}
		}
	}
	
	if($fieldname=="password2")
	{
		$password1 = $_POST['password1'];
		$password2 = $_POST['password2'];
		
		if($password1=="")
		{
			echo "";
		}
		else
		{
			if(strcmp($password1,$password2)==0)
			{
				echo "<img src=\"images/icon/success.png\" /> Passwords match.";
			}
			else
			{
				echo "<img src=\"images/icon/warning.png\" /> Passwords do not match.";
			}
		}
	}
	
	if($fieldname=="subjects")
	{
		if($_POST['curriculum_area_type']==0 && ($_POST['grade_type'] != 7 && $_POST['grade_type'] != 8))
		{
			echo "<img src=\"images/icon/warning.png\" /> Please select a subject.";
		}
		else
		{
			echo "<img src=\"images/icon/success.png\" />";
		}
	}
	
	
		if($fieldname=="specialization")
		{
			if($_POST['grade_type'])
			{
				echo "<img src=\"images/icon/success.png\" />";
			}
			else
			{
				echo "<img src=\"images/icon/warning.png\" /> Please select a grade level.";
			}
		}
	

?>