<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2017/12/17
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${basePath}/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/css/themes/icon.css">
    <script  src="${basePath}/js/common/jquery/jquery.min.js"></script>
    <script  src="${basePath}/js/common/jquery/jquery.easyui.min.js"></script>
    <script  src="${basePath}/js/common/layer/layer.js"></script>
</head>
<body>
<div class="easyui-panel" title="添加用户信息" style="width:100%;max-width:400px;padding:30px 60px;">
    <form id="form">
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="userName" style="width:100%"
                   data-options="label:'账号:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="userEmail" style="width:100%"
                   data-options="label:'email:',required:true,validType:'email'">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-passwordbox" name="userPswd" style="width:100%"
                   data-options="label:'密码:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-passwordbox" id="re_userPswd" style="width:100%"
                   data-options="label:'确认密码:',required:true">
        </div>
    </form>
    <div style="text-align:center;padding:5px 0">
        <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" style="width:80px">添加</a>
    </div>
</div>

<script>
    //提交表单
    $('#submit').click(function () {
        var pswd = $('input[name=userPswd]').val();
        var re_paswd= $('#re_userPswd').val();
        if(pswd != re_paswd){
            return layer.msg('2次密码输出不一样！',function(){}),!1;
        }
        var load = layer.load();
        $.post("${basePath}/user/saveUser.do",$("#form").serialize() ,function(res){
            layer.close(load);
            if(res && res.state!= 200){
                return layer.msg(res.msg,function(){}),!1;
            }else{
                layer.msg(res.msg);
            }
        },"json");
    });
</script>
</body>
</html>
