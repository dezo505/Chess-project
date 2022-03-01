package ChessEngine.Move;

import ChessEngine.Game.Game;
import ChessEngine.Piece.Piece;

//classic move, but it's illegal if active piece have moved
public class NOMcapedPeaceMove implements Move, Cloneable{
	private int startHorCord;
	private int startVerCord;
	
	private int finalHorCord;
	private int finalVerCord;
	
	private Game game;
	
	NOMcapedPeaceMove(Game game, int startHor, int startVer, int finalHor, int finalVer){
		this.game= game;
		startHorCord = startHor;
		startVerCord = startVer;
		finalHorCord = finalHor;
		finalVerCord = finalVer;
	}
	
	@Override
	public void executeOn() {
		Piece movingPiece = game.getTile(startHorCord, startVerCord);
		movingPiece.increaseMovesNumber();
		game.swapTiles(startHorCord, startVerCord, finalHorCord, finalVerCord);
		game.changeTurn();
		game.addMove(this);
	}
	
	@Override
	public void undoOn() {
		Piece movingPiece = game.getTile(finalHorCord, finalVerCord);
		movingPiece.decreaseMovesNumber();
		game.swapTiles(startHorCord, startVerCord, finalHorCord, finalVerCord);
		game.changeTurn();
		game.removeLastMove();
	}
	
	@Override
	public String toReadableString() {
		String result = game.getTile(startHorCord, startVerCord).getSymbol();
		result += tileToString(startHorCord, startVerCord);
		result += tileToString(finalHorCord, finalVerCord);
		return result;
	}
	
	@Override
	public boolean isLegal() {
		if(game.getTile(startHorCord, startVerCord).getMovesNumber() != 0)return false;
		if(finalHorCord < 0 || finalHorCord > 7)return false;
		if(finalVerCord < 0 || finalVerCord > 7)return false;
		if(game.getTile(finalHorCord, finalVerCord).getAlive()) {
			return false;
		}
		return true;
	}
	
	public boolean isEqual(int hor1, int ver1, int hor2, int ver2){
		return  hor1 == startHorCord &&
				ver1 == startVerCord &&
				hor2 == finalHorCord &&
				ver2 == finalVerCord;
	}
	
	@Override
	public boolean isCatch() {
		return false;
	}
	
	@Override
	public int getFinalHor() {
		return finalHorCord;
	}

	@Override
	public int getFinalVer() {
		return finalVerCord;
	}
	
	@Override
	public int getStartHor() {
		return startHorCord;
	}

	@Override
	public int getStartVer() {
		return startVerCord;
	}
	
	@Override
	public void setGame(Game g) {
		this.game = g;
	}
}
