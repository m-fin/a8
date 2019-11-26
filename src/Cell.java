import java.awt.*;

public interface Cell {

	int getCellX();
	int getCellY();
	CellGrid getGrid();

	boolean isDead();
	void setCellAlive();
	void setCellDead();
	default void toggleCell() {
		if (isDead()) {
			setCellAlive();
		} else {
			setCellDead();
		}
	}

	boolean isHighlighted();
	void highlightCell();
	void unhighlightCell();
	default void toggleHighlight() {
		if (isHighlighted()) {
			unhighlightCell();
		} else {
			highlightCell();
		}
	}

	void setBackground(Color c);
	Color getBackground();
	void setCellColor(Color c);
	Color getCellColor();
	void setHighlight(Color c);
	Color getHighlight();

	void addCellListener(CellListener l);
	void removeCellListener(CellListener l);

	default String getCoordString() {
		return "(" + getCellX() + ", " + getCellY() + ")";
	}
}