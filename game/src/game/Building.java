package game;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Building {
	int x, y, width = 200, height = 200;
	double radius =100;// 건물 범위
    String team = "1";
    BufferedImage sprite;
    int buildCooldown = 10; // 생산 시간 (프레임 단위)
    int buildTimer = 0;
    int maxHP = 100; //건물 최대체력설정
    int currentHP = 100;  // 건물 현재체력설정
    boolean buildingUnit = false;
    boolean isSelected = false; //건물선택 유무

    //건물 클릭범위 지정
    public boolean contains(int px, int py) {
        return px >= x-(width/2) && px <= x + (width/2) && py >= y-(height/2) && py <= y + (height/2);
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
		Building other = (Building) obj;
		if (team == null) {
			if (other.team != null)
				return false;
		} else if (!team.equals(other.team))
			return false;
		return true;
	}
////팀선택 글자 같은 글자로 인식 오버라이딩@@@@@@@@@@

	public Building(int x, int y, String team) {
        this.x = x;
        this.y = y;
        this.team = team;

        try {
            sprite = ImageIO.read(getClass().getResource("/건물1.png"));
        } catch (IOException e) {
            System.out.println("건물 이미지 로딩 실패");
        }
    }
    //유닛생산시작
    public void startBuilding() {
        if (!buildingUnit) {
            buildingUnit = true;
            buildTimer = buildCooldown;
        }
    }

    public Unit update(List<Unit> buildUnit) {
        if (buildingUnit) {
            buildTimer--;
            if (buildTimer <= 0) {
                buildingUnit = false;

                // 유닛 생성
                Unit newUnit = new Unit(x + 40, y, team); // 옆에 배치
                buildUnit.add(newUnit);
                return newUnit;
            }
        }
        return null;
    }
    
    

    public void draw(Graphics g, int cameraX , int cameraY) {
        if (sprite != null) {
            g.drawImage(sprite, x - sprite.getWidth()/2 -cameraX, y - sprite.getHeight()+(int)radius -cameraY, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(x - 20, y - 20, 40, 40);
        }
        hpBar(g,cameraX,cameraY);
        if (isSelected) {
            g.setColor(Color.GREEN);
            g.drawOval((int)x-(int)radius -cameraX, (int)y-(int)radius -cameraY, width, height);
        }
    }
    public void hpBar(Graphics g, int cameraX , int cameraY) {
    	// 체력바 그리기
    	int drawX = (int)x;
        int drawY = (int)y;
        int barWidth = 100;
        int hpWidth = (int)((currentHP / (double)maxHP) * barWidth);
     
        g.setColor(Color.GREEN);
        g.fillRect(drawX - barWidth/2 -cameraX, drawY+(int)radius -cameraY, hpWidth, 5);
        g.setColor(Color.BLACK);
        g.drawRect(drawX - barWidth/2 -cameraX, drawY+(int)radius -cameraY, barWidth, 5);
    	
    }
}
