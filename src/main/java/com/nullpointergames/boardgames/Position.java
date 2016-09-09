package com.nullpointergames.boardgames;

import static java.lang.String.format;

public class Position {

	private final char col;
	private final int row;

	public Position(char col, int row) {
		this.col = col;
		this.row = row;
	}
	
	public char col() {
		return col;
	}
	
	public int row() {
		return row;
	}
	
	@Override
	public boolean equals(Object obj) {
		Position anotherPosition = (Position) obj;
		return anotherPosition.col == col && anotherPosition.row == row;
	}
	
	@Override
	public String toString() {
		return format("%s%s", col, row);
	}
}
