
    let canvas = document.getElementById("gameCanvas");
    let ctx = canvas.getContext("2d");

    // 카메라 위치 (스크롤, 뷰 이동 등)
    let cameraX = 100;
    let cameraY = 50;

    canvas.addEventListener("click", function (e) {
        let rect = canvas.getBoundingClientRect();
        let localX = e.clientX - rect.left;
        let localY = e.clientY - rect.top;

        console.log("e.clientX:", e.clientX);
        console.log("e.clientY:", e.clientY);
        console.log("rect.left:", rect.left, "rect.top:", rect.top);
        console.log("localX:", localX, "localY:", localY);

        let worldClickX = localX + cameraX;
        let worldClickY = localY + cameraY;

        console.log("worldClickX:", worldClickX, "worldClickY:", worldClickY);
        console.log("테스트");

        document.getElementById("output").innerText =
            "월드 클릭 좌표: ("+Math.floor(worldClickX)+ ","+ Math.floor(worldClickY) +")";
        
        
    });

