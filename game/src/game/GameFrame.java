package game;
import javax.swing.*;

public class GameFrame extends JFrame {
	public GameFrame() {
		setTitle("예태게임-테스트중");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		add(new GamePanel());
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
}
