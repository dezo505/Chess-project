package ChessEngine.Piece;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;
import ChessEngine.Move.MoveFactory;

public class WhiteKing extends Piece{
	private static final long serialVersionUID = -1969594127687187219L;

	WhiteKing(Game game, int hor, int ver){
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
	public void updateMoves() {
		possibleMoves.clear();
		if(alive == false)return;
		
		int[][] tiles = new int[][] {
			new int[] {1 , 1},
			new int[] {1 ,-1},
			new int[] {-1, 1},
			new int[] {-1,-1},
			new int[] {0 , 1},
			new int[] {0 ,-1},
			new int[] {1 , 0},
			new int[] {-1, 0}
			};
			
			Move tempMove;
			
			for(int[] tile: tiles) {
				tempMove = MoveFactory.createPeaceMove(game, getHorCord(), getVerCord(), getHorCord() + tile[0], getVerCord() + tile[1]);
				if(tempMove.isLegal())possibleMoves.add(tempMove);
				else {
					tempMove = MoveFactory.createCatchMove(game, getHorCord(), getVerCord(), getHorCord() + tile[0], getVerCord() + tile[1]);
					if(tempMove.isLegal())possibleMoves.add(tempMove);
				}
			}
			
			tempMove = MoveFactory.createCastling(game, MoveFactory.createPeaceMove(game, 4, 7, 6, 7), MoveFactory.createPeaceMove(game, 7, 7, 5, 7), 1,
					new int[][]{new int[]{4,7}, new int[] {5,7}, new int[] {6,7}},
					new int[][]{new int[]{5,7}, new int[] {6,7}});
			
			if(tempMove.isLegal())possibleMoves.add(tempMove);
			
			
			tempMove = MoveFactory.createCastling(game, MoveFactory.createPeaceMove(game, 4, 7, 2, 7), MoveFactory.createPeaceMove(game, 0, 7, 3, 7), 1,
					new int[][]{new int[]{4,7}, new int[] {3,7}, new int[] {2,7}},
					new int[][]{new int[]{3,7}, new int[] {2,7}, new int[] {1,7}});
			
			if(tempMove.isLegal())possibleMoves.add(tempMove);
			
	}

	@Override
	public String getSymbol() {
		return "K";
	}

	@Override
	public char getIdentifier() {
		return 'K';
	}

}
