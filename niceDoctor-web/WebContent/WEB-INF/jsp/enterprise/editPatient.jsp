<%@ include file="/WEB-INF/jsp/common/left.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>

	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">			
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="#"><span class="glyphicon glyphicon-home"></span></a></li>
				<li class="active">Forms</li>
			</ol>
		</div><!--/.row-->
		
		<!--<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Forms</h1>
			</div>
		</div>/.row-->
				
		
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">修改病人资料</div>
					<div class="panel-body">
						<div class="col-md-6">
							<form role="form" action="${ctx}/jybUser/updateJybUserInfo" method="post">
							    <input class="form-control"  type="hidden" name="id" value="${jybUser.id}">
								<div class="form-group">
									<label>账号</label>
									<input class="form-control" name="account" value="${jybUser.account}">
								</div>
								
								<div class="form-group">
										<label>昵称</label>
										<input class="form-control"  name="nickname" value="${jybUser.nickname}">
								</div>
								<div class="form-group">
									<label>真实姓名</label>
									<input class="form-control"  name="realName" value="${jybUser.nickname}">
								</div>
								<div class="form-group">
										<label>性别${jybUser.sex}</label>
										<select class="form-control" name="sex" >
											<option value="M" <c:if test="${jybUser.sex eq 'M'}">selected</c:if>>男</option>
											<option value="F" <c:if test="${jybUser.sex eq 'F'}">selected</c:if>>女</option>
										</select>
								</div>
								<div class="form-group">
									<label>手机</label>
									<input class="form-control"  name="mobile" value="${jybUser.mobile}">
								</div>
								<div class="form-group">
									<label>生日</label>
									<input class="form-control"  name="birthday" value="${jybUser.birthday}">
								</div>
								
								<div class="form-group">
									<label>证件类型</label>
									<select class="form-control" name="cardType" >
										<option value="1" <c:if test="${jybUser.cardType == 1}">selected</c:if>>身份证</option>
										<option value="2" <c:if test="${jybUser.cardType == 2}">selected</c:if>>护照</option>
										<option value="3" <c:if test="${jybUser.cardType == 3}">selected</c:if>>港澳通行证</option>
										<option value="4" <c:if test="${jybUser.cardType == 4}">selected</c:if>>军官证</option>
									</select>
								</div>
								<div class="form-group">
									<label>证件号码</label>
									<input class="form-control" name="cardNum" value="${jybUser.cardNum}">
								</div>
								<div class="form-group">
									<label>地址</label>
									<textarea class="form-control" name="address" rows="3" value="${jybUser.address}">${jybUser.address}</textarea>
								</div>
								<div class="form-group">
									<label>注册来源</label>
									<select class="form-control" name="regisFrom" >
										<option value="1" <c:if test="${jybUser.regisFrom == 1}">selected</c:if>>平台</option>
										<option value="2" <c:if test="${jybUser.regisFrom == 2}">selected</c:if>>微信</option>
										<option value="3" <c:if test="${jybUser.regisFrom == 3}">selected</c:if>>QQ</option>
										<option value="4" <c:if test="${jybUser.regisFrom == 4}">selected</c:if>>新浪</option>
									</select>
								</div>
								<div class="form-group">
									<label>状态</label>
									<select class="form-control" name="status">
										<option value="1" <c:if test="${jybUser.status == 1}">selected</c:if>>启用</option>
										<option value="0" <c:if test="${jybUser.status == 0}">selected</c:if>>禁用</option>
									</select>
								</div>
								<div class="form-group">
									<label>是否拉黑</label>
									<select class="form-control" name="isBlock">
										<option value="Y" <c:if test="${jybUser.isBlock eq 'Y'}">selected</c:if>>是</option>
										<option value="F" <c:if test="${jybUser.isBlock eq 'F'}">selected</c:if>>否</option>
									</select>
								</div>
								
								<button type="submit" class="btn btn-primary">提交</button>
								<button type="reset" class="btn btn-default">重置</button>
							</div>
							<div class="col-md-6">
						        <div class="form-group">
									<label>头像</label>
									<input type="file" name="userHead">
									 <p class="help-block">Example block-level help text here.</p>
								</div>
								
							</div>
						</form>
					</div>
				</div>
			</div><!-- /.col-->
		</div><!-- /.row -->
		
	</div><!--/.main-->

	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/chart.min.js"></script>
	<script src="js/chart-data.js"></script>
	<script src="js/easypiechart.js"></script>
	<script src="js/easypiechart-data.js"></script>
	<script src="js/bootstrap-datepicker.js"></script>
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
	</script>	
</body>

</html>

