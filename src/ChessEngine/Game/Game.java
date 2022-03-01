package ChessEngine.Game;

import java.io.Serializable;
import java.util.ArrayList;

import ChessEngine.Move.Move;
import ChessEngine.Piece.Piece;

//Class that holds information about a game of chess
public class Game implements Serializable, Cloneable{		
	private static final long serialVersionUID = 1L;

	private Piece[][] board;		//classic 8x8 board of chess
	
	private ArrayList<Move> history;		//history of executed moves on board
	private ArrayList<char[][]> statesHistory; 		//history of "Visual" ook of board
	private int movesSinceCap;				//moves since last move that was pawn move or capture move
	
	
	private Piece whiteKing;			//white king reference (he is also on board as other pieces)
	private Piece blackKing;			//black king -||-
	
	private int onMove;					//color of player who is on play (-1 = BLACK, 1 = WHITE)
	
	Game(){
		movesSinceCap = 0;				
		onMove = 1;						//white starts
		board = new Piece[8][8];		//by default board is 8x8
		history = new ArrayList<Move>();			
		statesHistory = new ArrayList<char[][]>();
	}
	
	//Swaps to pieces on board, and update their positions
	public void swapTiles(int hor1, int ver1, int hor2, int ver2) {		
		Piece temp = board[hor1][ver1];
		
		board[hor1][ver1] = board[hor2][ver2];
		
		board[hor2][ver2] = temp;
		
		board[hor1][ver1].setHorCord(hor1);
		board[hor1][ver1].setVerCord(ver1);
		
		board[hor2][ver2].setHorCord(hor2);
		board[hor2][ver2].setVerCord(ver2);
	}
	
	//Changes player on move
	public void changeTurn() {
		onMove *= -1;
	}
	
	//board getter
	public Piece getTile(int hor, int ver) {
		return board[hor][ver];
	}
	
	//board setter
	public void setTile(int hor, int ver, Piece newPiece) {
		board[hor][ver] = newPiece;
	}
	
	//king setter
	public void setKing(int color, Piece king) {
		if(color == 1) {
			whiteKing = king;
		}else {
			blackKing = king;
		}
	}
	
	//history of moves getter
	public Move getLastMove() {
		return history.get(history.size() - 1);
	}
	
	//add move to both of histories and update movesSince last Cap variable
	public void addMove(Move move) {
		history.add(move);
		movesSinceCap = (move == null || move.isCatch() || board[move.getFinalHor()][move.getFinalVer()].getSymbol() == "P")? 0: movesSinceCap + 1;
		statesHistory.add(getBoardRepresentation());
	}
	
	//remove last added move from history
	public void removeLastMove() {
		history.remove(history.size() - 1);
		movesSinceCap = Math.max(movesSinceCap - 1, 0);
		statesHistory.remove(statesHistory.size() - 1);
	}
	
	//returns length of history (number of done moves)
	public int getGameLength() {
		return history.size();
	}
	
	//checks if target tile is attacked from color perspective
	//for example if isTileAttacked(1,3,4) will check if piece on board[3][4] is attacked by black
	public Boolean isTileAttacked(int color, int hor, int ver){
		if(color == -1) {
			for(Piece[] row: board) {
				for(Piece p: row) {
					if(p.getColor() == 1 && p.getAlive() == true && p.getSymbol() != "P") {
						for(Move m: p.getPossibleMoves()){
							if(m.getFinalHor() == hor && m.getFinalVer() == ver)return true;
						}
					}
				}
			}
			//Pawns are the only pieces that can't cap the same way as they are moving so we have to check it manually
			if(ver != 7) {		//checking extreme cases
				if(hor != 0) {
					if(board[hor - 1][ver + 1].getIdentifier() == 'P' && board[hor - 1][ver + 1].getAlive() == true)return true;
				}
				if(hor != 7) {
					if(board[hor + 1][ver + 1].getIdentifier() == 'P' && board[hor + 1][ver + 1].getAlive() == true)return true;
				}
			}
			return false;
		}else if(color == 1) {				//same but for white
			for(Piece[] row: board) {
				for(Piece p: row) {
					if(p.getColor() == -1 && p.getAlive() == true && p.getSymbol() != "P") {
						for(Move m: p.getPossibleMoves()) {
								if(m.getFinalHor() == hor && m.getFinalVer() == ver)return true;				
						}
					}
				}
			}
			if(ver != 0) {
				if(hor != 0) {
					if(board[hor - 1][ver - 1].getIdentifier() == 'p' && board[hor - 1][ver - 1].getAlive() == true)return true;
				}
				if(hor != 7) {
					if(board[hor + 1][ver - 1].getIdentifier() == 'p' && board[hor + 1][ver - 1].getAlive() == true)return true;
				}
			}
			return false;
		}
		
		System.out.println("Incorrect usage of isTileAttacked() method\n"
							+ "data:\n"
							+ color + " " + hor + " " + ver);
		return null;
	}
	
	//returns current board "look" in form of 2 dimensional table
	public char[][] getBoardRepresentation(){ 
		char[][] result = new char[8][8];	
		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				result[x][y] = board[x][y].getIdentifier();
			}
		}
		return result;
	}
	
	//returns tile representation for ex. if white  King is on 4,5 f(4,5) will return 'K'
	public char getTileRepresentation(int hor, int ver){
		if(board[hor][ver].getAlive() == false)return 'X';
		return board[hor][ver].getIdentifier();
	}
	
	//update all pieces moves
	public void updateMoves() {
		for(int hor = 0; hor < 8; hor++) {
			for(int ver = 0; ver < 8; ver++) {
				if(board[hor][ver].getSymbol() != "K")board[hor][ver].updateMoves();	//king have to be updated as last because of castling specification
				else board[hor][ver].clearMoves();
			}
		}
		whiteKing.updateMoves();
		blackKing.updateMoves();
	}
	
	//current possible moves in form of readable string
	public ArrayList<String> possibleMovesString() {
		ArrayList<String> result = new ArrayList<String>();
		for(int hor = 0; hor < 8; hor++) {
			for(int ver = 0; ver < 8; ver++) {
				for(Move m: board[ver][hor].getPossibleMoves()) {
					if(onMove == board[ver][hor].getColor())
					result.add(m.toReadableString());
				}
			}
		}
		return result;
	}
	
	//returns possible moves
	public ArrayList<Move> possibleMoves(){
		ArrayList<Move> result = new ArrayList<Move>();
		for(int ver = 0; ver < 8; ver++) {
			for(int hor = 0; hor < 8; hor++) {
				for(Move m: board[hor][ver].getPossibleMoves()) {
					if(onMove * board[hor][ver].getColor() != -1) {
						result.add(m);
					}
				}
			}
		}
		return result;
	}
	
	//returns possible legal moves (moves that won't cause possibility of king to be captred)
	public ArrayList<Move> possibleLegalMoves(){
		ArrayList<Move> result = new ArrayList<Move>();
		
		for(int ver = 0; ver < 8; ver++) {
			for(int hor = 0; hor < 8; hor++) {
				ArrayList<Move> checkedMoves = board[hor][ver].getPossibleMoves();
				Move m;
				for(int i = 0; i < checkedMoves.size(); i++) {
					if(onMove * board[hor][ver].getColor() != -1) {
						m = checkedMoves.get(i);
						m.executeOn();
						updateMoves();
						if(!isKingChecked(-onMove))result.add(m);
						m.undoOn();
						updateMoves();
					}
				}
			}
		}
		return result;
	}
	
	//returns current player color
	public int getOnMove() {
		return onMove;
	}
	
	//checks if king of given color is under check
	public Boolean isKingChecked(int color) {
		if(color == 1) {
			return isTileAttacked(color, whiteKing.getHorCord(), whiteKing.getVerCord());
		}else if(color == -1) {
			return isTileAttacked(color, blackKing.getHorCord(), blackKing.getVerCord());
		}
		return null;
	}
	
	//checks if player of given color hace any legal move
	public Boolean isThereLegalMove(int color){
		for(Move m: possibleMoves()) {
			m.executeOn();
			updateMoves();
			if(!isKingChecked(color)) {
				m.undoOn();
				updateMoves();
				return true;
			}
			updateMoves();
			m.undoOn();
		}
		return false;
	}
	
	//checks if there should be a draw because of 40 moves
	private boolean is40MovesDraw() {
		return (movesSinceCap >= 40)? true: false;
	}
	
	//Checks if LAST MOVE should cause repetition draw
	private boolean is3RepeatDraw() {			
		if(statesHistory.size() == 0)return false;
		char[][] last = statesHistory.get(statesHistory.size() - 1);
		char[][] current;
		int count = 1;
		boolean temp;
		
		for(int i = 0; i < statesHistory.size() - 1; i++) {
			current = statesHistory.get(i);
			temp = true;
			for(int x = 0; x < 8; x++) {
				for(int y = 0; y < 8; y++) {
					if(current[x][y] != last[x][y])temp = false;
				}
			}
			if(temp && statesHistory.size()%2 != i%2){
				count++;
				if(count == 3)return true;
			}	
		}
		return false;
	}
	//checks if game should end
	//0 - pending, 1 - white wins, 2 - black wins, 3 - draw
	public int getGameResult() { 
		if(!isThereLegalMove(onMove)) {
			if(isKingChecked(onMove)) {
				return (onMove == -1)?1:2;
			}else {
				return 3;
			}
		}else if(is3RepeatDraw() || is40MovesDraw()) {
			return 3;
		}
		return 0;
	}
	
	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
