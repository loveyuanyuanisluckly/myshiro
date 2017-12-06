<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2017/12/6
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <sitemesh:write property='head'/>
    <style type='text/css'>
        .mainBody {
            padding: 10px;
            border: 1px solid #555555;
        }
        .conbgbtm {
            width:100%;
            min-height:400px;
            height:auto;
            overflow:hidden;
            zoom:1;
        }
    </style>
</head>
<body>
<!--头部  -->
<div align="center">
    <h1 >头部信息：
        <sitemesh:write property='title' />
    </h1>
</div>
<hr>

<!--左侧菜单栏  -->
<div class="conbgbtm">
    <div class="leftbox">
        <ul>
            <li><a href="#">菜单1</a></li>
            <li><a href="#">菜单2</a></li>
            <li><a href="#">菜单3</a></li>
        </ul>
    </div>
    <sitemesh:write property='body'></sitemesh:write>
</div>
<hr>

<div align="center">
    <span>Copyright © 2012-2013 廊坊信息技术提高班 版权所有</span>
</div>

</body>
</html>
