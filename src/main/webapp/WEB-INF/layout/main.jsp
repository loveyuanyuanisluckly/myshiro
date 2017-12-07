<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2017/12/6
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/css/common/main.css">
    <script  src="${basePath}/js/common/jquery/jquery.min.js"></script>
    <script  src="${basePath}/js/common/jquery/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout" id="layout">

    <!--顶部-->
    <div data-options="region:'north',border:false" class="main-north">
        north region
    </div>
    <!-- 菜单-->
    <div data-options="region:'west',split:true,title:'West'" class="main-west">
        <div class="easyui-panel" style="padding:5px">
            <ul id="menu" class="easyui-tree" ></ul>
        </div>
    </div>
    <!-- 个人简介-->
    <div data-options="region:'east',split:true,collapsed:true,title:'East'" class="main-east">
        east region
    </div>
    <!--底部（产品所有权）-->
    <div data-options="region:'south',border:false" class="main-south">
        south region
    </div>
    <!-- 实际内容 -->
    <div id="content" data-options="region:'center',title:'Center'">
        <div id='title'><sitemesh:write property='title' /></div>
        <sitemesh:write property='body' />
    </div>

    <script>
        //获取标题的内容
        $(function(){
            dynamicTitle();
            loadTree('menu','${basePath}/user/loadMenu.do');
        });

        /**
         * 动态修改标题
         */
        var dynamicTitle = function(){
            var title = $('#title').text();
            if(title){
                $('#content').panel({title: title});
            }
        }


        /**
         * 延迟加载菜单树
         * @param treeId 展示节点id
         * @param url 请求数据地址
         */
        var loadTree = function (treeId,url){

            $('#'+treeId).tree({
                url:url,
                method:'get',
                loadFilter:function (data,parent) {
                    var state = $.data(this, 'tree');

                    function setData(){
                        var serno = 1;
                        var todo = [];
                        for(var i=0; i<data.length; i++){
                            todo.push(data[i]);
                        }
                        while(todo.length){
                            var node = todo.shift();
                            if (node.id == undefined){
                                node.id = '_node_' + (serno++);
                            }
                            if (node.children){
                                node.state = 'closed';
                                node.children1 = node.children;
                                node.children = undefined;
                                todo = todo.concat(node.children1);
                            }
                        }
                        state.tdata = data;
                    }
                    function find(id){
                        var data = state.tdata;
                        var cc = [data];
                        while(cc.length){
                            var c = cc.shift();
                            for(var i=0; i<c.length; i++){
                                var node = c[i];
                                if (node.id == id){
                                    return node;
                                } else if (node.children1){
                                    cc.push(node.children1);
                                }
                            }
                        }
                        return null;
                    }

                    setData();

                    var t = $(this);
                    var opts = t.tree('options');
                    opts.onBeforeExpand = function(node){
                        var n = find(node.id);
                        if (n.children && n.children.length){return}
                        if (n.children1){
                            var filter = opts.loadFilter;
                            opts.loadFilter = function(data){return data;};
                            t.tree('append',{
                                parent:node.target,
                                data:n.children1
                            });
                            opts.loadFilter = filter;
                            n.children = n.children1;
                        }
                    };
                    return data;
                }
            });
        }
    </script>
</body>
</html>