import { _decorator, Component, Node } from 'cc';
const { ccclass, property } = _decorator;

@ccclass('Unit')
export class Unit extends Component {
    @property
    maxHealth: number = 100;

    @property
    attackPower: number = 10;

    @property
    attackRange: number = 100;

    @property
    attackSpeed: number = 1; // attacks per second

    private _currentHealth: number = 100;
    private _attackCooldown: number = 0;

    start() {
        this._currentHealth = this.maxHealth;
    }

    update(deltaTime: number) {
        this._attackCooldown -= deltaTime;
    }

    public takeDamage(amount: number) {
        this._currentHealth -= amount;
        if (this._currentHealth <= 0) {
            this.die();
        }
    }

    public attack(target: Unit) {
        if (this._attackCooldown <= 0 && this.inRange(target)) {
            target.takeDamage(this.attackPower);
            this._attackCooldown = 1 / this.attackSpeed;
        }
    }

    private inRange(target: Unit): boolean {
        const distance = this.node.worldPosition.subtract(target.node.worldPosition).length();
        return distance <= this.attackRange;
    }

    private die() {
        this.node.destroy();
    }
}
