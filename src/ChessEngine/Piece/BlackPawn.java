package ChessEngine.Piece;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;
import ChessEngine.Move.MoveFactory;

public class BlackPawn extends Piece{
	private static final long serialVersionUID = 7164487704287859126L;

	BlackPawn(Game game, int hor, int ver){
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
		tempMove = MoveFactory.createPeaceMove(game, getHorCord(), getVerCord(), getHorCord(), getVerCord() + 1);
		if(tempMove.isLegal()) {				//FORWARD MOVE
			if(MoveFactory.createPromotionMove(game, getHorCord(), getVerCord(), getHorCord(), getVerCord() + 1, 'B', 'Q', false).isLegal()) {
				char[] types = new char[] {'B', 'N', 'R', 'Q'};
				for(char type: types) {
					possibleMoves.add(MoveFactory.createPromotionMove(game, getHorCord(), getVerCord(), getHorCord(), getVerCord() + 1, 'B', type, false));
				}
			}else {
				possibleMoves.add(tempMove);
				tempMove = MoveFactory.createNOMCapedPeaceMove(game, getHorCord(), getVerCord(), getHorCord(), getVerCord() + 2);
				if(tempMove.isLegal())possibleMoves.add(tempMove);
			}
		}
		
		tempMove = MoveFactory.createCatchMove(game, getHorCord(), getVerCord(), getHorCord() + 1, getVerCord() + 1);		//CATCH LEFT
		if(tempMove.isLegal()) {
			if(MoveFactory.createPromotionMove(game, getHorCord(), getVerCord(), getHorCord() + 1, getVerCord() + 1, 'B', 'Q', true).isLegal()) {
				char[] types = new char[] {'B', 'N', 'R', 'Q'};
				for(char type: types) {
					possibleMoves.add(MoveFactory.createPromotionMove(game, getHorCord(), getVerCord(), getHorCord() + 1, getVerCord() + 1, 'B', type, true));
				}
			}else {
				possibleMoves.add(tempMove);
			}
		}
		
		tempMove = MoveFactory.createCatchMove(game, getHorCord(), getVerCord(), getHorCord() - 1, getVerCord() + 1);		//CATCH RIGHT
		if(tempMove.isLegal()) {
			if(MoveFactory.createPromotionMove(game, getHorCord(), getVerCord(), getHorCord() - 1, getVerCord() + 1, 'B', 'Q', true).isLegal()) {
				char[] types = new char[] {'B', 'N', 'R', 'Q'};
				for(char type: types) {
					possibleMoves.add(MoveFactory.createPromotionMove(game, getHorCord(), getVerCord(), getHorCord() - 1, getVerCord() + 1, 'B', type, true));
				}
			}else {
				possibleMoves.add(tempMove);
			}
		}

								//CATCH EN PASSANT
		tempMove = MoveFactory.createEnPassantMove(game, getHorCord(), getVerCord(), getHorCord() - 1, getVerCord() + 1, 'B');
		if(tempMove.isLegal())possibleMoves.add(tempMove);
		
		tempMove = MoveFactory.createEnPassantMove(game, getHorCord(), getVerCord(), getHorCord() + 1, getVerCord() + 1, 'B');
		if(tempMove.isLegal())possibleMoves.add(tempMove);
	}

	@Override
	public String getSymbol() {
		return "P";
	}

	@Override
	public char getIdentifier() {
		return 'p';
	}
}