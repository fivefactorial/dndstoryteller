package se.fivefactorial.dnd.storyteller.model.story.link;

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

}
