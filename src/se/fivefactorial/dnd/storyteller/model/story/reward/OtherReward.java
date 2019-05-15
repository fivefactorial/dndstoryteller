package se.fivefactorial.dnd.storyteller.model.story.reward;

import se.fivefactorial.dnd.storyteller.model.character.Player;

public class OtherReward extends Reward {

	private String text;
	
	public OtherReward(String text) {
this.text = text;	}
	
	@Override
	public void apply(Player player) {
		player.addOtherReward(text);
	}

}
