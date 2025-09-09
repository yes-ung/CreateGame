import { _decorator, Component, Node, Vec3,Vec2, UITransform, input, Input, EventTouch } from 'cc';
const { ccclass, property } = _decorator;

@ccclass('Joystick')
export class Joystick extends Component {
    @property(Node)
    handle: Node = null;

    private _originPos: Vec3 = new Vec3();
    private _radius: number = 0;
    private _inputVector: Vec3 = new Vec3(0, 0, 0);

    onLoad() {
        this._originPos = this.handle.getPosition();

        const uiTrans = this.getComponent(UITransform);
        this._radius = uiTrans.width / 2;

        input.on(Input.EventType.TOUCH_START, this.onTouchStart, this);
        input.on(Input.EventType.TOUCH_MOVE, this.onTouchMove, this);
        input.on(Input.EventType.TOUCH_END, this.onTouchEnd, this);
        input.on(Input.EventType.TOUCH_CANCEL, this.onTouchEnd, this);
    }

    onDestroy() {
        input.off(Input.EventType.TOUCH_START, this.onTouchStart, this);
        input.off(Input.EventType.TOUCH_MOVE, this.onTouchMove, this);
        input.off(Input.EventType.TOUCH_END, this.onTouchEnd, this);
        input.off(Input.EventType.TOUCH_CANCEL, this.onTouchEnd, this);
    }

    onTouchStart(event: EventTouch) {
        this.updateHandlePosition(event);
    }

    onTouchMove(event: EventTouch) {
        this.updateHandlePosition(event);
    }

    onTouchEnd(event: EventTouch) {
        this.handle.setPosition(this._originPos);
        this._inputVector.set(0, 0, 0);
    }

    updateHandlePosition(event: EventTouch) {
        const touchPos2: Vec2 = event.getLocation();  // 실제론 Vec2임
    // 강제로 Vec3 타입으로 변환 (z=0)
    const touchPos3: Vec3 = new Vec3(touchPos2.x, touchPos2.y, 0);

        // Vec3 타입으로 바로 변환 (변경사항)
        const localPos3 = this.node.getParent().getComponent(UITransform).convertToNodeSpaceAR(touchPos3);

        let delta = localPos3.subtract(this._originPos);

        if (delta.length() > this._radius) {
            delta = delta.normalize().multiplyScalar(this._radius);
        }

        this.handle.setPosition(this._originPos.add(delta));
        this._inputVector.set(delta.x / this._radius, delta.y / this._radius, 0);
    }

    public getInput(): Vec3 {
        return this._inputVector.clone();
    }
}
