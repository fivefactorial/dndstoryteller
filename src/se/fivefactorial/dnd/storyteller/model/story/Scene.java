package se.fivefactorial.dnd.storyteller.model.story;

import java.util.ArrayList;
import java.util.List;

import se.fivefactorial.dnd.storyteller.model.story.link.Link;
import se.fivefactorial.dnd.storyteller.model.story.reward.Reward;

public class Scene {

	private int n;
	private ArrayList<String> texts;
	private ArrayList<Link> links;
	private ArrayList<Reward> rewards;

	public Scene(int n) {
		this.n = n;
		texts = new ArrayList<>();
		links = new ArrayList<>();
		rewards = new ArrayList<>();
	}

	public void addText(String text) {
		texts.add(text);
	}

	public boolean validate() {
		if (texts.isEmpty()) {
			System.out.printf("Scene %d is lacking texts\n", n);
			return false;
		}
		if (links.isEmpty()) {
			System.out.printf("Scene %d is lacking links\n", n);
			return false;
		}
		return true;
	}

	public void addLink(Link link) {
		links.add(link);
	}

	public void addReward(Reward reward) {
		rewards.add(reward);
	}

	public int getNumber() {
		return n;
	}

	public List<String> getText() {
		return texts;
	}

	public List<Link> getLinks() {
		return links;
	}

	public List<Reward> getRewards() {
		return rewards;
	}

}
