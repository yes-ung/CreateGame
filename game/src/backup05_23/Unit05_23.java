package backup05_23;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Unit05_23  {
    double x, y;
    double targetX, targetY;
    int speed = 10;
    double radius = 20;
    boolean hasArrived = false; // 트루 도착 펄스 도착안함
    int maxHP = 100; //유닛 최대체력설정
    int currentHP = 100;  // 유닛 현재체력설정
    int attackDamage = 100; //유닛 공격력
    int attackRange = 50;  // 공격 사거리 (픽셀)
    int attackCooldown = 30; // 공격 쿨타임 (프레임 단위)
    int attackTimer = 0;
    boolean isDead = false; // 유닛 생사유무
    String team;

    Unit05_23 targetEnemy = null; // 현재 공격대상
    
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
            targetEnemy.takeDamage(attackDamage);
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
  
//  충돌했는지 안했는지 트루,펄스 트루면 충돌 
  public boolean isColliding(Unit05_23 other) {
	    double dx = this.x - other.x;
	    double dy = this.y - other.y;
	    double distance = Math.sqrt(dx * dx + dy * dy);
	    double minDist = this.radius + other.radius;

	    return distance < minDist;
	}
//  유닛 충돌시 밀어내기
  public void resolveCollision(Unit05_23 other) {
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
//  가까운 적을 찾아내는 용도
  public Unit05_23 findNearestEnemy(List<Unit05_23> allUnits) {
	    Unit05_23 nearest = null;
	    double minDistance = Double.MAX_VALUE;

	    for (Unit05_23 other : allUnits) {
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



    public Unit05_23(double x, double y, String team) {
        this.x = x ;
        this.y = y ;
        this.team = team;
        this.targetX = x;
        this.targetY = y;
//        움직임 구현용 여러프레임 불러오기
        String[] dirs = { "up","down","left","right", "up_right",  "down_right",
        	     "down_left",  "up_left" };
        directionFrames = new BufferedImage[8][frameCount];

        for (int d = 0; d < 8; d++) {
            for (int f = 0; f < frameCount; f++) {
                try {
                    directionFrames[d][f] = ImageIO.read(getClass().getResource("/유닛기본모션/" + dirs[d] + "_" + f + ".png"));
                } catch (IOException | IllegalArgumentException e) {
                    System.out.println("로딩 실패: " + dirs[d] + "_" + f + ".png");
                }
            }
        }

    }

    public void setTarget(double tx, double ty) {
        this.targetX = tx;
        this.targetY = ty;
        this.hasArrived = false;
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
		Unit05_23 other = (Unit05_23) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
////팀선택 글자 같은 글자로 인식 오버라이딩@@@@@@@@@@
	
	
	
	public void update(List<Unit05_23> allUnits) {
    	if (isDead) return;
    	
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
    
    
    
    
    public void moveTowardTarget(List<Unit05_23> allUnits) {
  //목적지 근처 도착했으면 안움직이게 하기
	if (hasArrived) return; 
    double dx = targetX - x;
    double dy = targetY -  y;
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
        x = targetX;
        y = targetY;
        currentFrame = 0; 
        //        	도착시 달달 떨리는거 없애는 용도
    }
    
    for (Unit05_23 other : allUnits) {
        if (other != this && isColliding(other)&&distance<30) {
            hasArrived = true;
            return;
        }
    } // 도착지점 근처이며 유닛과 충돌했을경우 도착지점까지 도달안해도 완료된걸로 보고 멈추기 
    }


    public void draw(Graphics g) {
    	if (isDead) return; //죽었으면 안그리기,사라지기
        int dirIndex = direction.ordinal();
        BufferedImage frame = directionFrames[dirIndex][currentFrame];
        int drawX = (int)x;
        int drawY = (int)y+70;

        if (frame != null) {
            g.drawImage(frame, (int)x - frame.getWidth() / 2, (int)y - frame.getHeight() / 2, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillOval((int)x - 10, (int)y - 10, 20, 20);
        }
     // 체력바 그리기
        int barWidth = 50;
        int hpWidth = (int)((currentHP / (double)maxHP) * barWidth);
//        g.setColor(Color.RED);
//        g.fillRect(drawX - barWidth/2, drawY, barWidth, 5);
        
        g.setColor(Color.GREEN);
        g.fillRect(drawX - barWidth/2, drawY, hpWidth, 5);
        g.setColor(Color.BLACK);
        g.drawRect(drawX - barWidth/2, drawY, barWidth, 5);
        
        
        
        
        
        
    }
 

}
