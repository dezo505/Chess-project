package ChessEngine.Move;

import ChessEngine.Game.Game;

public interface Move {
	void executeOn();	//execute move on game (game reference is in every class that implements Move)
	void undoOn();		//undo move on game
	
	//return move in form of readable string
	String toReadableString();
	
	//returns two values in form of tile string 
	default String tileToString(int hor, int ver){
		String result = "";
		
		result += (char)(hor + 'a');
		result += String.valueOf(8 - ver);
		
		return result;
	}
	void setGame(Game g);
	
	boolean isLegal();	//checks if move is legal (without checking if move will cause illegal king move)
	boolean isCatch();	//return true if move will cause capture of piece
	
	int getStartHor();	//return starting horizontal piece position
	int getStartVer();	//return starting vertical piece position
	int getFinalHor();	//return final horizontal piece position
	int getFinalVer();	//return final vertical piece position
	
	//return value of 4 functions above in one array
	default int[][] getMoveTiles(){
		return new int[][]{
			new int[] {getStartHor(), getStartVer()},
			new int[] {getFinalHor(), getFinalVer()}
		};
	}
}
