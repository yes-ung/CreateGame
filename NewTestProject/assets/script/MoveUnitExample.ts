import { _decorator, Component, Vec3, Node } from 'cc';
import { SpriteSheetAnimator } from './SpriteSheetAnimator';
const { ccclass, property } = _decorator;

@ccclass('MoveUnitsExample')
export class MoveUnitsExample extends Component {
    @property({ type: [Node] })
    units: Node[] = [];

    start() {
        if (this.units.length === 0) {
            console.warn('유닛 리스트가 비어 있습니다.');
            return;
        }

        for (let i = 0; i < this.units.length; i++) {
            const unitNode = this.units[i];
            const animator = unitNode.getComponent(SpriteSheetAnimator);
            if (animator) {
                // MapRoot 기준 local 좌표를 직접 지정
                const targetLocalPos = new Vec3(200 + i * 150, 200, 0); // X 위치만 다르게
                animator.moveTo(targetLocalPos);
            } else {
                console.warn(`유닛 ${i}에 SpriteSheetAnimator 컴포넌트가 없습니다.`);
            }
        }
    }
}
