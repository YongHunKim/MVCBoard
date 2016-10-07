<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR" import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String[] options = {"name","subject","content"};
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
	<script src="//code.jquery.com/jquery.min.js"></script>	
	<link rel="stylesheet" type="text/css" href="board/table.css">
	<link rel="stylesheet" type="text/css" href="css/bootstrap-3.3.2-dist/css/bootstrap.min.css">
	<script>
		$(document).ready(function() {
			$('#class').click(function() {
				location.href="list.do?type=1";
			});
			$('#reply').click(function() {
				location.href="list.do?type=2";
			});			
		})
	</script>
</head>
<body>
	<center>
		<div style="margin-top: 30px;">
		<input class="btn btn-primary" type="button" value="수업용 게시판" id="class">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-primary" type="button" value="댓글형 게시판" id="reply">
		</div>		
		<hr>
		<table border="0" width="800">
			<tr>
				<td align="left">
					<a href="insert.do?type=${type }"><img src="board/image/btn_write.gif" border="0"></a>
				</td>
			</tr>
		</table>
		<table id="table_content" width="800">
			<tr>
				<th width="10%">번호</th>
				<th width="45%">제목</th>
				<th width="15%">이름</th>
				<th width="20%">작성일</th>
				<th width="10%">조회수</th>
			</tr>
			<c:forEach var="dto" items="${list}">
				
				<tr class="dataTr">
					<c:if test="${msg == dto.subject }">
						<td colspan="5" width="100%" style="text-align:center;" >
							<font color="blue">${dto.subject }</font>
						</td>					
					</c:if>
					<c:if test="${msg != dto.subject }">
						<td width="10%" class="tdcenter">${dto.no}</td>
						<td width="45%" class="tdleft">
							<!-- group_tab : 몇 번째 단계의 답변인지 나타내는 컬럼
								     새로운 글				0
								     |
								     -- 답변1				1
								     	 |
								     	 -- 답변1의 답변	2
							 -->
							 <c:if test="${dto.group_tab>0}">
							 	<c:forEach var="i" begin="1" end="${dto.group_tab}">
							 		&nbsp;&nbsp;
							 	</c:forEach>
							 	<img src="board/image/icon_reply.gif">
							 </c:if>
							 	<a href="content.do?type=${type }&no=${dto.no}&page=${curPage}&fs=${fs}&ss=${ss}">${dto.subject}</a>
							 <c:if test="${dto.replycount ne 0 }">
							 	<font color="blue">[${dto.replycount }]</font>
							 </c:if>	
							 <c:if test="${today == dto.dbday}">
							 	<img src="board/image/icon_new.gif">
							 </c:if>
						</td>
						<td width="15%" class="tdcenter">${dto.name}</td>
						<td width="20%" class="tdcenter">${dto.date}</td>
						<td width="10%" class="tdcenter">${dto.hit}</td>
					</c:if>
				</tr>
			</c:forEach>
		</table>
		<table border="0" width="800">
			<tr>
				<td width="50%">
					<form action="list.do" mehtod="post" name="frm">
					<c:set var="na" value="<%=options %>">
					</c:set>
					<select name="fs" id="fs">						
						<c:forEach var="name" items="${na }">
							<c:if test="${name==fs }">
							<option value="${name}" selected>						
							<c:choose>
								<c:when test="${name eq 'name' }">이름</c:when>
								<c:when test="${name eq 'subject' }">제목</c:when>
								<c:when test="${name eq 'content' }">내용</c:when>
							</c:choose>														
							</option>
							</c:if>
							<c:if test="${name!=fs }">
							<option value="${name}">
							<c:choose>
								<c:when test="${name eq 'name' }">이름</c:when>
								<c:when test="${name eq 'subject' }">제목</c:when>
								<c:when test="${name eq 'content' }">내용</c:when>
							</c:choose>																							
							</option>
							</c:if>
						</c:forEach>
					</select>
					<input type="text" name="ss" id="ss" value="${(ss==null)?'':ss }">
					<input type="hidden" name="type" value=${type }>
					<input type="submit" value="검색">
					</form>
				</td>
				<td align="right" width="50%">
					<!-- [1][2][3][4][5]
						  fp		  tp
					 -->
					 <a href="list.do?type=${type }&page=1&fs=${fs}&ss=${ss}">
					 		<img src="board/image/begin.gif" border="0">
					 </a>&nbsp;
					 <c:if test="${curPage > block}">					 
					 	<a href="list.do?type=${type }&page=${fromPage-1}&fs=${fs}&ss=${ss}">
					 		<img src="board/image/prev.gif" border="0">
					 	</a>
					 </c:if>
					 <c:if test="${curPage <= block}">
					 	<a href="list.do?type=${type }&page=${curPage>1?curPage-1:curPage}&fs=${fs}&ss=${ss}">
					 		<img src="board/image/prev.gif" border="0">
					 	</a>
					 </c:if>
					 
					 <c:forEach var="i" begin="${fromPage}" end="${toPage}">
					 	&nbsp;[
					 	<c:if test="${curPage == i}">
					 		<span style="color:red">${i}</span>
					 	</c:if>
					 	<c:if test="${curPage != i}">
					 		<a href="list.do?type=${type }&page=${i}&fs=${fs}&ss=${ss}">${i}</a>
					 	</c:if>
					 	]&nbsp;
					 </c:forEach>
					 
					 <c:if test="${toPage >= totalPage}">
					 	<a href="list.do?type=${type }&page=${curPage<totalPage?curPage+1:curPage}&fs=${fs}&ss=${ss}">
					 		<img src="board/image/next.gif" border="0">
					 	</a>&nbsp;
					 </c:if>
					 <c:if test="${toPage < totalPage}">
					 	<a href="list.do?type=${type }&page=${toPage+1}&fs=${fs}&ss=${ss}">
					 		<img src="board/image/next.gif" border="0">
					 	</a>&nbsp;					 	
					 </c:if>
					 <a href="list.do?type=${type }&page=${totalPage}&fs=${fs}&ss=${ss}">
					 		<img src="board/image/end.gif" border="0">
					 	</a>
					 &nbsp;&nbsp;
					 ${curPage} page / ${totalPage} pages
				</td>
			</tr>
		</table>
	</center>
</body>
</html>
