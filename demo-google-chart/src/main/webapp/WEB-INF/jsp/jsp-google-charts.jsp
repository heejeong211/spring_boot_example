<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
google.charts.load('current', {'packages' : [ 'bar' ]});
google.charts.setOnLoadCallback(drawChart); //drawChart는 밑에 데이터 setting 할 함수 이름임.

function drawChart() {
	
	console.log(${str});
	
	var data = google.visualization.arrayToDataTable(
		${str}
	);
	
	var options = {
		chart : {
			title : '피자 토핑',
			subtitle : '피자먹고싶당', // 클래식 막대형 차트에서는 영향x
		},
		bars : 'horizontal', //막대그래프 가로로(이부분 지우면 세로됨) 클래식 막대형 차트에서는 영향x
		colors: ['gray']
	}; 

	var chart = new google.charts.Bar(document.getElementById('barchart_material'));
	chart.draw(data, options);
}
//오류난거 무시하자... 그냥 ecilpse가 이상한거임...
</script>

</head>
<body>
	<div id="barchart_material" style="width: 500px; height: 300px;"></div>
</body>
</html>