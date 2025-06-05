package game;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import backup05_23.Unit05_23;
import game.AStarPathfinding.Node;

public class Unit_Marin extends Unit {
//	 List<Node> path = AStarPathfinding.aStar(GameConstants.MAP, (int)x/GameConstants.TILE_SIZE,
//			 (int)y/GameConstants.TILE_SIZE, (int)targetX/GameConstants.TILE_SIZE, (int)targetY/GameConstants.TILE_SIZE);
//	 
//	 public void setPath(List<Node> path) {
//		 this.path = path;
//	 }

	public Unit_Marin(double x, double y, String team) {
		
		super(y, y, team);
	    this.maxHP = 200; //유닛 최대체력설정
	    this.currentHP = 200;  // 유닛 현재체력설정
		this.deadFrameCount = 6;
		this.frameCount = 10;
		this.attackFrameCount = 4;
		this.attackCooldown = 30; //공격속도
		this.attackRange = 200;  //공격범위
		this.radius = 15;// 유닛범위 원형
		this.range = 15;//유닛범위 사각형
        this.x = x ;
        this.y = y ;
        this.team = team;
        this.targetX = x;
        this.targetY = y;
       
//        움직임 구현용 여러프레임 불러오기
        String[] dirs = { "up","down","left","right", "up_right",  "down_right",
        	     "down_left",  "up_left" };
        directionFrames = new BufferedImage[8][frameCount];
        attackSprites = new BufferedImage[8][attackFrameCount];
        deadSprites = new BufferedImage[deadFrameCount];

        for (int d = 0; d < 8; d++) {
            for (int f = 0; f < frameCount; f++) {
                try {
                    directionFrames[d][f] = ImageIO.read(getClass().getResource("/마린/" + dirs[d] + "_" + f + ".png"));               
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("마린_이동이미지 로딩 실패: " + dirs[d] + "_" + f + ".png");
                }
            }
            for (int f = 0; f < attackFrameCount; f++) {
                try {
                    attackSprites[d][f] = ImageIO.read(getClass().getResource("/마린/공격모션/attack_" + dirs[d] + "_" + f + ".png"));
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("마린_공격이미지 로딩 실패: " + dirs[d] + "_" + f + ".png");
                }
            }
            for (int f = 0; f < deadFrameCount; f++) {
                try {
                    deadSprites[f] = ImageIO.read(getClass().getResource("/마린/사망모션/dead_" + f + ".png"));
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("마린_사망이미지 로딩 실패: " + f + ".png");
                }
            }
        }

    }
//  이동 메소드
	int pathaTrgetX,pathaTrgetY;
	 public void moveTowardTarget(List<Unit> allUnits) {
		 if (path == null || path.isEmpty()) return;
		 
		 if (path.isEmpty()) {
	            System.out.println("경로를 찾을 수 없습니다.");
	        } else {
	            System.out.println("경로:");
	            for (Node node : path) {
	                System.out.printf("(%d, %d)\n", node.x, node.y);
	            }}
		 if(!path.isEmpty()) {
		 Node nextTile = path.get(0);
		 
		 pathaTrgetX = nextTile.x*GameConstants.TILE_SIZE;
		 pathaTrgetY = nextTile.y*GameConstants.TILE_SIZE;
		 }
		 
		 
		 
		 
		  //목적지 근처 도착했으면 안움직이게 하기
			if (hasArrived) return; 
		    double dx = pathaTrgetX - x;
		    double dy = pathaTrgetY -  y;
		    double distance = Math.sqrt(dx * dx + dy * dy);

		    if (distance > speed) {
		        x += speed * dx / distance;
		        y += speed * dy / distance;

		        // 방향 업데이트
		        direction = getDirectionFromDelta(dx, dy);

		        // 애니메이션 프레임 전환
		        frameTimer++;
		        if (frameTimer >= frameDelay) {
		            frameTimer = 0;
		            currentFrame = (currentFrame + 1) % frameCount;
		        }
		    } else {
		        x = pathaTrgetX;
		        y = pathaTrgetY;
		        
		        //        	도착시 달달 떨리는거 없애는 용도
		        path.remove(0); // 다음 목표로 진행
		    }
		    
		    for (Unit other : allUnits) {
		        if (other != this && isColliding(other)&&distance<30) {
		            hasArrived = true;
		            return;
		        }
		    } // 도착지점 근처이며 유닛과 충돌했을경우 도착지점까지 도달안해도 완료된걸로 보고 멈추기 
		    
	 }
	
	  
}
