/**
 * CSC221 - Assignment 5
 * GUI Development
 * by KLAUDIO VITO
 * 11/01/2015
 */

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame {
	private int PANEL_WIDTH = 800;
	private int PANEL_HEIGHT = 700;
    public Main(){
    	Panel panel = new Panel(PANEL_WIDTH,PANEL_HEIGHT);
    	setTitle("Programming Assignment #5 - GUI Development");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(panel.getPANEL_WIDTH(), panel.getPANEL_HEIGHT());
		panel.setLayout(null);
		add(panel);
		setVisible(true);
    }
    
    public static void main(String[] args){
    	new Main();
    }

}
