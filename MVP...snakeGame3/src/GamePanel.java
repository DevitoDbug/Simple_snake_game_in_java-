import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener{

	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT= 600;
	static final int UNIT_SIZE = 10;//every grid unit can be said to be a 10 by 10 unit
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;//we have taken the area of the screen divide by the 
	static final int DELAY = 75;										//unit size within the game 
	final int x[] = new int [GAME_UNITS];//the x and y show rep the coordinates  of the body parts of the snake
	final int y[]= new int [GAME_UNITS];
	int bodyParts = 6;//Initial number of body parts of the snake  //we can also say that this rep the number of rectangles filled with color 
	int applesEaten ;
	int appleX;//coordinates for the positioning off the apple 
	int appleY;
	char dirrection = 'R';//the snake begins by going right 
	boolean running = false ;
	Timer timer;
	Random random ;
	boolean pause = true;
	

	GamePanel()
	{
		random = new Random();
		this.setPreferredSize(new Dimension (SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		pauseGame();
		
	}
	public void pauseGame()
	{
		if (pause == true )
		{
			 pause = false;
			timer.start();
		}
		else
		{
			pause = true;
			timer.stop();
		}
		
	}
	public void startGame()
	{
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);//this made the panel turn black 
		draw(g);
		
	}
	public void draw(Graphics g)
	{
		
		if (running) {	
			for(int i= 0 ; i < SCREEN_HEIGHT/UNIT_SIZE;i++)
			{
				//g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE,SCREEN_HEIGHT);//draws the grid lines along the y axis i.e the vertical lines of the grid 
				//g.drawLine( 0, i*UNIT_SIZE ,SCREEN_WIDTH ,i*UNIT_SIZE );
	
			}
			//drawing the apple in the game 
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//drawing the snake in the game 
			//we draw the game by filling the rectangles that rep the body, with color 
			for (int i = 0; i < bodyParts ; i++)
			{
				if (i == 0 )//checking to see if we are dealing with the head of the snake 
				{
					g.setColor(Color.BLUE);
					g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else//checking to see if we are dealing with the body of the snake 
				{
					g.setColor(Color.MAGENTA);
					g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
				}
				
			}
			g.setColor(Color.blue);
			g.setFont(new Font("Times New Roman", Font.BOLD, 25));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("To pause press enter ",25,25);
			
		}
		else
		{
			gameOver(g);
		}
	}
	public void newApple()
	{
		appleX = random.nextInt((int)SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;//we are using the grids as the reference..... mapping the apples into the coordinates we drew 
		appleY = random.nextInt((int)SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
	}
	public void move()
	{
		//the code below moves the rest of the body of the snake
		for (int i = bodyParts ; i>0 ; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
			//the code below moves the head of the snake that is why the index of the array stays at zero
			//our array is originally empty of any values but the switch block below populates it with values 
			//by empty we mean it contained zeros only 
			switch (dirrection)
			{
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
				
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
				
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
				
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
				
			
			}
			
		
		
	}
	public void checkApple()
	{
		if ((x[0] == appleX )&& (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
		
	}
	public void checkCollisions()
	{
		for (int  i = bodyParts ; i >0 ; i--)
		{
			if ( (x[0] == x[i]) && (y[0] == y[i]))//collision with the body....head collides with the body 
			{
				running = false;
			}
			
		}
		//check if the head collides with the left border
		if (x[0] < 0)
		{
			running = false;
			
		}
		
		//checking to see if the head collides with the right border
		
		if (x[0] > SCREEN_WIDTH)
		{
			running = false;
		}
		
		//checking to see if the head of the snake collides with the top
		if (y[0] < 0)
		{
			running = false;
		}
		
		//checking to see if the head of the snake collides wih the bottom of the screen
		if (y[0]> SCREEN_HEIGHT)
		{
			running = false;
		}
		
		if (running == false)
		{
			timer.stop();
		}
		
		
	}
	public void gameOver(Graphics g)
	{
		//game over text 
		g.setColor(Color.red);
		g.setFont(new Font("Times New Roman", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over Nigga", (SCREEN_WIDTH - metrics.stringWidth("Game Over Nigga"))/2, SCREEN_HEIGHT /2);
		
		//
		g.setColor(Color.blue);
		g.setFont(new Font("Times New Roman", Font.BOLD, 35));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("SCORE: "+ applesEaten, (SCREEN_WIDTH - metrics2.stringWidth(("SCORE: "+ applesEaten)))/2,100);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (running)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (dirrection !='R')//this is used to make sure that the snake does not do a 360 trun
				{
					dirrection = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (dirrection !='L')
				{
					dirrection = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (dirrection !='D')
				{
					dirrection = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (dirrection !='U')
				{
					dirrection = 'D';
				}
				break;
			case KeyEvent.VK_ENTER:
				pauseGame();
				
			break;
			
		
			}
		}		
	}

}
