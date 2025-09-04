import { _decorator, Component, Node, Vec3 } from 'cc';
import { Unit } from './Unit';
const { ccclass, property } = _decorator;

@ccclass('CombatAI')
export class CombatAI extends Component {
    @property({ type: Node })
    targetNode: Node = null;

    private unit: Unit;

    start() {
        this.unit = this.getComponent(Unit);
    }

    update(deltaTime: number) {
        if (this.targetNode && this.targetNode.isValid) {
            const targetUnit = this.targetNode.getComponent(Unit);
            if (targetUnit) {
                this.unit.attack(targetUnit);
            }
        }
    }
}
