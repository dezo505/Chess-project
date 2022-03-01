package ChessEngine.Piece;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;
import ChessEngine.Move.MoveFactory;

public class WhiteRook extends Piece{
	private static final long serialVersionUID = -2155051963189714289L;

	WhiteRook(Game game, int hor, int ver){
		horizontalCoordinate = hor;
		verticalCoordinate = ver;
		this.game = game;
		setAlive(true);
	}
	
	@Override
	public int getColor() {
		return 1;
	}
	
	@Override
	public char getIdentifier() {
		return 'R';
	}

	@Override
	public void updateMoves() {
		possibleMoves.clear();
		if(alive == false)return;
		
		Move tempMove;
		
		int tiles[][] = new int[][]{new int[]{0, 1}, new int[] {0, -1}, new int[]{1, 0}, new int[]{-1, 0}};
		for(int[] tile: tiles) {
			for(int i = 1; (getHorCord() + i * tile[0] < 8) && (getHorCord() + i * tile[0] >= 0) && (getVerCord() + i * tile[1] < 8) && (getVerCord() + i * tile[1] >= 0) ; i++) {
				tempMove = MoveFactory.createPeaceMove(game, getHorCord(), getVerCord(), getHorCord() + i * tile[0], getVerCord() + i * tile[1]);
				if(tempMove.isLegal())possibleMoves.add(tempMove);
				else {
					tempMove =  MoveFactory.createCatchMove(game, getHorCord(), getVerCord(), getHorCord() + i * tile[0], getVerCord() + i * tile[1]);
					if(tempMove.isLegal())possibleMoves.add(tempMove);
					break;
				}
			}
		}	
	}

	@Override
	public String getSymbol() {
		return "R";
	}

}
