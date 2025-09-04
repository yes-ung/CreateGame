import { _decorator, Component, Node, Camera, PhysicsSystem, PhysicsRayResult, Vec3 } from 'cc';
import { UnitMover } from './UnitMover';
const { ccclass, property } = _decorator;

@ccclass('UnitSelector')
export class UnitSelector extends Component {
    @property({ type: Camera })
    camera: Camera = null;

    private selectedUnit: Node = null;

    onMouseDown(event: MouseEvent) {
        const screenPoint = new Vec3(event.x, event.y, 0);
        const ray = this.camera.screenPointToRay(screenPoint.x, screenPoint.y);

        if (PhysicsSystem.instance.raycast(ray)) {
            const result = PhysicsSystem.instance.raycastResults[0];

            const hitNode = result.collider.node;

            if (hitNode.getComponent(UnitMover)) {
                // 유닛 선택
                this.selectedUnit = hitNode;
            } else {
                // 위치 클릭 → 이동 명령
                if (this.selectedUnit) {
                    const worldPos = result.hitPoint;
                    this.selectedUnit.getComponent(UnitMover).moveTo(worldPos);
                }
            }
        }
    }
}
