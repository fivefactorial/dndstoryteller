package se.fivefactorial.dnd.storyteller.ui;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Story;

@SuppressWarnings("serial")
public class IntroFrame extends DnDFrame {

	private StoryTellerUI ui;

	private Story story;

	private List<String> text;
	private boolean active;

	public IntroFrame(StoryTellerUI ui, Story story) {
		this.ui = ui;
		this.story = story;
		text = new ArrayList<>();
		text.add("Today you will be running the story called " + story.getTitle() + ".");
		text.add(
				"You will be presented with some stories followed by one or more choices. By selecting a choice, the story continues on. After you are done you will get a summary of any possible affects this had on your character.");
		text.add("Please double check all the information, regarding your character, before continuing.");

	}

	@Override
	public void paint(Graphics g) {

		if (BACKGROUND_IMAGE != null) {
			g.drawImage(BACKGROUND_IMAGE, 0, 0, getWidth(), getHeight(), null);
		} else {
			g.setColor(BACKGROUND);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		g.setColor(TEXT);
		int y = PADDING;
		FontMetrics fm = g.getFontMetrics(FONT);
		FontMetrics fmBold = g.getFontMetrics(FONT_BOLD);

		int textWidth = getWidth() - 2 * PADDING;

		g.setFont(FONT_BOLD);
		y += fmBold.getHeight();
		g.drawString("Welcome to the D&D Storyteller", PADDING, y - fmBold.getDescent());

		g.setFont(FONT);
		List<String> rows = split(text, textWidth, fm);

		for (String row : rows) {
			y += fm.getHeight();
			g.drawString(row, PADDING, y - fm.getDescent());
		}

		y += 40;

		Player player = story.getPlayer();
		y += fm.getHeight();
		g.drawString(player.getName(), PADDING, y - fm.getDescent());
		y += fm.getHeight();
		g.drawString("Proficiency: +" + player.getProf(), PADDING, y - fm.getDescent());
		y += fm.getHeight();
		g.drawString(player.getStatsString(), PADDING, y - fm.getDescent());

		List<String> skills = split(player.getProficiencies(), textWidth, fm);
		for (String skill : skills) {
			y += fm.getHeight();
			g.drawString(skill, PADDING, y - fm.getDescent());
		}
		y += fm.getHeight();
		y = Math.max(y, getHeight() - PADDING);

		Rectangle r = new Rectangle(PADDING, y - fm.getHeight(), textWidth, fm.getHeight());
		if (r.contains(mouseX, mouseY)) {
			g.setColor(TEXT_HOVER);
			active = true;
		} else {
			g.setColor(TEXT);
			active = false;
		}
		if (StoryTeller.debug) {
			g.drawRect(r.x, r.y, r.width, r.height);
			String debug = String.format("x:%d y:%d", mouseX, mouseY);
			g.drawString(debug, PADDING + textWidth - fm.stringWidth(debug), y - fm.getDescent());
		}

		g.drawString("Start the adventure!", PADDING, y - fm.getDescent());

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (active) {
			ui.changeFrame(new SceneFrame(ui, story));
		}
	}
}
