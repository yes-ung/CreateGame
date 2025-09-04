import { _decorator, Component, Vec3, tween } from 'cc';
const { ccclass, property } = _decorator;

@ccclass('UnitMover')
export class UnitMover extends Component {
    private _targetPosition: Vec3 | null = null;

    public moveTo(position: Vec3) {
        this._targetPosition = position;

        // 간단한 tween 이동 (실제 게임에서는 pathfinding 필요)
        tween(this.node)
            .to(1, { worldPosition: position })
            .start();
    }
}
