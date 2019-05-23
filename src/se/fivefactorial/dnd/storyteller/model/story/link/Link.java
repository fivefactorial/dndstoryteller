package se.fivefactorial.dnd.storyteller.model.story.link;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.character.Player;

public class Link {

	protected String text;
	private int to;

	protected Link(String text) {
		this.text = text.trim();
		if (text.equals(""))
			this.text = "The story continues.";
	}

	public Link(String text, int to) {
		this(text);
		this.to = to;
	}

	@Override
	public String toString() {
		if (StoryTeller.debug) {
			return String.format("%s [%d]", text, to);
		}
		return String.format("%s", text);
	}

	public int getTo() {
		return to;
	}

	public boolean show(Player player) {
		return true;
	}

}
