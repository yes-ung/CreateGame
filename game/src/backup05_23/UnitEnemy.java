package backup05_23;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class UnitEnemy extends Unit05_23 {
	
	

	public UnitEnemy(double x, double y,String team) {
		super(x, y, team);
		
	}
	
	   @Override
	  public void update(List<Unit05_23> allUnits) {
	    	if (isDead) return;
	    	this.speed =3;
	    	this.maxHP =1000;
	    	this.currentHP =1000;
	    	this.attackDamage=1;
	    	
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
	                
	            } else {
	            	// 타겟에게 다가감
	                setTarget(targetEnemy.x, targetEnemy.y);
	            }
	        }
	        moveTowardTarget(allUnits);
	    	
	               
	    } 


	@Override
	public void draw(Graphics g) {
		if (isDead)
			return; // 죽었으면 안그리기,사라지기
		int dirIndex = direction.ordinal();
		BufferedImage frame = directionFrames[dirIndex][currentFrame];
		int drawX = (int) x;
		int drawY = (int) y + 70;

		if (frame != null) {
			g.drawImage(frame, (int) x - frame.getWidth() / 2, (int) y - frame.getHeight() / 2, null);
		} else {
			g.setColor(Color.GREEN);
			g.fillOval((int) x - 10, (int) y - 10, 20, 20);
		}
		// 체력바 그리기
		int barWidth = 50;
		int hpWidth = (int) ((currentHP / (double) maxHP) * barWidth);
//        g.setColor(Color.RED);
//        g.fillRect(drawX - barWidth/2, drawY, barWidth, 5);

		g.setColor(Color.red);
		g.fillRect(drawX - barWidth / 2, drawY, hpWidth, 5);
		g.setColor(Color.BLACK);
		g.drawRect(drawX - barWidth / 2, drawY, barWidth, 5);

	}
}
