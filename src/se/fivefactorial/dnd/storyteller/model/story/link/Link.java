package se.fivefactorial.dnd.storyteller.model.story.link;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Story;

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

	public boolean validate(int from,Story story) {
		if (to == -1)
			return true;
		if (story.hasScene(to)) {
			return true;
		}
		System.out.printf("Missing scene that %d leads to. Scene %d is missing.\n",from,to);
		return false;
	}

}
