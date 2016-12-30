package com.nullpointergames.boardgames.checkers.rules;

import static com.nullpointergames.boardgames.checkers.PieceType.KING;
import static java.lang.Math.abs;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.checkers.PieceType;

public class KingRule extends Rule {

	public KingRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return KING;
	}
	
	@Override
	public void aditionalMoves(Board board, Position from, Position to) {
		if(abs(to.col() - from.col()) > 1) {
			char rookColFrom = from.col() - to.col() > 0 ? 'a' : 'h';
			char rookColTo = from.col() - to.col() > 0 ? 'd' : 'f';
			
			Piece rook = board.getPiece(new Position(rookColFrom, to.row()));
			rook.setFirstMove(false);
			board.put(rook, new Position(rookColTo, to.row()));
			board.clear(new Position(rookColFrom, to.row()));
		}
	}
	
	@Override
	public List<Move> possibleMoves() {
		addPosition(+1, +1);
		addPosition(+1, -1);
		addPosition(-1, +1);
		addPosition(-1, -1);
		
		return possibleMoves;
	}

	protected void addPosition(int colIncrement, int rowIncrement) {
		int rowTo = from.row();
		int colTo = abs(from.col());
		
		while(true) {
			try {
				colTo += colIncrement;
				rowTo += rowIncrement;
				
				Piece anotherPiece = getPiece(board, (char)colTo, rowTo);
				
//				if(anotherPiece.color() == NULL)
//					possibleMoves.add(newPosition((char)colTo, rowTo));
//				else {
//					if(anotherPiece.color() != color) {
//						colTo += colIncrement;
//						rowTo += rowIncrement;
//						
//						anotherPiece = getPiece(board, (char)colTo, rowTo);
//						
//						if(anotherPiece.color() == NULL)
//							possibleMoves.add(newPosition((char)colTo, rowTo));
//					} else {
//						return;
//					}
//				}
			} catch (RuntimeException e) {
				return;
			}
		}
	}
	
	protected boolean canMove(Piece anotherPiece) {
		return anotherPiece.color() != color;
	}
}
