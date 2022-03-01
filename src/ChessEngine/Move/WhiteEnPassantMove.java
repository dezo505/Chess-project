package ChessEngine.Move;

import ChessEngine.Game.Game;
import ChessEngine.Piece.Piece;

//white pawn en passant move - additionally checks some circumstances and capture on another position than final
public class WhiteEnPassantMove implements Move, Cloneable{
	private int startHorCord;
	private int startVerCord;
	
	private int finalHorCord;
	private int finalVerCord;
	
	private Game game;
	
	WhiteEnPassantMove(Game game, int startHor, int startVer, int finalHor, int finalVer){
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
		
		game.getTile(finalHorCord, startVerCord).setAlive(false);
		game.changeTurn();
		game.addMove(this);
	}
	
	@Override
	public void undoOn() {
		Piece movingPiece = game.getTile(finalHorCord, finalVerCord);
		movingPiece.decreaseMovesNumber();
		game.swapTiles(startHorCord, startVerCord, finalHorCord, finalVerCord);
		game.getTile(finalHorCord, startVerCord).setAlive(true);
		game.changeTurn();
		game.removeLastMove();
	}
	
	@Override
	public String toReadableString() {
		String result = game.getTile(startHorCord, startVerCord).getSymbol();
		result += tileToString(startHorCord, startVerCord);
		result += "::";
		result += tileToString(finalHorCord, finalVerCord);
		return result;
	}
	
	@Override
	public boolean isLegal(){
		if(startVerCord != 3)return false;		//en passant can only occur when white pawn is on 3 row
		if(finalHorCord < 0 || finalHorCord > 7)return false;
		if(game.getGameLength() == 0)return false;	//en passant refer to last move so we have to check if there was one
		
		int[][] last = game.getLastMove().getMoveTiles();
		//checks if last move alow to make en passant move
		if(game.getLastMove() instanceof NOMcapedPeaceMove &&
			last[0][0] == finalHorCord && last[0][1] == finalVerCord - 1 && last[1][0] == finalHorCord && last[1][1] == finalVerCord + 1){
			return true;
		}

		return false;
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
}
