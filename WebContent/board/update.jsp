<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="board/table.css">
<script type="text/javascript" src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		$('#sendBtn').click(function(){
			var name=$('#name').val();
			if(name.trim()==""){
				$('#name').focus();
				$('#name').val("");
				return;
			}
			
			var subject=$('#subject').val();
			if(subject.trim()==""){
				$('#subject').focus();
				$('#subject').val("");
				return;
			}
			
			var content=$('#content').val();
			if(content.trim()==""){
				$('#content').focus();
				$('#content').val("");
				return;
			}
			
			var pwd=$('#pwd').val();
			if(pwd.trim()==""){
				$('#pwd').focus();
				$('#pwd').val("");
				return;
			}
			
			$('#frm').submit();
			
		})
	});
</script>
</head>
<body>
	<center>
		<h3>���뺸��</h3>
		<form id="frm" action="update_ok.do" method="post">
		<table border="0" width="500">
						<tr>
							<th width="15%" align="left">�̸�</th>
							<td width="85%" align="left">
								<input type="hidden" name="type" value="${type }">
								<input type="hidden" name="page" value="${page }">
								<input type="hidden" name="no" value="${no }">
								<input type="hidden" name="fs" value="${fs }">
								<input type="hidden" name="ss" value="${ss }">
								<input type="text" name="name" size="12" id="name" value="${dto.name }">
							</td>
						</tr>
						<tr>
							<th width="15%" align="left">����</th>
							<td width="85%" align="left">
								<input type="text" name="subject" size="53" id="subject" value="${dto.subject }">
							</td>
						</tr>
						<tr>
							<th width="15%" align="left">����</th>
							<td width="85%" align="left">								
								<textarea rows="10" cols="40" name="content" id="content">${dto.content }
								</textarea>
							</td>
						</tr>
						<tr>
							<th width="15%" align="left">��й�ȣ</th>
							<td width="85%" align="left">
								<input type="password" name="pwd" size="10" id="pwd" value="${dto.pwd }">
							</td>
						</tr>
			<tr>
				<td colspan="2" align="center">
					<input type="button" value="����" id="sendBtn">
					<input type="button" value="���" onclick="javascript:history.back();">
				</td>
			</tr>
		</table>
		</form>
	</center>
</body>
</html>
