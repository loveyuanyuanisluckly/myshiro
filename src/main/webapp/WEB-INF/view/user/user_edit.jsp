<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2017/12/17
  Time: 0:22
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
<div class="easyui-panel" title="用户信息修改" style="width:100%;max-width:400px;padding:30px 60px;">
    <form id="form">
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="id" value="${user.id}" style="width:100%"
                   data-options="label:'ID:',readonly:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="userName" value="${user.userName}" style="width:100%"
                   data-options="label:'账号:',required:true">
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="userEmail" value="${user.userEmail}" style="width:100%"
                   data-options="label:'email:',required:true,validType:'email'">
        </div>
        <div style="margin-bottom:20px">
            <input id="createTime" class="easyui-datebox" value="${user.createTime}" style="width:100%"
                   data-options="label:'创建时间:',readonly:true">
        </div>
        <div style="margin-bottom:20px">
            <input id="lastLoginName" class="easyui-datebox" value="${user.lastLoginTime}" style="width:100%"
                   data-options="label:'最近登陆时间:',readonly:true">
        </div>
    </form>
    <div style="text-align:center;padding:5px 0">
        <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" style="width:80px">修改</a>
    </div>
</div>
<script>
    $(function(){

        dateFormat('lastLoginName');
        dateFormat('createTime');

        //提交表单
        $('#submit').click(function () {
            var load = layer.load();
            $.post("${basePath}/user/editUser.do",$("#form").serialize() ,function(res){
                layer.close(load);
                if(res && res.state!= 200){
                    return layer.msg(res.msg,function(){}),!1;
                }else{
                    layer.msg(res.msg);
                }
            },"json");
        });
    });

    /**
     * 格式化时间
     * @param dateBoxId
     */
    var dateFormat = function (dateBoxId) {
        $('#'+dateBoxId+'').datebox({
            formatter: function(date){ return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate()
                +' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds();},
            parser: function(date){ return new Date(Date.parse(date.replace(/-/g,"/")));}
        });
    }
</script>
</body>
</html>
