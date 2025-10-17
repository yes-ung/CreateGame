import {
    _decorator,
    Component,
    Node,
    Vec3,
    Vec2,
    UITransform,
    EventTouch,
    EventTarget
} from 'cc';

const { ccclass, property } = _decorator;

@ccclass('Joystick')
export class Joystick extends Component {
    @property(Node)
    handle: Node = null;

    private _inputVector: Vec3 = new Vec3();
    private _radius: number = 0;

    onLoad() {
        const uiTransform = this.getComponent(UITransform);
        this._radius = uiTransform.width / 2;

        // Joystick 노드 자체에만 터치 이벤트 등록 (전역 X)
        this.node.on(Node.EventType.TOUCH_START, this.onTouchMove, this);
        this.node.on(Node.EventType.TOUCH_MOVE, this.onTouchMove, this);
        this.node.on(Node.EventType.TOUCH_END, this.onTouchEnd, this);
        this.node.on(Node.EventType.TOUCH_CANCEL, this.onTouchEnd, this);
    }

    onDestroy() {
        this.node.off(Node.EventType.TOUCH_START, this.onTouchMove, this);
        this.node.off(Node.EventType.TOUCH_MOVE, this.onTouchMove, this);
        this.node.off(Node.EventType.TOUCH_END, this.onTouchEnd, this);
        this.node.off(Node.EventType.TOUCH_CANCEL, this.onTouchEnd, this);
    }

    onTouchMove(event: EventTouch) {
        const touchPos = event.getUILocation();
        const local = this.node.getComponent(UITransform).convertToNodeSpaceAR(new Vec3(touchPos.x, touchPos.y, 0));
        const dir = new Vec3(local.x, local.y, 0);
        const len = dir.length();

        if (len > this._radius) {
            dir.normalize().multiplyScalar(this._radius);
        }

        this.handle.setPosition(dir);
        this._inputVector.set(dir.x / this._radius, dir.y / this._radius, 0);
    }

    onTouchEnd() {
        this.handle.setPosition(Vec3.ZERO);
        this._inputVector.set(0, 0, 0);
    }

    public getInput(): Vec3 {
        return this._inputVector.clone();
    }

    public isActive(): boolean {
        return this._inputVector.lengthSqr() > 0;
    }
}
