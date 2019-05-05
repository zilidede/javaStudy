<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
<title>MOBAN</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="App Loction Form,Login Forms,Sign up Forms,Registration Forms,News latter Forms,Elements"./>
<!--webfonts-->
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,300,600,700,800' rel='stylesheet' type='text/css'/>
<!--css-->
<link href="/static/search/css/login.css" rel='stylesheet' type='text/css' />
</head>
<body>
	<h1></h1>
		<div class="app-location">
			<h2>登陆页</h2>
			<div class="line"><span></span></div>
			<div class="location"><img src="/static/images/location.png" class="img-responsive" alt="" /></div>
			<form>
				<input  id = 'username' type="text" class="text" value="E-mail address" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'E-mail address';}" >
				<input  id = 'password' type="password" value="Password" onFocus="this.value = '';" onBlur="if (this.value == '') {this.value = 'Password';}">
				<div class="submit"><input type="submit" onClick="onLogin()" value="登 陆" ></div>
				<div class="clear"></div>
				<div class="new">
					<h3><a href="#">忘记 密码 ?</a></h3>
					<h4><a href="#">新来的 ? 注册点这儿</a></h4>
					<div class="clear"></div>
				</div>
			</form>
		</div>

</body>
<!--Javascript-->
<script src="/static/jquery/js/jquery-3.3.1.js"></script>
<script src="/static/jquery/js/jquery-3.3.1.min.js"></script>
<script>
    /*－－－ 事件－－－*/

    function onLogin() {
        var json =GetJsonData();
        $.ajax({
            type:"POST",
            url:"/login",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(json),
            success: function (jsonObj) {
                    if (jsonObj.retcode == 0) {
                        alert("登陆成功","提示");
                    }else{
                        alert("错误原因","错误");
                    }
                },
                error: function () {
                    alert("登陆失败","错误");;
                }


        })


    }
    function GetJsonData(){
        var json ={
            "username":$("#username").val(),
            "password":$("#password").val()
        }
        return json
    }
</script>
</html>