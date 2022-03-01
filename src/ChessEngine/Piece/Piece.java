package ChessEngine.Piece;

import java.io.Serializable;
import java.util.ArrayList;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;

public abstract class Piece implements Serializable, Cloneable{
	private static final long serialVersionUID = -7053627518591129278L;

	protected int horizontalCoordinate;
	protected int verticalCoordinate;
	
	protected ArrayList<Move> possibleMoves;
	
	protected int numberOfMoves;	//number of moves that piece have done in a game
	
	protected Game game;
	
	protected boolean alive;
	
	public Piece() {
		possibleMoves = new ArrayList<Move>();
	}
	
	public int getHorCord() {
		return horizontalCoordinate;
	}
	
	public int getVerCord() {
		return verticalCoordinate;
	}
	
	public void setHorCord(int horCord) {
		this.horizontalCoordinate = horCord;
	}
	
	public void setVerCord(int verCord) {
		this.verticalCoordinate = verCord;
	}
	
	public void increaseMovesNumber() {
		numberOfMoves++;
	}
	
	public void decreaseMovesNumber() {
		numberOfMoves--;
	}
	
	public int getMovesNumber() {
		return numberOfMoves;
	}
	
	public ArrayList<Move> getPossibleMoves(){
		return possibleMoves;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public boolean getAlive() {
		return alive;
	}
	
	public String toReadableString() {
		return  "HOR: " + horizontalCoordinate + "\n" +
				"VER: " + verticalCoordinate + "\n" +
				"COL: " + getColor() + "\n" + 
				"ID: "  + getIdentifier()  + "\n";
	}
	
	public abstract int getColor();
	
	public abstract void updateMoves();
	
	public void clearMoves() {
		possibleMoves.clear();
	}
	
	public abstract String getSymbol();
	
	public abstract char getIdentifier();
	
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
