import javax.swing.JFrame;

public class GameFrame extends JFrame{

	public GameFrame() {
		
		//we have added the instance of the GamePanel class to the JFrame of this class
		this.add(new GamePanel());
		this.setTitle("Simple Game in java");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	

}
