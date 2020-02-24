package edu.ycp.cs201.disks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class DisksPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	
	private Timer timer;
	//initializing variables
	private Disk[] disks;
	private int diskCount,cursorX,cursorY,cursorRad,diskX,diskY,diskRad;
	private Disk cursor,testdisk;
	private Random rand;
	private boolean gameDone; 
	private double cursorRadius;
	private int timeLeft;
	private DiskColor newDiskColor;
	
	//make background
	public DisksPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.GRAY);
		
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				handleMouseClick(e);
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				handleMouseMove(e);
			}
		});
		
		// Schedule timer events to fire every 100 milliseconds (0.1 seconds)
		this.timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				handleTimerEvent(e);
			}
		});
		
		//create array to store disks
		disks = new Disk[500];
		//start with count at 0
		diskCount = 0;
		//random object
		rand = new Random();
		//keeps track to see if game should end
		gameDone=false;
		//Initialize cursor at number
		cursorRadius = rand.nextInt(35)+10;
		//initaliize disk color
		newDiskColor = DiskColor.values()[rand.nextInt(15)];
		//start timer
		timer.start();
		//initalize timer
		timeLeft=WIDTH;
		
		//set inital cursor to center of game board
		cursor=new Disk(WIDTH/2,HEIGHT/2,cursorRadius,newDiskColor);
		
	}

	// You can call this method to convert a DiskColor value into
	// a java.awt.Color object.
	public Color colorOf(DiskColor dc) {
		return new Color(dc.red(), dc.green(), dc.blue());
	}

	// This method is called whenever the mouse is moved
	protected void handleMouseMove(MouseEvent e) {
		//if game is not done
		if(gameDone!=true) {
		//get mouse x and y locations
		double mouseX = e.getX();
		double mouseY = e.getY();
		
		//create disk from mouse's xLoc,yLoc,rand radius
		cursor=new Disk(mouseX,mouseY,cursorRadius,newDiskColor);
		
		//repaint gui
		repaint();
		}
	}
	
	// This method is called whenever the mouse is clicked
	protected void handleMouseClick(MouseEvent e) {

		//get x loc, yloc
		double mouseX = cursor.getX();
		double mouseY = cursor.getY();
		
		//make a testdisk to use in methods from the cursor 
		testdisk= cursor;
		
		//check if disk is out of bounds
		if(testdisk.isOutOfBounds( WIDTH, HEIGHT)){
			//disk is out of bounds, end game and print to console
			gameDone=true;
			System.out.println("GAME OVER");
		
		}
				
		//disk is in bounds
		//test disk against other disks	
		for(int i=0;i<diskCount;i++) {
			//if testdesk overlaps with any element of the disks array or is out of bounds
			if( testdisk.overlaps(disks[i])) {
				//disk overlaps w another disk
				//end game
				gameDone=true;
				//print to console
				System.out.println("GAME OVER");
				
				//exit the test loop
				break;
			}else {
				//if no overlap with disks[i], keep checking through the array
				continue;
			}
		}
		
		
		//if game is not done, add the test disk to the array and reset timer
		if(gameDone != true) {
			//stop timer
			timer.stop();
			disks[diskCount]= testdisk;
			//update number of disks in the array
			diskCount++;
			//make next cursor
			cursorRadius = rand.nextInt(35)+10;
			cursor=new Disk( mouseX, mouseY, cursorRadius, newDiskColor);
			//make next color
			newDiskColor = DiskColor.values()[rand.nextInt(15)];
			//update gui
			repaint();	
			//restart timer
			timeLeft=WIDTH;
			timer.start();
		} else if(gameDone == true) {
			//if game is done, repaint gui
			repaint();
		}
	}
		
	
	// This method is called whenever a timer event fires
	protected void handleTimerEvent(ActionEvent e) {
		// TODO
		//if there is no time left, end game
		if(timeLeft<0) {
			gameDone=true;
		}
		//if there is time left, decrease time left 
		if(gameDone!=true) {
			//extra credit: increases how much is ducted from timeleft based on the 
			//numbver of disks placed
			timeLeft-=1.25*diskCount;
		}
		//repaint timer bar
		repaint();
	}
	
	private static final Font FONT = new Font("Dialog", Font.BOLD, 24);

	// This method is called automatically whenever the contents of
	// the window need to be redrawed.
	@Override
	public void paintComponent(Graphics g) {
		// Paint the window background
		super.paintComponent(g);
		
		//colors used
		Color myBlue = new Color(0, 0, 255);
		Color myRed = new Color(255, 0, 0);
		Color myMaroon = new Color(255, 0, 0, 63);
		Color myBlack = new Color(0, 0, 0);

		// TODO: draw everything that needs to be drawn
	
		//skip loop until 1 disk is placed
		if(disks[0]!=null) {
			for(int i=0;i<diskCount;i++) {
			
			//set pen to disk's color
			g.setColor(colorOf(disks[i].getColor()));
	
			//get elements from disk
			diskX =(int) disks[i].getX();
			diskY =(int) disks[i].getY();
			diskRad=(int) disks[i].getRadius();

			//use disk elements to draw a disk centered at cursor
			g.fillOval(diskX-diskRad, diskY-diskRad, diskRad*2, diskRad*2);
			}
		}

		//draw timer bar
		g.setColor(myMaroon);
		g.fillRect(0, HEIGHT-15, timeLeft, 10);
		
		//draw disk counter
		g.setColor(myBlack);
		g.setFont(new Font("Dialog", Font.BOLD, 25));
		String counter = "Score:  " + diskCount;
		g.drawString(counter, 5, 25);
		
		
		//draw GAME OVER
		if(gameDone == true) {
		g.setFont(new Font("Dialog", Font.BOLD, 30));
		String gameOver= "GAME OVER";
		g.drawString(gameOver, WIDTH/4, HEIGHT/2);
		}
		
		//draw cursor
		g.setColor(myBlack);
		cursorX=(int)cursor.getX();
		cursorY=(int)cursor.getY();
		cursorRad=(int)cursor.getRadius();
		//draw hollow circle with elements from cursor centered at the cursor
		g.drawOval(cursorX-cursorRad,cursorY-cursorRad,cursorRad*2,cursorRad*2);
		
	}
}
