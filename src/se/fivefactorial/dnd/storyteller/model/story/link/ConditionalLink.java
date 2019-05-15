package se.fivefactorial.dnd.storyteller.model.story.link;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.story.Story;

public class ConditionalLink extends Link {

	private Story story;
	private String check;
	private int dc;
	private int sucess;
	private int fail;

	public ConditionalLink(Story story, String text, String check, int dc, int sucess, int fail) {
		super(text);
		this.story = story;
		this.check = check;
		this.dc = dc;
		this.sucess = sucess;
		this.fail = fail;
	}

	@Override
	public String toString() {
		if (StoryTeller.debug) {
			return String.format("%s (%s) [%d/%d]", text, check, sucess, fail);
		}
		return String.format("%s", text);
	}

	public int getTo() {
		boolean sucess = story.getPlayer().roll(check, dc);
		return sucess ? this.sucess : this.fail;
	}
}
