import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

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

	@Override
	public void addCellListener(CellListener l) {
		_cell_listeners.add(l);
	}

	@Override
	public void removeCellListener(CellListener l) {
		_cell_listeners.remove(l);
	}

	@Override
	public void paintComponent(Graphics g) {
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
