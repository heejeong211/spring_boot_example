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
		<form action="write" method="post">
			<tr>
				<td> 닉네임 </td>
				<td> <input type="text" name="b_nick" size = "50"> </td>
			</tr>
			<tr>
				<td> 제목 </td>
				<td> <input type="text" name="b_title" size = "50"> </td>
			</tr>
			<tr>
				<td> 내용 </td>
				<td> <textarea rows="10" name="b_content" ></textarea> </td>
			</tr>
			<tr >
				<td colspan="2"> <input type="submit" value="입력"> &nbsp;&nbsp; <a href="list">목록보기</a></td>
			</tr>
		</form>
	</table>
</body>
</html>
