package backup05_23;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener, MouseListener {
    Timer timer;
    List<Unit05_23> units = new ArrayList<>();
    List<Unit05_23> enemys = new ArrayList<>();
    List<Unit05_23> test = new ArrayList<>();
    List<Unit05_23> allUnits = new ArrayList<>(); //소환된 모든 진영유닛 한번에 선택
    
//  배경화면 삽입용@@@@@@@@@@@@@@@@@@@    
    private BufferedImage backgroundImage;
   
  
    public GamePanel() {
    	// 배경이미지 삽입용 링크걸기@@@@@@@@@@@@@@@@
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background.png")); // 이미지 경로는 실제 파일 위치에 맞게 변경
        } catch (IOException e) {
            e.printStackTrace();
        }  	
        setPreferredSize(new Dimension(800, 600));
//        setBackground(Color.blue); //(배경이미지 없을때 배경 단색으로 쓸 때)
        addMouseListener(this);
        
       units.add(new Unit05_23(100, 100,"1"));
       units.add(new Unit05_23(100, 110, "1"));
       units.add(new Unit05_23(100, 120, "1"));
       units.add(new Unit05_23(100, 130, "1"));
       enemys.add(new UnitEnemy(600, 400, "2"));
       enemys.add(new UnitEnemy(600, 400, "2"));
       enemys.add(new UnitEnemy(600, 400, "2"));
       test.add(new UnitEnemy(300,400,"3"));
       test.add(new UnitEnemy(300,400,"3"));
       test.add(new UnitEnemy(300,400,"3"));
       allUnits.addAll(units);
       allUnits.addAll(enemys);
       allUnits.addAll(test);
      
       
       
      
       
       
       timer = new Timer(16, this); // 약 60 FPS
       timer.start();
          
   
    }
    //화면 그리는 메소드
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 배경 이미지 그리기
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
       //유닛 그리기 
        for (Unit05_23 u : units) {
            u.draw(g);
        }
        for (Unit05_23 en : enemys) {
            en.draw(g);
        }
        for (Unit05_23 t : test) {
            t.draw(g);
        }
        
    }

    
    
    //케릭터 움직임 실시간 업데이트 메소드
    public void actionPerformed(ActionEvent e) {

        for (Unit05_23 t : allUnits) {
            t.update( allUnits);
            
        }
        
//        유닛충돌 해소@@@@@@@
        for (int i = 0; i < allUnits.size(); i++) {
            for (int j = i + 1; j < allUnits.size(); j++) {
                Unit05_23 u1 = allUnits.get(i);
                Unit05_23 u2 = allUnits.get(j);

                if (u1.isColliding(u2)) {
                    u1.resolveCollision(u2);
                }
            }
         }
//        유닛충돌 해소 @@@@@@
        
      
        repaint();
    }
//마우스 동작 메소드
    public void mouseClicked(MouseEvent e) {
        for (Unit05_23 u : units) {
            u.setTarget(e.getX(), e.getY());
        }
    }

    // 사용하지 않는 MouseListener 메소드
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
