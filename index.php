<html><style type="text/css">
<!--
body,td,th {
	color: #FF0000;
}
a:link {
	color: #FF0000;
	font-weight: bold;
}
a:hover {
	color: #000000;
}
-->
</style><body bgcolor="ffffff">
<head>
<title>TEST 웹 서버</title>
<style>
*{ FONT-SIZE: 8pt; FONT-FAMILY: verdana; } b { FONT-WEIGHT: bold; } .listtitle { BACKGROUND: #000000; COLOR: #EEEEEE; white-space: nowrap; } td.list { BACKGROUND: #EEEEEE; white-space: nowrap; } </style>
</head>
<center><p align='center'><a href="index.php" target="_self"><img src="logo.png" border=0></a>
</p>
  <p align='center'><br>
    <br>
    <strong><a href="register.php" target="halo">회원가입</a>| <a href="logout.php" target="halo">로그인</a></strong><br>
<br>
    <embed src="music.swf" align="middle" width=60 height=28><?php
include('config.php');
$result = mysql_query("SELECT * FROM accounts", $db);// Account section
$num_rows = mysql_num_rows($result);

echo '<font color=ff0000><b>서버 정보:</b>
<b>계정</b> <b>'.$num_rows.'</b>';
?>


<?php
include('config.php');
        echo $logserv_name;
        $fp = @fsockopen($serverip, $loginport, $errno, $errstr, 1);
	  if (!$fp) {
           echo $offline;
        } else {
           echo $online;
        }

        @fclose($fp);
        $sql = mysql_connect($sql_host, $sql_user, $sql_pass)or die("Error on trying to connect in MySQL: " . mysql_error());
        mysql_select_db($sql_db)or die("Error on trying to connect in Database");
        ?>
<br>
<strong><font color=ff0000>TEST SERVER</font></strong>
</center>
</body></html>