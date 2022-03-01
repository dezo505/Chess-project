package ChessEngine.Move;

import ChessEngine.Game.Game;

//class that creates object of piece interface
public class MoveFactory {
	public static Move createPeaceMove(Game game, int hor1, int ver1, int hor2, int ver2) {
		return new PeaceMove(game, hor1, ver1, hor2, ver2);
	}
	
	public static Move createCatchMove(Game game, int hor1, int ver1, int hor2, int ver2) {
		return new CatchMove(game, hor1, ver1, hor2, ver2);
	}
	
	public static Move createNOMCapedPeaceMove(Game game, int hor1, int ver1, int hor2, int ver2) {
		return new NOMcapedPeaceMove(game, hor1, ver1, hor2, ver2);
	}
	
	public static Move createEnPassantMove(Game game, int hor1, int ver1, int hor2, int ver2, char color) {
		if(color == 'W' || color == 'w') {
			return new WhiteEnPassantMove(game, hor1, ver1, hor2, ver2);
		}else if(color == 'B' || color == 'b') {
			return new BlackEnPassantMove(game, hor1, ver1, hor2, ver2);
		}
		
		System.out.println("Move Factory used incorrectly: \n"
							+ "Data:\n" 
							+ hor1 + " " + ver1 + " " + hor2 + " " + ver2 + " " + color);
		return null;
	}
	
	public static Move createPromotionMove(Game game, int hor1, int ver1, int hor2, int ver2, char color, char pieceType, boolean capture) {
		if(color == 'W' || color == 'w') {
			return (capture)?new WhitePromotionCatchMove(game, hor1, ver1, hor2, ver2, pieceType): new WhitePromotionPeaceMove(game, hor1, ver1, hor2, ver2, pieceType);
		}else if(color == 'B' || color == 'b') {
			return (capture)?new BlackPromotionCatchMove(game, hor1, ver1, hor2, ver2, pieceType): new BlackPromotionPeaceMove(game, hor1, ver1, hor2, ver2, pieceType);
		}
		
		System.out.println("Move Factory used incorrectly: \n"
				+ "Data:\n" 
				+ hor1 + " " + ver1 + " " + hor2 + " " + ver2 + " " + color + " " + pieceType);
		return null;
	}
	
	public static Move createCastling(Game game, Move kingMove, Move rookMove, int color, int[][] attackedTiles, int[][] emptyTiles) {
		return new Castling(game, kingMove, rookMove, color, attackedTiles, emptyTiles);
	}
}
