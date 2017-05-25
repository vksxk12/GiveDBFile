<html><body bgcolor="ffffff">
<head>
<title>
<div align="center">회원가입</div>
</title>
<div align="center">
  <style>
*{ FONT-SIZE: 8pt; FONT-FAMILY: verdana; } b { FONT-WEIGHT: bold; } .listtitle { BACKGROUND: #ffffff; COLOR: #ff0000; white-space: nowrap; } td.list { BACKGROUND: #EEEEEE; white-space: nowrap; } 
</style>
  </head>
  <table cellspacing=1 cellpadding=5>
    <tr><td class=listtitle colspan=2><center>
      <b>회원가입</b>
    </center></td></tr>
    <form action="register_do.php" method="POST">
      <tr><td class=list align=right>아이디:</td><td class=list><input type=text name=name maxlength="30"></td></tr>
      <tr><td class=list align=right>비밀번호:</td><td class=list><input type=password name=pass maxlength="30"></td></tr>
      <tr><td class=list align=right>비밀번호확인:</td><td class=list><input type=password name=vpass maxlength="30"></td></tr>
      <tr><td class=list align=right>이름:</td><td class=list><input type=text name=username maxlength="30"></td></tr>
      <tr><td class=list align=right>주소:</td><td class=list><input type=text name=address maxlength="30"></td></tr>
      <tr><td class=list align=right>전화번호:</td><td class=list><input type=text name=phone maxlength="30"></td></tr>
      <tr><td class=listtitle align=right colspan=2><center><input type=submit name=submit value='회원가입'</td></tr></center>
    </form>
  </table>
  <br>
</div>
<div align="center"></div>
<script>
self.resizeTo(document.body.scrollWidth,document.body.scrollHeight);
</script>
</body></html>