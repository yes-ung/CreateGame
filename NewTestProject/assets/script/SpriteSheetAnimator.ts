import { _decorator, Component, Sprite, SpriteFrame, Texture2D, Rect, Vec2, UITransform  } from 'cc';
import { AnimationClip } from './AnimationClip'; // AnimationClip 스크립트 import
const { ccclass, property } = _decorator;

@ccclass('SpriteSheetAnimator')
export class SpriteSheetAnimator extends Component {
    @property({ type: Sprite })
    sprite: Sprite = null;

    @property({ type: Texture2D })
    spriteSheet: Texture2D = null;

    @property
    frameWidth: number = 64;

    @property
    frameHeight: number = 64;

    @property
    columns: number = 4;

    @property
    rows: number = 4;

    @property
    frameRate: number = 10;

    // 에디터에서 사용자 입력 받기
    @property({ type: [AnimationClip] })
    animationClips: AnimationClip[] = [];

    private _animations: Record<string, AnimationClip> = {};
    private _currentAnimation: AnimationClip = null;
    private _elapsed = 0;
    private _frameIndex = 0;

    start() {
        // 배열을 딕셔너리로 변환
        for (const clip of this.animationClips) {
            this._animations[clip.name] = clip;
        }

        this.playAnimation('idle');
    }

    // SpriteSheetAnimator.ts 내부

private _targetPos: Vec2 = null;
private _moveSpeed: number = 100; // px/s

update(deltaTime: number) {
    if (!this._currentAnimation) return;

    // 움직임 처리
    if (this._targetPos) {
        const currentPos = this.node.position;
        const toTarget = this._targetPos.subtract(new Vec2(currentPos.x, currentPos.y));
        const distance = toTarget.length();

        if (distance > 1) {
            const direction = toTarget.normalize();
            const moveDelta = direction.multiplyScalar(this._moveSpeed * deltaTime);
            this.node.setPosition(currentPos.add3f(moveDelta.x, moveDelta.y, 0));

            // 방향에 따라 애니메이션 설정
            const dirName = getDirection(direction.x, direction.y);
            this.playAnimation(dirName);
        } else {
            // 도착
            this._targetPos = null;
            this.playAnimation('idle');
        }
    }

    // 기존 애니메이션 프레임 업데이트
    this._elapsed += deltaTime;
    const frameDuration = 1 / this.frameRate;

    if (this._elapsed >= frameDuration) {
        this._elapsed = 0;

        const frame = this._currentAnimation.startFrame + this._frameIndex;
        const totalFrames = this._currentAnimation.endFrame - this._currentAnimation.startFrame + 1;

        const col = frame % this.columns;
        const row = Math.floor(frame / this.columns);
        const x = col * this.frameWidth;
        const y = row * this.frameHeight;

        const rect = new Rect(x, y, this.frameWidth, this.frameHeight);
        const spriteFrame = new SpriteFrame();
        spriteFrame.texture = this.spriteSheet;
        spriteFrame.rect = rect;
        this.sprite.spriteFrame = spriteFrame;

        this._frameIndex++;

        if (this._frameIndex >= totalFrames) {
            if (this._currentAnimation.loop) {
                this._frameIndex = 0;
            } else {
                this._frameIndex = totalFrames - 1;
            }
        }
    }
}
// 부모 기준으로 targetPos 변환해서 넘기기
const worldTarget = new Vec2(300 + i * 100, 200); // 화면 상 원하는 월드 좌표
const localTarget = this.units[i].parent!.getComponent(UITransform).convertToNodeSpaceAR(worldTarget);
animator.moveTo(localTarget);


public moveTo(worldPos: Vec2) {
    if (!this.node.parent) return;

    const localPos = this.node.parent.getComponent(UITransform).convertToNodeSpaceAR(worldPos);
    this._targetPos = localPos;
}


    public playAnimation(name: string) {
        const anim = this._animations[name];
        if (!anim) {
            console.warn(`애니메이션 '${name}'을(를) 찾을 수 없습니다.`);
            return;
        }

        if (this._currentAnimation?.name === name) return;

        this._currentAnimation = anim;
        this._frameIndex = 0;
        this._elapsed = 0;
    }




}
//   방향 결정
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

    return 'idle'; // 기본값
}
