package se.fivefactorial.dnd.storyteller.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Scene;
import se.fivefactorial.dnd.storyteller.model.story.Story;
import se.fivefactorial.dnd.storyteller.model.story.link.CheckLink;
import se.fivefactorial.dnd.storyteller.model.story.link.ConditionalLink;
import se.fivefactorial.dnd.storyteller.model.story.link.EndLink;
import se.fivefactorial.dnd.storyteller.model.story.link.Link;
import se.fivefactorial.dnd.storyteller.model.story.reward.ExpReward;
import se.fivefactorial.dnd.storyteller.model.story.reward.MoneyReward;
import se.fivefactorial.dnd.storyteller.model.story.reward.OtherReward;
import se.fivefactorial.dnd.storyteller.model.story.reward.Reward;

public class Parser {

	private Settings settings;
	private File folder;

	public Parser() throws FileNotFoundException, IOException {
		this.settings = new Settings();
	}

	public Story parse() throws FileNotFoundException {
		selectFolder();
		if (folder == null)
			return null;
		Player player = parsePlayer();
		if (player == null)
			return null;

		Story story = parseStory();
		if (story == null)
			return null;

		settings.setLastOpened(folder.getParentFile());

		story.addPlayer(player);
		return story;
	}

	private File getFile(String extensions) {
		for (File file : folder.listFiles()) {
			if (file.getName().endsWith(extensions)) {
				return file;
			}
		}
		System.out.printf("Could not find any .%s file.\n", extensions);
		return null;
		/*
		 * File origin = settings.getLastOpened(); JFileChooser fc = new
		 * JFileChooser(origin); FileNameExtensionFilter filter = new
		 * FileNameExtensionFilter("." + extensions, extensions);
		 * fc.setFileFilter(filter); int returnVal = fc.showOpenDialog(null);
		 * 
		 * if (returnVal == JFileChooser.APPROVE_OPTION) { File file =
		 * fc.getSelectedFile(); settings.setLastOpened(file.getParentFile());
		 * return file; } else { return null; }
		 */
	}

	private Story parseStory() throws FileNotFoundException {
		File file = getFile("story");
		if (file == null)
			return null;

		Story story = new Story();

		Scanner scan = new Scanner(file, "UTF-8");
		int row = 0;
		Scene scene = null;
		try {
			while (scan.hasNextLine()) {
				row++;
				String line = scan.nextLine();
				if (line.length() == 0)
					continue;
				switch (line.charAt(0)) {
				case '@':
					String title = parseTitle(line);
					story.setTitle(title);
					break;
				case '&':
					int beginIndex = parseBeginIndex(line);
					story.setBeginIndex(beginIndex);
					break;
				case '#':
					if (scene != null) {
						story.addScene(scene);
					}
					scene = parseScene(line);
					break;
				case '-':
					Link link = parseLink(line, story, row);
					if (link != null)
						scene.addLink(link);
					break;
				case '+':
					Reward reward = parseReward(line, row);
					if (reward != null)
						scene.addReward(reward);
					break;
				case '¤':
					String token = parseToken(line);
					scene.addToken(token);
					break;
				default:
					scene.addText(line);
				}
			}
			story.addScene(scene);
		} catch (

		Exception e) {
			e.printStackTrace();
			System.out.println(row);
			scan.close();
			return null;
		}

		scan.close();

		if (story.validate()) {
			return story;
		} else {
			System.err.println("Story not valid");
			return null;
		}
	}

	private String parseToken(String line) {
		return line.substring(1).trim();
	}

	private Reward parseReward(String line, int row) {
		if (line.endsWith("exp")) {
			int exp = Integer.parseInt(line.split("\\s+")[1]);
			return new ExpReward(exp);
		} else if (line.endsWith("gp")) {
			int gold = Integer.parseInt(line.split("\\s+")[1]);
			return new MoneyReward(0, 0, 0, gold, 0);
		} else if (line.startsWith("+ other")) {
			String text = line.substring(8).trim();
			return new OtherReward(text);
		} else {
			System.out.printf("Error at line %d, illegal reward\n", row);
			return null;
		}
	}

	private Link parseLink(String line, Story story, int row) {
		String data = line.substring(1).trim();
		if (data.equals("!")) {
			return new EndLink();
		} else {
			switch (data.charAt(data.length() - 1)) {
			case ']': {
				String[] temp = data.split("[\\[\\]]");
				String text = temp[0].trim();
				int to = Integer.parseInt(temp[1]);
				return new Link(text, to);

			}
			case '}': {
				String[] temp = data.split("[\\{\\}]");
				String text = temp[0].trim();

				String[] condition = temp[1].split(",");
				String check = condition[0];
				int dc = Integer.parseInt(condition[1]);
				int sucess = Integer.parseInt(condition[2]);
				int fail = Integer.parseInt(condition[3]);

				return new CheckLink(story, text, check, dc, sucess, fail);
			}
			case '>': {
				Pattern pattern = Pattern.compile("(.*?)<(.+?),(.+?),([0-9]+?),([0-9]+?)>");
				Matcher match = pattern.matcher(data);
				if (match.find()) {
					String text = match.group(1);
					String token = match.group(2);
					String expression = match.group(3);
					int val = Integer.parseInt(match.group(4));
					int to = Integer.parseInt(match.group(5));
					return new ConditionalLink(text, token, expression, val, to);
				}
				return null;
			}
			default:
				System.out.printf("Error at line %d, illegal character\n", row);
				return null;
			}
		}
	}

	private Scene parseScene(String line) {
		int n = Integer.parseInt(line.substring(1).trim());
		return new Scene(n);
	}

	private int parseBeginIndex(String line) {
		return Integer.parseInt(line.substring(1).trim());
	}

	private String parseTitle(String line) {
		return line.substring(1).trim();
	}

	private Player parsePlayer() throws FileNotFoundException {
		File file = getFile("character");
		if (file == null)
			return null;

		Scanner scan = new Scanner(file, "UTF-8");

		String name = scan.nextLine();
		int prof = scan.nextInt();
		int[] stats = new int[6];
		for (int i = 0; i < stats.length; i++) {
			stats[i] = scan.nextInt();
		}
		int[] profs = new int[19];
		for (int i = 0; i < profs.length; i++) {
			profs[i] = scan.nextInt();
		}

		Player player = new Player(name, prof, stats, profs);
		scan.close();
		return player;
	}

	public void selectFolder() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(settings.getLastOpened());
		chooser.setDialogTitle("Select folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			folder = chooser.getSelectedFile();
		}
	}

}
