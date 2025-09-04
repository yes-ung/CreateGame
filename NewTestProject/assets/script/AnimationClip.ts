import { _decorator, Component, Sprite, SpriteFrame, Texture2D, Rect } from 'cc';
const { ccclass, property, type } = _decorator;

@ccclass('AnimationClip')
export class AnimationClip {
    @property
    name: string = '';

    @property
    startFrame: number = 0;

    @property
    endFrame: number = 0;

    @property
    loop: boolean = true;
}
