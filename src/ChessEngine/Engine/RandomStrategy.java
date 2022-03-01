package ChessEngine.Engine;

import java.util.ArrayList;
import java.util.Random;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;

public class RandomStrategy implements MoveCalculationStrategy{
	Game strategyGame;
	
	@Override
	public Move calculatedMove() {
		ArrayList<Move> possibleMoves = strategyGame.possibleLegalMoves();

		Random rn = new Random();
		int randomIndex = rn.nextInt(possibleMoves.size());
		return possibleMoves.get(randomIndex);
	}

	@Override
	public void setGame(Game game){
		strategyGame = game;
	}

}
