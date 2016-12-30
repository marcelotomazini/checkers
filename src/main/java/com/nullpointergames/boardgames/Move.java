package com.nullpointergames.boardgames;

import java.util.ArrayList;
import java.util.List;

public class Move {

	private final Position from;
	private final Position to;
	private final List<Position> takenPositions = new ArrayList<>();

	public Move(Position from, Position to) {
		super();
		this.from = from;
		this.to = to;
	}
	
	public void addTakenPositions(Position position) {
		takenPositions.add(position);
	}

	public Position to() {
		return to;
	}
	
	public int maxPiecesTaken() {
		return takenPositions.size();
	}
}
