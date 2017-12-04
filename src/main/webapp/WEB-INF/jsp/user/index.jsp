<%--
  Created by IntelliJ IDEA.
  User: zj
  Date: 2017/11/8
  Time: 23:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Edmin</title>
        <link rel="stylesheet" type="text/css" href="${basePath}/js/common/bootstrap/css/bootstrap.min.css">
        <link type="text/css" href="${basePath}/js/common/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
        <link type="text/css" href="${basePath}/css/common/theme.css" rel="stylesheet">
        <link type="text/css" href="${basePath}/image/icons/css/font-awesome.css" rel="stylesheet">
    </head>
<body>
<!--S top-->
<div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <a class="btn btn-navbar" data-toggle="collapse" data-target=".navbar-inverse-collapse">
                <i class="icon-reorder shaded"></i></a><a class="brand" href="index.html">Edmin </a>
            <div class="nav-collapse collapse navbar-inverse-collapse">
                <ul class="nav nav-icons">
                    <!--邮箱-->
                    <li class="active"><a href="#"><i class="icon-envelope"></i></a></li>
                </ul>
                <!--搜索-->
                <form class="navbar-search pull-left input-append" action="#">
                    <input type="text" class="span3">
                    <button class="btn" type="button">
                        <i class="icon-search"></i>
                    </button>
                </form>
                <!--个人信息-->
                <ul class="nav pull-right">
                    <li class="nav-user dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <img src="${basePath}/image/user.png" class="nav-avatar"/>
                        <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">个人信息</a></li>
                            <li><a href="#">账户设置</a></li>
                            <li class="divider"></li>
                            <li><a href="#">退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--E top-->

<!--S content-->
<div class="wrapper">
    <div class="container">
        <div class="row">
            <div class="span3">
                <div class="sidebar">

                    <ul class="widget widget-menu unstyled">
                        <li><a href="ui-button-icon.html"><i class="menu-icon icon-bold"></i> Buttons </a></li>
                        <li><a href="ui-typography.html"><i class="menu-icon icon-book"></i>Typography </a></li>
                        <li><a href="form.html"><i class="menu-icon icon-paste"></i>Forms </a></li>
                        <li><a href="table.html"><i class="menu-icon icon-table"></i>Tables </a></li>
                        <li><a href="charts.html"><i class="menu-icon icon-bar-chart"></i>Charts </a></li>
                    </ul>

                    <!--/.widget-nav-->
                    <ul class="widget widget-menu unstyled">
                        <li><a class="collapsed" data-toggle="collapse" href="#togglePages"><i
                                class="menu-icon icon-cog">
                        </i><i class="icon-chevron-down pull-right"></i><i class="icon-chevron-up pull-right">
                        </i>更多</a>
                            <ul id="togglePages" class="collapse unstyled">
                                <li><a href="other-login.html"><i class="icon-inbox"></i>登陆</a></li>
                                <li><a href="other-user-profile.html"><i class="icon-inbox"></i>个人信息</a></li>
                                <li><a href="other-user-listing.html"><i class="icon-inbox"></i>所有用户</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="menu-icon icon-signout"></i>Logout </a></li>
                    </ul>

                </div>
                <!--/.sidebar-->
            </div>
            <!--/.span3-->
            <div class="span9">
                <div class="content">
                    <div class="module">
                        <div class="module-head">
                            <h3>
                                DataTables</h3>
                        </div>
                        <div class="module-body table">
                            <table cellpadding="0" cellspacing="0" border="0"
                                   class="datatable-1 table table-bordered table-striped	 display"
                                   width="100%">
                                <thead>
                                    <tr>
                                        <th>
                                            Rendering engine
                                        </th>
                                        <th>
                                            Browser
                                        </th>
                                        <th>
                                            Platform(s)
                                        </th>
                                        <th>
                                            Engine version
                                        </th>
                                        <th>
                                            CSS grade
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="odd gradeX">
                                        <td>
                                            Trident
                                        </td>
                                        <td>
                                            Internet Explorer 4.0
                                        </td>
                                        <td>
                                            Win 95+
                                        </td>
                                        <td class="center">
                                            4
                                        </td>
                                        <td class="center">
                                            X
                                        </td>
                                    </tr>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th>
                                            Rendering engine
                                        </th>
                                        <th>
                                            Browser
                                        </th>
                                        <th>
                                            Platform(s)
                                        </th>
                                        <th>
                                            Engine version
                                        </th>
                                        <th>
                                            CSS grade
                                        </th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--E content-->

<!--S foot-->
<div class="footer">
    <div class="container">
        <b class="copyright">&copy; 2014 Edmin - EGrappler </b>All rights reserved. More Templates <a href="###"
                                                                                                      target="_blank"
                                                                                                      title="web">web</a>
        - Collect from <a href="###" title="web" target="_blank">web</a>
    </div>
</div>
<!--E foot-->

<script src="${basePath}/js/common/jquery/jquery1.8.3.min.js" type="text/javascript"></script>
<script src="${basePath}/js/common/jquery/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>
<script src="${basePath}/js/common/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}/js/common/flot/jquery.flot.js" type="text/javascript"></script>
<script src="${basePath}/js/common/flot/jquery.flot.resize.js" type="text/javascript"></script>
<script src="${basePath}/js/common/datatables/jquery.dataTables.js" type="text/javascript"></script>
<script src="${basePath}/js/common/common.js" type="text/javascript"></script>

</body>
</html>

