<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Insert title here</title>
	<link rel="stylesheet" type="text/css" href="board/table.css">
	<script src="//code.jquery.com/jquery.min.js"></script>
	<script>
		$(document).ready(function() {
			$('#comBtn').click(function() {
				var content = $('#content').val();
				if(content==""){
					alert('댓글을 입력하세요');
					return;
				}
				$('#com_form').submit();
			})
		})
	</script>
</head>
<body>
	<center>
		<h3>내용보기</h3>
		<table id="table_content" width="600">
			<tr height="27">
				<th width="20%">번호</th>
				<td width="30%" align="center">${dto.no}</td>
				
				<th width="20%">작성일</th>
				<td width="30%" align="center">${dto.date}</td>
			</tr>
			<tr height="27">
				<th width="20%">이름</th>
				<td width="30%" align="center">${dto.name}</td>
				
				<th width="20%">조회수</th>
				<td width="30%" align="center">${dto.hit}</td>
			</tr>
			<tr height="27">
				<th width="20%">제목</th>
				<td width="30%" align="left" colspan="3">${dto.subject}</td>
			</tr>
			<tr>
				<td colspan="4" align="left" valign="top" height="200">
<pre>${dto.content}</pre>
				</td>
			</tr>
		</table>
		<c:if test="${type ne 1 }">
			<form action="comment.do" method="post" id="com_form">			
			<table id="table_content" width="600" style="border-top:none;border-bottom: none;">				
				<tr>
					<td width="20%">
						댓글
					</td>
					<td width="60%" align="left">
						<input type="hidden" name="type" value="${type }">
						<input type="hidden" name="page" value="${page }">
						<input type="hidden" name="no" value="${no }">
						<input type="hidden" name="fs" value="${fs }">
						<input type="hidden" name="ss" value="${ss }">
						<textarea rows="5" cols="40" name="content" id="content"></textarea>
					</td>
					<td width="20%">
						<input type="button" value="작성" id="comBtn">
					</td>
				</tr>
			</table>
			</form>
		</c:if>
		<c:if test="${repList.size() > 0 }">
			<table width="600">
					<tr>
						<td width="20%">댓글번호</td>
						<td width="80%">댓글내용</td>
					</tr>
					<c:forEach items="${repList }" var="dto">
					<tr>
						<td width="20%">${dto.repno }</td>
						<td width="80%">${dto.content }</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<table border="0" width="600" style="border-top:none;border-bottom: none;">
			<tr>
				<td align="right">
					<a href="reply.do?type=${type }&no=${dto.no}&page=${page}&fs=${fs}&ss=${ss}">
						<img src="board/image/btn_reply.gif" border="0">
						<%--
										request
							reply.do =============> DispatcherServlet(Controller)
											===========> ReplyModel(처리) ======>
											DispatcherServlet(Controller)
											==> jsp(View)
						 --%>
					</a>&nbsp;
					<a href="update.do?type=${type }&no=${dto.no}&page=${page}&fs=${fs}&ss=${ss}">
						<img src="board/image/btn_modify.gif" border="0">
					</a>&nbsp;
					<a href="delete.do?type=${type }&no=${dto.no}&page=${page}&fs=${fs}&ss=${ss}">
						<img src="board/image/btn_delete.gif" border="0">
					</a>&nbsp;
					<a href="list.do?type=${type }&page=${page}&fs=${fs}&ss=${ss}">
						<img src="board/image/btn_list.gif" border="0">
					</a>
				</td>
			</tr>
		</table>	
	</center>
</body>
</html>
