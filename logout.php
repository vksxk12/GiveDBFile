<center>
<?php

$conn = mysql_connect("127.0.0.1", "root", "root1") or die ('Error connecting to mysql!');
mysql_select_db("odinms");

$self = $_SERVER['PHP_SELF'];

if(isset($_POST['logout']))
{
	$validlogin = 0;
	$usr = $_POST['user'];
	
	if($usr == "")
		die(" ������ �Է��� �ּ���.");

	$usr = mysql_real_escape_string($usr); // prevents SQL injection

	$result = mysql_query("SELECT name, password FROM accounts");
	while($row = mysql_fetch_array($result))
	
		{ 
			$loggedin = $row[2];
			$loggedin = $row[1];
			$validlogin = 1;
			break;
		}
	
	
	if($validlogin == 0)
	{
		echo "<meta http-equiv='refresh' content='4;url=$self'>";
		die("<h1> ������ �������� �ʽ��ϴ�! </h1>");
	}
	else
	{
		if($loggedin > 0)
		{
			mysql_query("UPDATE accounts SET loggedin = 0 WHERE name = '$usr'");
			echo "<meta http-equiv='refresh' content='10;url=$Self'>";
			die("<h1>$usr ������ �α׾ƿ� �Ǿ����ϴ�.</h1>");
			
		}
		else
		{
			echo "<meta http-equiv='refresh' content='4;url=$self'>";
			die("<h1>$usr! ������ �α׾ƿ� �Ǿ����ϴ�.</h1>");
		}
	}	
}
else
{
	echo "<form method='post'>";
	echo "�α��� ���¶�� �߸� ������ ������ �� ���� ��� �����ϼ���.<br>
ĳ���� �̸��� �ƴ� ������ �Է��ϼž� �մϴ�.
";
	echo "<br><br>����: <input type='text' name='user' id='user'>";
	echo " <input type='submit' name='logout' id='logout' value='�α׾ƿ�!'><br><br>";
	echo "</form>";
}

mysql_close($conn);

?>
<script>
self.resizeTo(document.body.scrollWidth,document.body.scrollHeight);
</script>
