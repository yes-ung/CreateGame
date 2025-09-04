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
