package com.nullpointergames.boardgames;

import com.nullpointergames.boardgames.checkers.PieceType;

public class NullPiece extends Piece {

	private NullPiece() {
		super(PieceType.NULL, PieceColor.NULL);
	}

	public static Piece nullPiece() {
		return new NullPiece();
	}
}
