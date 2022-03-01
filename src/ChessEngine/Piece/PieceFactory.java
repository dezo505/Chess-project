package ChessEngine.Piece;

import ChessEngine.Game.Game;

public class PieceFactory {
	public static Piece createPiece(Game game, int hor, int ver ,char color, char pieceSymbol) {
		if(color == 'n' || color == 'N') {
			return new EmptyTile(game, hor, ver);
		}
		
		if(color == 'w' || color == 'W') {
			if(pieceSymbol == 'r' || pieceSymbol == 'R') {
				return new WhiteRook(game, hor, ver);
			}else if(pieceSymbol == 'b' || pieceSymbol == 'B') {
				return new WhiteBishop(game, hor, ver);
			}else if(pieceSymbol == 'n' || pieceSymbol == 'N') {
				return new WhiteKnight(game, hor, ver);
			}else if(pieceSymbol == 'q' || pieceSymbol == 'Q') {
				return new WhiteQueen(game, hor, ver);
			}else if(pieceSymbol == 'p' || pieceSymbol == 'P') {
				return new WhitePawn(game, hor, ver);
			}else if(pieceSymbol == 'k' || pieceSymbol == 'K') {
				return new WhiteKing(game, hor, ver);
			}
		}
		
		if(color == 'b' || color == 'B') {
			if(pieceSymbol == 'r' || pieceSymbol == 'R') {
				return new BlackRook(game, hor, ver);
			}else if(pieceSymbol == 'b' || pieceSymbol == 'B') {
				return new BlackBishop(game, hor, ver);
			}else if(pieceSymbol == 'n' || pieceSymbol == 'N') {
				return new BlackKnight(game, hor, ver);
			}else if(pieceSymbol == 'q' || pieceSymbol == 'Q') {
				return new BlackQueen(game, hor, ver);
			}else if(pieceSymbol == 'p' || pieceSymbol == 'P') {
				return new BlackPawn(game, hor, ver);
			}else if(pieceSymbol == 'k' || pieceSymbol == 'K') {
				return new BlackKing(game, hor, ver);
			}
		}
		
		System.out.println("Incorrect usage of PieceFactory: \ndata: \n" +
							hor + " " + ver + " " + color + " " + pieceSymbol);
		return null;
	}
}
