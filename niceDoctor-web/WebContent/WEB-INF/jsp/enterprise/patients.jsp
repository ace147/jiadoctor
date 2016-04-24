<%@ include file="/WEB-INF/jsp/common/left.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
				<li class="active">Tables</li>
			</ol>
		</div><!--/.row-->
		<!--
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Tables</h1>
			</div>
		</div>/.row-->
				
		
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">病人资料</div>
					
					<div class="panel-body">
					<div class="bootstrap-table">
					<div class="fixed-table-toolbar">
					<form action="${ctx}/jybUser/getJybUserList">
					  <div class="columns pull-right">
					      <button type="submit" class="btn btn-primary">查询</button>
					  </div>
					  <div class="pull-right search">
					    <input class="form-control" type="text" placeholder="Search" name="mobile">
					  </div>
					  </form>
					</div>
					   
					   <div class="fixed-table-container">
					        <div class="fixed-table-header"></div>
					   		<div class="fixed-table-body">
								<table class="table table-hover">
									<thead>
										<tr>
											<th  style>
											    <div class="th-inner sortable"> 用户账户</div>
												<div class="fht-cell"></div>
											</th>
											<th  style>
											    <div class="th-inner sortable"> 昵称</div>
												<div class="fht-cell"></div>
											</th>
											<th  style>
											    <div class="th-inner sortable"> 真实名称 </div>
												<div class="fht-cell"></div>
											</th>
											<th  style>
											    <div class="th-inner sortable"> 性别</div>
												<div class="fht-cell"></div>
											</th>
											<th  style>
											    <div class="th-inner sortable"> 手机</div>
												<div class="fht-cell"></div>
											</th>
											<th  style>
											    <div class="th-inner sortable"> 操作</div>
												<div class="fht-cell"></div>
											</th>
											
											
										</tr>
									</thead>
									<tbody>
										<c:if test="${empty pager.resultList}">
										 <tr class="no-records-found">
										    <td colspan="4">暂无病人资料</td>
										 </tr>
										</c:if>
										<c:forEach items="${pager.resultList}" var="bean">
										<tr>
											 <td style>${bean.account}</td>
											 <td style>${bean.nickname}</td>
											 <td style>${bean.realName}</td>
											
											 <td style>${bean.sex}</td>
											  <td style>${bean.mobile}</td>
											 <td style> 
											     <a href="${ctx}/jybUser/eidtPatientPage?id=${bean.id}" title="编辑"><span class="glyphicon glyphicon-pencil"></span></a>
											     &nbsp&nbsp&nbsp&nbsp
											     <a href="#" title="查看"><span class="glyphicon glyphicon-zoom-in"></span></a>
											     &nbsp&nbsp&nbsp&nbsp
											     <a href="${ctx}/jybUser/delJybUser/${bean.id}" title="删除"><span class="glyphicon glyphicon-remove"></span></a>
											     
											  </td>
										    </tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<form id="form1" action="${ctx}/jybUser/getJybUserList">
						    <%@ include file="/WEB-INF/jsp/common/pager.jsp"%> 
						</form>
						<!-- <div class="fixed-table-pagination">
						   <div class="pull-left pagination-detail">
						     <span class="pagination-info"></span>
						   </div>
						   <div class="pull-right pagination">
							    <ul class="pagination">
							       <li class="page-first disabled"><a href="#"><<</a></li>
							       <li class="page-pre disabled"><a href="#"><</a></li>
							       <li class="page-number active disabled"><a href="#"><<</a></li>
							       <li class="page-next disabled"><a href="#">></a></li>
							       <li class="page-last disabled"><a href="#">>></a></li>
							    </ul>
							</div>
						</div> -->
					</div>
				</div>
			</div>
		</div><!--/.row-->	
		
		
	</div><!--/.main-->

	<script src="${ctx}/js/jquery-1.11.1.min.js"></script>
	<script src="${ctx}/js/bootstrap.min.js"></script>
	<script src="${ctx}/js/chart.min.js"></script>
	<script src="${ctx}/js/chart-data.js"></script>
	<script src="${ctx}/js/easypiechart.js"></script>
	<script src="${ctx}/js/easypiechart-data.js"></script>
	<script src="${ctx}/js/bootstrap-datepicker.js"></script>
	<script src="${ctx}/js/bootstrap-table.js"></script>
	<script>
		!function ($) {
			$(document).on("click","ul.nav li.parent > a > span.icon", function(){		  
				$(this).find('em:first').toggleClass("glyphicon-minus");	  
			}); 
			$(".sidebar span.icon").find('em:first').addClass("glyphicon-plus");
		}(window.jQuery);

		$(window).on('resize', function () {
		  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
		})
		$(window).on('resize', function () {
		  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
		})
		
		extendedMenu("jybUserList");
	</script>	
</body>

</html>