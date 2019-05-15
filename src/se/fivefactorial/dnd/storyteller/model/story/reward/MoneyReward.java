package se.fivefactorial.dnd.storyteller.model.story.reward;

import se.fivefactorial.dnd.storyteller.model.character.Player;

public class MoneyReward extends Reward {
	private int cp;
	private int sp;
	private int ep;
	private int gp;
	private int pp;

	public MoneyReward(int copper, int silver, int electrum, int gold, int platina) {
		cp = copper;
		sp = silver;
		ep = electrum;
		gp = gold;
		pp = platina;
	}

	@Override
	public void apply(Player player) {
		player.addMoney(cp, sp, ep, gp, pp);
	}
}
