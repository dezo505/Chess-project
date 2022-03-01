package ChessEngine.Move;

import ChessEngine.Game.Game;
import ChessEngine.Piece.Piece;
import ChessEngine.Piece.PieceFactory;

//black pawn catch after which pawn will get promoted
public class BlackPromotionCatchMove implements Move, Cloneable, PromotionMove{
	private int startHorCord;
	private int startVerCord;
	
	private int finalHorCord;
	private int finalVerCord;
	
	private Piece orginalPiece;
	private char promotedPieceIdentifier;
	
	private Game game;
	
	BlackPromotionCatchMove(Game game, int startHor, int startVer, int finalHor, int finalVer, char promotedTo){
		this.game= game;
		startHorCord = startHor;
		startVerCord = startVer;
		finalHorCord = finalHor;
		finalVerCord = finalVer;
		promotedPieceIdentifier = promotedTo;
		orginalPiece = (Piece)game.getTile(startHor, startVer).clone();//.clone
	}
	
	@Override
	public void executeOn() {
		game.getTile(finalHorCord, finalVerCord).setAlive(false);
		game.setTile(startHorCord, startVerCord, PieceFactory.createPiece(game, startHorCord, startVerCord, 'B', promotedPieceIdentifier));
		Piece movingPiece = game.getTile(startHorCord, startVerCord);
		movingPiece.increaseMovesNumber();
		game.swapTiles(startHorCord, startVerCord, finalHorCord, finalVerCord);
		game.changeTurn();
		game.addMove(this);
	}
	
	@Override
	public void undoOn() {
		game.getTile(startHorCord, startVerCord).setAlive(true);
		Piece movingPiece = game.getTile(finalHorCord, finalVerCord);
		movingPiece.decreaseMovesNumber();
		game.setTile(finalHorCord, finalVerCord, orginalPiece);
		game.swapTiles(startHorCord, startVerCord, finalHorCord, finalVerCord);
		game.changeTurn();
		game.removeLastMove();
	}
	
	@Override
	public String toReadableString() {
		String result = game.getTile(startHorCord, startVerCord).getSymbol();
		result += tileToString(startHorCord, startVerCord);
		result += ":";
		result += tileToString(finalHorCord, finalVerCord);
		result += "*" + String.valueOf(Character.toUpperCase(promotedPieceIdentifier));
		return result;
	}
	
	@Override
	public boolean isLegal(){
		if(finalVerCord != 7)return false;
		if(finalHorCord < 0 || finalHorCord > 7)return false;
		if(game.getTile(finalHorCord, finalVerCord).getAlive() == false)return false;
		if(game.getTile(startHorCord, startVerCord).getColor() * game.getTile(finalHorCord, finalVerCord).getColor() == 1)return false;
		
		return true;
	}
	
	@Override
	public boolean isCatch() {
		return true;
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

	@Override
	public char getPromotedToIdentifier() {
		return promotedPieceIdentifier;
	}
}
