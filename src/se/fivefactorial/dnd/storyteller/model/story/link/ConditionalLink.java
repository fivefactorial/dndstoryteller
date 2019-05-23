package se.fivefactorial.dnd.storyteller.model.story.link;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.character.Player;

public class ConditionalLink extends Link {

	private String token;
	private String expression;
	private int val;

	public ConditionalLink(String text, String token, String expression, int val, int to) {
		super(text, to);
		this.token = token;
		this.expression = expression;
		this.val = val;
	}

	@Override
	public String toString() {
		if (StoryTeller.debug) {
			return String.format("%s [if %s %s %d then goto %d]", text, token, expression, val, getTo());
		}
		return String.format("%s", text);
	}

	@Override
	public boolean show(Player player) {
		int val = player.getToken(token);
		switch (expression) {
		case "=":
			return val == this.val;
		case ">":
			return val > this.val;
		}

		return false;
	}
}
