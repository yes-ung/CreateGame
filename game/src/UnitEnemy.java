import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class UnitEnemy extends Unit {
	
	

	public UnitEnemy(double x, double y,String team) {
		super(x, y, team);
		this.speed =3;
		this.maxHP =1000;
    	this.currentHP =1000;
    	this.attackDamage=30;
		
	}
	
	   @Override
	  public void update(List<Unit> allUnits) {
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
	                
	            } else {
	            	// 타겟에게 다가감
	                setTarget(targetEnemy.x, targetEnemy.y);
	            }
	        }
	        moveTowardTarget(allUnits);
	    	
	               
	    } 


	@Override
	public void hpBar(Graphics g, int cameraX , int cameraY) {
    	// 체력바 그리기
    	int drawX = (int)x;
        int drawY = (int)y;
        int barWidth = 50;
        int hpWidth = (int)((currentHP / (double)maxHP) * barWidth);
     
        g.setColor(Color.RED);
        g.fillRect(drawX - barWidth/2 -cameraX, drawY+20-cameraY, hpWidth, 5);
        g.setColor(Color.BLACK);
        g.drawRect(drawX - barWidth/2 -cameraX, drawY+20-cameraY, barWidth, 5);
    	
    }
}
