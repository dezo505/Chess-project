package ChessEngine.Piece;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;
import ChessEngine.Move.MoveFactory;

public class BlackKnight extends Piece{
	private static final long serialVersionUID = 5654805190359768122L;

	BlackKnight(Game game, int hor, int ver){
		horizontalCoordinate = hor;
		verticalCoordinate = ver;
		this.game = game;
		setAlive(true);
	}

	@Override
	public int getColor() {
		return -1;
	}

	@Override
	public void updateMoves() {
		possibleMoves.clear();
		if(alive == false)return;
		
		Move tempMove;
		
		int tiles[][] = new int[][]{new int[]{-1, -2}, new int[] {1, -2}, new int[]{-1, 2}, new int[]{1, 2},
						new int[]{-2, -1}, new int[] {-2, 1}, new int[]{2, -1}, new int[]{2, 1}};
		
		for(int[] tile: tiles) {
			tempMove = MoveFactory.createPeaceMove(game, getHorCord(), getVerCord(), getHorCord() + tile[0], getVerCord() + tile[1]);
			if(tempMove.isLegal())possibleMoves.add(tempMove);
			else {
				tempMove =  MoveFactory.createCatchMove(game, getHorCord(), getVerCord(), getHorCord() + tile[0], getVerCord() + tile[1]);
				if(tempMove.isLegal())possibleMoves.add(tempMove);
			}
		}
	}

	@Override
	public String getSymbol() {
		return "N";
	}

	@Override
	public char getIdentifier() {
		return 'n';
	}
}
