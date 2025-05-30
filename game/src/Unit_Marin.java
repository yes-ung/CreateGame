import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Unit_Marin extends Unit {
	

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

	
	   public void moveTowardTarget(List<Unit> allUnits) {
		   if (path == null || path.isEmpty()) return;

	        Point nextTile = path.get(0); // 다음 도착 지점 (타일 좌표)
	        int targetX = nextTile.x * GameConstants.TILE_SIZE + GameConstants.TILE_SIZE / 2;
	        int targetY = nextTile.y * GameConstants.TILE_SIZE + GameConstants.TILE_SIZE / 2;

	        int dx = targetX - (int)x;
	        int dy = targetY - (int)y;
	        double distance = Math.sqrt(dx * dx + dy * dy);

	        if (distance < speed) {
	            // 거의 도착했으면 해당 타일은 완료 → 다음 타일로 이동
	            x = targetX;
	            y = targetY;
	            path.remove(0); // 다음 목표로 진행
	        } else {
	            // 아직 도착 전이면 이동
	            x += speed * dx / distance;
	            y += speed * dy / distance;
	        }
	    }
}
