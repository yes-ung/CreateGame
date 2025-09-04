import { _decorator, Component, Vec2, Node, UITransform } from 'cc';
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
                const worldTarget = new Vec2(300 + i * 100, 200);
                const localTarget = unitNode.parent!.getComponent(UITransform).convertToNodeSpaceAR(worldTarget);
                animator.moveTo(localTarget);
            } else {
                console.warn(`유닛 ${i}에 SpriteSheetAnimator 컴포넌트가 없습니다.`);
            }
        }
    }
}
