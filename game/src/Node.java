
class Node implements Comparable<Node> {
    int x, y;
    int g; // 시작점부터의 거리
    int h; // 휴리스틱 (목표까지 예상 거리)
    Node parent;

    public int f() {
        return g + h;
    }

    @Override
    public int compareTo(Node o) {
        return this.f() - o.f();
    }
}
