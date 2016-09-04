/**
 * CSC221 - Assignment 5
 * GUI Development
 * by KLAUDIO VITO
 * 11/01/2015
 */

import java.awt.*;
import java.awt.event.*;
import java.security.SecureRandom;
import javax.swing.*;

@SuppressWarnings("serial")
public class Panel extends JComponent {
	
	private int PANEL_WIDTH; //width of panel, to be used as reference for calculations
	private int PANEL_HEIGHT; //height of panel, to be used as reference for calculations
	private static SecureRandom rnd = new SecureRandom(); //random variable
	JFormattedTextField outputArea, outputLength, outputHeight, panelDim, outputPerimeter; //label to output the area, length, and height of the rectangle
	JButton redraw, quit, motionMenu, expand, shrink, rotate, ccwRotate, getOval, getRect; //buttons to be used in the frame
	private boolean expandClicked = false; //checks whether Expand is clicked or not, initially it is not clicked
	private boolean shrinkClicked = false; //checks whether Shrink is clicked or not, initially it is not clicked
	private boolean rotateClicked = false; //checks whether Rotate is clicked or not, initially it is not clicked
	private boolean rotateCCW = false; //checks whether Rotate CCW is clicked or not, initially it is not clicked
	private boolean ovalClicked = false; //checks whether getOval is clicked or not, initially it is not clicked
	private boolean redrawClicked = false; //checks whether Redraw is clicked or not, initially it is not clicked
	private boolean menuClicked = false; //checks whether Menu is clicked or not, initially it is not clicked
	private static int x, y; //length and height of the rectangle
	private static double theta = 0; //rotation angle, initially it is 0. It will increase every rotate is clicked
	private static Color squareColor = Color.BLACK; //initial color of the rectangle
	private static Rectangle rect = new Rectangle(); //a rectangle object from Rectangle class
	
	//constructor, calls different methods
	public Panel(int width, int height){
		setPANEL_WIDTH(width);//sets the width of the panel
		setPANEL_HEIGHT(height); //sets the height of the panel
		generateRandomCoordinates(); //generates random height and length for rectangle
		addButtons();	//adds the buttons on the frame
	}
	
	//paint component to do the actual painting on the frame
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g); //inherited constructor
        Graphics2D g2d = (Graphics2D) g.create(); //create a g2d object from graphics2d class
		g2d.setStroke(new BasicStroke(3)); //set the thickness of the lines to 3
		rect.x = x; //set the rectangle length to random number x
		rect.y = y; //set the rectangle height to random number y
		panelDim = new JFormattedTextField(" PANEL DIMENSIONS: " + PANEL_WIDTH + " x " + PANEL_HEIGHT);
		panelDim.setBounds(PANEL_WIDTH-210, PANEL_HEIGHT-70, 190, 20);
		panelDim.setForeground(Color.GREEN);
		panelDim.setBackground(Color.BLUE);
		panelDim.setToolTipText("This label displays the dimensions of the panel");
		add(panelDim);
		
		//statements to deal with the figure when the user wants an oval
		if(ovalClicked){
			//this statement is used to expand while rotated clockwise
			if (rotateClicked && expandClicked){

	        	if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){ //length and height should not be very big
	        		x *= 1.04881; //increase length of rectangle by sqrt(10%) = 0.04881
					y *= 1.04881; //increase height of rectangle by sqrt(10%) = 0.04881
				}
	        	g2d.setColor(Color.RED); //set the color to RED
	    		g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //Rotate by theta clockwise about the center of the panel
	    		remove(outputArea); //remove old area display
	    		g2d.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the rotated oval
	    		expandClicked = false; //reset to false because we might want to expand again
			}
			
			//this statement is used to expand while rotated counter-clockwise
			else if (rotateCCW && expandClicked){
	        	if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){ //length and height should not be very big
	        		x *= 1.04881; //increase length of rectangle by sqrt(10%) = 0.04881
					y *= 1.04881; //increase height of rectangle by sqrt(10%) = 0.04881
				}
	        	g2d.setColor(Color.RED); //set the color to RED
	    		g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //Rotate by theta clockwise about the center of the panel
	    		remove(outputArea); //remove old area display
	    		g2d.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the rotated oval
	    		expandClicked = false; //reset to false because we might want to expand again
			}
			//this statement is used to shrink while rotated counter-clockwise
			else if (rotateCCW && shrinkClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){//length and height should not be very big
					x /= 1.04881;//decrease length of rectangle by sqrt(10%) = 0.04881
					y /= 1.04881;//decrease height of rectangle by sqrt(10%) = 0.04881
				}
	        	g2d.setColor(Color.BLUE);//set the color to BLUE
	    		g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2);//Rotate by theta clockwise about the center of the panel
	    		remove(outputArea);//remove old area display
	    		g2d.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y);//draw the rotated oval
	    		shrinkClicked = false;//reset to false because we might want to shrink again
			}
			//this statement is used to shrink while rotated clockwise
			else if (rotateClicked && shrinkClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){//length and height should not be very big
					x /= 1.04881;//decrease length of rectangle by sqrt(10%) = 0.04881
					y /= 1.04881;//decrease height of rectangle by sqrt(10%) = 0.04881
				}
	        	g2d.setColor(Color.BLUE);//set the color to BLUE
	    		g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2);//Rotate by theta clockwise about the center of the panel
	    		remove(outputArea);//remove old area display
	    		g2d.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y);//draw the rotated oval
	    		shrinkClicked = false;//reset to false because we might want to shrink again
			}
			//this statement is used to expand non-rotated oval
			else if(expandClicked){
	        	if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){//length and height should not be very big
					rect.x *= 1.04881;//increase length of rectangle by sqrt(10%) = 0.04881
					rect.y *= 1.04881;//increase height of rectangle by sqrt(10%) = 0.04881
				}
				squareColor = Color.RED; //set color to red
				remove(outputArea); // remove old are display
	        	drawOval(g2d,rect, squareColor); //draw the new oval
	        	expandClicked = false;//reset to false
	        }
			//this statement is used to expand non-rotated oval
	        else if (shrinkClicked){
	        	if(x > 30 && y > 30){ //I choose to have my smallest possible rectangle 30x30
					rect.x /= 1.04881;//decrease length of rectangle by sqrt(10%) = 0.04881
					rect.y /= 1.04881;//decrease height of rectangle by sqrt(10%) = 0.04881
				}
				squareColor = Color.BLUE; //set color to BLUE
				remove(outputArea); //remove old area display
	        	drawOval(g2d,rect, squareColor); //draw the new oval
	        	shrinkClicked = false; //reset to zero
	        }
			//this statement is used to only rotate the oval counter-clockwise
	        else if (rotateCCW){
	        	x = rect.x; //set length
	    		y = rect.y; //set height
	    		g2d.setColor(Color.GREEN.darker()); //set color to darker GREEN
	    		g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //rotate theta degrees clockwise about the center of the panel
	    		remove(outputArea); //remove old area display
	    		g2d.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the new oval
	        }
			//this statement is used to only rotate the oval clockwise
	        else if(rotateClicked){
	        	x = rect.x; //set length
	    		y = rect.y; //set height
	    		g2d.setColor(Color.GREEN.darker()); //set color to brighter GREEN
	    		g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //rotate theta degrees clockwise about the center of the panel
	    		remove(outputArea); //remove old area display
	    		g2d.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the new oval
	        }
	        //this statement is used to draw a new random oval
	        else if (redrawClicked || menuClicked){
	        	drawOval(g2d, rect, squareColor); //draws the oval
	        	redrawClicked = false; //reset to false
	        }
	        else//this is used for the beginning of the program where no buttons are clicked yet
	        	drawOval(g2d, rect, squareColor); //if none of the previous statements stand, simply draw a oval
		}
		//in this case we are dealing with a rectangle
		else{
			
		
			//this statement is used to expand while rotated clockwise
			if (rotateClicked && expandClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){ //length and height should not be very big
					x *= 1.04881; //increase length of rectangle by sqrt(10%) = 0.04881
					y *= 1.04881; //increase height of rectangle by sqrt(10%) = 0.04881
				}
				g2d.setColor(Color.RED); //set the color to RED
				g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //Rotate by theta clockwise about the center of the panel
				remove(outputArea); //remove old area display
				g2d.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the rotated rectangle
				expandClicked = false; //reset to false because we might want to expand again
			}
		
			//this statement is used to expand while rotated counter-clockwise
			else if (rotateCCW && expandClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){ //length and height should not be very big
					x *= 1.04881; //increase length of rectangle by sqrt(10%) = 0.04881
					y *= 1.04881; //increase height of rectangle by sqrt(10%) = 0.04881
				}
				g2d.setColor(Color.RED); //set the color to RED
				g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //Rotate by theta clockwise about the center of the panel
				remove(outputArea); //remove old area display
				g2d.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the rotated rectangle
				expandClicked = false; //reset to false because we might want to expand again
			}
			//this statement is used to shrink while rotated counter-clockwise
			else if (rotateCCW && shrinkClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){//length and height should not be very big
					x /= 1.04881;//decrease length of rectangle by sqrt(10%) = 0.04881
					y /= 1.04881;//decrease height of rectangle by sqrt(10%) = 0.04881
				}
				g2d.setColor(Color.BLUE);//set the color to BLUE
				g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2);//Rotate by theta clockwise about the center of the panel
				remove(outputArea);//remove old area display
				g2d.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y);//draw the rotated rectangle
    			shrinkClicked = false;//reset to false because we might want to shrink again
			}
			//this statement is used to shrink while rotated clockwise
			else if (rotateClicked && shrinkClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){//length and height should not be very big
					x /= 1.04881;//decrease length of rectangle by sqrt(10%) = 0.04881
					y /= 1.04881;//decrease height of rectangle by sqrt(10%) = 0.04881
				}
				g2d.setColor(Color.BLUE);//set the color to BLUE
				g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2);//Rotate by theta clockwise about the center of the panel
				remove(outputArea);//remove old area display
				g2d.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y);//draw the rotated rectangle
				shrinkClicked = false;//reset to false because we might want to shrink again
			}
			//this statement is used to expand non-rotated rectangle
			else if(expandClicked){
				if(x < PANEL_WIDTH-100 && y < PANEL_HEIGHT-100){//length and height should not be very big
					rect.x *= 1.04881;//increase length of rectangle by sqrt(10%) = 0.04881
					rect.y *= 1.04881;//increase height of rectangle by sqrt(10%) = 0.04881
				}
				squareColor = Color.RED; //set color to red
				remove(outputArea); // remove old are display
				drawRectangle(g2d,rect, squareColor); //draw the new rectangle
        		expandClicked = false;//reset to false
			}
			//this statement is used to expand non-rotated rectangle
			else if (shrinkClicked){
				if(x > 30 && y > 30){ //I choose to have my smallest possible rectangle 30x30
					rect.x /= 1.04881;//decrease length of rectangle by sqrt(10%) = 0.04881
					rect.y /= 1.04881;//decrease height of rectangle by sqrt(10%) = 0.04881
				}
				squareColor = Color.BLUE; //set color to BLUE
				remove(outputArea); //remove old area display
				drawRectangle(g2d,rect, squareColor); //draw the new rectangle
				shrinkClicked = false; //reset to zero
			}
			//this statement is used to only rotate the rectangle counter-clockwise
			else if (rotateCCW){
				x = rect.x; //set length
				y = rect.y; //set height
				g2d.setColor(Color.GREEN.darker()); //set color to brighter GREEN
				g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //rotate theta degrees clockwise about the center of the panel
				remove(outputArea); //remove old area display
				g2d.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the new rectangle
			}
			//this statement is used to only rotate the rectangle clockwise
			else if(rotateClicked){
				x = rect.x; //set length
				y = rect.y; //set height
				g2d.setColor(Color.GREEN.darker()); //set color to brighter GREEN
				g2d.rotate(theta, PANEL_WIDTH/2,PANEL_HEIGHT/2); //rotate theta degrees clockwise about the center of the panel
				remove(outputArea); //remove old area display
				g2d.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draw the new rectangle 
			}
			//this statement is used to draw a new random rectangle
			else if (redrawClicked || menuClicked){
				drawRectangle(g2d, rect, squareColor); //draws the rectangle
				redrawClicked = false; //reset to false
			}
			else//this is used for the beginning of the program where no buttons are clicked yet
				drawRectangle(g2d, rect, squareColor); //if none of the previous statements stand, simply draw a rectangle
		}
		getArea(x,y); //displays the area of the rectangle on the screen
	}
	
	//method to draw a rectangle given its color
	private void drawRectangle(Graphics g, Rectangle rect, Color c){
		g.setColor(c); //sets the color of the rectangle
		x = rect.x; //sets the length
		y = rect.y; //sets the height
		g.drawRect(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draws rectangle based on length and height
	}
	
	//method to draw an oval given its color
	private void drawOval(Graphics g, Rectangle rect, Color c){
		g.setColor(c); //sets the color of the rectangle
		x = rect.x; //sets the length
		y = rect.y; //sets the height
		g.drawOval(PANEL_WIDTH/2-x/2, PANEL_HEIGHT/2-y/2, x, y); //draws rectangle based on length and height
	}

	
	//action listener for the expand button
	class Expand implements ActionListener{
		public void actionPerformed (ActionEvent e){
			//checks if the rectangle goes out of bounds or not; shows message if it does and disables expanding
			if (x >= PANEL_WIDTH-150){
				expandClicked = false;
				JOptionPane.showMessageDialog(null, 
						"You cannot expand anymore because the rectangle/oval goes out of bound", 
						"BOUNDS",
						0);
			}
			//checks if the rectangle goes out of bounds or not; shows message if it does and disables expanding	
			else if(y >= PANEL_HEIGHT-150){
				expandClicked = false;
				JOptionPane.showMessageDialog(null, 
						"You cannot expand anymore because the rectangle/oval goes out of bound",
						"BOUNDS", 
						0);
			}
			else
				expandClicked = true; //enables expanding if conditions are met
			repaint();
		}
	}
	
	//action listener for the shrink button
	class Shrink implements ActionListener{
		public void actionPerformed (ActionEvent e){
			//checks if the triangle's length or height are lower then 30, if so then disables shrinking and shows message
			if(x <= 30 || y <= 30){
				JOptionPane.showMessageDialog(null, 
						"You cannot shrink anymore because minumum rectangle/oval length or height is 30",
						"BOUNDS", 
						0);
				shrinkClicked = false;
			}
			else
				shrinkClicked = true;// enables shrinking if conditions are met
			repaint();
		}
	}
	
	//action listener for the rotate button
	class Rotation implements ActionListener{
		public void actionPerformed(ActionEvent e){
			theta += Math.PI/6; //theta is clockwise changed
			rotateClicked = true;//simply say that the button was clicked
			repaint();
		}
	}
	
	//action listener for ccw rotation
	class ccwRotation implements ActionListener{
		public void actionPerformed(ActionEvent e){
			theta -= Math.PI/6; //theta is counter-clockwise changed
			rotateCCW = true;//simply say that the button was clicked
			repaint();
		}
	}
	
	//action listener for getOval
	class getOval implements ActionListener{
		public void actionPerformed(ActionEvent e){
			squareColor = Color.ORANGE;
			ovalClicked = true;//simply say that the button was clicked
			repaint();
		}
	}
	
	//action listener for getRect
	class getRect implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			ovalClicked = false;//simply say that the button was clicked again
			squareColor = Color.BLACK;
			repaint();
		}
	}
	
	//action listener for the redraw button
	class reDraw implements ActionListener{
		public void actionPerformed(ActionEvent e){
			generateRandomCoordinates(); //generate new random length and height
			squareColor = Color.BLACK; //set the color of the rectangle to black
			remove(outputArea); //remove old area display
			theta = 0; //reset the angle of rotation
			redrawClicked = true; //say that the button was clicked
			rotateClicked = false; //deactivate the rotation 
			rotateCCW = false; //deactivate counter-clockwise rotation
			ovalClicked = false;
			menuClicked = false;
			remove(expand);
			remove(shrink);
			remove(rotate);
			remove(ccwRotate);
			remove(getOval);
			remove(getRect);
			add(motionMenu);
			repaint();
		}
	}
	
	//action listener for the menu button
	class Menu implements ActionListener{
		public void actionPerformed (ActionEvent e){
			remove(motionMenu); //remove the button
			remove(outputArea); //remove old are display
			add(expand); //add the expand button
			add(shrink); //add the shrink button
			add(rotate); //add the rotate clockwise button
			add(ccwRotate); //add the counter-clockwise rotation button
			add(getOval);
			add(getRect);
			menuClicked = true; //says that the button was clicked
			repaint();
		}
	}
	
	//action listener for the quit button
	class Quit implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.exit(0); //exit if clicked
		}
	}
	
	
	//method to get the area of a given rectangle
	private void getArea(int x, int y){
		if(ovalClicked)
			outputArea = new JFormattedTextField(" Area is: " + Math.round(Math.PI*x/2*y/2) ); //display area of oval as a label
		else
			outputArea = new JFormattedTextField(" Area is: " + Math.round(x*y)); //display area of rectangle as a label
		outputArea.setBackground(Color.YELLOW.brighter()); //set background color
		outputArea.setBounds(PANEL_WIDTH-290, 10, 130, 20); //set the position on the screen
		outputArea.setFont(new Font("BROADWAY",Font.PLAIN,13)); //set font
		outputArea.setToolTipText("This label shows the area of the current figure");//set tip text
		
		outputLength = new JFormattedTextField("Length is: " + x); //display length as a label
		outputLength.setBounds(PANEL_WIDTH-150, 10, 130, 20);//set the position on the screen
		outputLength.setToolTipText("This label shows the length of the current figure");//set tip text
		outputLength.setBackground(Color.YELLOW.brighter()); //set background color
		outputLength.setFont(new Font("BROADWAY",Font.PLAIN,13)); //set font
		
		outputHeight = new JFormattedTextField("Height is: " + y); //display height as a label
		outputHeight.setBounds(PANEL_WIDTH-150, 40, 130, 20);//set the position on the screen
		outputHeight.setToolTipText("This label shows the height of the current figure");//set tip text
		outputHeight.setBackground(Color.YELLOW.brighter()); //set background color
		outputHeight.setFont(new Font("BROADWAY",Font.PLAIN,13)); //set font
		
		if(ovalClicked)
			outputPerimeter = new JFormattedTextField("Perimeter: " + 
					Math.round( 2*Math.PI*(Math.sqrt((Math.pow(x, 2)+Math.pow(y, 2))/2)))); //display perimeter of oval as a label
		else
			outputPerimeter = new JFormattedTextField("Perimeter: " + Math.round(2*x+2*y));//display perimeter of rectangle as a label
		outputPerimeter.setBounds(PANEL_WIDTH-290, 40, 130, 20); //set the position on the screen
		outputPerimeter.setBackground(Color.YELLOW.brighter()); //set background color
		outputPerimeter.setFont(new Font("BROADWAY",Font.PLAIN,13)); //set font
		outputPerimeter.setToolTipText("This label shows the perimeter of the current figure");//set tip text
		
		add(outputArea); //add it to the frame
		add(outputLength); //add it to the frame
		add(outputHeight); //add it to the frame
		add(outputPerimeter); //add it to the frame
	}
	
	//method to add the buttons to the frame
	private void addButtons(){
		/*
		 * THE QUIT BUTTON AND ITS SPECIFICATIONS
		 */
		quit = new JButton ("QUIT");
		quit.setBounds(10, 10, 100, 20);
		quit.setBackground(Color.RED);
		quit.setFont(new Font("Arial", Font.BOLD, 15));
		quit.setToolTipText("Click this button to exit the program");
		add(quit);
		
		/*
		 * THE REDRAW BUTTON AND ITS SPECIFICATIONS
		 */
		redraw = new JButton ("REDRAW");
		redraw.setBounds(120, 10, 110,20);
		redraw.setBackground(Color.GREEN);
		redraw.setFont(new Font("Times New Roman",Font.BOLD,15));
		redraw.setToolTipText("Click this button to redraw a rectangle");
		add(redraw);
		
		/*
		 * THE MENU BUTTON AND ITS SPECIFICATIONS
		 */
		motionMenu = new JButton ("Motion menu");
		motionMenu.setBounds(10, PANEL_HEIGHT-70, 130, 20);
		motionMenu.setBackground(Color.ORANGE);
		motionMenu.setFont(new Font("Arial",Font.BOLD,15));
		motionMenu.setToolTipText("Click this button to display menu");
		add(motionMenu);
		
		
		/*
		 * THE exapnd, shrink, and rotate BUTTONS AND their SPECIFICATIONS
		 */
		expand = new JButton ("Expand");
		expand.setBounds(10, PANEL_HEIGHT-100, 115, 20);
		expand.setToolTipText("Click this button to expand the current figure");
		
		shrink = new JButton ("Shrink");
		shrink.setBounds(10, PANEL_HEIGHT-70, 115, 20);
		shrink.setToolTipText("Click this button to shrink the current figure");
		
		rotate = new JButton ("Rotate CW");
		rotate.setBounds((int) (expand.getBounds().getWidth()+20), PANEL_HEIGHT-100, 115, 20);
		rotate.setToolTipText("Click this button to rotate the current figure CLOCKWISE by 15 degrees");
		
		ccwRotate = new JButton ("Rotate CCW");
		ccwRotate.setBounds((int) (expand.getBounds().getWidth()+20), PANEL_HEIGHT-70, 115, 20);
		ccwRotate.setToolTipText("Click this button to rotate the current figure COUNTER-CLOCKWISE by 15 degrees");
		
		getOval = new JButton ("Get Oval");
		getOval.setBounds((int) (expand.getBounds().getWidth()+145), PANEL_HEIGHT-100, 115, 20);
		getOval.setToolTipText("Click this button to get the oval inside the current rectangle");
		
		getRect = new JButton ("Get Rectangle");
		getRect.setBounds((int) (expand.getBounds().getWidth()+145), PANEL_HEIGHT-70, 115, 20);
		getRect.setToolTipText("Click this button to get the rectangle that envelopes the current oval");

		
		/*
		 * ADDING ACTION LISTENERS TO EACH BUTTON
		 */
		quit.addActionListener(new Quit());
		redraw.addActionListener(new reDraw());
		motionMenu.addActionListener(new Menu());
		expand.addActionListener(new Expand());
		shrink.addActionListener(new Shrink());
		rotate.addActionListener(new Rotation());
		ccwRotate.addActionListener(new ccwRotation());
		getOval.addActionListener(new getOval());
		getRect.addActionListener(new getRect());
	}
	
	//method to generate random length and height
	private void generateRandomCoordinates(){
		do{
			x = rnd.nextInt(PANEL_WIDTH-100); //get a random length within 20 less than the width
		}while (x >= PANEL_WIDTH-100 || x <= 30); //x should not exceed the width - 50
		
		do{
			y = rnd.nextInt(PANEL_HEIGHT-100);//get a random height within 20 less than the width
		}while (y >= PANEL_HEIGHT-100 || y <= 30);//y should not exceed the width - 50

	}
	
	private void setPANEL_WIDTH(int pANEL_WIDTH) { PANEL_WIDTH = pANEL_WIDTH;} //set the width of the panel
	
	public int getPANEL_WIDTH(){ return PANEL_WIDTH;}
	
	private void setPANEL_HEIGHT(int pANEL_HEIGHT) {PANEL_HEIGHT = pANEL_HEIGHT;} //set the height of the panel

	public int getPANEL_HEIGHT(){ return PANEL_HEIGHT;}
}
