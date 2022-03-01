package ChessEngine.Engine;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;

public interface MoveCalculationStrategy {
	Move calculatedMove();
	void setGame(Game game);
}
