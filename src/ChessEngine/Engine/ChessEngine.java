package ChessEngine.Engine;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;

public class ChessEngine {
	Game actualGame;
	MoveCalculationStrategy strategy;
	
	public ChessEngine(Game game){
		actualGame = game;
	}
	
	public Move getMove() {
		return strategy.calculatedMove();
	}
	
	public void setStrategy(MoveCalculationStrategy newStrategy) {
		newStrategy.setGame(actualGame);
		strategy = newStrategy;
	}
}

