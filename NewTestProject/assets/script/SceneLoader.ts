import { _decorator, Component, director } from 'cc';
const { ccclass } = _decorator;

@ccclass('SceneLoader')
export class SceneLoader extends Component {
    start() {
        // URL 해시값 가져오기 (예: #scene=scene1)
        const hash = window.location.hash;
        let sceneToLoad = "DefaultScene"; // 기본 씬 이름

        if (hash.includes("scene=")) {
            sceneToLoad = hash.split("scene=")[1];
        }

        director.loadScene(sceneToLoad);
    }
}
// 주소창에 example.com/index.html#scene=Stage
// #scene=Stage 이런식으로 값 전달받아 씬전환할때 사용
