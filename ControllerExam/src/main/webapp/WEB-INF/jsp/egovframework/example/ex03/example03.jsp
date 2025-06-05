<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- bootstrap 5.2 css cdn  -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
	crossorigin="anonymous">
<link rel="stylesheet" href="/css/style.css">
<link rel="stylesheet" href="/css/main.css">
</head>
<body>
	<div class="page mt5">
		<table class="table tcenter">
			<thead>
				<tr>
					<th scope="col">fno</th>
					<th scope="col">title</th>
					<th scope="col">content</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<c:forEach var="data" items="${list}">
						<td>${data}</td>
					</c:forEach>
				</tr>
			</tbody>

		</table>
	</div>
</body>
</html>