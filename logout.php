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
		die(" 계정을 입력해 주세요.");

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
		die("<h1> 계정이 존재하지 않습니다! </h1>");
	}
	else
	{
		if($loggedin > 0)
		{
			mysql_query("UPDATE accounts SET loggedin = 0 WHERE name = '$usr'");
			echo "<meta http-equiv='refresh' content='10;url=$Self'>";
			die("<h1>$usr 계정이 로그아웃 되었습니다.</h1>");
			
		}
		else
		{
			echo "<meta http-equiv='refresh' content='4;url=$self'>";
			die("<h1>$usr! 계정이 로그아웃 되었습니다.</h1>");
		}
	}	
}
else
{
	echo "<form method='post'>";
	echo "로그인 상태라고 뜨며 서버에 접속할 수 없을 경우 실행하세요.<br>
캐릭터 이름이 아닌 계정을 입력하셔야 합니다.
";
	echo "<br><br>계정: <input type='text' name='user' id='user'>";
	echo " <input type='submit' name='logout' id='logout' value='로그아웃!'><br><br>";
	echo "</form>";
}

mysql_close($conn);

?>
<script>
self.resizeTo(document.body.scrollWidth,document.body.scrollHeight);
</script>
