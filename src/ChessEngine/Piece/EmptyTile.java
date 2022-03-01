package ChessEngine.Piece;

import ChessEngine.Game.Game;

public class EmptyTile extends Piece{
	private static final long serialVersionUID = 2328148029236889880L;

	EmptyTile(Game game, int hor, int ver){
		verticalCoordinate = ver;
		horizontalCoordinate = hor;
		this.game = game;
		setAlive(false);
	}
	
	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public void updateMoves() {
		possibleMoves.clear();
	}

	@Override
	public String getSymbol() {
		return "EMPTY";
	}

	@Override
	public char getIdentifier() {
		return 'X';
	}
	
	@Override
	public boolean getAlive() {
		return false;
	}
	
}
