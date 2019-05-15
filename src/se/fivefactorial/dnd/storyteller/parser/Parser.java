package se.fivefactorial.dnd.storyteller.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Scene;
import se.fivefactorial.dnd.storyteller.model.story.Story;
import se.fivefactorial.dnd.storyteller.model.story.link.ConditionalLink;
import se.fivefactorial.dnd.storyteller.model.story.link.EndLink;
import se.fivefactorial.dnd.storyteller.model.story.link.Link;
import se.fivefactorial.dnd.storyteller.model.story.reward.ExpReward;
import se.fivefactorial.dnd.storyteller.model.story.reward.MoneyReward;
import se.fivefactorial.dnd.storyteller.model.story.reward.OtherReward;
import se.fivefactorial.dnd.storyteller.model.story.reward.Reward;

public class Parser {

	private Settings settings;

	public Parser() throws FileNotFoundException, IOException {
		this.settings = new Settings();
	}

	public Story parse() throws FileNotFoundException {
		Player player = parsePlayer();
		if (player == null)
			return null;

		Story story = parseStory();
		if (story == null)
			return null;

		story.addPlayer(player);
		return story;
	}

	private File getFile(String extensions) {
		File origin = settings.getLastOpened();
		JFileChooser fc = new JFileChooser(origin);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("." + extensions, extensions);
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			settings.setLastOpened(file.getParentFile());
			return file;
		} else {
			return null;
		}
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
					String title = line.substring(1).trim();
					story.setTitle(title);
					break;
				case '&':
					int beginIndex = Integer.parseInt(line.substring(1).trim());
					story.setBeginIndex(beginIndex);
					break;
				case '#':
					if (scene != null) {
						story.addScene(scene);
					}
					scene = new Scene(Integer.parseInt(line.substring(1).trim()));
					break;
				case '-':
					String data = line.substring(1).trim();
					if (data.equals("!")) {
						scene.addLink(new EndLink());
					} else {
						switch (data.charAt(data.length() - 1)) {
						case ']': {
							String[] temp = data.split("[\\[\\]]");
							String text = temp[0].trim();
							int to = Integer.parseInt(temp[1]);
							Link link = new Link(text, to);
							scene.addLink(link);
						}
							break;
						case '}': {
							String[] temp = data.split("[\\{\\}]");
							String text = temp[0].trim();

							String[] condition = temp[1].split(",");
							String check = condition[0];
							int dc = Integer.parseInt(condition[1]);
							int sucess = Integer.parseInt(condition[2]);
							int fail = Integer.parseInt(condition[3]);

							Link link = new ConditionalLink(story, text, check, dc, sucess, fail);
							scene.addLink(link);
						}
							break;
						default:
							System.out.printf("Error at line %d, illegal character\n", row);
						}
					}
					break;
				case '+':
					if (line.endsWith("exp")) {
						int exp = Integer.parseInt(line.split("\\s+")[1]);
						Reward reward = new ExpReward(exp);
						scene.addReward(reward);
					} else if (line.endsWith("gp")) {
						int gold = Integer.parseInt(line.split("\\s+")[1]);
						Reward reward = new MoneyReward(0, 0, 0, gold, 0);
						scene.addReward(reward);
					} else if (line.startsWith("+ other")) {
						String text = line.substring(8).trim();
						Reward reward = new OtherReward(text);
						scene.addReward(reward);
					} else {
						System.out.printf("Error at line %d, illegal reward\n", row);
					}
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

}
