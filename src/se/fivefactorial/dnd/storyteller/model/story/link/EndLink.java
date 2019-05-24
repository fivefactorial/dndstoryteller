package se.fivefactorial.dnd.storyteller.model.story.link;

import se.fivefactorial.dnd.storyteller.model.story.Story;

public class EndLink extends Link {

	public EndLink() {
		super("The story ends.");
	}

	@Override
	public String toString() {
		return String.format("%s", text);
	}

	public int getTo() {
		return -1;
	}
	
	public boolean validate(int from,Story story) {
		return true;
	}

}
