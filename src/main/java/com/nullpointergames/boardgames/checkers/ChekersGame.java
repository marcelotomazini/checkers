package com.nullpointergames.boardgames.checkers;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.checkers.PieceType.NORMAL;
import static com.nullpointergames.boardgames.utils.MessageUtils.CHOOSE_YOUR_PIECE;
import static com.nullpointergames.boardgames.utils.MessageUtils.GAME_OVER;
import static com.nullpointergames.boardgames.utils.MessageUtils.IT_IS_NOT_YOUR_TURN;
import static com.nullpointergames.boardgames.utils.MessageUtils.YOU_LOST;
import static com.nullpointergames.boardgames.utils.MessageUtils.YOU_WON;
import static com.nullpointergames.boardgames.utils.MessageUtils.getMessage;
import static java.lang.String.format;

import java.util.List;

import com.nullpointergames.boardgames.Block;
import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.TurnChangeListener;
import com.nullpointergames.boardgames.checkers.rules.NormalRule;


public class ChekersGame implements TurnChangeListener {

	private final Board board;
	private final PieceColor myColor;
	private PieceColor turn = WHITE;
	private boolean isOver;
	private PieceColor winner;

	public ChekersGame(PieceColor myColor) {
		this(myColor, new Board(myColor != WHITE));
		putPieces();
	}
	
	private ChekersGame(PieceColor myColor, Board board) {
		this.myColor = myColor;
		this.board = board;
	}

	public Board getBoard() {
		return board;
	}
	
	public void move(final Position from, final Position to) {
		if(isOver) {
			String result = winner == myColor ? getMessage(YOU_WON) : getMessage(YOU_LOST);
			throw new RuntimeException(format("%s. %s", getMessage(GAME_OVER), result));
		}
		
		if(board.getPieceColor(from) != myColor)
			throw new RuntimeException(getMessage(CHOOSE_YOUR_PIECE, getMessage(myColor.name())));
		
		if(board.getPieceColor(from) != turn)
			throw new RuntimeException(getMessage(IT_IS_NOT_YOUR_TURN));
		
		move(board, from, to);
	}
	
	public void moveWithoutVerification(final Position from, final Position to) {
		Rule rule = RuleFactory.getRule(board, from);
		rule.addTurnChangerListener(this);
		rule.moveWithoutVerification(to);
	}
	
	public Block find(Position position) {
		return board.find(position);
	}
	
	public List<Move> getPossibleMoves(Position from) {
		return RuleFactory.getRule(board, from).possibleMoves();
	}
	
	public void promoteTo(PieceType pieceType) {
		NormalRule pawnRule = new NormalRule(board, null);
		pawnRule.promoteTo(board, pieceType);
	}

	public PieceColor getTurn() {
		return turn;
	}
	
	public void verifyGameOver() {
		boolean hasWhitePiece = false;
		boolean hasBlackPiece = false;
		
		for (Block b : board) {
			if(b.piece().color() == WHITE)
				hasWhitePiece = true;
			if(b.piece().color() == BLACK)
				hasBlackPiece = true;
		}
		
		if(hasWhitePiece && hasBlackPiece)
			return;
		
		isOver = true;
		winner = hasWhitePiece ? WHITE : BLACK;
		String result = winner == myColor ? getMessage(YOU_WON) : getMessage(YOU_LOST);
		throw new RuntimeException(format("%s", result));
	}

	public ChekersGame clone() {
		ChekersGame chessGame = new ChekersGame(myColor, board.clone());
		chessGame.isOver = new Boolean(isOver);
		chessGame.winner = winner;
		chessGame.turn = turn == WHITE ? WHITE : BLACK;
		
		return chessGame;
	}

	@Override
	public void onTurnChanged() {
		nextTurn();
	}
	
	private void move(Board board, Position from, Position to) {
		Rule rule = RuleFactory.getRule(board, from);
		rule.addTurnChangerListener(this);
		rule.move(to);
	}
	
	private void putPiece(PieceType type, PieceColor color, char col, int row) {
		board.put(new Piece(type, color), new Position(col, row));		
	}

	private final void nextTurn() {
		turn = turn.equals(WHITE) ? BLACK : WHITE;
	}

	private void putPieces() {
		putPiece(NORMAL, WHITE, 'a', 1);
		putPiece(NORMAL, WHITE, 'c', 1);
		putPiece(NORMAL, WHITE, 'e', 1);
		putPiece(NORMAL, WHITE, 'g', 1);
		putPiece(NORMAL, WHITE, 'b', 2);
		putPiece(NORMAL, WHITE, 'd', 2);
		putPiece(NORMAL, WHITE, 'f', 2);
		putPiece(NORMAL, WHITE, 'h', 2);
		putPiece(NORMAL, WHITE, 'a', 3);
		putPiece(NORMAL, WHITE, 'c', 3);
		putPiece(NORMAL, WHITE, 'e', 3);
		putPiece(NORMAL, WHITE, 'g', 3);

		putPiece(NORMAL, BLACK, 'b', 6);
		putPiece(NORMAL, BLACK, 'd', 6);
		putPiece(NORMAL, BLACK, 'f', 6);
		putPiece(NORMAL, BLACK, 'h', 6);
		putPiece(NORMAL, BLACK, 'a', 7);
		putPiece(NORMAL, BLACK, 'c', 7);
		putPiece(NORMAL, BLACK, 'e', 7);
		putPiece(NORMAL, BLACK, 'g', 7);
		putPiece(NORMAL, BLACK, 'b', 8);
		putPiece(NORMAL, BLACK, 'd', 8);
		putPiece(NORMAL, BLACK, 'f', 8);
		putPiece(NORMAL, BLACK, 'h', 8);

	}
}
