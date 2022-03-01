package ProgramGUI;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class ProgramPanel extends JPanel{		//JPanel with tree like construction
	private static final long serialVersionUID = 3291953981942598126L;
	
	protected ProgramGUI programGUI;	//GUI of given panel
	private String panelTitle;			//title of panel
	private ProgramPanel panelParent;	//parent in tree
	private ArrayList<ProgramPanel> kidPanels = new ArrayList<ProgramPanel>();		//kids in tree
	
	protected ProgramPanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int width, int height){
		this.setLayout(null);
		this.programGUI = programGUI;
		this.panelParent = panelParent;
		this.panelTitle = panelTitle;
		
		this.setBackground(new Color(128,128,128));
		
	}
	
	public String getPanelTitle() {
		return panelTitle;
	}
	
	public ProgramPanel getPanelParent() {
		return panelParent;
	}
	
	public void addKid(ProgramPanel p) {
		kidPanels.add(p);
	}
	
	public void removeKid(int index) {
		kidPanels.remove(index);
	}
	
	public ProgramPanel getKid(int index) {
		return kidPanels.get(index);
	}
	
	public int getKidsNumber() {
		return kidPanels.size();
	}
	
	public ProgramGUI getPanelGUI() {
		return programGUI;
	}
	
	public JFrame getFrame() {
		return programGUI.getFrame();
	}
	
	public void update() {
		
	}
	
	public abstract void addComponents(int width, int height);
}