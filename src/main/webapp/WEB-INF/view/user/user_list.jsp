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
<h2 style="text-align: center">用户管理</h2>
<div style="margin:20px;"></div>
<!-- 工具栏-->
<div id="toolbar" >
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="addUser()">添加</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="editUser()">修改</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="deleteUser()">删除</a>
    <div style="width:25%;float: right;">
        <input id="account" class="easyui-searchbox" data-options="prompt:'请输入账号',searcher:doSearch">
    </div>
</div>
<!-- 显示-->
<table id="list_data" cellspacing="0" cellpadding="0" toolbar="#toolbar">
    <thead>
        <tr>
            <th data-options="field:'id',width:336">用户ID</th>
            <th data-options="field:'userName',width:336">账号</th>
            <th data-options="field:'userEmail',width:336">Email</th>
            <th data-options="field:'createTime',width:336,align:'left'">创建时间</th>
            <th data-options="field:'lastLoginTime',width:336,align:'left'">最后登陆时间</th>
        </tr>
    </thead>
</table>
<script type="text/javascript">
    $(function () {
        //分页初始化参数
        var pager = $('#list_data').datagrid({
            title: '用户信息',
            iconCls: 'icon-edit',//图标
            fit: false,//自动大小
            rownumbers: true,
            singleSelect: false,
            pagination: true,
            url: '${basePath}/user/queryUsers.do',
            frozenColumns: [[{field: 'ck', checkbox: true}]]//选择框

            /*toolbar: [{
                text: '添加',
                iconCls: 'icon-add',
                handler: addUser
            }, '-', {
                text: '修改',
                iconCls: 'icon-edit',
                handler: editUser
            }, '-', {
                text: '删除',
                iconCls: 'icon-remove',
                handler: deleteUser
            }]*/
        }).datagrid('getPager');

        //分页控件设置
        pager.pagination({
            pageSize: 10,
            pageList: [5, 10, 15],
            beforePageText: '第',//页数文本框前显示的汉字
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
        });

        //参考https://www.cnblogs.com/huozhicheng/archive/2011/09/27/2193605.html
    });

    /**
     * 打开新的窗口
     * @param url 窗口地址
     * @param width 宽度
     * @param height 高度
     */
    var openWindow = function (url,width,height) {
        layer.open({
            type: 2,
            title: false,
            closeBtn: 1, //不显示关闭按钮
            shade: [0],
            area: [width, height],
            offset: 'auto', //右下角弹出
            content: [url, 'no'],
            cancel: function(){
                $('#list_data').datagrid('reload');
            }
        });
    };

    /**
     * 查询
     */
    var doSearch = function () {
        var account = {userName:$('#account').val()};
        $('#list_data').datagrid({ queryParams:account});
    };

    /**
     * 添加用户
     */
    var addUser = function () {
        var url = '${basePath}/user/addUser.do';
        openWindow(url,'384px','330px');
    };

    /**
     * 修改用户
     */
    var editUser = function () {
        var rows = $('#list_data').datagrid('getChecked');
        if(!rows)return;
        if(rows.length<1||rows.length>1){
            return layer.msg("请选择一条数据！")||!1;
        }
        var url = '${basePath}/user/findUser.do?userId='+rows[0].id;
        openWindow(url,'384px','380px');
    };

    /**
     * 删除用户
     * @returns {*|boolean}
     */
    var deleteUser = function () {
        //校验
        var rows = $('#list_data').datagrid('getChecked');
        if(!rows)return;
        if(rows.length<1){
            return layer.msg("请至少选择一条数据！")||!1;
        }

        //免责提醒
        var title = '您确定要删除这'+rows.length+'条数据？';
        layer.confirm(title, {icon: 2, title:''}, function(index){
            //请求参数
            var ids = new Array();
            for (var i =0;i<rows.length;i++){
                ids[i] = rows[i].id;
            }

            //执行删除
            var load = layer.load();
            $.post("${basePath}/user/deleteUsers.do",'ids='+ids,function(res){
                layer.close(load);
                if(res && res.state!= 200){
                    return layer.msg(res.msg)||!1;
                }else{
                    layer.msg(res.msg);
                    $('#list_data').datagrid('reload');
                }
            },"json");
            layer.close(index);
        });
    };
</script>
</body>
</html>