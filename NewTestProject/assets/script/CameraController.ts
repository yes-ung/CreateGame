import {
    _decorator,
    Component,
    input,
    Input,
    EventKeyboard,
    KeyCode,
    Vec3,
    Camera
} from 'cc';

const { ccclass, property } = _decorator;

@ccclass('CameraController')
export class CameraController extends Component {
    @property
    moveSpeed: number = 300; // px/s

    @property
    mapWidth: number = 3000;

    @property
    mapHeight: number = 2560;

    @property
    viewWidth: number = 720;

    @property
    viewHeight: number = 1440;

    private _moveDir: Vec3 = new Vec3(0, 0, 0);

    onLoad() {
        input.on(Input.EventType.KEY_DOWN, this.onKeyDown, this);
        input.on(Input.EventType.KEY_UP, this.onKeyUp, this);
    }

    onDestroy() {
        input.off(Input.EventType.KEY_DOWN, this.onKeyDown, this);
        input.off(Input.EventType.KEY_UP, this.onKeyUp, this);
    }

    onKeyDown(event: EventKeyboard) {
        switch (event.keyCode) {
            case KeyCode.ARROW_UP:
                this._moveDir.y = 1;
                break;
            case KeyCode.ARROW_DOWN:
                this._moveDir.y = -1;
                break;
            case KeyCode.ARROW_LEFT:
                this._moveDir.x = -1;
                break;
            case KeyCode.ARROW_RIGHT:
                this._moveDir.x = 1;
                break;
        }
    }

    onKeyUp(event: EventKeyboard) {
        switch (event.keyCode) {
            case KeyCode.ARROW_UP:
            case KeyCode.ARROW_DOWN:
                this._moveDir.y = 0;
                break;
            case KeyCode.ARROW_LEFT:
            case KeyCode.ARROW_RIGHT:
                this._moveDir.x = 0;
                break;
        }
    }

    update(deltaTime: number) {
        if (this._moveDir.lengthSqr() === 0) return;

        const direction = this._moveDir.normalize();
        const moveDelta = direction.multiplyScalar(this.moveSpeed * deltaTime);

        const currentPos = this.node.getPosition();
        let newPos = currentPos.add(moveDelta);

        // 경계 제한
        const halfViewW = this.viewWidth / 2;
        const halfViewH = this.viewHeight / 2;

        newPos.x = Math.max(halfViewW, Math.min(this.mapWidth - halfViewW, newPos.x));
        newPos.y = Math.max(halfViewH, Math.min(this.mapHeight - halfViewH, newPos.y));

        this.node.setPosition(newPos);
    }
}
