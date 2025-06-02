package TestSit;
import java.util.List;

import TestSit.AStarPathfinding.Node;



public class test {
	
	 public static void main(String[] args) {
	        int[][] grid = {
	            {0, 0, 0, 0, 0},
	            {1, 1, 1, 0, 1},
	            {0, 0, 0, 0, 0},
	            {0, 1, 1, 1, 0},
	            {0, 0, 0, 0, 0}
	        };

	        int startX = 0, startY = 0;
	        int goalX = 4, goalY = 4;

	        List<Node> path = AStarPathfinding.aStar(grid, startX, startY, goalX, goalY);

	        if (path.isEmpty()) {
	            System.out.println("경로를 찾을 수 없습니다.");
	        } else {
	            System.out.println("경로:");	         
	            for (Node node : path) {
	                System.out.printf("(%d, %d)\n", node.x, node.y);
	            }
	        }
	    }

}
