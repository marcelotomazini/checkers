package com.nullpointergames.boardgames.checkers.rules;

import static com.nullpointergames.boardgames.NullPiece.nullPiece;
import static com.nullpointergames.boardgames.PieceColor.NULL;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.checkers.PieceType.KING;
import static com.nullpointergames.boardgames.checkers.PieceType.NORMAL;
import static java.lang.Math.abs;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
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
		if(abs(x) > 1) { //piece's been taken
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
		List<Move> moves = new NormalRule(board, to).possibleMoves();
		for(Move m : moves) {
			Position p = m.to();
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
	public List<Move> possibleMoves() {
		move(+1, +1);
		move(+1, -1);
		move(-1, +1);
		move(-1, -1);
		
		capture(+1, +1);
		capture(+1, -1);
		capture(-1, +1);
		capture(-1, -1);
		
		selectBestMoves();
		
		return possibleMoves;
	}

	private void selectBestMoves() {
		int maxPiecesTaken = 0;
		for(Move move : possibleMoves)
			if(move.maxPiecesTaken() > maxPiecesTaken)
				maxPiecesTaken = move.maxPiecesTaken();
		
		for(Move move : new ArrayList<>(possibleMoves))
			if(move.maxPiecesTaken() < maxPiecesTaken)
				possibleMoves.remove(move);
	}


	public void promoteTo(Board board, PieceType pieceType) {
		Position position = findPawnPositionToPromote(board);
		Piece promotedPiece = new Piece(pieceType, board.getPieceColor(position));
		board.put(promotedPiece, position);
	}

	private void move(int colIncrement, int rowIncrement) {
		int rowTo = from.row() + rowIncrement;
		int colTo = abs(from.col()) + colIncrement;
		
		Piece piece = board.getPiece(from);	
		
		int moveDirection = piece.color() == WHITE ? 1 : -1;
		Piece anotherPiece;
		
		try {
			anotherPiece = getPiece(board, (char)colTo, rowTo);
		} catch (RuntimeException e) {
			return;
		}
		
		if(!canMove(anotherPiece))
			return;
		
		
		if(anotherPiece.color() == NULL) {
			if(from.row() - rowTo != moveDirection) {
				Move move = new Move(from, newPosition((char)colTo, rowTo));
				possibleMoves.add(move);
			}
			return;
		}
	}

	private Position capture(int colIncrement, int rowIncrement) {
		int rowTo = from.row() + rowIncrement;
		int colTo = abs(from.col()) + colIncrement;
		
		Piece anotherPiece;
		
		try {
			anotherPiece = getPiece(board, (char)colTo, rowTo);
		} catch (RuntimeException e) {
			return null;
		}
		
		if(!canCapture(anotherPiece))
			return null;
		
		Position positionThatCanBeTaken = newPosition((char)colTo, rowTo);
//		
//		if(positionsTaken.contains(positionThatCanBeTaken))
//			return null;
		
		rowTo = from.row() + (rowIncrement * 2);
		colTo = abs(from.col()) + (colIncrement * 2);
		
		try {
			anotherPiece = getPiece(board, (char)colTo, rowTo);
		} catch (RuntimeException e) {
			return null;
		}
		
		if(anotherPiece.color() == NULL) {
//			positionsTaken.add(positionThatCanBeTaken);
//			return newPosition((char)colTo, rowTo);
			Move move = new Move(from, newPosition((char)colTo, rowTo));
			move.addTakenPositions(positionThatCanBeTaken);
			possibleMoves.add(move);
		}
		
		return null;
	}
	
	protected boolean canMove(Piece anotherPiece) {
		return anotherPiece.color() != color;
	}

	protected boolean canCapture(Piece anotherPiece) {
		return anotherPiece.color() != color && anotherPiece.color() != NULL;
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
