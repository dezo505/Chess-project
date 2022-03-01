package ChessEngine.Engine;

public class StrategyFactory {
	public static MoveCalculationStrategy createStrategy(String strategy) {
		if(strategy == "random") {
			return new RandomStrategy();
		}else if(strategy == "minimax") {
			return new MiniMaxStrategy();
		}
		
		System.out.println("Incorrect usage of Strategy Factory \ndata: " + strategy);
		return null;
	}
}
