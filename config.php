


<?php
$host['naam'] = 'localhost';                // my host
$host['gebruikersnaam'] = 'root';       // my database username
$host['wachtwoord'] = 'root1';   // my database password
$host['databasenaam'] = 'odinms';       // my database name

$db = mysql_connect($host['naam'], $host['gebruikersnaam'], $host['wachtwoord']) OR die ('Cant connect to the database');
mysql_select_db($host['databasenaam'], $db);

mysql_query("set names utf8"); 



$serverip = "dbstjsehd.codns.com";     //Replace with your WAN IP if public
$loginport = "7575";	     //Don't change
$sql_db = "odinms";		     //DB Name
$sql_host = "localhost";     //DB Host
$sql_user = "root";	     	 //DB User
$sql_pass = "root1";		     	 //DB Password
$logserv_name = "<b>����</b>: ";		 //Status Server Name
$offline = "���� ����";  //Displays Offline Status
$online = "���� ����";	//Displays Online Status
?>
<?php
?>