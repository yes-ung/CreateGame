import {
    _decorator,
    Component,
    input,
    Input,
    EventKeyboard,
    KeyCode,
    Vec3
} from 'cc';

import { SpriteSheetAnimator } from './SpriteSheetAnimator';
import { Joystick } from './Joystick';

const { ccclass, property } = _decorator;

@ccclass('PlayerController')
export class PlayerController extends Component {
    @property
    moveSpeed: number = 200;

    @property
    mapWidth: number = 3000;

    @property
    mapHeight: number = 2560;

    @property(Joystick)
    joystick: Joystick = null;
//  좌표 움직임 조작 관련
    private _moveDir: Vec3 = new Vec3();
    private _keysPressed = new Set<KeyCode>();
//  움직일때 애니메이션 관련
    private _animator: SpriteSheetAnimator | null = null;
    private _lastDirection: string = 'down';

    onLoad() {
        input.on(Input.EventType.KEY_DOWN, this.onKeyDown, this);
        input.on(Input.EventType.KEY_UP, this.onKeyUp, this);

        this._animator = this.getComponent(SpriteSheetAnimator);
    }

    onDestroy() {
        input.off(Input.EventType.KEY_DOWN, this.onKeyDown, this);
        input.off(Input.EventType.KEY_UP, this.onKeyUp, this);
    }

    onKeyDown(event: EventKeyboard) {
    this._keysPressed.add(event.keyCode);
    this.updateMoveDir();
}

    onKeyUp(event: EventKeyboard) {
    this._keysPressed.delete(event.keyCode);
    this.updateMoveDir();
}
private updateMoveDir() {
    this._moveDir.set(0, 0, 0);

    if (this._keysPressed.has(KeyCode.ARROW_LEFT)) {
        this._moveDir.x -= 1;
    }
    if (this._keysPressed.has(KeyCode.ARROW_RIGHT)) {
        this._moveDir.x += 1;
    }
    if (this._keysPressed.has(KeyCode.ARROW_UP)) {
        this._moveDir.y += 1;
    }
    if (this._keysPressed.has(KeyCode.ARROW_DOWN)) {
        this._moveDir.y -= 1;
    }
}

    update(deltaTime: number) {
    let inputVec = this._moveDir.clone();

    if (this.joystick && this.joystick.isActive()) {
        const joyInput = this.joystick.getInput();
        inputVec.set(joyInput.x, joyInput.y, 0);
    }

    // ✅ 정지 상태
    if (inputVec.lengthSqr() === 0) {
        let idleAnim = `idle_${this._lastDirection}`;

        // fallback 처리
        if (!this._animator?.hasAnimation(idleAnim)) {
            idleAnim = 'idle';
        }

        if (this._animator?.getCurrentAnimation() !== idleAnim) {
            this._animator.playAnimation(idleAnim);
        }

        return;
    }

    // ✅ 이동 중
    const dir = inputVec.clone().normalize();
    const delta = dir.multiplyScalar(this.moveSpeed * deltaTime);

    let newPos = this.node.getPosition().add(delta);
    newPos.x = Math.max(0, Math.min(this.mapWidth, newPos.x));
    newPos.y = Math.max(0, Math.min(this.mapHeight, newPos.y));
    this.node.setPosition(newPos);

    let animName = getDirection(dir.x, dir.y);

    // ✅ 이동 중에도 애니메이션 존재 여부 확인 (보호용)
    if (!this._animator?.hasAnimation(animName)) {
        console.warn(`움직임 애니메이션 '${animName}' 없음`);
        animName = 'idle'; // 혹은 'walk' 같은 기본 대체
    }

    if (this._animator?.getCurrentAnimation() !== animName) {
        this._animator.playAnimation(animName);
    }

    this._lastDirection = animName;
}


}

function getDirection(dx: number, dy: number): string {
    const angle = Math.atan2(dy, dx) * (180 / Math.PI);

    if (angle >= -22.5 && angle < 22.5) return 'right';
    if (angle >= 22.5 && angle < 67.5) return 'right_up';
    if (angle >= 67.5 && angle < 112.5) return 'up';
    if (angle >= 112.5 && angle < 157.5) return 'left_up';
    if (angle >= 157.5 || angle < -157.5) return 'left';
    if (angle >= -157.5 && angle < -112.5) return 'left_down';
    if (angle >= -112.5 && angle < -67.5) return 'down';
    if (angle >= -67.5 && angle < -22.5) return 'right_down';

    return 'down';
}
