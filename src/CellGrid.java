import java.util.Iterator;

public interface CellGrid extends Iterable<Cell> {

	int getCellWidth();
 
	int getCellHeight();

	Cell getCellAt(int x, int y);

	void addCellListener(CellListener spot_listener);

	void removeCellListener(CellListener spot_listener);

	Iterator<Cell> iterator();

}