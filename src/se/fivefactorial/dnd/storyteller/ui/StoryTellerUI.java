package se.fivefactorial.dnd.storyteller.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

import se.fivefactorial.dnd.storyteller.model.story.Story;

@SuppressWarnings("serial")
public class StoryTellerUI extends JFrame {

	private JPanel panel;

	public StoryTellerUI(Story story) {
		super(story.getTitle());

		panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.BLUE);
		add(panel, BorderLayout.CENTER);

		panel.add(new IntroFrame(this,story), BorderLayout.CENTER);

		setSize(700, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void changeFrame(JPanel frame) {
		panel.removeAll();
		panel.add(frame, BorderLayout.CENTER);
		panel.repaint();
		panel.updateUI();

	}
}