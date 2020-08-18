<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>list</title>
</head>
<body>
<table width="500" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>번호</td>
			<td>제목</td>
			<td>닉네임</td>
		</tr>
		<c:forEach items="${list}" var="dto"> <!-- list안에 있는 모든 정보를 다 빼오는 코드 -->
		<tr>
			<td>${dto.b_no}</td>
			<td>
				<a href="content_view?b_no=${dto.b_no}">${dto.b_title}</a>
			</td>
			<td>${dto.b_nick}</td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="5"> <a href="write_view">글작성</a></td>
		</tr>
	</table>
</body>
</html>
