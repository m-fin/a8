import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class JCellGrid extends JPanel implements CellGrid {

	private static final int DEFAULT_SCREEN_WIDTH = 500;
	private static final int DEFAULT_SCREEN_HEIGHT = 500;
	private static final Color DEFAULT_BACKGROUND_LIGHT = new Color(0.8f, 0.8f, 0.8f);
	private static final Color DEFAULT_BACKGROUND_DARK = new Color(0.5f, 0.5f, 0.5f);
	private static final Color DEFAULT_CELL_COLOR = Color.WHITE;
	private static final Color DEFAULT_HIGHLIGHT_COLOR = Color.YELLOW;

	private Cell[][] _cells;
	
	public JCellGrid(int width, int height) {
		if (width < 1 || height < 1 || width > 500 || height > 500) {
			throw new IllegalArgumentException("Illegal spot board geometry");
		}
		setLayout(new GridLayout(height, width));
		_cells = new Cell[width][height];
		
		Dimension preferred_size = new Dimension(DEFAULT_SCREEN_WIDTH/width, DEFAULT_SCREEN_HEIGHT/height);
		
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				Color bg = Color.BLACK;
				_cells[x][y] = new JCell(bg, DEFAULT_CELL_COLOR, DEFAULT_HIGHLIGHT_COLOR, this, x, y);
				((JCell)_cells[x][y]).setPreferredSize(preferred_size);
				add(((JCell) _cells[x][y]));
			}			
		}
	}

	// Getters for CellWidth and CellHeight properties
	
	@Override
	public int getCellWidth() {
		return _cells.length;
	}
	
	@Override
	public int getCellHeight() {
		return _cells[0].length;
	}

	// Lookup method for Cell at position (x,y)
	
	@Override
	public Cell getCellAt(int x, int y) {
		if (x < 0 || x >= getCellWidth() || y < 0 || y >= getCellHeight()) {
			throw new IllegalArgumentException("Illegal spot coordinates");
		}
		
		return _cells[x][y];
	}
	
	@Override
	public void addCellListener(CellListener spot_listener) {
		for (int x=0; x<getCellWidth(); x++) {
			for (int y=0; y<getCellHeight(); y++) {
				_cells[x][y].addCellListener(spot_listener);
			}
		}
	}
	
	@Override
	public void removeCellListener(CellListener spot_listener) {
		for (int x=0; x<getCellWidth(); x++) {
			for (int y=0; y<getCellHeight(); y++) {
				_cells[x][y].removeCellListener(spot_listener);
			}
		}
	}

	@Override
	public Iterator<Cell> iterator() {
		return new CellGridIterator(this);
	}
}
