<div align="center">
<?php
/* Store user details */
$name = $_POST['name'];
$pass = sha1($_POST['pass']);
$vpass = sha1($_POST['vpass']);
$username = $_POST['username'];
$address = $_POST['address'];
$phone = $_POST['phone'];
include('config.php');
$sel = 'SELECT * FROM accounts WHERE name="'.$_POST['name'].'"';
if($name == ""){
echo '계정을 입력해 주세요.';
exit();
}elseif(mysql_num_rows(mysql_query($sel)) >= 1 ){
echo '이미 사용중인 계정입니다!';
exit();
}elseif($pass == ""){
echo '비밀번호를 입력해 주세요.';
exit();
}elseif($vpass != $pass){
echo '비밀번호 확인이 틀렸습니다.';
exit();
}else{
mysql_query("set names euckr");
$d = 'INSERT INTO accounts (name, password, username, address, phone) VALUES ("'.$name.'", "'.$pass.'", "'.$username.'", "'.$address.'", "'.$phone.'")';
mysql_query($d) OR die (mysql_error());
echo '회원가입이 완료되었습니다.';
}
?>
<script>
self.resizeTo(document.body.scrollWidth,document.body.scrollHeight);
</script>
</div>