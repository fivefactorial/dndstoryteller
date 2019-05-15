package se.fivefactorial.dnd.storyteller;

import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.fivefactorial.dnd.storyteller.model.Parser;
import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Story;
import se.fivefactorial.dnd.storyteller.ui.StoryTellerUI;

public class StoryTeller {

	public static boolean debug = false;

	private static final String ORIGIN = System.getProperty("user.home");

	public static void main(String[] args) {
		Player player = getPlayer();
		if (player == null) {
			System.err.println("Did not parse character");
			return;
		}
		Story story = getStory();
		if (story == null) {
			System.err.println("Did not parse story");
			return;
		}
		story.addPlayer(player);
		new StoryTellerUI(story);
	}

	private static Story getStory() {
		JFileChooser fc = new JFileChooser(ORIGIN);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Stories", "story");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.out.println("Opening: " + file.getAbsolutePath());

			try {
				Story story = Parser.parse(file);
				return story;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Open command cancelled by user.");
		}
		return null;
	}

	private static Player getPlayer() {
		JFileChooser fc = new JFileChooser(ORIGIN);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Characters", "character");
		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			System.out.println("Opening: " + file.getAbsolutePath());

			try {
				return Parser.parseCharacter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Open command cancelled by user.");
		}
		return null;
	}
}
