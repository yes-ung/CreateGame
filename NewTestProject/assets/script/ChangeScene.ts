// ChangeScene.ts
import { _decorator, Component, director } from 'cc';
const { ccclass } = _decorator;

@ccclass('ChangeScene')
export class ChangeScene extends Component {
    
    // 씬 이름을 인자로 받아서 이동
    public loadSceneByName(event: Event, sceneName: string) {
        if (!sceneName) {
            console.warn('씬 이름이 비어 있습니다.');
            return;
        }

        director.loadScene(sceneName);
    }
}
