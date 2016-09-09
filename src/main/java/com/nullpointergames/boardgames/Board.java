package com.nullpointergames.boardgames;

import static com.nullpointergames.boardgames.Rule.FIRST_COLUMN_IN_THE_BOARD;
import static com.nullpointergames.boardgames.Rule.FIRST_LINE_IN_THE_BOARD;
import static com.nullpointergames.boardgames.Rule.LAST_COLUMN_IN_THE_BOARD;
import static com.nullpointergames.boardgames.Rule.LAST_LINE_IN_THE_BOARD;

import java.util.ArrayList;

import com.nullpointergames.boardgames.checkers.PieceType;

public class Board extends ArrayList<Block> {

	private static final long serialVersionUID = 1L;

	public Board(boolean rotate) {
		createBlocks(rotate);
	}

	public void put(Piece piece, Position position) {
		find(position).piece(piece);
	}

	public Block find(Position position) {
		for (Block block : this)
			if (block.position().equals(position))
				return block;

		throw new RuntimeException();
	}

	private void createBlocks(boolean rotate) {
		if(rotate)
			for (int row = FIRST_LINE_IN_THE_BOARD; row <= LAST_LINE_IN_THE_BOARD; row++)
				addNewBlock(row);
		else
			for (int row = LAST_LINE_IN_THE_BOARD; row >= FIRST_LINE_IN_THE_BOARD; row--)
				addNewBlock(row);
	}

	private void addNewBlock(int row) {
		for (char col = FIRST_COLUMN_IN_THE_BOARD; col <= LAST_COLUMN_IN_THE_BOARD; col++)
			add(new Block(new Position(col, row)));
	}

	private boolean isInTheLastColumn(Block block) {
		return block.position().col() == LAST_COLUMN_IN_THE_BOARD;
	}

	public Piece getPiece(Position position) {
		return find(position).piece();
	}

	public PieceColor getPieceColor(Position position) {
		return getPiece(position).color();
	}

	public PieceType getPieceType(Position position) {
		return getPiece(position).type();
	}
	
	public void clear(Position position) {
		find(position).clear();
	}
	
	public Board clone() {
		Board newBoard = new Board(false);
		for(Block block : this)
			for(Block newBlock : newBoard)
				if(newBlock.position().equals(block.position())) {
					Piece piece = block.piece();
					Piece newPiece = new Piece(piece.type(), piece.color());
					newPiece.setFirstMove(piece.isFirstMove());
					newBlock.piece(newPiece);
				}
		
		return newBoard;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		for (Block block : this) {
			final Piece piece = block.piece();
			sb.append(piece.unicode());
			if (isInTheLastColumn(block))
				sb.append('\n');
		}
		return sb.toString();
	}
}
