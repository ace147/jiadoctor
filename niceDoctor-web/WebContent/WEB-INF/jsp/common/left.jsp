<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="zh">
<head>
 <meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
</head>
<body>

<link href="${ctx}/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/css/datepicker3.css" rel="stylesheet">
<link href="${ctx}/css/bootstrap-table.css" rel="stylesheet">
<link href="${ctx}/css/styles.css" rel="stylesheet">
 <link rel="stylesheet" href="${ctx}/js/datetimepicker/jquery.datetimepicker.css">

<!--[if lt IE 9]>
<script src="js/html5shiv.js"></script>
<script src="js/respond.min.js"></script>
<![endif]-->

</head>
    <script src="${ctx}/js/jquery-1.11.1.min.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/chart.min.js"></script>
	<script src="${ctx}/js/chart-data.js"></script>
	<script src="${ctx}/js/easypiechart.js"></script>
	<script src="${ctx}/js/easypiechart-data.js"></script>
	<script src="${ctx}/js/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/bootstrap-table.js"></script>
	<script src="${ctx}/js/datetimepicker/jquery.datetimepicker.js"></script>
	<script>
    
    $(function(){
    	   $(".examDate").datetimepicker({
               lang:'zh',
               timepicker:false,
               format:'Y-m-d',
               closeOnDateSelect: true
           });
    	   	
    });
    
    function  extendedMenu(id){
    var  $current=$("#"+id);
    var  $parent=$current.parent();
    var  $prev=$parent.prev();
    $current.addClass("active");
}
</script>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"><span>佳医生</span>平台中心系统</a>
				<ul class="user-menu">
					<li class="dropdown pull-right">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span>  浩哥 <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#"><span class="glyphicon glyphicon-user"></span> 个人资料</a></li>
							<li><a href="#"><span class="glyphicon glyphicon-cog"></span> 设置</a></li>
							<li><a href="#"><span class="glyphicon glyphicon-log-out"></span> 退出</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div><!-- /.container-fluid -->
	</nav>
		
	<div id="sidebar-collapse" class="col-sm-3 col-lg-2 sidebar">
		<form role="search">
			<div class="form-group">
				<input type="text" class="form-control" placeholder="Search">
			</div>
		</form>
		<ul class="nav menu">
			<li class="active"><a href="index.html"><span class="glyphicon glyphicon-stats"></span> 统计信息</a></li>
			<li class="parent ">
				<a href="#">
					<span class="glyphicon glyphicon-tree-deciduous"></span> 病人中心 <span data-toggle="collapse" href="#sub-item-1" class="icon pull-right"><em class="glyphicon glyphicon-s glyphicon-plus"></em></span> 
				</a>
				<ul class="children collapse" id="sub-item-1">
					<li id="jybUserList">
						<a class="" href="${ctx}/jybUser/getJybUserList">
							<span class="glyphicon glyphicon-share-alt"></span> 病人资料
						</a>
					</li>
					<li>
						<a class="" href="${ctx}/jybUser/addPatientPage">
							<span class="glyphicon glyphicon-share-alt"></span> 添加病人
						</a>
					</li>
					<li>
						<a class="" href="${ctx}/jybUser/patient">
							<span class="glyphicon glyphicon-share-alt"></span> 查询病人
						</a>
					</li>
				</ul>
			</li>
			<li class="parent">
				<a href="#">
					<span class="glyphicon glyphicon-road"></span> 医生中心 <span data-toggle="collapse" href="#sub-item-2" class="icon pull-right"><em class="glyphicon glyphicon-s glyphicon-plus"></em></span> 
				</a>
				<ul class="children collapse" id="sub-item-2">
					<li>
						<a class="" href="${ctx}/jydUser/getJydUserList">
							<span class="glyphicon glyphicon-share-alt"></span> 医生资料
						</a>
					</li>
					<li>
						<a class="" href="${ctx}/jydUser/addDoctorPage">
							<span class="glyphicon glyphicon-share-alt"></span> 添加医生
						</a>
					</li>
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 身份审核
						</a>
					</li>
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 查询医生
						</a>
					</li>
				</ul>
			</li>
			<li><a href="tables.html"><span class="glyphicon glyphicon-list-alt"></span> 订单管理</a></li>
			<li class="parent">
				<a href="#">
					<span class="glyphicon glyphicon-plane"></span> 医疗转运 <span data-toggle="collapse" href="#sub-item-3" class="icon pull-right"><em class="glyphicon glyphicon-s glyphicon-plus"></em></span> 
				</a>
				<ul class="children collapse" id="sub-item-3">
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 转运订单查询
						</a>
					</li>
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 转运订单确认
						</a>
					</li>
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 转运参数设置
						</a>
					</li>
				</ul>
			</li>
			<li class="parent">
				<a href="#">
					<span class="glyphicon glyphicon-pencil"></span> 系统设置 <span data-toggle="collapse" href="#sub-item-4" class="icon pull-right"><em class="glyphicon glyphicon-s glyphicon-plus"></em></span> 
				</a>
				<ul class="children collapse" id="sub-item-4">
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 用户管理
						</a>
					</li>
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 角色管理
						</a>
					</li>
					<li>
						<a class="" href="#">
							<span class="glyphicon glyphicon-share-alt"></span> 权限管理
						</a>
					</li>
				</ul>
			</li>
			<li><a href="panels.html"><span class="glyphicon glyphicon-info-sign"></span> Alerts &amp; Panels</a></li>
			<li role="presentation" class="divider"></li>
			<li><a href="login.html"><span class="glyphicon glyphicon-user"></span> Login Page</a></li>
		</ul>
	<!-- 	<div class="attribution">Copyright © 2016 JiaDoctor Inc. 保留所有权利。</div> -->
	</div><!--/.sidebar-->
