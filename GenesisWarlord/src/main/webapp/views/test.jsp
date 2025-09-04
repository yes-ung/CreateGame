<%@ page import="java.util.*" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head
        prefix="og: http://ogp.me/ns# fb: http://ogp.me/ns/fb# website: http://ogp.me/ns/website#"
>
    <meta charset="UTF-8" />
    <meta
            id="viewport"
            name="viewport"
            content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
    />
    <title>게임 제작중</title>
    <meta name="title" content="창세기 군주전" />
    <meta
            name="description"
            content="아직 주인이 없어 혼란스러운 세상속, 새로운 질서를 세우기 위해 고군분투하는 군주들의 이야기! &#34;Beta Test&#34; 버전입니다."
    />
    <%-- 여러 페이지중 이 페이지가 정식 대표 페이지임을 지정 --%>
    <link rel="canonical" href="http://localhost:8080/" />
    <%--  이 웹페이지가 어플처럼 작동하게 한다는 표시, 작동값은 json 파일로 설정
    crossorigin="use-credentials"는 앱으로 변환할때 쿠키도 같이 넘김을 의미--%>
    <link rel="manifest" href="/json/manifest.json" crossorigin="use-credentials" />
    <%--  브라우저 기본 UI 색상 지정 주로 모바일 대응용  #C19A6B=카멜브라운 --%>
    <meta name="theme-color" content="#C19A6B" />
    <%-- 화면 세로모드 유지하는 기능, 단 표준 코드가 아니라 일부 플랫폼에서만 적용됨--%>
    <meta name="screen-orientation" content="portrait" />
    <%--    아이폰에서 웹앱이 전체화면 모드로 실행되게 함--%>
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <%-- 아이폰 홈화면에서 실행된 웹 앱 상태표시줄 스타일 설정 black=검은색 배경 --%>
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <%--  아이폰에서 브라우저를 앱으로 추가했을때 아래에 표시될 이름--%>
    <meta name="apple-mobile-web-app-title" content="창세기 군주전" />
    <%--   삼성폰에서 웹앱이 전체화면 모드로 실행되게 함--%>
    <meta name="mobile-web-app-capable" content="yes" />
    <%--    삼성폰에서 브라우저를 앱으로 추가했을때 아래에 표시될 이름--%>
    <meta name="application-name" content="창세기 군주전" />
    <%--nternet Explorer 및 Microsoft Edge(구형 버전) 에서 링크나 버튼을 탭할 때 생기는 회색 하이라이트 효과를 제거--%>
    <meta name="msapplication-tap-highlight" content="no" />
    <%-- 비표준 태그로 일부 브라우저나 기기에서 전체화면이 되도록 돕는 역할--%>
    <meta name="full-screen" content="yes" />
    <%--  비표준 태그로 중국계 브라우저에서 일반모드가 아닌 앱모드로 실행되도록 유도--%>
    <meta name="browsermode" content="application" />


    <style>
        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
            background: #96d6c8;
        }
        .page {
            display: flex;
            justify-content: center;  /* 수평 가운데 */
            align-items: center;      /* 수직 가운데 */
            height: 100vh;
        }
        .frame-wrapper {
            height: 100vh;
            aspect-ratio: 9 / 16;
            max-height: 100vh;
            background: #111;
        }
        .frame-wrapper iframe {
            width: 100%;
            height: 100%;
            border: none;
            display: block;
        }
    </style>
</head>
<body>
<div class="page">
    <div class="frame-wrapper">
        <iframe src="/web-mobile/index.html" title="게임화면"></iframe>
    </div>
</div>
</body>
</html>

