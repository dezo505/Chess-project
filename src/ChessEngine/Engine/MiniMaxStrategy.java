package ChessEngine.Engine;

import java.util.*;

import ChessEngine.Game.Game;
import ChessEngine.Move.Move;
import ChessEngine.Piece.Piece;

public class MiniMaxStrategy implements MoveCalculationStrategy {
	private Game strategyGameClone;
	private Game strategyGameOrginal;
	private HashMap<Character,Double> piecesValue;
	
	public MiniMaxStrategy() {
		piecesValue = new HashMap<Character, Double>();
		
		piecesValue.put('P',  1.0);
		piecesValue.put('p', -1.0);
		piecesValue.put('N',  3.0);
		piecesValue.put('n', -3.0);
		piecesValue.put('B',  3.0);
		piecesValue.put('b', -3.0);
		piecesValue.put('R',  5.0);
		piecesValue.put('r', -5.0);
		piecesValue.put('Q',  9.0);
		piecesValue.put('q', -9.0);
		piecesValue.put('K',  200.0);
		piecesValue.put('k', -200.0);
		piecesValue.put('X',  0.0);
		piecesValue.put('x',  0.0);
	}
	
	@Override
	public Move calculatedMove() {
		MoveTree calculation = new MoveTree((3) + 1, null, null);
		Move result = calculation.getKidsMove();
		result.setGame(strategyGameOrginal);
		return result;
	}

	@Override
	public void setGame(Game game) {
		strategyGameOrginal = game;
		strategyGameClone = (Game)(strategyGameOrginal).clone();
	}

	class MoveTree{
		private ArrayList<MoveTree> kids;
		
		private Move branchMove;
		
		
		private int length;
		
		private Double branchValue;
		
		public MoveTree(int parentLength, Move move, MoveTree parent){
			
			length = parentLength - 1;
			branchMove = move;
			
			ArrayList<Move> moves = (ArrayList<Move>)(strategyGameClone.possibleLegalMoves().clone());
			
			if(length == 0 || moves.size() == 0) {
				branchValue = evaluatePosition();
			}else {		//create kids
				kids = new ArrayList<MoveTree>();
				for(Move m: moves) {
					if(m.isLegal()) {
						m.executeOn();
						kids.add(new MoveTree(length, m, this));
						m.undoOn();
					}
				}
			}
			
			if(parent != null) {
				if(strategyGameClone.getOnMove() == 1) {
					if(parent.getBranchValue() == null || parent.getBranchValue() < branchValue)
						parent.setBranchValue(branchValue);
				}else {
					if(parent.getBranchValue() == null || parent.getBranchValue() > branchValue)
						parent.setBranchValue(branchValue);
				}
			}
		}
		
		public void setBranchValue(double value) {
			branchValue = value;
		}
		
		public Double getBranchValue() {
			return branchValue;
		}
		
		public Move getMove() {
			return branchMove;
		}
		
		public Move getKidsMove(){
			Move result = null;
			Double max = null;
			for(MoveTree kid: kids){
				System.out.println(kid.getBranchValue());
				if(max == null || kid.getBranchValue() * strategyGameClone.getOnMove() > max) {
					max = kid.getBranchValue() * strategyGameClone.getOnMove();
					result = kid.getMove();
					System.out.println(kid.getBranchValue());
				}
			}
			return result;
		}
		
		double evaluatePosition() {
			int gameResult = strategyGameClone.getGameResult();
			/*if(gameResult != 0) {
				if(gameResult == 3)return 0;
				else if(gameResult == 2)return 1000;
				else if(gameResult == 1)return -1000;
			}*/
			
			double piecesValueSum = 0.0;
			
			for(int x = 0; x< 8; x++) {
				for(int y = 0; y < 8; y++) {
					Piece p = strategyGameClone.getTile(x, y);
					if(p.getAlive())piecesValueSum += piecesValue.get(p.getIdentifier());
				}
			}
			
			
			double moveNumberSum = 0.0;
			
			for(int x = 0; x < 8; x++) {
				for(int y = 0; y < 8; y++) {
					Piece p = strategyGameClone.getTile(x, y);
					if(p.getColor() == 1 && p.getAlive()) {
						for(Move m: p.getPossibleMoves()) {
							if(m.isCatch())moveNumberSum += 0.2;
							else moveNumberSum += 0.1;
						}
					}else if(p.getColor() == -1 && p.getAlive()) {
						for(Move m: p.getPossibleMoves()) {
							if(m.isCatch())moveNumberSum -= 0.2;
							else moveNumberSum -= 0.1;
						}
					}
				}
			}
			
			double totalSum = piecesValueSum + moveNumberSum;
			return totalSum;
		}
	}
}
