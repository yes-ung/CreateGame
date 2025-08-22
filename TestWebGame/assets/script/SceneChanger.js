// SceneChanger.js
const { ccclass, property } = cc._decorator;

@ccclass
export default class SceneChanger extends cc.Component {

    @property(cc.String)
    sceneName = '';  // 이동할 씬 이름

    changeScene() {
        if (this.sceneName) {
            cc.director.loadScene(this.sceneName);
        } else {
            cc.log("sceneName이 설정되지 않았습니다.");
        }
    }
}
