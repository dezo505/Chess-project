package ChessEngine.Game;

import ChessEngine.Piece.PieceFactory;

// class that creates chess games
public class GameFactory {
	//create classic chess game
	public static Game createDefaultGame() {
		Game newGame = new Game();
		
		newGame.setTile(0, 0, PieceFactory.createPiece(newGame, 0, 0, 'B', 'R'));
		newGame.setTile(1, 0, PieceFactory.createPiece(newGame, 1, 0, 'B', 'N'));
		newGame.setTile(2, 0, PieceFactory.createPiece(newGame, 2, 0, 'B', 'B'));
		newGame.setTile(3, 0, PieceFactory.createPiece(newGame, 3, 0, 'B', 'Q'));
		newGame.setTile(4, 0, PieceFactory.createPiece(newGame, 4, 0, 'B', 'K'));
		newGame.setTile(5, 0, PieceFactory.createPiece(newGame, 5, 0, 'B', 'B'));
		newGame.setTile(6, 0, PieceFactory.createPiece(newGame, 6, 0, 'B', 'N'));
		newGame.setTile(7, 0, PieceFactory.createPiece(newGame, 7, 0, 'B', 'R'));
		
		for(int hor = 0; hor < 8; hor++) {
			newGame.setTile(hor, 1, PieceFactory.createPiece(newGame, hor, 1, 'B', 'P'));
		}
		
		
		newGame.setTile(0, 7, PieceFactory.createPiece(newGame, 0, 7, 'W', 'R'));
		newGame.setTile(1, 7, PieceFactory.createPiece(newGame, 1, 7, 'W', 'N'));
		newGame.setTile(2, 7, PieceFactory.createPiece(newGame, 2, 7, 'W', 'B'));
		newGame.setTile(3, 7, PieceFactory.createPiece(newGame, 3, 7, 'W', 'Q'));
		newGame.setTile(4, 7, PieceFactory.createPiece(newGame, 4, 7, 'W', 'K'));
		newGame.setTile(5, 7, PieceFactory.createPiece(newGame, 5, 7, 'W', 'B'));
		newGame.setTile(6, 7, PieceFactory.createPiece(newGame, 6, 7, 'W', 'N'));
		newGame.setTile(7, 7, PieceFactory.createPiece(newGame, 7, 7, 'W', 'R'));
		
		for(int hor = 0; hor < 8; hor++) {
			newGame.setTile(hor, 6, PieceFactory.createPiece(newGame, hor, 6, 'W', 'P'));
		}
		
		
		for(int hor = 0; hor < 8; hor++) {
			for(int ver = 2; ver <= 5; ver++) {
				newGame.setTile(hor, ver, PieceFactory.createPiece(newGame, hor, ver, 'N', '-'));
			}
		}
		
		newGame.setKing( 1, newGame.getTile(4, 7));
		newGame.setKing(-1, newGame.getTile(4, 0));
		
		return newGame;
	}
}
