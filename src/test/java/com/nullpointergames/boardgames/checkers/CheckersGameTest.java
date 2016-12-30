package com.nullpointergames.boardgames.checkers;

import static com.nullpointergames.boardgames.PieceColor.BLACK;
import static com.nullpointergames.boardgames.PieceColor.WHITE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Locale;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.nullpointergames.boardgames.PieceColor;
import com.nullpointergames.boardgames.Position;

public class CheckersGameTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private ChekersGame game;
	
	@Before
	public void setup() {
		Locale.setDefault(Locale.US);
		newGame();
	}

	private void newGame() {
		newGame(WHITE);
	}

	private void newGame(PieceColor color) {
		game = new ChekersGame(color);
	}
	
	@Test
	public void newCheckersGame() {
		assertThat(game.getTurn(), equalTo(WHITE));
	}

	@Test
	public void validWhiteMove() {
		move('a', 3, 'b', 4);

		assertThat(game.getTurn(), equalTo(BLACK));
	}

	@Test
	public void validBlackMove() {
		newGame(BLACK);
		
		moveWithoutVerification('a', 3, 'b', 4);
		move('b', 6, 'a', 5);
		
		assertThat(game.getTurn(), equalTo(WHITE));
	}

	@Test
	public void invalidMove() {
		exception.expect(RuntimeException.class);
	    exception.expectMessage("Illegal move");
	    
		move('a', 3, 'b', 3);
	}

	@Test
	public void itsNotYourTurn() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("It's not your turn");
		
		newGame(BLACK);
		
		move('a', 7, 'a', 6);
	}

	@Test
	public void chooseWhitePiece() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Choose a white piece to move");
		
		move('a', 7, 'a', 6);
	}

	@Test
	public void chooseBlackPiece() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Choose a black piece to move");
		
		newGame(BLACK);
		
		move('a', 2, 'a', 3);
	}
	
	@Test
	public void canNotMoveTwiceSameColor() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("It's not your turn");
		
		move('a', 3, 'b', 4);
		move('c', 3, 'd', 4);
	}

	@Test
	public void whiteWins() {
		try {
			move('c', 3, 'b', 4);
			moveWithoutVerification('f', 6, 'g', 5);
			move('g', 3, 'f', 4);
			moveWithoutVerification('g', 7, 'f', 6);
			move('f', 2, 'g', 3);
			moveWithoutVerification('f', 8, 'g', 7);
			move('e', 1, 'f', 2);
			moveWithoutVerification('d', 6, 'c', 5);
			move('b', 4, 'd', 6);
			move('d', 6, 'f', 8);
			verifyCheckAndCheckmate();
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Checkmate. You won"));
		}
		
		try {
			move('a', 4, 'a', 3);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(), equalTo("Game over. You won"));
		}
	}
	
	@Test
	public void captureTwoPieces() {
		move('c', 3, 'b', 4);
		moveWithoutVerification('f', 6, 'e', 5);
		move('g', 3, 'h', 4);
		moveWithoutVerification('d', 6, 'c', 5);
		move('b', 4, 'd', 6);
		move('d', 6, 'f', 4);
		
		assertThat(game.getTurn(), equalTo(BLACK));
	}

	@Test
	public void canMoveOnlyToCapture() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Illegal move");
		
		move('c', 3, 'b', 4);
		moveWithoutVerification('f', 6, 'e', 5);
		move('g', 3, 'h', 4);
		moveWithoutVerification('d', 6, 'c', 5);
		move('b', 4, 'd', 6);
		move('e', 3, 'f', 4);
	}
	
	@Test
	public void promotionMove() {
		move('c', 3, 'b', 4);
		moveWithoutVerification('f', 6, 'g', 5);
		move('g', 3, 'f', 4);
		moveWithoutVerification('g', 7, 'f', 6);
		move('f', 2, 'g', 3);
		moveWithoutVerification('f', 8, 'g', 7);
		move('e', 1, 'f', 2);
		moveWithoutVerification('d', 6, 'c', 5);
		move('b', 4, 'd', 6);
		move('d', 6, 'f', 8);
	}
	
	@Test
	public void mustCapture() {
		exception.expect(RuntimeException.class);
		exception.expectMessage("Illegal move");
		
		move('c', 3, 'b', 4);
		moveWithoutVerification('b', 6, 'c', 5);
		move('g', 3, 'h', 4);
		moveWithoutVerification('d', 6, 'e', 5);
		move('b', 4, 'a', 5);
	}
	
	private void verifyCheckAndCheckmate() {
		game.verifyGameOver();
	}
	
	private void move(char colFrom, int rowFrom, char colTo, int rowTo) {
		game.move(new Position(colFrom, rowFrom), new Position(colTo, rowTo));
	}

	private void moveWithoutVerification(char colFrom, int rowFrom, char colTo, int rowTo) {
		game.moveWithoutVerification(new Position(colFrom, rowFrom), new Position(colTo, rowTo));
	}
}
