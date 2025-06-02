package game;
import java.awt.Point;
import java.util.*;

public class AStarPathfinding {
	public static class Node implements Comparable<Node> {
		public int x, y;
		public double g, h;
		public Node parent;

		public Node(int x, int y, double g, double h, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

		public double f() {
            return g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.f(), other.f());
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node)) return false;
            Node other = (Node) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // 8방향 이동 (dx, dy)
    static final int[][] directions = {
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1},           {0, 1},
        {1, -1},  {1, 0},  {1, 1}
    };

    public static java.util.List<Node> aStar(int[][] grid, int startX, int startY, int goalX, int goalY) {
        int rows = grid.length;
        int cols = grid[0].length;

        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedSet = new HashSet<>();

        Node start = new Node(startX, startY, 0, heuristic(startX, startY, goalX, goalY), null);
        openList.add(start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            closedSet.add(current);

            for (int[] dir : directions) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (nx < 0 || ny < 0 || nx >= cols || ny >= rows || grid[ny][nx] == 1)
                    continue;

                double stepCost = (dir[0] == 0 || dir[1] == 0) ? 1.0 : Math.sqrt(2); // 직선:1, 대각선:√2
                double gCost = current.g + stepCost;

                Node neighbor = new Node(nx, ny, gCost, heuristic(nx, ny, goalX, goalY), current);

                if (closedSet.contains(neighbor))
                    continue;

                Optional<Node> existing = openList.stream().filter(n -> n.equals(neighbor)).findFirst();

                if (existing.isEmpty() || gCost < existing.get().g) {
                    openList.remove(existing.orElse(null));
                    openList.add(neighbor);
                }
            }
        }

        return java.util.Collections.emptyList(); // 경로 없음
    }

    static double heuristic(int x1, int y1, int x2, int y2) {
        return Math.hypot(x2 - x1, y2 - y1); // 유클리디안 거리
    }

     static List<Node> reconstructPath(Node end) {
        List<Node> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }


}