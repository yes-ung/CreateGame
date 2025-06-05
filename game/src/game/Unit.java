package game;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


import game.AStarPathfinding.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Unit  {
	
    double x, y;
    int intx = (int)x;  int inty = (int)y;
    double targetX, targetY;
    int speed = 3;
    double radius = 30; //유닛범위 타원형 y=radius/2
    double range = 30; // 유닛범위 사각형 가로 range 세로 range/2
    boolean hasArrived = false; // 트루 도착 펄스 도착안함
    int maxHP = 100; //유닛 최대체력설정
    int currentHP = 100;  // 유닛 현재체력설정
    int attackDamage = 5; //유닛 공격력
    int attackRange = 70;  // 공격 사거리 (픽셀)
    int attackCooldown = 60; // 공격 쿨타임 (프레임 단위)
    int attackTimer = 0;
    boolean isDead = false; // 유닛 생사유무
    boolean deadUnitDelete = false;
    boolean isAttacking = false;//공격중인지 유무
    boolean isSelected = false; // 유닛 선택 유무
    int isCollidingWaitFrames = 0; //충돌했을때 잠시 멈추기
    String team;
    Unit targetEnemy = null; // 현재 공격대상
    Unit self = this; //자기자신 지정
//    List<Node> path = AStarPathfinding.aStar(GameConstants.MAP, (int)x/GameConstants.TILE_SIZE,
//			 (int)y/GameConstants.TILE_SIZE, (int)x/GameConstants.TILE_SIZE, (int)y/GameConstants.TILE_SIZE);
//    
    List<Node> path;
	 public void setPath(List<Node> path) {
		 this.path = path;
	 }
   
    
    
//    public void setPath(List<Point> newPath) {
//        this.path = newPath;
//    }
    public synchronized double getY() {
		return y;
	}
	
	//공격받을때
    public void takeDamage(int dmg) {
        currentHP -= dmg;
        if (currentHP <= 0) {
            currentHP = 0;
            isDead = true;
        }
    }
    //공격할때
    public void attackEnemy() {
        if (targetEnemy != null && !targetEnemy.isDead) {
        	// 방향 계산
            double dx = targetEnemy.x - this.x;
            double dy = targetEnemy.y - this.y;
            attackDirection = getDirectionFromDelta(dx, dy);
            // 공격 처리
            targetEnemy.takeDamage(attackDamage);
            isAttacking = true;
        }
        if(targetEnemy.isDead) {targetEnemy=null;}
    }




    
//  방향 정의용 8방향
  public enum Direction {
	  UP, DOWN,LEFT,RIGHT,  UP_RIGHT,  DOWN_RIGHT,
	     DOWN_LEFT,  UP_LEFT }
    
//  움직임 구현용 여러 프레임 선언
  BufferedImage[][] directionFrames; // [방향][프레임]
  Direction direction = Direction.DOWN;
  int currentFrame = 0;
  int frameCount = 4; // 총 프레임 수
  int frameDelay = 5; // 프레임 바꾸는 속도 제어용
  int frameTimer = 0;
//  공격 모션 구현용 여러 프레임 선언
  BufferedImage[][] attackSprites;
  Direction attackDirection = Direction.DOWN;
  int attackCurrentFrame = 0;
  int attackFrameCount = 1; // 총 프레임 수
  int attackFrameDelay = 5; // 프레임 바꾸는 속도 제어용
  int attackFrameTimer = 0;
//  죽는 모션 구형용 여러 프레임 선언
  BufferedImage[] deadSprites;
  Direction deadDirection = Direction.DOWN;
  int deadCurrentFrame = 0;
  int deadFrameCount = 3; // 총 프레임 수
  int deadFrameDelay = 5; // 프레임 바꾸는 속도 제어용
  int deadFrameTimer = 0;
  
  
//  충돌했는지 안했는지 트루,펄스 트루면 충돌 
  public boolean isColliding(Unit other) {
	    double dx = this.x - other.x;
	    double dy = this.y - other.y;
	    double distance = Math.sqrt(dx * dx + dy * dy);
	    if(distance==0) {return true;} //완전히 겹친 경우	    
	 // 방향 벡터 정규화
	    double nx = dx / distance;
	    double ny = dy / distance;
	 // 해당 방향에서의 각 타원의 유효 반지름 계산 (타원의 방정식에서 파생)
	    double thisEffectiveRadius = 1.0 / Math.sqrt(
	        (nx * nx) / (this.radius * this.radius) + (ny * ny) / (this.radius/2 * this.radius/2)
	    );

	    double otherEffectiveRadius = 1.0 / Math.sqrt(
	        (nx * nx) / (other.radius * other.radius) + (ny * ny) / (other.radius/2 * other.radius/2)
	    );

	    double minDist = thisEffectiveRadius + otherEffectiveRadius;

	    return distance < minDist;
	}
//  유닛 충돌시 밀어내기
  public void resolveCollision(Unit other) {
	    double dx = this.x - other.x;
	    double dy = this.y - other.y;
	    double dist = Math.sqrt(dx * dx + dy * dy);
	    double overlap = (this.radius + other.radius) - dist;

	    if (overlap > 0 && dist != 0) {
	        double pushX = (dx / dist) * (overlap / 2);
	        double pushY = (dy / dist) * (overlap / 2);

	        this.x += pushX;
	        this.y += pushY;
	        other.x -= pushX;
	        other.y -= pushY;
	    }else if(dist == 0){
	    	double pushX = 20;
	        double pushY = 20;

	        this.x += pushX;
	        this.y += pushY;
	        other.x -= pushX;
	        other.y -= pushY;
	    }

	}
  
  // 겹친거 밀어내기 코드 잘 살펴보고 넣을지 결정하기
  public void resolveOverlap(List<Unit> allUnits) {
	    for (Unit other : allUnits) {
	        if (other == this) continue;
	        Rectangle myBox = getHitbox(intx, inty);
	        Rectangle otherBox = other.getHitbox(other.intx, other.inty);

	        if (myBox.intersects(otherBox)) {
	            int dx = intx - other.intx;
	            int dy = inty - other.inty;
	            double dist = Math.sqrt(dx * dx + dy * dy);
	            if (dist == 0) dist = 1; // 같은 좌표 방지
	            int pushX = (int)(dx / dist);
	            int pushY = (int)(dy / dist);

	            x += pushX;
	            y += pushY;
	        }
	    }
	}


//  가까운 적을 찾아내는 용도
  public Unit findNearestEnemy(List<Unit> allUnits) {
	    Unit nearest = null;
	    double minDistance = Double.MAX_VALUE;

	    for (Unit other : allUnits) {
	        if (other == this || other.isDead || other.team.equals(this.team)) continue;

	        double dx = other.x - this.x;
	        double dy = other.y - this.y;
	        double distance = Math.sqrt(dx * dx + dy * dy);

	        if (distance < minDistance) {
	            minDistance = distance;
	            nearest = other;
	        }
	    }

	    return nearest;
	}

    public Unit(double x, double y, String team) {
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
                    directionFrames[d][f] = ImageIO.read(getClass().getResource("/유닛기본모션/" + dirs[d] + "_" + f + ".png"));               
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("이동이미지 로딩 실패: " + dirs[d] + "_" + f + ".png");
                }
            }
            for (int f = 0; f < attackFrameCount; f++) {
                try {
                    attackSprites[d][f] = ImageIO.read(getClass().getResource("/유닛기본모션/공격모션/attack_" + dirs[d] + "_" + f + ".png"));
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("공격이미지 로딩 실패: " + dirs[d] + "_" + f + ".png");
                }
            }
            for (int f = 0; f < deadFrameCount; f++) {
                try {
                    deadSprites[f] = ImageIO.read(getClass().getResource("/유닛기본모션/사망모션/dead_" + f + ".png"));
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("사망이미지 로딩 실패: " + f + ".png");
                }
            }
        }

    }
    
    

    public void setTarget(double tx, double ty) {
        this.targetX = tx;
        this.targetY = ty;
        this.hasArrived = false;
        targetEnemy = null;
    }
    
//    8방향 결정
    public Direction getDirectionFromDelta(double dx, double dy) {
        double angle = Math.atan2(-dy, dx); // y축이 아래로 향하므로 -dy
        double degrees = Math.toDegrees(angle);
        if (degrees < 0) degrees += 360;

        if (degrees >= 337.5 || degrees < 22.5) return Direction.RIGHT;
        else if (degrees < 67.5) return Direction.UP_RIGHT;
        else if (degrees < 112.5) return Direction.UP;
        else if (degrees < 157.5) return Direction.UP_LEFT;
        else if (degrees < 202.5) return Direction.LEFT;
        else if (degrees < 247.5) return Direction.DOWN_LEFT;
        else if (degrees < 292.5) return Direction.DOWN;
        else return Direction.DOWN_RIGHT;
    }

   
    
////팀선택 글자 같은 글자로 인식 오버라이딩@@@@@@@@@@
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((team == null) ? 0 : team.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Unit other = (Unit) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
////팀선택 글자 같은 글자로 인식 오버라이딩@@@@@@@@@@
	
	
	
	public void update(List<Unit> allUnits ) {
		
		
//		 for (Unit u : allUnits) {
//	        	if (u == self) continue; // ⭐ 자기 자신은 무시
//	            int tx = (int) u.x;
//	            int ty = (int) u.y;
//
//	            for (int dx = (int)-u.range/2; dx < u.range/2; dx++) {
//	                for (int dy = (int)-u.range/4; dy < u.range/4; dy++) {
//	                    int mx = tx + dx;
//	                    int my = ty + dy;
//	                    if (isInBounds(mx, my, map)) {
//	                        map[mx][my] = 1;
//	                    }
//	                }
//	            }
//	        }
//		if (isCollidingWaitFrames > 0) {
//			isCollidingWaitFrames--;
//	        return;
//	    }
    	if (isDead&&deadUnitDelete) return;
    	
    	// 타겟이 없거나 죽었으면 다시 찾기
        if (targetEnemy == null || targetEnemy.isDead) {
            targetEnemy = findNearestEnemy(allUnits);
        }  	
    	attackTimer++; // 공격 쿨타임
    	// 공격 처리
        if (targetEnemy != null && !targetEnemy.isDead) {
            double dx = targetEnemy.x - this.x;
            double dy = targetEnemy.y - this.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= attackRange) {
                if (attackTimer >= attackCooldown) {
                    attackEnemy();
                    attackTimer = 0;
                }
                // return;공격 중엔 이동하지 않음
                
            }
        }
        moveTowardTarget(allUnits);    	              
    }
	
	//유닛범위 사각형
	Rectangle getHitbox(int nextX, int nextY) {
	    return new Rectangle(nextX-(int)range, nextY-(int)range/2, (int)range*2, (int)range); // 중심 기준 히트박스
	}
	
	boolean isInBounds(int x, int y, int[][] map) {
	    return x >= 0 && y >= 0 && x < map.length && y < map[0].length;
	}

    
    
//    이동 메소드
    public void moveTowardTarget(List<Unit> allUnits ) {
    	
//    	if (path.isEmpty()) {
//            System.out.println("경로를 찾을 수 없습니다.");
//        } else {
//            System.out.println("경로:");
//            for (Node node : path) {
//                System.out.printf("(%d, %d)\n", node.x, node.y);
//            }
//    	if (path == null || path.isEmpty()) return;
//    	for (Node node : path) {
//    	 Node nextTile = path.get(0); // 다음 도착 지점 (타일 좌표)
//         int pathTargetX = nextTile.x ;
//         int pathTargetY = nextTile.y ;
//         
//         int pathDx = pathTargetX - (int)this.x;
//         int pathDy = pathTargetY - (int)this.y;
//         double pathDistance = Math.sqrt(pathDx * pathDx + pathDy * pathDy);
//         
//         if (pathDistance < speed) {
//             // 거의 도착했으면 해당 타일은 완료 → 다음 타일로 이동
//             x = pathTargetX;
//             y = pathTargetY;
//             path.remove(0); // 다음 목표로 진행
//         } else {
//             // 아직 도착 전이면 이동
//             x += speed * pathDx / pathDistance;
//             y += speed * pathDy / pathDistance;
//         }
//    	}
    	
    	
  //목적지 근처 도착했으면 안움직이게 하기
	if (hasArrived) return; 
    double dx = targetX - x;
    double dy = targetY -  y;
    double distance = Math.sqrt(dx * dx + dy * dy);
 
    if (distance > speed) {
//    	x += speed * dx / distance;
//        y += speed * dy / distance;
        
//      유닛이 유닛을 못 밀게 하려고 추가했는데 결과가 이상해서 보류
    	double moveX = (speed * dx / distance);
        double moveY = (speed * dy / distance);

        double newX = x + moveX;
        double newY = y + moveY;
        
        Rectangle futureHitbox = getHitbox((int)newX, (int)newY);
        
        boolean blocked = false;
        
        for (Unit other : allUnits) {
            if (other == this) continue; // 자기 자신 제외
            

            Rectangle otherHitbox = other.getHitbox((int)other.x, (int)other.y);

            if (futureHitbox.intersects(otherHitbox)) {
                blocked = true;
                break;
            }
            
        }
        if (!blocked) {
            x = newX;
            y = newY;
        }

        // 방향 업데이트
        direction = getDirectionFromDelta(dx, dy);

        // 이동 애니메이션 프레임 전환
        frameTimer++;
        if (frameTimer >= frameDelay) {
            frameTimer = 0;
            currentFrame = (currentFrame + 1) % frameCount;
        }
    } else {
        x = targetX;
        y = targetY; 
        //        	도착시 달달 떨리는거 없애는 용도
    }


    for (Unit other : allUnits) {
        if (other != this && isColliding(other)&&distance<30) {
            hasArrived = true;
            return;
        }
      } // 도착지점 근처이며 유닛과 충돌했을경우 도착지점까지 도달안해도 완료된걸로 보고 멈추기 
    } 
    


    public void draw(Graphics g, int cameraX , int cameraY) {
    	if(isDead && deadUnitDelete) return;
    	
        int dirIndex = direction.ordinal();
        int attackDirIndex = attackDirection.ordinal();
        BufferedImage frame = directionFrames[dirIndex][currentFrame];
        BufferedImage attackFrame = attackSprites[attackDirIndex][attackCurrentFrame];
        BufferedImage deadFrame = deadSprites[deadCurrentFrame];
        int drawX = (int)x;
        int drawY = (int)y;
        //유닛 선택시 표시인데 유닛그림 안 가리도록 제일 먼저 실행되도록 앞쪽에 배치
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.drawOval((int)x -(int)radius -cameraX, (int)y -(int)radius/2 -cameraY, (int)radius*2, (int)radius);
        }
        if (isDead && !deadUnitDelete) {
    		g.drawImage(deadFrame, drawX - deadFrame.getWidth()/2 -cameraX, drawY - deadFrame.getHeight()+20 -cameraY , null);
    		deadFrameTimer++;
    		if (deadFrameTimer >= deadFrameDelay) {
            	 deadFrameTimer = 0;
                 deadCurrentFrame = (deadCurrentFrame + 1);                 
             }
    		if (deadCurrentFrame==deadFrameCount) {
    			deadUnitDelete = true;
    		}
    		
    	} else if(isAttacking && attackFrame != null) {
        	g.drawImage(attackFrame, drawX - attackFrame.getWidth()/2  -cameraX, drawY - attackFrame.getHeight()+20-cameraY, null);
        	attackFrameTimer++;
    		if (attackFrameTimer >= attackFrameDelay) {
    			attackFrameTimer = 0;
    			attackCurrentFrame = (attackCurrentFrame + 1);                 
             }
    		if (attackCurrentFrame==attackFrameCount) {
    			isAttacking = false;
    			attackCurrentFrame=0;    			
    		}
        } else   if (frame != null) {
            g.drawImage(frame, drawX - frame.getWidth() / 2 -cameraX, drawY - frame.getHeight()+20-cameraY, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillOval((int)x - 10 -cameraX, (int)y - 10-cameraY, 20, 20);
        }
        hpBar(g,cameraX ,cameraY);
      
    
    }
    
    //체력바 구현
    public void hpBar(Graphics g, int cameraX , int cameraY) {
    	// 체력바 그리기
    	int drawX = (int)x;
        int drawY = (int)y;
        int barWidth = 50;
        int hpWidth = (int)((currentHP / (double)maxHP) * barWidth);
     
        g.setColor(Color.GREEN);
        g.fillRect(drawX - barWidth/2 -cameraX, drawY+20-cameraY, hpWidth, 5);
        g.setColor(Color.BLACK);
        g.drawRect(drawX - barWidth/2 -cameraX, drawY+20-cameraY, barWidth, 5);
    	
    }

}
