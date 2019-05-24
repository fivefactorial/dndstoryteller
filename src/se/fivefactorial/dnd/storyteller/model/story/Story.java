package se.fivefactorial.dnd.storyteller.model.story;

import java.util.ArrayList;

import se.fivefactorial.dnd.storyteller.model.character.Player;

public class Story {

	private Player player;
	private String title;
	private ArrayList<Scene> scenes;
	private int currentScene = -1;
	private ArrayList<Integer> path;

	public Story() {
		scenes = new ArrayList<>();
		path = new ArrayList<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addScene(Scene scene) {
		scenes.add(scene);
	}

	public boolean validate() {
		System.out.printf("Validating %s\n", title);
		System.out.printf("%d scenes.\n", scenes.size());

		if (currentScene == -1) {
			System.out.printf("No begin index were set. use the & notation\n");
			return false;
		}

		boolean found = false;
		for (Scene scene : scenes) {
			if (!scene.validate(this))
				return false;
			if (currentScene == scene.getNumber()) {
				found = true;
			}
		}
		if (!found) {
			System.out.printf("Invalid begin index were set, %d.\n");
		}
		return true;
	}

	public void setBeginIndex(int beginIndex) {
		currentScene = beginIndex;
		path.add(beginIndex);
	}

	public Scene getCurrentScene() {
		return getScene(currentScene);
	}

	public Scene swapScene(int to) {
		path.add(to);
		currentScene = to;
		return getCurrentScene();
	}

	public void addPlayer(Player player) {
		this.player = player;

	}

	public Player getPlayer() {
		return player;
	}

	public String getPath() {
		return path.toString();
	}

	public boolean hasScene(int n) {
		return getScene(n) != null;
	}

	public Scene getScene(int n) {
		for (Scene scene : scenes) {
			if (n == scene.getNumber()) {
				return scene;
			}
		}
		return null;
	}

}
