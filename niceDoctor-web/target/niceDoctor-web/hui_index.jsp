<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html>
<head> 
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <!--[if lt IE 9]>
    <script type="text/javascript" src="libs/H-ui.2.1/Lib/html5.js"></script>
    <script type="text/javascript" src="libs/H-ui.2.1/Lib/respond.min.js"></script>
    <script type="text/javascript" src="libs/H-ui.2.1/Lib/PIE_IE678.js"></script>
    <![endif]-->
    <link href="libs/H-ui.2.1/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css">
    <link href="libs/H-ui.2.1/static/h-ui/css/H-ui.admin.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="css/style.css"/>
    <link href="libs/H-ui.2.1/Lib/icheck/icheck.css" rel="stylesheet" type="text/css" >
    <link rel="stylesheet" href="libs/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <title>MCCMS</title>
    <meta name="keywords" content="MCCMS">
    <meta name="description" content="MCCMS">
</head>
<body onload="prettyPrint()" class="">
<header class="Hui-header cl mccmsa-header"> 
    <div class="header-logo"></div>
    <span class="">您好，admin，欢迎使用MCCMSA！</span>
    <div class="user-operation f-r text-r">
        <div class="button change-pwd">修改密码</div>
        <span class="pipe">|</span>
        <div class="button logout">退出</div>
    </div>
    <a href="#" class="Hui-nav-toggle Hui-iconfont" aria-hidden="false"></a>
</header>
<aside class="Hui-aside">
    <input runat="server" id="divScrollValue" type="hidden" value="">
    <div class="menu_dropdown bk_2" id="navContainer">

    </div>
</aside>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onclick="displaynavbar(this)"></a></div>
<section class="Hui-article-box" id="mainContainer"></section>

<script type="text/javascript" src="libs/H-ui.2.1/Lib/prettify.js"></script>
<script src="libs/H-ui.2.1/Lib/jquery.min.js"></script>
<script type="text/javascript" src="libs/H-ui.2.1/static/h-ui/js/H-ui.js"></script>
<script src="libs/H-ui.2.1/Lib/handlebars.js"></script>
<script src="libs/H-ui.2.1/Lib/layer1.8/layer.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="libs/H-ui.2.1/Lib/icheck/jquery.icheck.min.js" ></script>
<script src="libs/ajaxfileupload/ajaxfileupload.js"></script>
<!--zTree文件-->
<script type="text/javascript" src="libs/zTree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="libs/zTree/js/jquery.ztree.excheck-3.5.js"></script>
<!--end of zTree文件-->
<!--编辑器源码文件-->
<script type="text/javascript" src="libs/ueditor/ueditor.config.js"></script>
<script type="text/javascript" src="libs/ueditor/ueditor.all.js"></script>
<script type="text/javascript" src="libs/ueditor/ueditor.parse.min.js"></script>
<script type="text/javascript" charset="utf-8" src="libs/ueditor/lang/zh-cn/zh-cn.js"></script>
<!--end of 编辑器源码文件-->
<script type="text/javascript" src="libs/bootstrap-modal/bootstrap-modal.js"></script>
<script type="text/javascript" src="libs/bootstrap-modal/bootstrap-modalmanager.js"></script>
</body>
</html>