/*This class creates a JFrame using a Card Layout
 * The first panel (Start up screen) take in player names, and allows the player to select a level of difficulty
 * The second Panel is the Tron Panel which runs the game.
 * Help with the CardLayout was taken from http://stackoverflow.com/questions/10823382/how-to-show-different-cards-in-a-cardlayout
 * the main method is from http://math.hws.edu/eck/cs124/s06/lab8/
 * images are from http://wallpaperhdfree.com/wp-content/ and
 * http://rowlandnicholas.com/?page_id=390
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.applet.*;
import java.net.*;
import javax.swing.*;
import java.io.*;

public class Tron extends JFrame {

	private static final long serialVersionUID = 1L;
    private JPanel cardPanel, buttonPanel;
    private TronPanel jp2;//game panel
    private JPanel jp1;//start screen
	private ImageIcon ic;//logo
	private ImageIcon ic2;//tiny logo
	private JLabel pic;//holds the logo
	private JLabel pic2;//holds the tiny logo
    private CardLayout cardLayout = new CardLayout();
	private JLabel p1;//instructions for players
	private JLabel p2;
	private JLabel select;//instructions to select a difficulty
	private JButton easy;//buttons for levels of difficulty
	private JButton medium;
	private JButton difficult;
	private String name1;//holds player names
	private String name2;
	private JTextField n1;//area to take in names
	private JTextField n2;
	private JTextArea nn1;//displayes submitted names
	private JTextArea nn2;
	private JLabel nn11;//enter your name
	private JLabel nn22;
	private AudioClip audio;
	private JRadioButton on;
	private JRadioButton off;

//constructor
    public Tron() {
    	cardPanel = new JPanel();//creates the outside panel
    	cardPanel.setBackground(Color.BLACK);//sets background color to black
        buttonPanel = new JPanel();//creates bottom button panel
        cardPanel.setLayout(cardLayout);//sets the layout
        jp1 = new JPanel(new GridBagLayout());//sets the layout for the first panel
		ic = new ImageIcon("tester.png");//adds intro panel image
		pic = new JLabel("", ic, JLabel.CENTER);
		ic2 = new ImageIcon("tinytron.jpg");//adds second panel image
		pic2 = new JLabel("", ic2, JLabel.CENTER);
		n1 = new JTextField(10);//text field for player 1 name
		nn1 = new JTextArea();//displays player1 name
		nn1.setBackground(Color.BLACK);//sets background color of text area
		nn1.setForeground(Color.CYAN);//sets text color of text area
		nn2 = new JTextArea();//second text area
		nn2.setBackground(Color.BLACK);//background of text area 2
		nn2.setForeground(Color.ORANGE);//text color of text area 2
		nn11 = new JLabel ("Type your name and press ENTER");//instructions to type name
		nn11.setForeground(Color.CYAN);//sets text color for JLabel
		nn22 = new JLabel("Type your name and press ENTER");
		nn22.setForeground(Color.ORANGE);//sets text color for player 2 instructions
		n2 = new JTextField(10);//text field for player 2 name
		jp1.setBackground(Color.BLACK);//sets background color of panel to black
		GridBagConstraints c = new GridBagConstraints();//creates constraints on the gridbaglayout
		c.gridx=0;//coordinates for component
		c.gridy=0;
		jp1.add(pic, c);//adds picture to panel 1 in specified coordinates
		p1 = new JLabel("Player 1: Move your bike with the UP, DOWN, LEFT and RIGHT arrow keys");
		p1.setForeground(Color.CYAN);//sets text color 
		p2 = new JLabel("Player 2: Move your bike with the W, S, A, D keys");
		p2.setForeground(Color.ORANGE);//sets text color
		c.gridx=0;//coordinates
		c.gridy=1;
		c.insets = new Insets(20,0,0,0);//vertical padding
		jp1.add(p1, c);
		c.gridx=0;
		c.gridy=2;
		c.insets = new Insets(0,0,0,0);
		jp1.add(nn11,c);
		c.gridx=0;
		c.gridy=3;
		jp1.add(n1,c);
		c.gridx=0;
		c.gridy=4;
		jp1.add(nn1,c);
		c.gridx=0;
		c.gridy=5;
		c.insets = new Insets(20,0,0,0);
		jp1.add(p2, c);
		c.gridx=0;
		c.gridy=6;
		c.insets = new Insets(0,0,0,0);
		jp1.add(nn22,c);
		c.gridx=0;
		c.gridy=7;
		jp1.add(n2,c);
		c.gridx=0;
		c.gridy=8;
		jp1.add(nn2,c);
		select = new JLabel("Select the difficulty");//label for button panel
		select.setForeground(Color.WHITE);//text color to white
        jp2 = new TronPanel();
        jp2.setBackground(Color.BLACK);//background color to black
        //adds audio
        try{
        	File song = new File("Derezzed.wav");
        	audio = Applet.newAudioClip(new URL("file://"+song.getAbsolutePath()));
        	audio.loop();
        }
        catch(MalformedURLException e){
        	System.exit(0);
        }
   
        //creates buttons for audio
        on = new JRadioButton("Music on");
        on.setBackground(Color.BLACK);
        on.setForeground(Color.WHITE);
        off = new JRadioButton("Music off");
        off.setBackground(Color.BLACK);
        off.setForeground(Color.WHITE);
        ButtonGroup group = new ButtonGroup();
        group.add(on);
        group.add(off);
        on.setVisible(false);
        //turns audio on
        on.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	audio.loop();
            	off.setVisible(true);
            	on.setVisible(false);
            }
        });
        //turns audio off
        off.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
            	audio.stop();
            	off.setVisible(false);
            	on.setVisible(true);
            }
        });
 
        cardPanel.add(jp1, "1");
        cardPanel.add(jp2, "2");
        cardPanel.setBackground(Color.BLACK);
        buttonPanel.setBackground(Color.BLACK);
        easy = new JButton("EASY");
        medium = new JButton("MEDIUM");
        difficult = new JButton("DIFFICULT");
        buttonPanel.add(select);
        buttonPanel.add(easy);
        buttonPanel.add(medium);
        buttonPanel.add(difficult);
        buttonPanel.add(on);
        buttonPanel.add(off);


        n1.addActionListener(new ActionListener() {//when first name text field is used

            public void actionPerformed(ActionEvent e) {
            	name1 = n1.getText();//get the text from the field and store it as player1 name
            	nn1.setText("");//clear the text field
            	nn1.append("Player 1: "+name1);//add player1 name to the corresponding text area
            	jp2.setPlayer1(name1);//pass the name to the Tron Panel
                n2.addActionListener(new ActionListener() {//let the second text field be active

                    public void actionPerformed(ActionEvent e) {
                    	name2 = n2.getText();//get the second name
                    	nn2.setText("");//clear the text field
                    	nn2.append("Player 2: "+name2);//add player2 name
                    	jp2.setPlayer2(name2);//pass the name to the Tron Panel
                    	//activate all difficulty buttons
                        medium.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                            	jp2.setTimer(2);//sets the speed of events in tron panel
                                cardLayout.show(cardPanel, "2");//show the tron panel
                                medium.setVisible(false);//hide all difficulty buttons
                                easy.setVisible(false);
                                difficult.setVisible(false);
                                select.setVisible(false);
                                buttonPanel.add(pic2);//add tiny logo to bottom buttonPanel
                            }
                        });
                        easy.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                            	jp2.setTimer(1);
                                cardLayout.show(cardPanel, "2");
                                easy.setVisible(false);
                                medium.setVisible(false);
                                difficult.setVisible(false);
                                select.setVisible(false);
                                buttonPanel.add(pic2);
                            }
                        });
                        difficult.addActionListener(new ActionListener() {

                            public void actionPerformed(ActionEvent e) {
                            	jp2.setTimer(3);
                                cardLayout.show(cardPanel, "2");
                                difficult.setVisible(false);
                                medium.setVisible(false);
                                easy.setVisible(false);
                                select.setVisible(false);
                                buttonPanel.add(pic2);
                            }
                        });
                    }
                });
            }
        });

        add(cardPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Tron frame = new Tron();//creates the frame 
            	frame.pack(); 
        		frame.setResizable(false);
        		frame.setBackground(Color.BLACK);
        		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        		int w = (screenSize.width - frame.getWidth()) / 2;
        		int h = (screenSize.height - frame.getHeight()) / 2;
        		frame.setLocation(w,h); 
        		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        		frame.setVisible(true);
            }
        });
    }
}