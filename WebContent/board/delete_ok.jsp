<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${bCheck==false }">
	<script>
		alert("비밀번호가 틀립니다.");
		history.back();
	</script>
</c:if>
<c:if test="${bCheck==true }">
	<script>
		location.href="list.do?type=${type }&page=${page }&fs=${fs }&ss=${ss }";
	</script>
</c:if>

