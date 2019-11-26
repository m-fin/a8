import java.util.Iterator;
import java.util.NoSuchElementException;

public class CellGridIterator implements Iterator<Cell> {

	private CellGrid _grid;
	int _x;
	int _y;
	
	public CellGridIterator(CellGrid grid) {
		_grid = grid;
		_x = 0;
		_y = 0;
	}

	@Override
	public boolean hasNext() {
		return (_y < _grid.getCellHeight());
	}

	@Override
	public Cell next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		Cell s = _grid.getCellAt(_x, _y);
		if (_x < _grid.getCellWidth()-1) {
			_x++;
		} else {
			_x = 0;
			_y++;
		}
		return s;
	}

}
