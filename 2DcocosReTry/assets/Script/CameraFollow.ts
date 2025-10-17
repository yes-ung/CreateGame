import {
    _decorator,
    Component,
    Node,
    Vec3
} from 'cc';

const { ccclass, property } = _decorator;

@ccclass('CameraFollow')
export class CameraFollow extends Component {
    @property({ type: Node })
    target: Node = null;

    @property
    mapWidth: number = 3000;

    @property
    mapHeight: number = 2560;

    @property
    viewWidth: number = 720;

    @property
    viewHeight: number = 1440;

    update(deltaTime: number) {
        if (!this.target) return;

        const targetPos = this.target.getPosition();

        // 카메라 위치 = 유닛 위치 중심 기준으로 (카메라의 z 위치는 유지)
        let cameraPos = new Vec3(
            targetPos.x,
            targetPos.y+360,
            this.node.getPosition().z
        );

        // 카메라 절반 크기
        const halfW = this.viewWidth / 2;
        const halfH = this.viewHeight / 2;

       // 카메라 위치 제한 (맵 가장자리에서 멈추게 함)
        cameraPos.x = Math.max(halfW, Math.min(this.mapWidth - halfW, cameraPos.x));
        cameraPos.y = Math.max(halfH, Math.min(this.mapHeight, cameraPos.y));

        this.node.setPosition(cameraPos);
    }
}
