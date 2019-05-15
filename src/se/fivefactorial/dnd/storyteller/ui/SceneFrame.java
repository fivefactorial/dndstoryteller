package se.fivefactorial.dnd.storyteller.ui;

import java.awt.Color;
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
import se.fivefactorial.dnd.storyteller.model.story.Scene;
import se.fivefactorial.dnd.storyteller.model.story.Story;
import se.fivefactorial.dnd.storyteller.model.story.link.Link;
import se.fivefactorial.dnd.storyteller.model.story.reward.Reward;

@SuppressWarnings("serial")
public class SceneFrame extends DnDFrame implements MouseMotionListener, MouseListener {

	private StoryTellerUI ui;
	private Story story;
	private Scene scene;
	private Link active;
	private int mouseX;
	private int mouseY;

	public SceneFrame(StoryTellerUI ui, Story story) {
		this.ui = ui;
		this.story = story;
		scene = story.getCurrentScene();
		setBackground(Color.PINK);
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		g.setFont(FONT);
		g.setColor(BACKGROUND);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(TEXT);
		String number = "Scene: " + scene.getNumber();
		List<String> texts = scene.getText();
		int y = PADDING;
		int textWidth = getWidth() - PADDING * 2;
		if (StoryTeller.debug) {
			FontMetrics fm = g.getFontMetrics();
			y += fm.getHeight();
			g.drawString(number, PADDING, y - fm.getDescent());
		}

		{
			FontMetrics fm = g.getFontMetrics();

			List<String> rows = split(texts, textWidth, fm);

			for (String row : rows) {
				y += fm.getHeight();
				g.drawString(row, PADDING, y - fm.getDescent());
			}

		}

		{
			FontMetrics fm = g.getFontMetrics();
			y += fm.getHeight() * 2;

			Link active = null;
			String before = "- ";
			int linkWidth = textWidth - fm.stringWidth(before);
			for (Link link : scene.getLinks()) {
				g.setColor(TEXT);
				List<String> text = new ArrayList<>();
				text.add(link.toString());
				text = split(text, linkWidth, fm);

				int rx = PADDING;
				int ry = y;
				int rw = textWidth;
				int rh = fm.getHeight() * text.size();

				Rectangle r = new Rectangle(rx, ry, rw, rh);
				if (r.contains(mouseX, mouseY)) {
					active = link;
					g.setColor(TEXT_HOVER);
				}
				g.drawString(before, PADDING, y + fm.getHeight() - fm.getDescent());
				for (String t : text) {
					y += fm.getHeight();
					g.drawString(t, PADDING + fm.stringWidth(before), y - fm.getDescent());
				}
				if (StoryTeller.debug) {
					g.drawRect(r.x, r.y, r.width, r.height);
				}
			}
			this.active = active;
		}

		if (StoryTeller.debug) {
			FontMetrics fm = g.getFontMetrics();
			String text = String.format("x: %d y: %d active: %s", mouseX, mouseY, active);
			g.drawString(text, PADDING, getHeight() - PADDING - fm.getDescent());
		}

	}

	public static List<String> split(List<String> texts, int width, FontMetrics fm) {
		ArrayList<String> rows = new ArrayList<>();
		for (String text : texts) {
			String[] words = text.split("\\s+");
			String current = "";
			for (String word : words) {
				String temp = (current + " " + word).trim();
				if (fm.stringWidth(temp) > width) {
					rows.add(current);
					current = word;
				} else {
					current = temp;
				}
			}
			rows.add(current);
		}
		return rows;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (active != null) {

			List<Reward> rewards = scene.getRewards();
			for (Reward reward : rewards) {
				reward.apply(story.getPlayer());
			}

			Scene nextScene = story.swapScene(active.getTo());
			if (nextScene == null) {
				ui.changeFrame(new EndFrame(story));
			} else {
				scene = nextScene;
				active = null;

				repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

}
