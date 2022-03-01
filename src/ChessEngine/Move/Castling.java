package ChessEngine.Move;

import ChessEngine.Game.Game;

//move that represent castling (either white or black)
public class Castling implements Move, Cloneable{

	
	PeaceMove kingMove;	//how king should move in castling
	PeaceMove rookMove;	//how rook should move in castling
	
	Game game;
	
	int color;			//color of king
	
	int[][] attackedTiles;	//tiles that can't be attacked if king want to castle
	int[][] emptyTiles;		//tiles that have to be empty if king want to castle
	
	public Castling(Game game, Move kingMove, Move rookMove, int color, int[][] attackedTiles, int[][] emptyTiles){
		this.game= game;
		this.kingMove = (PeaceMove)kingMove;
		this.rookMove = (PeaceMove)rookMove;
		this.color = color;
		this.attackedTiles = attackedTiles;
		this.emptyTiles = emptyTiles;
	}
	
	@Override
	public void executeOn() {		//TODO: rework xd
		kingMove.executeOn();
		rookMove.executeOn();
		game.removeLastMove();
		game.removeLastMove();
		game.changeTurn();
		game.addMove(this);
	}

	@Override
	public void undoOn() {
		game.addMove(null);
		game.addMove(null);
		kingMove.undoOn();
		rookMove.undoOn();
		game.changeTurn();
		game.removeLastMove();
	}

	@Override
	public String toReadableString() {
		if(emptyTiles.length == 2) {
			return "O-O";
		}
		if(emptyTiles.length == 3) {
			return "O-O-O";
		}
		return null;
	}

	@Override
	public boolean isLegal() {
		//checks if king is on right position and haven't moved
		if(game.getTile(kingMove.getStartHor(), kingMove.getStartVer()).getIdentifier() != ((color == 1)?'K':'k') || game.getTile(kingMove.getStartHor(), kingMove.getStartVer()).getMovesNumber() != 0) {
			return false;
		}
		//checks if rook is on right position and haven't moved
		if(game.getTile(rookMove.getStartHor(), rookMove.getStartVer()).getIdentifier() != ((color == 1)?'R':'r') || game.getTile(rookMove.getStartHor(), rookMove.getStartVer()).getMovesNumber() != 0) {
			return false;
		}
		
		for(int i = 0; i < emptyTiles.length; i++){		//checks if tiles are empty
			if(game.getTile(emptyTiles[i][0], emptyTiles[i][1]).getAlive())return false;
		}
		
		for(int i = 0; i < attackedTiles.length; i++){	//checks if tiles are attacked
			if(game.isTileAttacked(color, attackedTiles[i][0], attackedTiles[i][1]))return false;
		}
		return true;
	}

	@Override
	public boolean isCatch() {
		return false;
	}

	@Override
	public int getFinalHor() {
		return kingMove.getFinalHor();
	}

	@Override
	public int getFinalVer() {
		return kingMove.getFinalVer();
	}
	
	@Override
	public int getStartHor() {
		return kingMove.getStartHor();
	}

	@Override
	public int getStartVer() {
		return kingMove.getStartVer();
	}
	
	@Override
	public void setGame(Game g) {
		this.game = g;
	}
}
