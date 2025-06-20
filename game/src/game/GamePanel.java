package game;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	Timer timer;	
	
	//마우스 드래그용
	int dragStartX, dragStartY;
	int dragEndX, dragEndY;
	int currentMouseX, currentMouseY;
	boolean dragging = false;
	//화면이동용
	int cameraX = 0;
	int cameraY = 0;
	int cameraSpeed = 10;
	int mouseX = 0;
	int mouseY = 0;
	int worldClickX = 0;
	int worldClickY = 0;
	public static final int MAP_WIDTH = 2048;  //맵 최대넓이
	public static final int MAP_HEIGHT = 2048; // 맵 최대높이	
	
	int border = 20; // 경계 감지 거리	
	Dimension screenSize = getSize(); // JPanel 크기
	int screenSizeWidth =0;
	int screenSizeHight =0;
	// 카메라 제한 (맵 최대크기)
	
	
	
	
	
	
	
	Building selectedBuilding = null; // 선택된 건물

	// 버튼 위치 (화면 고정)
	final Rectangle buttonRect = new Rectangle(610, 450, 100, 40); // x, y, width, height

	
	
	
	
	
    //유닛 및 건물 선언
    List<Unit> userUnits = new LinkedList<>();
    List<Unit> enemys = new LinkedList<>();
    List<Unit> test = new LinkedList<>();
    List<Unit> allUnits = new LinkedList<>(); //소환된 모든 진영유닛 한번에 선택
    List<Building> buildings = new ArrayList<>(); //생산건물 
    
//  배경화면 삽입용@@@@@@@@@@@@@@@@@@@    
    private BufferedImage backgroundImage;
   
  
    public GamePanel() {
    	// 배경이미지 삽입용 링크걸기@@@@@@@@@@@@@@@@
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background.png")); // 이미지 경로는 실제 파일 위치에 맞게 변경
        } catch (IOException e) {
            e.printStackTrace();
        }
        //게임창 크기
        setPreferredSize(new Dimension(800, 600));
//        setBackground(Color.blue); //(배경이미지 없을때 배경 단색으로 쓸 때)
        //마우스 액션 
        addMouseListener(this);
        addMouseMotionListener(this);
       
       buildings.add(new Building(200, 200, "1"));
       buildings.add(new MarinBuilding(500, 200, "1"));
       userUnits.add(new Unit_Marin(500, 500,"1"));

//       enemys.add(new UnitEnemy(600, 400, "2"));
//       enemys.add(new UnitEnemy(600, 400, "2"));
//       enemys.add(new UnitEnemy(600, 400, "2"));
//       enemys.add(new UnitEnemy(300,400,"2"));
//       test.add(new UnitEnemy(300,400,"3"));
//       test.add(new UnitEnemy(300,400,"3"));
       allUnits.addAll(userUnits);
       allUnits.addAll(enemys);
       allUnits.addAll(test);

       
      
       
       
      
       
       
       timer = new Timer(16, this); // 약 60 FPS // 시간마다 actionPerformed 를 실행
       timer.start();
          
   
    }
    //화면 그리는 메소드
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
      //앞쪽에 있는 이미지 앞에 출력되도록 정렬
        allUnits.sort(Comparator.comparingDouble(s -> s.getY())); 
        // 배경 이미지 그리기
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, -cameraX, -cameraY, null);
        }
       //유닛 그리기      
        for (Unit u : allUnits) {
            u.draw(g,cameraX,cameraY);
        }

        
        //건물 그리기
        for (Building u : buildings) {
            u.draw(g,cameraX,cameraY);
        }
        //마우스 드래그 그리기
        if (dragging) {
            int left = Math.min(dragStartX, currentMouseX);
            int top = Math.min(dragStartY, currentMouseY);
            int width = Math.abs(currentMouseX - dragStartX);
            int height = Math.abs(currentMouseY - dragStartY);

            g.setColor(new Color(0, 255, 255, 100));
            g.fillRect(left, top, width, height);
            g.setColor(Color.CYAN);
            g.drawRect(left, top, width, height);
        }
        //화면에 고정된 UI칸
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(screenSizeWidth-200, screenSizeHight-200, 200, 200);
        g.setColor(Color.BLACK);
        g.drawRect(screenSizeWidth-200, screenSizeHight-200, 200, 200);
        //UI칸 내용물 그리기
        if (selectedBuilding != null) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);
            g.setColor(Color.BLACK);
            g.drawRect(buttonRect.x, buttonRect.y, buttonRect.width, buttonRect.height);
            g.drawString("유닛 생산", buttonRect.x + 15, buttonRect.y + 25);
        }
        
        
        
        
    }

    
    
    //케릭터 움직임 실시간 업데이트 메소드
    @Override
    public void actionPerformed(ActionEvent e) {

        for (Unit t : allUnits) {
            t.update( allUnits);
            t.resolveOverlap(allUnits); // 겹쳤을 경우 처리
         }
  
        
        for (Building b : buildings) {
            Unit produced = b.update(userUnits);
            
            if (produced != null) {
                System.out.println("유닛 생산됨!");
                allUnits.add(produced);
            }
        }
        //지도에 건물 좌표인식 (장애물로 인식)
        for (Building b : buildings) {
            int tx = (b.x/GameConstants.TILE_SIZE) ;
            int ty = (b.y/GameConstants.TILE_SIZE) ;
            GameConstants.MAP[tx][ty] = 1;
        }
     // 일시적으로 유닛을 길찾기 방해물로 취급
//        for (Unit u : allUnits) {
//            int tx = (int) u.x;
//            int ty = (int) u.y;
//
//            for (int dx = (int)-u.range/2; dx < u.range/2; dx++) {
//                for (int dy = (int)-u.range/4; dy < u.range/4; dy++) {
//                    int mx = tx + dx;
//                    int my = ty + dy;
//                    if (isInBounds(mx, my, map)) {
//                        map[mx][my] = 1;
//                    }
//                }
//            }
//        }
        
//        유닛충돌 해소
        for (int i = 0; i < allUnits.size(); i++) {
            for (int j = i + 1; j < allUnits.size(); j++) {
                Unit u1 = allUnits.get(i);
                Unit u2 = allUnits.get(j);

                if (u1.isColliding(u2)) {
                    u1.resolveCollision(u2);
                    u1.isCollidingWaitFrames =0;
                }
            }
         }
//        카메라 화면전환   
        int border = 20; // 경계 감지 거리	
    	Dimension screenSize = getSize(); // JPanel 크기 브라우저 창
    	screenSizeWidth= screenSize.width;  // 브라우저 현재 크기 저장
    	screenSizeHight = screenSize.height; // 브라우저 현재 크기 저장
    	
        if (mouseX < border) {
            cameraX -= cameraSpeed;
            System.out.println(cameraX);
        }
        if (mouseX > screenSize.width - border) {
            cameraX += cameraSpeed;
            System.out.println(cameraX);
        }
        if (mouseY < border) {
            cameraY -= cameraSpeed;
            System.out.println(cameraY);
            
        }
        if (mouseY > screenSize.height - border) {
            cameraY += cameraSpeed;
            System.out.println(cameraY);
        }
        cameraX = Math.max(0, Math.min(cameraX, MAP_WIDTH - screenSize.width));
    	cameraY = Math.max(0, Math.min(cameraY, MAP_HEIGHT - screenSize.height));
      
        repaint();
    }
    boolean isInBounds(int x, int y, int[][] map) {
        return x >= 0 && y >= 0 && x < map.length && y < map[0].length;
    }

//마우스 동작 메소드
    @Override
    public void mouseClicked(MouseEvent e) {
    	        
    	worldClickX = e.getX() + cameraX;
    	worldClickY = e.getY() + cameraY;
        handleRightClick(worldClickX, worldClickY,allUnits);
        //버벅여서 단일 유닛 선택 임시로 사용안함
//        handleLeftClick(worldClickX, worldClickY,allUnits);
        
        // 버튼 클릭 처리
        if (selectedBuilding != null && buttonRect.contains(e.getX(), e.getY())) {
            selectedBuilding.startBuilding();
        }
       
        
    }
    @Override
    public void mouseDragged(MouseEvent e) {
    	worldClickX = e.getX() + cameraX;
    	worldClickY = e.getY() + cameraY;
        if (dragging) {
            currentMouseX = e.getX();
            currentMouseY = e.getY();
            System.out.println(e.getX()+"+"+e.getY());
            repaint();
            
        }
    }
    @Override     //BUTTON1=왼쪽 2= 휠 3= 오른쪽
    public void mousePressed(MouseEvent e) {
    	worldClickX = e.getX() + cameraX;
    	worldClickY = e.getY() + cameraY;
    	//버튼이 아닌곳 누르면 건물선택 취소
    	if (selectedBuilding != null && buttonRect.contains(e.getX(), e.getY())) {}
    	else if(selectedBuilding != null) {selectedBuilding.isSelected=false; selectedBuilding = null;}
    	else {selectedBuilding = null;}
 
        
       //건물 클릭 건물선택됨
        for (Building b : buildings) {
            if (b.contains(worldClickX, worldClickY)) {
                if (b.team == "1") {
                    selectedBuilding = b;
                    b.isSelected = true;
                    deselectAll(allUnits);
                    break;
                }
            }
        } 
        
    	//마우스 드래그
        if (e.getButton() == MouseEvent.BUTTON1) {
            dragStartX = e.getX();
            dragStartY = e.getY();
            currentMouseX = dragStartX;
            currentMouseY = dragStartY;
            dragging = true;
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    	worldClickX = e.getX() + cameraX;
    	worldClickY = e.getY() + cameraY;

        if (e.getButton() == MouseEvent.BUTTON1 && dragging) {
            dragging = false;
            dragEndX = e.getX();
            dragEndY = e.getY();
            reSelectUnitsInBox(dragStartX + cameraX, dragStartY + cameraY, dragEndX + cameraX, dragEndY + cameraY, allUnits);
            selectUnitsInBox(dragStartX + cameraX, dragStartY + cameraY, dragEndX + cameraX, dragEndY + cameraY, allUnits);
        }
    }
   // 드래그 범위 내 유닛 선택
    public void selectUnitsInBox(int x1, int y1, int x2, int y2, List<Unit> units) {
    	
    	
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);

        for (Unit u : units) {
            if (u.team == "1" && u.x >= left && u.x <= right && u.y >= top && u.y <= bottom) {
                u.isSelected = true;
            }
        }
    }
    //드래그 범위 내 유닛 재선택
    public void reSelectUnitsInBox(int x1, int y1, int x2, int y2, List<Unit> units) {
    	
    	
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);

        for (Unit u : units) {
            if (u.team == "1" && u.x >= left && u.x <= right && u.y >= top && u.y <= bottom) {
            	deselectAll(allUnits);
            }
        }
    }
    //유닛 개별 마우스 선택
    public void handleLeftClick(int mx, int my, List<Unit> allUnits) {
        for (Unit u : allUnits) {
            if (u.team == "1" && isPointInsideUnit(mx, my, u)) {
                deselectAll(allUnits);
                u.isSelected = true;
                return;
            }
        }
        
    }
    public boolean isPointInsideUnit(int px, int py, Unit u) {
        int radius = 15;
        return Math.abs(u.x - px) < radius && Math.abs(u.y - py) < radius;
    }
    public void deselectAll(List<Unit> allUnits) {
        for (Unit u : allUnits) {
            u.isSelected = false;
        }
    }    
    public void handleRightClick(int mx, int my, List<Unit> allUnits) {
        // 적 유닛인지 확인
        for (Unit u : allUnits) {
            if (u.team != "1" && isPointInsideUnit(mx, my, u)) {
                // 공격 명령
                for (Unit myUnit : allUnits) {
                    if (myUnit.team == "1" && myUnit.isSelected) {
                        myUnit.targetEnemy = u;
                    }
                }
                return;
            }
        }

        // 이동 명령
        for (Unit myUnit : allUnits) {
            if (myUnit.team == "1" && myUnit.isSelected) {
                myUnit.setTarget(mx, my);
                myUnit.setPath(AStarPathfinding.aStar(GameConstants.MAP, (int)myUnit.x/GameConstants.TILE_SIZE,
			 (int)myUnit.y/GameConstants.TILE_SIZE, (int)mx/GameConstants.TILE_SIZE, (int)my/GameConstants.TILE_SIZE));
                myUnit.targetEnemy = null;
            }
        }
    }
    //건물 클릭 유닛 생성
    public void handleMouseClick(int mx, int my) {
        for (Building b : buildings) {
            if (Math.abs(b.x - mx) < 40 && Math.abs(b.y - my) < 40) {
                b.startBuilding();
                
                break;
            }
        }
    }
    public void mouseMoved(MouseEvent e) {
    	mouseX = e.getX();
        mouseY = e.getY();
    }

    // 사용하지 않는 MouseListener 메소드
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
