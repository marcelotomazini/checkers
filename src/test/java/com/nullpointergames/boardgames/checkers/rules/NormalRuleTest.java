package com.nullpointergames.boardgames.checkers.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.checkers.PieceType.KING;
import static com.nullpointergames.boardgames.checkers.PieceType.NORMAL;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.checkers.rules.NormalRule;


public class NormalRuleTest extends RuleTest {

	@org.junit.Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Override
	protected NormalRule rule(Position from) {
		return new NormalRule(board, from);
	}
	
	@Before
	public void setup() {
		board.put(whitePiece(), new Position('b', 2));
	}

	@Test
	public void whiteMove() {
		moveFrom('b', 2);
		
		assertThat(possibleMoves, hasSize(2));
		assertThatCanMoveTo('a', 3);
		assertThatCanMoveTo('c', 3);
	}

	@Test
	public void blackMove() {
		board.put(blackPiece(), new Position('a', 7));
		moveFrom('a', 7);
		
		assertThat(possibleMoves, hasSize(1));
		assertThatCanMoveTo('b', 6);
	}

	@Test
	public void captureMove() {
		putBlackPiece('a', 3);
		putBlackPiece('c', 3);
		
		moveFrom('b', 2);
		
		assertThat(possibleMoves, hasSize(1));
		assertThatCanMoveTo('d', 4);
	}

	@Test
	public void canNotMove() {
		putWhitePiece('a', 3);
		putWhitePiece('c', 3);
		
		moveFrom('b', 2);
		
		assertThat(possibleMoves, hasSize(0));
	}

	@Test
	public void promotion() {
		board.put(whitePiece(), new Position('b', 7));
		Piece piece = board.getPiece(new Position('b', 7));
		piece.setFirstMove(false);
		
		move('b', 7, 'a', 8);
		assertThat(board.getPiece(new Position('a', 8)).type(), equalTo(KING));
	}
	
	private void putWhitePiece(char col, int row) {
		put(whitePiece(), col, row);
	}
	
	private void putBlackPiece(char col, int row) {
		put(blackPiece(), col, row);
	}
	
	private Piece whitePiece() {
		return new Piece(NORMAL, WHITE);
	}

	private Piece blackPiece() {
		return new Piece(NORMAL, BLACK);
	}

	private void move(char colFrom, int rowFrom, char colTo, int rowTo) {
		Rule rule = rule(new Position(colFrom, rowFrom));
		rule.move(new Position(colTo, rowTo));
	}
}
