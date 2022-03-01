package ProgramGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ChessEngine.Game.GameFactory;

public class ProgramGUI {		//Program GUI
	private JFrame programFrame;					//onScreen frame
	private JPanel currentPanel;					//panel that is currently on screen
	private ProgramPanel mainPanel;					//root of tree
	
	public ProgramGUI(int width, int height){
		
		programFrame = new JFrame("Chess");
		programFrame.setBounds(0, 0, width,height);
		programFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		mainPanel = new GamePanel(this, null, "-", width, height, GameFactory.createDefaultGame());
		
		setPanel(mainPanel);
		
		programFrame.setVisible(true);
	}
	
	public void setPanel(ProgramPanel p) {		//set current panel to another
		p.update();
		if(currentPanel != null) {
			currentPanel.setVisible(false);
			programFrame.remove(currentPanel);
		}
		currentPanel = p;
		programFrame.add(p);
		p.setVisible(true);
	}
	
	public JFrame getFrame() {
		return programFrame;
	}
	
	public void update(){
		
	}
}
