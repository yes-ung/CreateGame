// SceneSwitcher.ts
import { _decorator, Component, Button, director } from 'cc';
const { ccclass } = _decorator;

@ccclass('SceneSwitcher')
export class SceneSwitcher extends Component {
    // 버튼 클릭 시 호출될 함수
    goToScene2() {
        director.loadScene('Scene2');  // 'Scene2'는 전환할 씬의 이름입니다
    }
}
