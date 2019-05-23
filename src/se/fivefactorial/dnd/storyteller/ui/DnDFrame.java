package se.fivefactorial.dnd.storyteller.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class DnDFrame extends JPanel implements MouseMotionListener, MouseListener {

	protected static final int PADDING = 20;
	protected static final Font FONT = new Font("Bookman Old Style", Font.PLAIN, 18);
	protected static final Font FONT_BOLD = new Font("Bookman Old Style", Font.BOLD, 18);

	protected static final Color BACKGROUND = Color.WHITE;
	protected static final Color TEXT = Color.BLACK;
	protected static final Color TEXT_HOVER = new Color(88,23,13);

	protected static Image BACKGROUND_IMAGE;

	protected int mouseX;
	protected int mouseY;

	public DnDFrame() {
		addMouseMotionListener(this);
		addMouseListener(this);

		try {
			BACKGROUND_IMAGE = ImageIO.read(new File("img/bg.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
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

	protected List<String> split(List<String> texts, int width, FontMetrics fm) {
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

}
