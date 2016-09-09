package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.PieceColor.WHITE;

import com.nullpointergames.boardgames.checkers.PieceType;

public class Piece {

	private PieceColor color;
	private PieceType type;
	private boolean firstMove = true;

	public Piece(PieceType type, PieceColor color) {
		this.type = type;
		this.color = color;

	}

	public PieceType type() {
		return type;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public PieceColor color() {
		return color;
	}

	public char unicode() {
		return color == WHITE ? type.getWhiteUnicode() : type.getBlackUnicode();
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj))
			return true;
		
		Piece anotherPiece = (Piece) obj;
		return anotherPiece.type == type &&
				anotherPiece.color == color;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
}
