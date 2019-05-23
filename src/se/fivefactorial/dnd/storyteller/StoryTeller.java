package se.fivefactorial.dnd.storyteller;

import java.io.FileNotFoundException;
import java.io.IOException;

import se.fivefactorial.dnd.storyteller.model.story.Story;
import se.fivefactorial.dnd.storyteller.parser.Parser;
import se.fivefactorial.dnd.storyteller.ui.StoryTellerUI;

public class StoryTeller {

	public static boolean debug = true;
	public static boolean compile = false;

	public static void main(String[] args) {

		try {
			Parser parser = new Parser();
			if (compile) {

			} else {
				Story story;
				story = parser.parse();

				if (story == null) {
					System.exit(0);
				}
				new StoryTellerUI(story);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
