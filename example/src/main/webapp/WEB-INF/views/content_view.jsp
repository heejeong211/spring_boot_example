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
		<form action="modify" method="post">
			<input type="hidden" name="b_no" value="${content_view['B_NO']}">
			<tr>
				<td> 번호 </td>
				<td> ${content_view['B_NO']} </td>
			</tr>
			<tr>
				<td> 닉네임 </td>
				<td> <input type="text" name="b_nick" value="${content_view['B_NICK']}"></td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="b_title" value="${content_view['B_TITLE']}"></td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea rows="10" name="b_content" >${content_view['B_CONTENT']}</textarea></td>
			</tr>
			<tr >
				<td colspan="2"> <input type="submit" value="수정"> &nbsp;&nbsp; <a href="list">목록보기</a> &nbsp;&nbsp; <a href="delete?b_no=${content_view['B_NO']}">삭제</a></td>
			</tr>
		</form>
	</table>
</body>
</html>
