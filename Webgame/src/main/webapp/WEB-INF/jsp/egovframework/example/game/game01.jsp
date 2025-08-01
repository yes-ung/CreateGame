<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마우스 클릭 위치 → 월드 좌표 변환</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/test01.css" />
</head>
<body>
<div class="page">
<h3>Canvas 클릭 시 월드 좌표 출력</h3>
<h2>테스트1</h2>
<canvas id="gameCanvas" width="600" height="400"></canvas>
<p id="output"></p>
</div>

<script src="${pageContext.request.contextPath}/js/test01.js"></script>
</body>
</html>