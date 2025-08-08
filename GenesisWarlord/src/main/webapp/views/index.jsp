<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 7. 24.
  Time: 오전 11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*" %>
<%@ page import="com.fasterxml.jackson.databind.ObjectMapper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>게임 제작중</title>
    <link rel="stylesheet" href="Utils/slick.css" />
    <!-- <link rel="stylesheet" href="Utils/prism.css" />
    <link rel="stylesheet" href="Utils/tiny-slider.css" /> -->
    <link rel="stylesheet" href="css/style.css" />
    <link rel="stylesheet" href="css/mapStyle.css" />
    <link rel="stylesheet" href="css/buttonStyle.css" />
    <link rel="stylesheet" href="css/portraitStyle.css" />
</head>
<body>
<div id="pageCover">
    <!-- 게임로딩화면 -->
    <div id="GameLoading" class="GameLayer">
        <div class="LoadingDiv">
            <div class="LoadingMsg">예성 게임즈</div>
            <div class="LoadingBlock">
                <div class="LoadedBlock"></div>
            </div>
        </div>
    </div>
    <!-- 로비화면 -->
    <div id="GameStart" class="GameLayer">
        <div class="menuSelectionBg UiBack1"></div>
    </div>
    <!-- 스테이지 선택화면 -->
    <div id="GameStage" class="GameLayer">
        <div class="StageButtonWrapper">
            <div class="StageSearch">
            <input type="number" id="slideInput" placeholder="스테이지 번호 입력" />
            <div id="goToSlideBtn" class="BackButton">이동</div>
            </div>
            <div class="StageButtonSlider">
            </div>
            <div class="sliderArrows">
                <div class="prevArrow BackButton">←</div>
                <div class="nextArrow BackButton">→</div>
            </div>
        </div>
        <div class="UiBack1 fixedBox"><div role="button" class="BackButton" onClick="Game.moveGameLobby()">뒤로가기</div></div>
    </div>
    <!-- 캐시상점 화면 -->
    <div id="GameStore" class="GameLayer">
        <div class="StoreImg"></div>
        <div role="button" class="BackButton" onClick="Game.moveGameLobby()">뒤로가기</div>
    </div>
    <!-- 병영관리 선택화면 -->
    <div id="GameBarracks" class="GameLayer">
        <div role="button" class="BackButton" onClick="Game.moveGameLobby()">뒤로가기</div>
        <div class="BarracksInfo  UiBack1"></div>
    </div>
    <!-- 모집화면,카드뽑기화면 -->
    <div id="GameRecruitment" class="GameLayer">
        <div class="RecruitmentBenner1"><div class="RecruitmentBenner1Button BackButton">일반모집</div></div>
        <div class="RecruitmentBenner2"><div class="RecruitmentBenner2Button BackButton">특수모집</div></div>
        <div role="button" class="BackButton" onClick="Game.moveGameLobby()">뒤로가기</div>
    </div>
    <!-- 플레이 화면 -->
    <div id="GamePlay" class="GameLayer" race="Terran">
        <canvas id="backCanvas"></canvas>
        <canvas id="middleCanvas"></canvas>
        <canvas id="frontCanvas"></canvas>
        <canvas id="fogCanvas"></canvas>
        <div class="panel_Map">
            <canvas name="mini_map" width="130" height="130"></canvas>
        </div>
        <div class="panel_Info">
            <div class="infoLeft">
                <div name="portrait"></div>
                <div id="HP_SP">
            <span class="_Shield"
            ><span class="shield"></span>/<span class="SP"></span
            ></span>
                    <span class="_Health"
                    ><span class="life"></span>/<span class="HP"></span
                    ></span>
                </div>
                <div id="MP">
            <span class="_Magic"
            ><span class="magic"></span>/<span class="MP"></span
            ></span>
                </div>
            </div>
            <div class="infoCenter">
                <h3 class="name"></h3>
                <p class="detector">Detector</p>
                <p class="kill">Kill:<span></span></p>
                <p class="damage">Damage:<span></span></p>
                <p class="armor">Armor:<span></span></p>
                <p class="plasma">Plasma:<span></span></p>
                <p class="passenger">Passenger:<span></span></p>
                <p class="icons"></p>
            </div>
            <div class="infoRight">
                <div class="upgraded">
                    Upgraded:
                    <div name="icon"></div>
                    <div name="icon"></div>
                    <div name="icon"></div>
                </div>
                <div class="upgrading">
                    <div name="icon"></div>
                    <div name="processing">
                        <span></span>%<br />
                        <div class="processBar">
                            <div class="processedBar"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="override">
                <div class="multiSelection">
                    <div name="portrait" class="Lurker"></div>
                </div>
            </div>
        </div>
        <div class="panel_Control"></div>
        <div class="warning_Box"></div>
        <div id="cheat_Box">Message: <input type="text" id="cheatInput" /></div>
        <div class="message_Box"></div>
        <div class="resource_Box">
            <div class="mine"></div>
            <span class="mineNum"></span>
            <div class="gas"></div>
            <span class="gasNum"></span>
            <div class="man"></div>
            <span class="manNum"><span></span>/<span></span></span>
        </div>
        <div class="tooltip_Box">
            <div class="itemName"></div>
            <div class="cost">
                <div class="mine"></div>
                <span class="mineNum"></span>
                <div class="gas"></div>
                <span class="gasNum"></span>
                <div class="man"></div>
                <span class="manNum"></span>
                <div class="magic"></div>
                <span class="magicNum"></span>
            </div>
        </div>
    </div>



</div>
<%
    // 서버에서 JSON으로 변환
    String barracksInfoJson = new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request.getAttribute("barracksInfos"));
    request.setAttribute("barracksInfoJson", barracksInfoJson);
%>
<script>
    // 서버 값을 전역 변수로 먼저 넘겨줌 (DB정보 JS에 넘김)
    // 안전하게 JSON 파싱
    window.barracksInfo = JSON.parse('<%= barracksInfoJson %>');
</script>
<script src="Utils/jquery.min.js"></script>
<script src="Utils/gFrame.js"></script>
<script src="Utils/sourceLoader.js"></script>
<script src="Utils/slick.min.js"></script>
<script>
    // 스테이지창 슬라이더 선언
    // $(document).ready(function(){
    //   $('.StageButtonSlider').slick({
    //     slidesToShow: 3,
    //     slidesToScroll: 3,
    //     arrows: true,
    //     infinite: false,
    //   });
    // });
</script>


<script src="js/Characters/Gobj.js"></script>
<script src="js/Characters/Burst.js"></script>
<script src="js/Characters/Animation.js"></script>
<script src="js/Characters/Units.js"></script>
<script src="js/Characters/Magic.js"></script>
<script src="js/Characters/Upgrade.js"></script>
<script src="js/Characters/Building.js"></script>
<script src="js/Characters/Zerg.js"></script>
<script src="js/Characters/Terran.js"></script>
<script src="js/Characters/Protoss.js"></script>
<script src="js/Characters/Neutral.js"></script>
<script src="js/Characters/Hero.js"></script>
<script src="js/Characters/Bullets.js"></script>
<script src="js/Characters/Button.js"></script>
<script src="js/Characters/Map.js"></script>

<script src="js/GameRule/Game.js"></script>
<script src="js/GameRule/Resource.js"></script>
<script src="js/GameRule/Levels.js"></script>
<script src="js/GameRule/Referee.js"></script>
<script src="js/GameRule/Cheat.js"></script>
<script src="js/GameRule/Multiplayer.js"></script>
<!-- <script src="js/GameRule/SC_server.js"></script> -->

<!-- <script src="Utils/prism.js"></script> -->


<script src="js/Controller/mouseController.js"></script>
<script src="js/Controller/keyController.js"></script>

<script>
    //########## AutoStart ##########//
    $(function () {
        Game.init();
    });
</script>

</body>
</html>

