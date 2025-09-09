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
    joystick: Joystick = null;  // 에디터에서 조이스틱 노드 연결

    private _moveDir: Vec3 = new Vec3();
    private _animator: SpriteSheetAnimator | null = null;

    private _lastDirection: string = 'down';  // ✅ 기본 방향 설정

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
        if (this._moveDir.lengthSqr() === 0) {
            // ✅ 방향키 입력이 없을 때 마지막 방향 기준 idle 애니메이션
            // this._animator?.playAnimation(`idle_${this._lastDirection}`);
             this._animator?.playAnimation(`idle`);
            return;
        }

        // ✅ 정규화된 방향 벡터 사용 (속도 일정 유지)
        const dir = this._moveDir.clone().normalize();
        const delta = dir.multiplyScalar(this.moveSpeed * deltaTime);

        let newPos = this.node.getPosition().add(delta);

        // 경계 제한
        newPos.x = Math.max(0, Math.min(this.mapWidth, newPos.x));
        newPos.y = Math.max(0, Math.min(this.mapHeight, newPos.y));

        this.node.setPosition(newPos);

        // ✅ 이동 방향 애니메이션
        const animName = getDirection(dir.x, dir.y);
        this._animator?.playAnimation(animName);

        // ✅ 마지막 방향 저장
        this._lastDirection = animName;
    }
}

// 방향 벡터 → 애니메이션 이름
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

    return 'down'; // 기본값
}
