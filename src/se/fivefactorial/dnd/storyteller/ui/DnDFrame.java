package se.fivefactorial.dnd.storyteller.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class DnDFrame extends JPanel implements MouseMotionListener, MouseListener {

	protected static final int PADDING = 20;
	protected static final Font FONT = new Font("Bookman Old Style", Font.PLAIN, 18);
	protected static final Color BACKGROUND = Color.WHITE;
	protected static final Color TEXT = Color.BLACK;
	protected static final Color TEXT_HOVER = Color.RED;

	protected int mouseX;
	protected int mouseY;

	public DnDFrame() {
		addMouseMotionListener(this);
		addMouseListener(this);
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
}
