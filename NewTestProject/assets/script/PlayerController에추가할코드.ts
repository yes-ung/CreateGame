import { Joystick } from './Joystick';

// 기존 PlayerController 내에...
@property(Joystick)
joystick: Joystick = null;  // 에디터에서 조이스틱 노드 연결

update(deltaTime: number) {
    let moveX = this._moveDir.x;
    let moveY = this._moveDir.y;

    // 조이스틱 입력 추가
    if (this.joystick) {
        const joyInput = this.joystick.getInput();
        moveX = joyInput.x;
        moveY = joyInput.y;
    }

    const inputVec = new Vec3(moveX, moveY, 0);

    if (inputVec.lengthSqr() === 0) {
        this._animator?.playAnimation(`idle_${this._lastDirection}`);
        return;
    }

    const dir = inputVec.normalize();
    const delta = dir.multiplyScalar(this.moveSpeed * deltaTime);

    let newPos = this.node.getPosition().add(delta);
    newPos.x = Math.max(0, Math.min(this.mapWidth, newPos.x));
    newPos.y = Math.max(0, Math.min(this.mapHeight, newPos.y));
    this.node.setPosition(newPos);

    const animName = getDirection(dir.x, dir.y);
    this._animator?.playAnimation(animName);
    this._lastDirection = animName;
}
