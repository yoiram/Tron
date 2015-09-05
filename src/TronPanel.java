import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


/**
 * This class is a starter file for a "Tron LightCycle" game in which two players drive lightcycles in
 * an arena, leaving trails behind them as they go.  If a player bumps into one of these trails or
 * into a wall, the other player wins.  This game does not try to show the lightcycles themselves,
 * only the trails that are left.  In this starter program, there is just one lightcycle that leaves a
 * trail of random colors, and crossing a trail or hitting a wall has no effect.
 */
public class TronPanel extends JPanel implements FocusListener, KeyListener, ActionListener, MouseListener {

	private MosaicPanel arena;  // The arena in which the "lightcycles" move.  It consists of squares arranged
	                            // into rows and columns.
	private JPanel score;
	private JLabel scoreText;
	private JLabel scoreText2;
	private JLabel message;   // This label appears under the arena and holds a message for the user.

	private Timer timer;  // Whenever the arena has the input focus, this timer emits a stream of ActionEvents
	                      // that drive the action.  Everytime an ActionEvent occurs, the actionPerformed method
	                      // in this class is called.
	private int timeywimey;//sets the speed
	private String n1;//player 1 name
	private String n2;//player 2 name
	private int p1score=0;//player1 score (out of 3 rounds)
	private int p2score=0;//player2 score (out of 3 rounds)
	private int gameCount=0;//keeps count of the number of games
	private JButton nextRound;//sets the next round (out of 3)
	private JButton newGame;//starts a new game when a player wins
	private JButton exit;//quits the game
	private JButton easy;
	private JButton medium;
	private JButton difficult;
	private JLabel choose;
	private final static int ROWS = 60;         // Number of rows in the arena.
	private final static int COLUMNS = 80;      // Number of columns in the arena.
	private final static int BLOCKSIZE = 10;    // Size of each square in the arena-- used only in creating the arena object.
	private final static int BORDER_WIDTH = 5;  // The width of the colored border around the arena.
	
	private final static int UP = 0, LEFT = 1, DOWN = 2, RIGHT = 3, NOT_MOVING = 4; // Constants representing possible directions.
	private int direction, direction2;  // The direction that the "lightcycle" is currently heading, one of the above constants.

	//MARIO. I added in two new int variables that are the current column and current row of the second lightbike. I also added in another direction int.
	private int currentColumn, currentRow, currentColumn2, currentRow2; // The current row number and column number where the "lightcycle" is located.

	
	/**
	 * The "constructor" for the TronPanel class creates all the components that are
	 * displayed in the panel and adds them to the panel.  It also sets up listening
	 * for events.  The panel listens for mouse events from the label and from the
	 * arena, for focus events from the arena, for action events from the timer, and
	 * for key events from the arena.  (Thus, key events are only processed when the
	 * arena has the input focus.)
	 */
	public TronPanel() {
		arena = new MosaicPanel(ROWS, COLUMNS, BLOCKSIZE, BLOCKSIZE, Color.GRAY, BORDER_WIDTH);
		score = new JPanel(new GridBagLayout());
		score.setBackground(Color.BLACK);
		GridBagConstraints s = new GridBagConstraints();
		scoreText = new JLabel ();
		scoreText2 = new JLabel();
		choose = new JLabel("Choose the difficulty of the new game");
		choose.setForeground(Color.WHITE);
		scoreText2.setForeground(Color.WHITE);
		nextRound = new JButton("Continue to next round");
		  nextRound.addActionListener(new ActionListener() {

	            public void actionPerformed(ActionEvent e) {
	            	score.setVisible(false);
	            	message.setText("To begin click the arena");
	            }
	        });
		newGame = new JButton("Continue Playing? Start New Game");
			  newGame.addActionListener(new ActionListener() {

		            public void actionPerformed(ActionEvent e) {
		            	GridBagConstraints s = new GridBagConstraints();
		            	score.remove(exit);
		            	score.remove(newGame);
		            	s.gridx=0;
		            	s.gridy=3;
		            	score.add(choose, s);
		            	s.gridx=0;
		            	s.gridy=4;
		            	score.add(easy, s);
		            	s.gridx=0;
		            	s.gridy=5;
		            	score.add(medium, s);
		            	s.gridx=0;
		            	s.gridy=6;
		            	score.add(difficult, s);
		            	gameCount=0;
		            	p1score=0;
		            	p2score=0;
		            	message.setText("To begin click the arena");
		            }
		        });
		exit = new JButton("Quit Game");
			exit.addActionListener(new ActionListener() {

			       public void actionPerformed(ActionEvent e) {
			          System.exit(0);
			     }
			});
		s.gridx=0;
		s.gridy=0;
		score.add(scoreText, s);
		s.gridx=0;
		s.gridy=1;
		score.add(scoreText2, s);
		s.gridx=0;
		s.gridy=2;
		score.add(nextRound, s);
		score.setVisible(false);
		easy = new JButton ("EASY");
		easy.addActionListener(new ActionListener() {

		       public void actionPerformed(ActionEvent e) {
		    	  GridBagConstraints s = new GridBagConstraints();
		          timeywimey=1;
		          score.remove(easy);
		          score.remove(difficult);
		          score.remove(medium);
		          score.remove(choose);
		          s.gridx=0;
		          s.gridy=3;
		          score.add(nextRound, s);
		          score.setVisible(false);
		     }
		});
		medium = new JButton ("MEDIUM");
		medium.addActionListener(new ActionListener() {

		       public void actionPerformed(ActionEvent e) {
		          timeywimey=2;
		          GridBagConstraints s = new GridBagConstraints();
		          score.remove(easy);
		          score.remove(difficult);
		          score.remove(medium);
		          score.remove(choose);
		          s.gridx=0;
		          s.gridy=3;
		          score.add(nextRound, s);
		          score.setVisible(false);
		     }
		});
		difficult = new JButton ("DIFFICULT");
		difficult.addActionListener(new ActionListener() {

		       public void actionPerformed(ActionEvent e) {
		    	  GridBagConstraints s = new GridBagConstraints();
		          timeywimey=3;
		          score.remove(easy);
		          score.remove(difficult);
		          score.remove(medium);
		          score.remove(choose);
		          s.gridx=0;
		          s.gridy=3;
		          score.add(nextRound, s);
		          score.setVisible(false);
		     }
		});
		message = new JLabel("To Start, Click the Arena", JLabel.CENTER);
		message.setBackground(Color.LIGHT_GRAY);
		message.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout());
		bottom.setBackground(Color.LIGHT_GRAY);
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(3,3));
		bottom.add(message,BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		add(arena, BorderLayout.CENTER);
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
		arena.setGroutingColor(null);
		arena.addFocusListener(this);
		arena.addKeyListener(this);
		arena.addMouseListener(this);
		message.addMouseListener(this);
	}

	/**
	 * This method responds to the action events that are generated by the timer as
	 * long as the game is in progress.  It simply moves the "lightcycle" one square
	 * in the curretnly selected direction.  (It gets "stuck" in the same position
	 * when it hits one of the walls of the arena.)  The square containing the
	 * "lightcycle" is colored with a random color.  Note that if the current
	 * direction is NOT_MOVING, then the light cycle does not move.
	 */
	public void actionPerformed(ActionEvent e) {
		switch (direction) {
		case UP:
			if (currentRow > 0 && arena.getColor(currentRow-1, currentColumn) != Color.orange && arena.getColor(currentRow-1, currentColumn) != Color.cyan)
				currentRow--;    // Move up one row, unless already in the top row, number 0.
			else{
				message.requestFocus();
				p2score++;
				gameCount++;
				displayScore(n2);
			}
			break;
		case DOWN:
			if (currentRow < ROWS-1 && arena.getColor(currentRow+1, currentColumn) != Color.orange && arena.getColor(currentRow+1, currentColumn) != Color.cyan)
				currentRow++;   // Move down one row, unless already in the bottom row, number ROWS-1.
			else
			{
				message.requestFocus();
				p2score++;
				gameCount++;
				displayScore(n2);
			}
			break;
		case RIGHT:
			if (currentColumn < COLUMNS-1 && arena.getColor(currentRow, currentColumn+1) != Color.orange && arena.getColor(currentRow, currentColumn+1) != Color.cyan)
				currentColumn++;  // Move right one column, unless already in the rightmost column, number COLUMNS-1.
			else
			{
				message.requestFocus();
				p2score++;
				gameCount++;
				displayScore(n2);
			}
			break;
		case LEFT:
			if (currentColumn > 0 && arena.getColor(currentRow, currentColumn-1) != Color.orange && arena.getColor(currentRow, currentColumn-1) != Color.cyan)
				currentColumn--;  // Move left one column, unless already in the leftmost column, number 0.
			else
			{
				message.requestFocus();
				p2score++;
				gameCount++;
				displayScore(n2);
			}
			break;
		}
		arena.setColor(currentRow, currentColumn, Color.cyan);
		
		//MARIO. I changed the colour of the first lightbike so that its color would only be cyan. Below are the actions to move the second light bike that is orange.
		//MARIO. I chose orange and cyan because those are the original tron colors, but feel free to change it to whatever.
		switch (direction2) {
		case UP:
			if (currentRow2 > 0 && arena.getColor(currentRow2-1, currentColumn2) != Color.orange && arena.getColor(currentRow2-1, currentColumn2) != Color.cyan)
				currentRow2--;    // Move up one row, unless already in the top row, number 0.
			else
			{
				message.requestFocus();
				p1score++;
				gameCount++;
				displayScore(n1);
			}
			break;
		case DOWN:
			if (currentRow2 < ROWS-1 && arena.getColor(currentRow2+1, currentColumn2) != Color.orange && arena.getColor(currentRow2+1, currentColumn2) != Color.cyan)
				currentRow2++;   // Move down one row, unless already in the bottom row, number ROWS-1.
			else
			{
				message.requestFocus();
				p1score++;
				gameCount++;
				displayScore(n1);
			}
			break;
		case RIGHT:
			if (currentColumn2 < COLUMNS-1 && arena.getColor(currentRow2, currentColumn2+1) != Color.orange && arena.getColor(currentRow2, currentColumn2+1) != Color.cyan)
				currentColumn2++;  // Move right one column, unless already in the rightmost column, number COLUMNS-1.
			else
			{
				message.requestFocus();
				p1score++;
				gameCount++;
				displayScore(n1);
			}
			break;
		case LEFT:
			if (currentColumn2 > 0 && arena.getColor(currentRow2, currentColumn2-1) != Color.orange && arena.getColor(currentRow2, currentColumn2-1) != Color.cyan)
				currentColumn2--;  // Move left one column, unless already in the leftmost column, number 0.
			else
			{
				message.requestFocus();
				p1score++;
				gameCount++;
				displayScore(n1);
			}
			break;
		}
		arena.setColor(currentRow2, currentColumn2, Color.orange);
	}

	/**
	 * This method is called when the user presses a key on the keyboard (but only if the
	 * arena has the input focus).  If the user presses an arrow key, the current direction
	 * of motion of the light cycle is set to the direction that corresponds to the arrow
	 * (up, down, left, or right).  If the "P" key is pressed, the game is paused (by giving
	 * the input focus to the message component, which indirectly stops the game from running).
s	 */
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();  // This code tells which key was pressed.  The value is one of the 
		                            // virtual keyboard ("VK") constants in the KeyEvent class.
		if (code == KeyEvent.VK_LEFT)
			direction = LEFT;
		else if (code == KeyEvent.VK_RIGHT)
			direction = RIGHT;
		else if (code == KeyEvent.VK_UP)
			direction = UP;
		else if (code == KeyEvent.VK_DOWN)
			direction = DOWN;
		else if (code == KeyEvent.VK_P)
			message.requestFocus();
	
		//MARIO. These are the keyevents for the second player incase WASD are pressed.
		else if (code == KeyEvent.VK_A)
			direction2 = LEFT;
		else if (code == KeyEvent.VK_D)
			direction2 = RIGHT;
		else if (code == KeyEvent.VK_W)
			direction2 = UP;
		else if (code == KeyEvent.VK_S)
			direction2 = DOWN;
	}

	/**
	 * This method is called when the arena gains focus, which means that it will start
	 * receiving Key events.  When this happens, the game action is turned on (by creating
	 * a timer to drive the game), the color of the arena's border is changed to cyan,
	 * and the text of the message is changed.
	 */
	public void focusGained(FocusEvent e) {
		arena.setBorder(BorderFactory.createLineBorder(Color.CYAN, BORDER_WIDTH));
		arena.fill(null);  // This resets all the squares to black to erase the picture from the previous run.
		currentColumn = COLUMNS/4*3;  // Starting column, 3/4 of the way across the screen.
		currentRow = ROWS/2;          // Starting row, halfway down the screen
		direction = UP;  // sets the direction of the first player to UP
		message.setText(n1+" score: "+p1score+"   "+n2+" score: "+p2score);
		//MARIO. This creates a second lightbike.
		currentColumn2 = COLUMNS/4;
		currentRow2 = ROWS/2;
		direction2 = UP;  // sets the direction of the second player to UP

		
		//Creates options of how quickly the bikes move to allow for variation in difficulty
		if(timeywimey==2)
		timer = new Timer(50,this); // timer generates an ActionEvent every 50 milliseconds
		else if(timeywimey==3)
			timer = new Timer (25, this);
		else if (timeywimey==1)
			timer = new Timer (100, this);
		timer.start();
	}

	/**
	 * This method is called when the arena loses focus, which means that it will not
	 * receive Key events.  When this happens, the game is suspended (by turning off 
	 * the timer that drives the game), the color of the arena's border is changed to
	 * gray, and the text of the message is changed.
	 */
	public void focusLost(FocusEvent e) {
		arena.setBorder(BorderFactory.createLineBorder(Color.GRAY, BORDER_WIDTH));
		if (timer != null)
			timer.stop();
		timer = null;
		if(gameCount==0)
			message.setText("To START, Click the Arena");
		else
			message.setText("");
	}

	/**
	 * Mouse clicks on the arena and on the message are simply used to move the focus to
	 * the component that was clicked.  (The point of giving the focus to the message is
	 * simply to make the arena lose focus, which causes the game to be paused.)
	 */
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == arena&&gameCount<3&&!score.isVisible())
			arena.requestFocus();
		else
			message.requestFocus();
	}

	
	// The following subroutines are required by the "KeyListener" and "MouseListener" interfaces,
	// but are not used in this program.  They are defined as subrotines with empty bodies, which
	// means that they do nothing.
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	public void setTimer(int t){
		timeywimey=t;
	}
	
	public void setPlayer1(String n){
		n1=n;
	}
	public void setPlayer2(String n){
		n2=n;
	}
	public void displayScore(String n){
		GridBagConstraints s = new GridBagConstraints();
		if (gameCount==3){
			score.remove(nextRound);
			s.gridx=0;
			s.gridy=3;
			score.add(newGame,s);
			s.gridx=0;
			s.gridy=4;
			score.add(exit,s);
			if(p1score>p2score){
				scoreText.setText("Congratulations "+n1+" you have defeated "+n2+" !");
				scoreText.setForeground(Color.CYAN);
				scoreText2.setText("Final score: "+n1+" score: "+p1score+"  "+n2+" score: "+p2score);
				score.setVisible(true);
				arena.add(score);
			}
			else if (p1score<p2score){
				scoreText.setText("Congratulations "+n2+" you have defeated "+n1+" !");
				scoreText.setForeground(Color.ORANGE);
				scoreText2.setText("Final score: "+n1+" score: "+p1score+"  "+n2+" score: "+p2score);
				score.setVisible(true);
				arena.add(score);
			}
			else if (p1score==p2score){
				scoreText.setText("Congratulations it's a tie!");
				scoreText.setForeground(Color.WHITE);
				scoreText2.setText("Final score: "+n1+" score: "+p1score+"  "+n2+" score: "+p2score);
				score.setVisible(true);
				arena.add(score);
			}
	
		}
		else if (gameCount==2){
			if(p1score>p2score)	{
				score.remove(nextRound);
				s.gridx=0;
				s.gridy=3;
				score.add(newGame, s);
				s.gridx=0;
				s.gridy=4;
				score.add(exit, s);
				scoreText.setText("Congratulations "+n1+" you have defeated "+n2+" !");
				scoreText.setForeground(Color.CYAN);
				scoreText2.setText("Final score: "+n1+" score: "+p1score+"  "+n2+" score: "+p2score);
				score.setVisible(true);
				arena.add(score);
			}
			if(p2score>p1score){
				score.remove(nextRound);
				s.gridx=0;
				s.gridy=3;
				score.add(newGame, s);
				s.gridx=0;
				s.gridy=4;
				score.add(exit, s);
				scoreText.setText("Congratulations "+n2+" you have defeated "+n1+" !");
				scoreText.setForeground(Color.ORANGE);
				scoreText2.setText("Final score: "+n1+" score: "+p1score+"  "+n2+" score: "+p2score);
				score.setVisible(true);
				arena.add(score);}
			 if (p1score==p2score){
				 scoreText.setText(n+" has won this round! ");
				 scoreText.setForeground(Color.WHITE);
				 scoreText2.setText(n1+" score: "+p1score+"   "+n2+" score: "+p2score);
				 score.setVisible(true);
				 arena.add(score);
				}
		}
		else if (gameCount==1){
			scoreText.setText(n+" has won this round! ");
			scoreText.setForeground(Color.WHITE);
			scoreText2.setText(n1+" score: "+p1score+"   "+n2+" score: "+p2score);
			score.setVisible(true);
			arena.add(score);
		}

		
	}
} // end class TronPanel


