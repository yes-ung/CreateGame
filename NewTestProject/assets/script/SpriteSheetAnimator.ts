import {
    _decorator,
    Component,
    Sprite,
    SpriteFrame,
    Texture2D,
    Rect,
    Vec3,
    UITransform,
    Camera,
    Node
} from 'cc';
import { AnimationClip } from './AnimationClip';

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

    @property({ type: [AnimationClip] })
    animationClips: AnimationClip[] = [];


    private _animations: Record<string, AnimationClip> = {};
    private _currentAnimation: AnimationClip = null;

    private _elapsed = 0;
    private _frameIndex = 0;

    private _targetPos: Vec3 = null;
    private _moveSpeed: number = 100; // px/s

    start() {
        for (const clip of this.animationClips) {
            this._animations[clip.name] = clip;
        }

        this.playAnimation('idle');
    }

    update(deltaTime: number) {
        // 이동 처리
        if (this._targetPos) {
    const currentPos = this.node.getPosition(); // 현재 위치 (로컬 좌표)

    const toTarget = this._targetPos.subtract(currentPos); // 목표지점과 현재 위치 벡터 차이
    const distance = toTarget.length();

    if (distance > 1) {
        const direction = toTarget.normalize();
        const moveDelta = direction.multiplyScalar(this._moveSpeed * deltaTime);
        const newPos = currentPos.add(moveDelta);
        this.node.setPosition(newPos);

        const dirName = getDirection(direction.x, direction.y);
        this.playAnimation(dirName);
            } else {
        this._targetPos = null;
        this.playAnimation('idle');
            }
        }


        // 애니메이션 프레임 처리
        if (!this._currentAnimation) return;

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

    public moveTo(screenPos: Vec3) {
        console.log("moveTo 함수 호출됨, screenPos:", screenPos);
        this._targetPos = screenPos.clone();
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

// 방향 계산 함수
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

    return 'idle';
}
