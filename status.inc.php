<?php
        echo $logserv_name;
        $fp = @fsockopen($serverip, $loginport, $errno, $errstr, 1);
	  if (!$fp) {
           echo $offline;
        } else {
           echo $online;
        }

        @fclose($fp);
        echo $worldserv_name;
        $fp = @fsockopen($severip, $worldport, $errno, $errstr, 1);
        if (!$fp) {
           echo $offline;
        } else {
           echo $online;
        }
        @fclose($fp);
        $sql = mysql_connect($sql_host, $sql_user, $sql_pass)or die("Error on trying to connect in MySQL: " . mysql_error());
        mysql_select_db($sql_db)or die("Error on trying to connect in Database");
        ?>
