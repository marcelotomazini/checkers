package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.NullPiece.nullPiece;

public class Block {

	private final Position position;
	private Piece piece;

	public Block(final Position position) {
		this.position = position;
	}

	public Position position() {
		return position;
	}

	public Piece piece() {
		return piece == null ? nullPiece() : piece;
	}

	public void piece(Piece piece) {
		this.piece = piece;
	}

	public void clear() {
		piece = nullPiece();
	}
}
