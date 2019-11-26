import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/*
 * JCell
 * 
 * A custom user interface component that implements a cell on a cell board
 * as an extension of JPanel.
 * 
 * A JCell acts as its own mouse listener and will translate mouse events into
 * notifications to registered CellListener objects when the cell is entered,
 * exited, or clicked using the appropriate methods (see CellListener).
 *
 */

public class JCell extends JPanel implements MouseListener, Cell {

	private Color _cell_color;
	private Color _highlight_color;

	private boolean _is_dead;
	private boolean _is_highlighted;
	private CellGrid _board;
	private int _x;
	private int _y;

	private List<CellListener> _cell_listeners;

	public JCell(Color background, Color cell_color, Color highlight,
                 CellGrid board, int x, int y) {

		// Background color inherited from JPanel
		setBackground(background);

		_cell_color = cell_color;
		_highlight_color = highlight;
		_is_dead = true;
		_is_highlighted = false;
		_board = board;
		_x = x;
		_y = y;

		_cell_listeners = new ArrayList<CellListener>();

		addMouseListener(this);
	}

	// Getters for X, Y, and Grid properties

	@Override
	public int getCellX() {
		return _x;
	}

	@Override
	public int getCellY() {
		return _y;
	}

	@Override
	public CellGrid getGrid() {
		return _board;
	}

	// Empty / Filled status methods

	@Override
	public boolean isDead() {
		return _is_dead;
	}

	@Override
	public void setCellAlive() {
		_is_dead = false;
		trigger_update();
	}

	@Override
	public void setCellDead() {
		_is_dead = true;
		trigger_update();
	}

	// Highlight status methods

	@Override
	public boolean isHighlighted() {
		return _is_highlighted;
	}

	@Override
	public void highlightCell() {
		_is_highlighted = true;
		trigger_update();
	}

	@Override
	public void unhighlightCell() {
		_is_highlighted = false;
		trigger_update();
	}

	// Getters / Setters for color properties Highlight and CellColor.
	// getBackground and setBackground are inherited from JPanel.

	@Override
	public void setCellColor(Color c) {		
		if (c == null) throw new IllegalArgumentException("null color");

		_cell_color = c;
		trigger_update();
	}

	@Override
	public Color getCellColor() {
		return _cell_color;
	}

	@Override
	public void setHighlight(Color c) {
		if (c == null) throw new IllegalArgumentException("null color");

		_highlight_color = c;
		trigger_update();
	}

	@Override
	public Color getHighlight() {
		return _highlight_color;
	}

	// Cell listener (de)registration methods.

	@Override
	public void addCellListener(CellListener l) {
		_cell_listeners.add(l);
	}

	@Override
	public void removeCellListener(CellListener l) {
		_cell_listeners.remove(l);
	}

	// Overriding paintComponent from JPanel to paint ourselves
	// the way we want.

	@Override
	public void paintComponent(Graphics g) {
		// Super class paintComponent will take care of
		// painting the background.
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create();
		if (isHighlighted()) {
			g2d.setColor(getHighlight());
			g2d.setStroke(new BasicStroke(4));
			g2d.drawRect(0, 0, getWidth(), getHeight());
		}
		if (!isDead()) {
			g2d.setColor(getCellColor());
			g2d.fillRect(0, 0, this.getWidth()-1, this.getHeight()-1);
		}
	}

	// Mouse listener implementation below for translating
	// mouse events into cell listener events.

	@Override
	public void mouseClicked(MouseEvent e) {
		for (CellListener s : _cell_listeners) {
			s.cellClicked(this);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		for (CellListener s : _cell_listeners) {
			s.cellEntered(this);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		for (CellListener s : _cell_listeners) {
			s.cellExited(this);
		}
	}	
	
	private void trigger_update() {		
		repaint();

		// Not sure why, but need to schedule a call
		// to repaint for a little bit into the future
		// as well as the one we just did above
		// in order to make sure that we don't end up
		// with visual artifacts due to race conditions.
		
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}).start();

	}
}
