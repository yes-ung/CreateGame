<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.Date" %>
 <%--TODO: c lib import --%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- TODO: fn lib import--%>
 <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <%--TODO: fmt lib import--%>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예제 홈페이지 메인</title>
<link rel="stylesheet"href="/css/style.css">
<link rel="stylesheet"href="/css/main.css">
</head>
<body>
<div class="page mt5">
<h1>메인 페이지입니다.</h1>
<br>
<h2>jsp 사용법 소개</h2>
<br>
<%--변수 저장소(사용범위)--%>
<!--      page: 현재 JSP 페이지 내에서만 유효,  -->
<!--      request: 클라이언트가(화면) 요청을 보내고 서버가(spring java) 응답할 때까지.  -->
 <!--      session: 세션이 유효한 동안 (기본 30분, 설정에 따라 다름). 예) 로그인  -->
<!--      application: 모든 사용자와 모든 요청에 대해 전역적으로 공유.  -->
 <%--      : page(현재페이지) <request <session <application(전체프로그램)  --%>
 <%--     사용법 : <c:set var="변수명"value="값"/>--%>
 <%--     jstl 표현식 --%>
 <h2>1. c 라이브러리</h2>
 <h3>1) 변수에 값을 저장</h3>
 <c:set var="num" value="10" scope="page"/>
 <c:set var="num2" value="10" scope="page"/>
 <c:set var="total" value="${num+num2}" scope="page"/>
 <%-- TODO: el 표현식 --%>
 <p>${num}</p>
 <p>${num2}</p>
 <p>${total}</p>
 <br>
  <%--    TODO: 2) 문자열 구분자로 구분하여 전체 출력함 --%>
 <%--     사용법 : <c:forTokens items="문자열"delims="구분자"var="변수명">--%>
 <%--                실행문      --%>
 <%--             </c:forTokens>--%>
 <h3>2) 문자열 구분자로 구분하여 전체 출력함</h3>
 <c:forTokens items="1/2/3" delims="/" var="a">
 <p>${a}</p>
 </c:forTokens>
 <br>
 <h3>3) not empty : 조건문에서 값이 없으면 화면 표시</h3>
 <c:set var="num3" value="" scope="page"/>
 <c:if test="${not empty num3}">
 <p>값이 없습니다.</p>
 </c:if>
 <h3>3) empty : 조건문에서 값이 있으면 화면 표시</h3>
 <c:if test="${empty num3}">
<p>값이 있습니다.</p>
 </c:if>
<br>

<h3>4) 페이지 강제 이동</h3>
 <%--     사용법 : <c:redirect url="이동할_url주소"/>--%>
 <%-- <c:redirect url="/ex01/example01.do"/>--%>
 <br>
 <h3>5) 공통 페이지 끼워 넣기 : 머리말, 꼬리말</h3>
 <%--    TODO: 5) 공통 페이지 끼워 넣기 : 머리말, 꼬리말 --%>
 <%--     사용법 : <c:import url="보여줄페이지"/>--%>
 <%--     사용법 : <jsp:include page="보여줄페이지"></jsp:include>--%>
 <c:import url="/common/footer.jsp"/>
 <jsp:include page="/common/footer.jsp"/>
 <br>
  <%--    TODO: 6) url 로 페이지 이동하기 --%>
 <%--     사용법 : <c:url value="url주소"var="변수명"/>--%>
 <%--        사용 : <a href="${변수명}">네이버</a>--%>
 <h3>6) url로 페이지 이동하기</h3>
 <c:url value="http://www.naver.com" var="naver"/>
 <h2><a href=${naver }>네이버</a></h2>
 <br>
 
 <h2>2. fn(function) 라이브러리</h2>
 <%--    TODO: 2. fn(function) 라이브러리 --%>
    <c:set var="a" value="안녕하세요 홍길동입니다."/>
    <c:set var="b" value="홍길동"/>
 <%--     1) 사용법 : ${fn:contains(chars, name)} --%>
 <%--       chars 문자열에 name 값 있으면 true, 없으면 false 화면 표시 --%>
 <h3>1) 사용법 : fn:contains(변수, 값)</h3>
 <p>${fn:contains(a,b) }</p>
<br> 
<%--   TODO: 2) chars 문자열에 "홍길동" 값이 있는 index 번호 리턴해서 화면 표시 --%>
<h3>2) 사용법 : fn:indexOf(변수,값)</h3>
<p>${fn:indexOf(a,"홍길동")}</p>
<br>
<%--   TODO: 3) 전체 길이를 리턴 --%>
<h3>3) 사용법 : fn:length(변수)</h3>
<p>${fn:length(a)}</p>
<br>
 <%--   TODO: 4) chars 문자열에서 "홍길동" -> "장길산" 변경해서 화면 표시 --%>
 <h3>4) 사용법 : fn:replace(변수,원본값,바꿀값)</h3>
 <p>replace: ${fn:replace(a,"홍길동","장길산") }</p>
 <br>
 <%--   TODO: 5) chars 에서 " " 로 문자열 자르기해서 화면 표시 : 리턴값 : 배열 --%>
 <h3>5) 사용법 : fn:split(변수,자를글자)</h3>
 <c:set var="b" value='${fn:split(a," ")}'/>
 <p>${b[0]}</p>
 <p>${b[1]}</p>
 <br>
 <%--   TODO: 6) chars 에서 0 인덱스 ~ 6-1 인덱스까지 자르기 --%>
 <h3>6) 사용법 : fn:substring(변수,첫인덱스번호,끝인덱스번호)</h3>
 <p>${fn:substring(a,0,6) }</p>
 <br>
 <%--   TODO: 7) 대/소문자 바꾸기 --%>
 <h3>7) 사용법 : fn:toLowerCase(변수)/fn:toUpperCase(a)</h3>
 <c:set var="a" value="aB"/>
 <p>${fn:toLowerCase(a) }</p>
 <p>${fn:toUpperCase(a) }</p>
 <br>
  <%--    jsp 과거 코딩 : 스크립틀릿 -> el, jstl 사용(권고) --%>
 <h2>3. jsp 과거 코딩 : 스크립틀릿->el,jstl 사용(권고)</h2>
  
<!-- /* 사용법1: 여기서는 조건문, 반복문, 일반 변수 자바의 모든 것을 
코딩가능합니다. */
 Date date = new java.util.Date();  -->
 <%--   TODO: 변수 화면 표시 : <%=변수명%> --%>
 <%Date date = new java.util.Date(); %>
<p><%=date%></p>
 <br>
 
 <h2>4. fmt(format) 라이브러리</h2>
 <h3>1) 숫자 찍기 : 3 자리마다 쉼표 추가</h3>
 <c:set var="a" value="1234567890"/>
 <%--   TODO: 1) 숫자 찍기 : 3자리마다 쉼표 추가 --%>
 <p><fmt:formatNumber value="${a}"/></p>
 <br>
 <%--   TODO: 2) 통화 찍기  --%>
 <h3>2) 통화 찍기</h3>
 <p><fmt:formatNumber value="${a}" type="currency"/></p>
 <br>
 <%--   TODO: 3) 날짜 포맷 주기--%>
 <h3>3) 날짜 포맷 주기</h3>
 <c:set var="a" value="<%=new java.util.Date()%>"></c:set>
 <p><fmt:formatDate value="${a }" pattern="yyyy-MM-dd hh:mm:ss"/></p>
 
 
 
 
 
 
 
 
 
 
 
 
</div>
</body>
</html>