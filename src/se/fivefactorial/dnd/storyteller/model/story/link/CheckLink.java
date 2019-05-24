package se.fivefactorial.dnd.storyteller.model.story.link;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Story;

public class CheckLink extends Link {

	private Story story;
	private String check;
	private int dc;
	private int sucess;
	private int fail;

	public CheckLink(Story story, String text, String check, int dc, int sucess, int fail) {
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
		return String.format("%s (%s)", text, check);
	}

	public int getTo() {
		Result result = story.getPlayer().roll(check, dc);
		boolean sucess = result.isSucess();
		int to = sucess ? this.sucess : this.fail;
		story.getScene(to).addResult(result);
		return to;
	}

	public boolean validate(int from, Story story) {
		boolean found = false;
		for (String skill : Player.SKILLS) {
			if (skill.equals(check))
				found = true;
		}
		if (!found) {
			System.out.printf("Illegal check \"%s\" in scene %d\n", check, from);
			return false;
		}

		if (story.hasScene(sucess)) {
			return true;
		}
		System.out.printf("Missing scene that %d leads to if sucess. Scene %d is missing.\n", from, sucess);

		if (story.hasScene(fail)) {
			return true;
		}
		System.out.printf("Missing scene that %d leads to if fail. Scene %d is missing.\n", from, fail);
		return false;
	}
}
