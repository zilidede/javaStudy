<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<body  text-align:center class="frame"  onload="Load()">

<title>搜索首页</title>
<!--css-->
<link rel="stylesheet" href="/static/search/css/main.css">
<meta http-equiv="Content-Type" content="text/html charset=utf-8">
<form >
    <div class="set">
        <span  class = "login_span" id="id_s" aria-hidden="false">11</span>
        <img  id ="user_img"src="/static/images/login.png" alt="" height="38" width="56" data-atf="3" class="login_img"   target="_blank" onclick="window.location.href='login'">
        <img src="/static/images/menu.png" alt="" height="42" width="42" data-atf="3" class="menu_img">
    </div>

    <div class="search">
        <img src="/static/images/logo.png" alt="" height="30" width="92" data-atf="3" class="logo">
    <br>
        <br>
        <input type="text" class="self-input" id="keyword" height="30" width="400">
        <input type="submit" value="搜索" id="" class="self-btn" ONCLICK="onQuery()">
    </div>
</form>
<!--Javascript-->
  <script src="/static/jquery/js/jquery-3.3.1.js"></script>
  <script src="/static/jquery/js/jquery-3.3.1.min.js"></script>

<script>
    /*－－－ 全局变量－－－*/
    var username="登陆"
    var userImg="/static/images/login.png"
    /*－－－ 事件－－－*/
    /*－－－ 自定义函数－－－*/

    function onQuery(){
        var keyword =$("#keyword").val()
        alert(keyword)
    }

    function Load() {
        $('#id_s').text(username);
        $('#user_img').attr('src',userImg);

    }

</script>



</body>
</html>
