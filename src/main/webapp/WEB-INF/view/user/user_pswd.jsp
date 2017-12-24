<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2017/12/6
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<body>
<div style="margin:188px 0;"></div>
<h2 style="text-align: center">密码修改</h2>
<form id="form" style="text-align: center">
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" name="userName" style="width:16%"
               data-options="label:'账号:',required:true">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" name="userEmail" style="width:16%"
               data-options="label:'email:',required:true,validType:'email'">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-passwordbox" name="userPswd" style="width:16%"
               data-options="label:'原密码:',required:true">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-passwordbox" id="new_userPswd" style="width:16%"
               data-options="label:'修改密码:',required:true">
    </div>
</form>
<div style="text-align:center;padding:5px 0">
    <a href="javascript:void(0)" class="easyui-linkbutton" id="submit" style="width:80px">修改</a>
</div>
<script type="text/javascript">
    $('#submit').click(function () {
        var title = '您确定要修改密码？';
        layer.confirm(title, {icon: 2, title: ''}, function (index) {
            var load = layer.load();
            var param = $("#form").serialize() + "&newPswd=" + $('#new_userPswd').val();
            $.post("${basePath}/user/updatePswd.do", param, function (res) {
                layer.close(load);
                if (res && res.state != 200) {
                    return layer.msg(res.msg)||!1;
                } else {
                    layer.msg(res.msg);
                }
            }, "json");
            layer.close(index);
        });
    });
</script>
</body>
</html>