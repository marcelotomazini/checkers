package com.nullpointergames.boardgames.checkers;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import com.nullpointergames.boardgames.Board;
import com.nullpointergames.boardgames.NullRule;
import com.nullpointergames.boardgames.Position;
import com.nullpointergames.boardgames.Rule;
import com.nullpointergames.boardgames.checkers.rules.KingRule;
import com.nullpointergames.boardgames.checkers.rules.NormalRule;

public class RuleFactory {

	private static final List<Class<? extends Rule>> RULES = Arrays.asList(
			NormalRule.class, 
			KingRule.class, 
			NullRule.class);
	
	public static Rule getRule(Board board, Position from) {
		for(Class<? extends Rule> aClass : RULES)
			try {
				Rule rule = aClass.
						getDeclaredConstructor(Board.class, Position.class).
						newInstance(board, from);
				if(rule.pieceType() == board.getPiece(from).type())
					return rule;
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {}
		
		throw new RuntimeException();
	}
}
