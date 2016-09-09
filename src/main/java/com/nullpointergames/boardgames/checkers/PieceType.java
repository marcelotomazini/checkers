package com.nullpointergames.boardgames.checkers;

public enum PieceType {

	KING('\u26C1', '\u26C3'), 
	NORMAL('\u26C0', '\u26C2'),
	NULL('-', '-');
	
	private final char whiteUnicode;
	private final char blackUnicode;

	PieceType(char white, char black) {
		this.whiteUnicode = white;
		this.blackUnicode = black;
	}

	public char getWhiteUnicode() {
		return whiteUnicode;
	}

	public char getBlackUnicode() {
		return blackUnicode;
	}
}
