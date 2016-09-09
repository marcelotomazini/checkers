package com.nullpointergames.boardgames.checkers.rules;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static com.nullpointergames.boardgames.checkers.PieceType.KING;
import static com.nullpointergames.boardgames.checkers.PieceType.NORMAL;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.checkers.PieceType;
import com.nullpointergames.boardgames.checkers.rules.KingRule;

public class KingRuleTest extends RuleTest {

	@Override
	protected Rule rule(Position from) {
		return new KingRule(board, from);
	}

	@Before
	public void setup() {
		board.put(whiteKing(), new Position('a', 1));
	}
	
	@Test
	public void kingMoves() {
		board.put(whiteKing(), new Position('d', 4));
		board.put(blackPiece(), new Position('g', 7));
		board.put(whiteKing(), new Position('b', 6));
		
		moveFrom('d', 4);
		
		assertThat(possibleMoves, hasSize(9));
		assertThatCanMoveTo('e', 5);
		assertThatCanMoveTo('f', 6);
		assertThatCanMoveTo('h', 8);
		assertThatCanMoveTo('c', 5);
		assertThatCanMoveTo('e', 3);
		assertThatCanMoveTo('f', 2);
		assertThatCanMoveTo('g', 1);
		assertThatCanMoveTo('c', 3);
		assertThatCanMoveTo('b', 2);
	}
	
	private Piece whiteKing() {
		return new Piece(KING, WHITE);
	}
	
	private Piece blackPiece() {
		return new Piece(NORMAL, BLACK);
	}
}
