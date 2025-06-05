<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/main.css">
</head>
<body>
	<div class="page mt5">
		<c:if test="${name =='삼식이'}">
			<p>삼식이가 맞습니다.</p>
			</c:if>
			<c:choose>
				<c:when test="${color =='brown'}">
					<p>갈색입니다.</p>
				</c:when>
				<c:when test="${color =='yellow'}">
					<p>노란색입니다.</p>
				</c:when>
				<c:otherwise>
					<p>다른색깔입니다.</p>
				</c:otherwise>
			</c:choose>
			
		
	</div>
</body>
</html>