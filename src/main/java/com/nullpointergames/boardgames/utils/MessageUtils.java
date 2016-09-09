package com.nullpointergames.boardgames.utils;

import java.util.ResourceBundle;

public class MessageUtils {

	public static String getMessage(String key, Object... parameters) {
		ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle");
		String message = messages.getString(key);
		
		for(Object p : parameters)
			message = message.replaceFirst("@", p.toString());
		
		return message;
	}
	
	public static final String ILLEGAL_MOVE = "illegalMove";
	public static final String IT_IS_NOT_YOUR_TURN = "itIsNotYourTurn";
	public static final String OOPS = "oops";
	public static final String CHECK = "check";
	public static final String CHECKMATE = "checkmate";
	public static final String GAME_OVER = "gameOver";
	public static final String YOU_WON = "youWon";
	public static final String YOU_LOST = "youLost";
	public static final String CHOOSE_YOUR_PIECE = "chooseYourPiece";
}
