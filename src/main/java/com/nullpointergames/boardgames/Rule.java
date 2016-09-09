package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.PieceColor.NULL;
import static com.nullpointergames.boardgames.checkers.PieceType.KING;
import static com.nullpointergames.boardgames.utils.MessageUtils.ILLEGAL_MOVE;
import static com.nullpointergames.boardgames.utils.MessageUtils.getMessage;

import java.util.ArrayList;
import java.util.List;

import com.nullpointergames.boardgames.checkers.PieceType;
import com.nullpointergames.boardgames.checkers.RuleFactory;
import com.nullpointergames.boardgames.checkers.exceptions.NoMoreMovesForThisDirection;

public abstract class Rule {

	protected static final char FIRST_COLUMN_IN_THE_BOARD = 'a';
	protected static final char LAST_COLUMN_IN_THE_BOARD = 'h';
	protected static final int LAST_LINE_IN_THE_BOARD = 8;
	protected static final int FIRST_LINE_IN_THE_BOARD = 1;
	
	protected final List<Position> possibleMoves = new ArrayList<>();
	private final List<TurnChangeListener> turnChangeListeners = new ArrayList<>();
	
	protected final Board board; 
	protected final PieceColor color; 
	protected final Position from;
	protected boolean canChangeTurn = true;
	
	public Rule(final Board board, final Position from) {
		this.board = board;
		this.from = from;
		this.color = from == null ? null : board.getPieceColor(from);
	}
	
	public abstract List<Position> possibleMoves();

	public abstract PieceType pieceType();

	protected Piece getPiece(Board board, Position position) {
		return board.find(position).piece();
	}

	protected Piece getPiece(Board board, char col, int row) {
		return getPiece(board, new Position(col, row));
	}

	public void move(Position to) {
		if(!possibleMoves().contains(to))
			throw new RuntimeException(getMessage(ILLEGAL_MOVE));
		
		moveWithoutVerification(to);
	}
	
	public void moveWithoutVerification(Position to) {
		Piece piece = board.getPiece(from);
		board.put(piece, to);
		board.clear(from);
		piece.setFirstMove(false);
		aditionalMoves(board, from, to);
		if(canChangeTurn())
			dispatchChangeTurn();
	}

	public void addTurnChangerListener(TurnChangeListener l) {
		turnChangeListeners.add(l);
	}
	
	private void dispatchChangeTurn() {
		for(TurnChangeListener l : turnChangeListeners)
			l.onTurnChanged();
	}

	private boolean canChangeTurn() {
		return canChangeTurn;
	}

	protected void aditionalMoves(Board board, Position from, Position to) {}
	
	protected void addPosition(int col, int row) {
		Piece anotherPiece = getPiece(board, (char)col, row);
		
		if (canMove(anotherPiece, col))
			possibleMoves.add(newPosition((char)col, row));
		
		if (anotherPiece.color() != NULL)
			throw new NoMoreMovesForThisDirection();
	}

	protected Position newPosition(char col, int row) {
		return new Position(col, row);
	}

	protected boolean canMove(Piece anotherPiece, int col) {
		return anotherPiece.color() != color;
	}
}
