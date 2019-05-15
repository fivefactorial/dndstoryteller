package se.fivefactorial.dnd.storyteller.model.story.reward;

import se.fivefactorial.dnd.storyteller.model.character.Player;

public class ExpReward extends Reward {

	private int exp;

	public ExpReward(int exp) {
		this.exp = exp;
	}

	@Override
	public void apply(Player player) {
		player.addExp(exp);
	}
}
