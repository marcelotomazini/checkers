package com.nullpointergames.boardgames.checkers.rules;

import static org.junit.Assert.fail;

import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.Move;
import com.nullpointergames.boardgames.Piece;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;

public abstract class RuleTest {

	protected List<Move> possibleMoves;
	protected Board board = new Board(false);
	
	protected abstract Rule rule(Position from);
	
	protected void moveFrom(char col, int row) {
		Rule rule = rule(new Position(col, row));
		possibleMoves = rule.possibleMoves();
	}

	protected void assertThatCanMoveTo(char col, int row) {
		Move newMove = new Move(null, new Position(col, row));
		for(Move move : possibleMoves)
			if(move.to().equals(newMove.to()))
				return;
		
		fail(String.format("Cannot move to %s%s", col, row));
	}
	
	protected void put(Piece piece, char col, int row) {
		board.put(piece, new Position(col, row));
	}
}
