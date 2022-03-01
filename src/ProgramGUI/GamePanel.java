package ProgramGUI;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ChessEngine.Engine.*;
import ChessEngine.Game.*;
import ChessEngine.Move.*;


import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GamePanel extends ProgramPanel{

	private static final long serialVersionUID = 1752676061817167121L;

	private GamePanel self = this;
	
	private Game panelGame;
	
	private GameTile[][] gameTiles;
	
	private Map<String, ImageIcon> pieceIcons;
	private ImageIcon greenDot;
	
	private Map<String, Move> possibleMoves;
	
	private ArrayList<Integer> clickedHor;
	private ArrayList<Integer> clickedVer;
	
	private JLabel onMoveImage;
	
	private int width, height;
	
	public GamePanel(ProgramGUI programGUI, ProgramPanel panelParent, String panelTitle, int width, int height, Game panelGame){
		super(programGUI, panelParent, panelTitle, width, height);
		// TODO Auto-generated constructor stub
		
		this.panelGame = panelGame;
		gameTiles = new GameTile[8][8];
		
		String[] imagesSource = new String[] {"images/bb.png", "images/bk.png", "images/bn.png", "images/bq.png", "images/br.png", "images/bp.png",
											  "images/wb.png", "images/wk.png", "images/wn.png", "images/wq.png", "images/wr.png", "images/wp.png"};
		pieceIcons = new HashMap<String, ImageIcon>();
		for(String filePath: imagesSource) {
			pieceIcons.put(filePath.substring(7,9), resizeIcon(new ImageIcon(filePath), height * 1/8 * 6/8, height * 1/8 * 6/8));
		}
		
		greenDot = resizeIcon(new ImageIcon("images/greenDot.png"), height * 1/32 * 6/8, height * 1/32 * 6/8);
		
		
		clickedHor = new ArrayList<Integer>();
		clickedVer = new ArrayList<Integer>();
		
		possibleMoves = new HashMap<String, Move>();
		
		this.width = width;
		this.height = height;
		
		addComponents(width, height);
		
		update();
	}

	@Override
	public void addComponents(int width, int height) {
		for(int hor = 0; hor < 8; hor++) {
			for(int ver = 0; ver < 8; ver++) {
				gameTiles[hor][ver] = new GameTile(hor, ver, height * 1/8 + height * hor/8 * 6/8, height * 1/8 + height * ver/8 * 6/8, height * 1/8 * 6/8 + 1, height * 1/8 * 6/8 + 1);
				this.add(gameTiles[hor][ver]);
			}
		}
		
		JButton reverseButton = new JButton(resizeIcon(new ImageIcon("images/reverse.png"), height * 1/8, height * 1/8));
		reverseButton.setBounds(height * 8/8, height * 6/8, height * 1/8, height * 1/8);
		
		reverseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for(GameTile[] gtrow: gameTiles) {
					for(GameTile gt: gtrow) {
						gt.reverse();
						gt.update();
					}
				}
			}
		});
		
		this.add(reverseButton);
		
		onMoveImage = new JLabel();
		onMoveImage.setBounds(height * 8/8 + height * 3/16, height * 6/8, height * 1/8, height * 1/8);
		this.add(onMoveImage);
		
		JButton undoButton = new JButton("Cofnij");
		undoButton.setBounds(height * 8/8, height * 1/8, height * 3/16, height * 1/16);
		undoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(panelGame.getGameLength() != 0){
				panelGame.getLastMove().undoOn();
				update();
				}
			}
		});
		this.add(undoButton);
		
		JButton restartButton = new JButton("Restart");
		restartButton.setBounds(height * 8/8 + height * 6/16, height * 1/8, height * 3/16, height * 1/16);
		restartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(panelGame.getGameLength() != 0){
					if(JOptionPane.showConfirmDialog (null, "Czy napewno chcesz zrestartowaæ grê?","restart",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						restart();
						update();
					}
				}
			}
		});
		this.add(restartButton);
		
		/*JButton AIMoveButton = new JButton("Ruch komputera");
		AIMoveButton.setBounds(height * 8/8 + height * 6/16, height * 2/8, height * 3/16, height * 1/16);
		
		AIMoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChessEngine engine = new ChessEngine(panelGame);
				engine.setStrategy(StrategyFactory.createStrategy("minimax"));
				engine.getMove().executeOn();
				update();
			}
		});
		
		this.add(AIMoveButton);*/
	}
	
	public void update() {
		panelGame.updateMoves();
		
		for(GameTile[] gtrow: gameTiles) {
			for(GameTile gt: gtrow) {
				gt.update();
			}
		}
		
		possibleMoves.clear();
		
		for(Move m: panelGame.possibleLegalMoves()) {
			possibleMoves.put(m.toReadableString(), m);
		}
		
		onMoveImage.setIcon((panelGame.getOnMove() == 1)? pieceIcons.get("wp"): pieceIcons.get("bp"));
	}
	
	private  ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image temp = icon.getImage();
		temp = temp.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(temp);
	}
	
	public void restart() {
		panelGame = GameFactory.createDefaultGame();
		removeAll();
		addComponents(width, height);
		update();
	}
	
	class GameTile extends JButton{
		private static final long serialVersionUID = 945336917668623399L;
		
		int horizontal;
		int vertical;
		
		GameTile(int hor, int ver, int x, int y, int width, int height){
			horizontal = hor;
			vertical = ver;
			
			super.setBounds(x, y, width, height);
			
			this.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					clickedHor.add(horizontal);
					clickedVer.add(vertical);
					
					if(clickedHor.size() == 2) {
						ArrayList<Move> moves = new ArrayList<Move>();
						
						Move m;
						for(String key: possibleMoves.keySet()) {
							m = possibleMoves.get(key);
							int[][] tiles = m.getMoveTiles();
							if(clickedHor.get(0) == tiles[0][0] && clickedHor.get(1) == tiles[1][0] &&
							   clickedVer.get(0) == tiles[0][1] && clickedVer.get(1) == tiles[1][1]) {
								moves.add(m);
							}
						}
						
						if(moves.size() == 1) {
							moves.get(0).executeOn();
						}else if(moves.size() > 1) {
							
							Object[] possibilities = {"Bishop", "Knight",  "Rook", "Queen"};
							String s = (String)JOptionPane.showInputDialog(
							                    self.getFrame(),
							                    "Promote to:",
							                    "Customized Dialog",
							                    JOptionPane.PLAIN_MESSAGE,
							                    (panelGame.getOnMove() == 1)?pieceIcons.get("wp"):pieceIcons.get("bp"),
							                    possibilities,
							                    "-");
							
							if(s.equals("Bishop")) {
								for(Move move: moves){
									if(((PromotionMove)move).getPromotedToIdentifier() == 'B' || ((PromotionMove)move).getPromotedToIdentifier() == 'b') {
										move.executeOn();
									}
								}
							}else if(s.equals("Knight")) {
								for(Move move: moves){
									if(((PromotionMove)move).getPromotedToIdentifier() == 'N' || ((PromotionMove)move).getPromotedToIdentifier() == 'n') {
										move.executeOn();
									}
								}
								
							}else if(s.equals("Rook")) {
								for(Move move: moves){
									if(((PromotionMove)move).getPromotedToIdentifier() == 'R' || ((PromotionMove)move).getPromotedToIdentifier() == 'r') {
										move.executeOn();
									}
								}
								
							}else if(s.equals("Queen")) {
								for(Move move: moves){
									if(((PromotionMove)move).getPromotedToIdentifier() == 'Q' || ((PromotionMove)move).getPromotedToIdentifier() == 'q') {
										move.executeOn();
									}
								}
								
							}
						}
						
						clickedHor.clear();
						clickedVer.clear();
					}
					self.update();
					
					int result = panelGame.getGameResult();
					if(result != 0) {
						
						switch(result){
						case 1:
							JOptionPane.showMessageDialog(
								    null,  
								    "Bia³y wygrywa!",
								    "wynik",
								    JOptionPane.INFORMATION_MESSAGE, 
								    pieceIcons.get("wp")); 
							break;
						case 2:
							JOptionPane.showMessageDialog(
								    null, 
								    "Czarny wygrywa!", 
								    "wynik",
								    JOptionPane.INFORMATION_MESSAGE, 
								    pieceIcons.get("bp")); 
							
							break;
						case 3:
							JOptionPane.showMessageDialog(
								    null, 
								    "Remis!", 
								    "wynik",
								    JOptionPane.INFORMATION_MESSAGE); 
							
							break;
						}
						int restart = JOptionPane.showConfirmDialog (null, "Czy chcesz zrestartowaæ grê?","restart",JOptionPane.YES_NO_OPTION);
						if(restart == JOptionPane.YES_OPTION)restart();
					}
				}
			});
		}
		
		void reverse() {
			vertical = 7 - vertical;
			horizontal = 7 - horizontal;
		}
		
		void update() {
			String key = "";
			key += (Character.isUpperCase(panelGame.getTileRepresentation(horizontal, vertical)))? "w": "b";
			key += String.valueOf(Character.toLowerCase(panelGame.getTileRepresentation(horizontal, vertical)));
			this.setIcon(pieceIcons.get(key));
			
			this.setBackground(((horizontal + vertical)%2 == 1)?new Color(50,50,50): new Color(200,200,200));
			
			
			if(clickedHor.size() == 1) {
				if(clickedHor.get(0) == horizontal && clickedVer.get(0) == vertical) {
					this.setBackground(new Color(0,200,100));
				}
				for(String tkey: possibleMoves.keySet()){
					if(possibleMoves.get(tkey).getStartHor() == clickedHor.get(0) && possibleMoves.get(tkey).getStartVer() == clickedVer.get(0)) {
						if(possibleMoves.get(tkey).getFinalHor() == horizontal && possibleMoves.get(tkey).getFinalVer() == vertical) {
							if(possibleMoves.get(tkey).isCatch())this.setBackground(new Color(255, 150, 150));
							else this.setIcon(greenDot);
							break;
						}
					}
				}
			}
		}
	}

}
