package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.checkers.PieceType.NULL;
import static java.util.Collections.emptyList;

import java.util.List;

import com.nullpointergames.boardgames.checkers.PieceType;

public class NullRule extends Rule {

	public NullRule(Board board, Position from) {
		super(board, from);
	}

	@Override
	public List<Position> possibleMoves() {
		return emptyList();
	}

	@Override
	public PieceType pieceType() {
		return NULL;
	}

}
