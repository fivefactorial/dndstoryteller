package se.fivefactorial.dnd.storyteller.ui;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import se.fivefactorial.dnd.storyteller.model.story.Story;

@SuppressWarnings("serial")
public class EndFrame extends DnDFrame {

	private Story story;

	public EndFrame(Story story) {
		this.story = story;
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
		g.drawString("The story is over", PADDING, y - fmBold.getDescent());

		g.setFont(FONT);

		List<String> text = new ArrayList<>();
		text.add(String.format("Thank you for playing the story %s. We hope that it was interesting and fun.",
				story.getTitle()));

		List<String> rewards = story.getPlayer().getRewards();
		if (!rewards.isEmpty()) {
			text.add("Rewards:");
			text.addAll(rewards);
		}
		text.add("");
		text.add("");
		text.add("Info to the DM:");
		text.add(story.getPath());

		List<String> rows = split(text, textWidth, fm);
		for (String row : rows) {
			y += fm.getHeight();
			g.drawString(row, PADDING, y - fm.getDescent());
		}

		y += fmBold.getHeight();
		y = Math.max(y, getHeight() - PADDING);
		g.setFont(fontBold);
		String s = "Until next time...";
		g.drawString(s, PADDING + textWidth - fmBold.stringWidth(s), y - fmBold.getDescent());
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

}
