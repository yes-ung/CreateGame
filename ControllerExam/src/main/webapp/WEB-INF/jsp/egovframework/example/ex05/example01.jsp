<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<h2>Hello Ajax 분리 예제</h2>
		<button id="btn">Say Hello</button>
		<p id="result"></p>
	</div>
<script src="https://code.jquery.com/jquery-3.1.0.js"></script>
<script>
$(function() {
	$("#btn").click(function() {
		$.ajax({
			url:"/api/select.do",
			type:"GET",
			success:function(data){
				console.log(data);
				alert(data);
				$("#result").text(data);
			},
			error:function(){
				console.log("에러 발생");
			}
		});
	});
});

</script>
</body>
</html>