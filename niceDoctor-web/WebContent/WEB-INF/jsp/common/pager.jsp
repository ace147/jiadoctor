<%@ page contentType="text/html;charset=UTF-8"%>
 <div class="pagination-wrapper">
			&nbsp;&nbsp;共有 ${pager.rowCount} 条记录，每页行数：
			<!-- <s:select  name="pageSize" id="pageSize" list="pager.pageSizeIndexs" theme="simple" value="pager.pageSize"
					onchange="$('#pageNo').val('1');$('#form1').submit();" /> -->
					<select theme="simple" id="pageSize" name="pageSize" onchange="$('#pageNo').val('1');$('#form1').submit();">
						<c:forEach items="${pager.pageSizeList}" var="bean">
						    <option value="${bean}" <c:if test="${pager.pageSize == bean}">selected</c:if>>${bean}</option>
						</c:forEach>
					</select>
			当前第
          <!-- 	<s:select name="pageNo" id="pageNo" list="pager.pageNoIndexs" theme="simple"
					value="pager.pageNo" onchange="$('#form1').submit();" /> -->
					<select theme="simple" id="pageNo" name="pageNo" onchange="$('#form1').submit();" >
						<c:forEach items="${pager.pageNoIndexs}" var="bean">
						    <option value="${bean}" <c:if test="${pager.pageNo == bean}">selected</c:if>>${bean}</option>
						</c:forEach>
					</select>
					页
			<c:if test="${pager.pageNo == '1'}">
				<a class="btn-pagination">首页</a>
				<a class="btn-pagination">上一页</a>
			</c:if>
			<c:if test="${pager.pageNo != '1'}">
				<a class="btn-pagination" href="javascript:$('#pageNo').val('${pager.firstPageNo}');$('#form1').submit();">首页</a>
				<a class="btn-pagination" href="javascript:$('#pageNo').val('${pager.prePageNo}');$('#form1').submit();">上一页</a>
            </c:if>
            
            <c:if test="${pager.pageNo == pager.pageCount}">
            	<a class="btn-pagination">下一页</a>
				<a class="btn-pagination">尾页</a>
			</c:if>
			<c:if test="${pager.pageNo != pager.pageCount}">
				<a class="btn-pagination" href="javascript:$('#pageNo').val('${pager.nextPageNo}');$('#form1').submit();">下一页</a>
				<a class="btn-pagination" href="javascript:$('#pageNo').val('${pager.lastPageNo}');$('#form1').submit();">尾页</a>
            </c:if>
</div>