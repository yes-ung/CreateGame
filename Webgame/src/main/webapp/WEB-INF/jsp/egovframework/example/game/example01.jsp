<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>캐릭터 이동</title>
    <style>
        #game-area {
            width: 800px;
            height: 600px;
            border: 1px solid black;
            position: relative;
            overflow: hidden;
        }

        #character {
            width: 50px;
            height: 50px;
            background-color: red;
            position: absolute;
            top: 100px;
            left: 100px;
            transition: top 0.5s, left 0.5s;
        }
    </style>
</head>
<body>
    <div id="game-area">
        <div id="character"></div>
    </div>

    <script>
        const character = document.getElementById('character');
        const gameArea = document.getElementById('game-area');

        gameArea.addEventListener('click', function (e) {
            const rect = gameArea.getBoundingClientRect();
            const x = e.clientX - rect.left - 25; // center character
            const y = e.clientY - rect.top - 25;

            character.style.left = `${x}px`;
            character.style.top = `${y}px`;
        });
    </script>
</body>
</html>