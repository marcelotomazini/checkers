package com.nullpointergames.boardgames.checkers.rules;

import static com.nullpointergames.boardgames.NullPiece.nullPiece;
import static com.nullpointergames.boardgames.PieceColor.NULL;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.checkers.PieceType.KING;
import static com.nullpointergames.boardgames.checkers.PieceType.NORMAL;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.NullPiece;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.checkers.PieceType;

public class NormalRule extends Rule {

	public NormalRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public PieceType pieceType() {
		return NORMAL;
	}
	
	@Override
	protected void aditionalMoves(Board board, Position from, Position to) {
		int positionToPromotion = board.getPieceColor(to) == WHITE ? 8 : 1;
		
		if(positionToPromotion == to.row()) {
			Piece piece = new Piece(KING, color);
			piece.setFirstMove(false);
			board.put(piece, to);
		}
		
		removeCapturedPiece(to);
		verifyCapture(to);
	}
	
	private void removeCapturedPiece(Position to) {
		int x = from.row() - to.row();
		if(abs(x) > 1) { //capturou peça
			for(int i = from.col(); i < to.col(); i++) {
				for(int j = from.row(); j < to.row(); j++) {
					Position position = new Position((char)i, j);
					Piece piece = board.getPiece(position);
					if(piece.color() != color)
						board.put(nullPiece(), position);
				}
			}
			for(int i = from.col(); i > to.col(); i--) {
				for(int j = from.row(); j > to.row(); j--) {
					Position position = new Position((char)i, j);
					Piece piece = board.getPiece(position);
					if(piece.color() != color)
						board.put(nullPiece(), position);
				}
			}
			for(int i = from.col(); i < to.col(); i++) {
				for(int j = from.row(); j > to.row(); j--) {
					Position position = new Position((char)i, j);
					Piece piece = board.getPiece(position);
					if(piece.color() != color)
						board.put(nullPiece(), position);
				}
			}
			for(int i = from.col(); i > to.col(); i--) {
				for(int j = from.row(); j < to.row(); j++) {
					Position position = new Position((char)i, j);
					Piece piece = board.getPiece(position);
					if(piece.color() != color)
						board.put(nullPiece(), position);
				}
			}
		}
	}

	private void verifyCapture(Position to) {
		List<Position> m = new NormalRule(board, to).possibleMoves();
		for(Position p : m) {
			int x = to.row() - p.row();
			if(abs(x) > 1)
				canChangeTurn = false;
		}
	}

	@Override
	protected boolean canMove(Piece anotherPiece, int col) {
		return (from.col() == col && anotherPiece.color() == NULL) ||
				(from.col() != col && anotherPiece.color() != NULL && anotherPiece.color() != color);
	}
	
	@Override
	public List<Position> possibleMoves() {
		addPosition(+1, +1);
		addPosition(+1, -1);
		addPosition(-1, +1);
		addPosition(-1, -1);
		
		return possibleMoves;
	}

	public void promoteTo(Board board, PieceType pieceType) {
		Position position = findPawnPositionToPromote(board);
		Piece promotedPiece = new Piece(pieceType, board.getPieceColor(position));
		board.put(promotedPiece, position);
	}

	protected void addPosition(int colIncrement, int rowIncrement) {
		int rowTo = from.row() + rowIncrement;
		int colTo = abs(from.col()) + colIncrement;
		
		if(!isInTheBoard(colTo, rowTo))
			return;
		
		Piece piece = board.getPiece(from);
		int moveDirection = piece.color() == WHITE ? 1 : -1;
		Piece anotherPiece = getPiece(board, (char)colTo, rowTo);
		
		if(!canMove(anotherPiece))
			return;
		
		if(anotherPiece.color() == NULL) {
			if(from.row() - rowTo != moveDirection)
				possibleMoves.add(newPosition((char)colTo, rowTo));
			return;
		}

		rowTo = from.row() + (rowIncrement * 2);
		colTo = abs(from.col()) + (colIncrement * 2);
		
		if(!isInTheBoard(colTo, rowTo))
			return;
		
		anotherPiece = getPiece(board, (char)colTo, rowTo);
		
		if(anotherPiece.color() == NULL) {
			possibleMoves.add(newPosition((char)colTo, rowTo));
		}
	}
	
	private boolean isInTheBoard(int colTo, int rowTo) {
		return rowTo >= FIRST_LINE_IN_THE_BOARD && rowTo <= LAST_LINE_IN_THE_BOARD && 
				colTo >= abs(FIRST_COLUMN_IN_THE_BOARD) && colTo <= abs(LAST_COLUMN_IN_THE_BOARD);
	}

	protected boolean canMove(Piece anotherPiece) {
		return anotherPiece.color() != color;
	}
	
	private Position findPawnPositionToPromote(Board board) {
		for(int line : asList(FIRST_LINE_IN_THE_BOARD, LAST_LINE_IN_THE_BOARD)) {
			Position position = findPawnPosition(line);
			if(position != null)
				return position;
		}
		
		return null;
	}

	private Position findPawnPosition(int line) {
		for(int i = abs(FIRST_COLUMN_IN_THE_BOARD); i <= abs(LAST_COLUMN_IN_THE_BOARD); i++) {
			Position position = new Position((char)i, line);
			Piece piece = board.getPiece(position);
			if(piece.type() == NORMAL)
				return position;
		}
		
		return null;
	}
}
