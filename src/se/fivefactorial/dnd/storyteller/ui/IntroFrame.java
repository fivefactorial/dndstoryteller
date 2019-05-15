package se.fivefactorial.dnd.storyteller.ui;

import static se.fivefactorial.dnd.storyteller.ui.SceneFrame.BACKGROUND;
import static se.fivefactorial.dnd.storyteller.ui.SceneFrame.FONT;
import static se.fivefactorial.dnd.storyteller.ui.SceneFrame.PADDING;
import static se.fivefactorial.dnd.storyteller.ui.SceneFrame.TEXT;
import static se.fivefactorial.dnd.storyteller.ui.SceneFrame.TEXT_HOVER;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import se.fivefactorial.dnd.storyteller.StoryTeller;
import se.fivefactorial.dnd.storyteller.model.character.Player;
import se.fivefactorial.dnd.storyteller.model.story.Story;

@SuppressWarnings("serial")
public class IntroFrame extends JPanel implements MouseMotionListener, MouseListener {

	private StoryTellerUI ui;

	private Story story;

	private List<String> text;
	private int mouseX;
	private int mouseY;
	private boolean active;

	public IntroFrame(StoryTellerUI ui, Story story) {
		this.ui = ui;
		this.story = story;
		addMouseMotionListener(this);
		addMouseListener(this);
		text = new ArrayList<>();
		text.add("Today you will be running the story called " + story.getTitle() + ".");
		text.add(
				"You will be presented with some stories followed by one or more choices. By selecting a choice, the story continues on. After you are done you will get a summary of any possible affects this had on your character.");
		text.add("Please double check all the information, regarding your character, before continuing.");

	}

	@Override
	public void paint(Graphics g) {
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(TEXT);

		Font fontBold = new Font(FONT.getName(), Font.BOLD, FONT.getSize());

		int y = PADDING;
		FontMetrics fm = g.getFontMetrics(FONT);
		FontMetrics fmBold = g.getFontMetrics(fontBold);

		int textWidth = getWidth() - 2 * PADDING;

		g.setFont(fontBold);
		y += fmBold.getHeight();
		g.drawString("Welcome to the D&D Storyteller", PADDING, y - fmBold.getDescent());

		g.setFont(FONT);
		List<String> rows = SceneFrame.split(text, textWidth, fm);

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

		List<String> skills = SceneFrame.split(player.getProficiencies(), textWidth, fm);
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

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}
}
