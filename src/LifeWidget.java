import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LifeWidget extends JPanel implements ActionListener, CellListener {

    private JCellGrid _grid;        /* CellBoard playing area. */
    private JLabel _message;        /* Label for messages. */

    private int _grid_width;
    private int _grid_height;

    JButton reset_button;

    public LifeWidget() {

        _grid_width = 20;
        _grid_height = 20;

        _grid = new JCellGrid(_grid_width, _grid_height);
        _message = new JLabel();

        /* Set layout and place CellBoard at center. */
        setLayout(new BorderLayout());
        add(_grid, BorderLayout.CENTER);

        /* Create subpanel for message area and reset button. */
        JPanel sub_panel = new JPanel();
        sub_panel.setLayout(new BorderLayout());

        /* Reset button. Add ourselves as the action listener. */
        JButton reset_button = new JButton("Reset");
        reset_button.addActionListener(this);
        sub_panel.add(reset_button, BorderLayout.LINE_END);
//		reset_message_panel.add(_message, BorderLayout.CENTER);

        JButton random_button = new JButton("Random");
        random_button.addActionListener(this);
        sub_panel.add(random_button, BorderLayout.CENTER);

        JButton next_button = new JButton("Next");
        next_button.addActionListener(this);
        sub_panel.add(next_button, BorderLayout.WEST);

        /* Add subpanel in south area of layout. */
        add(sub_panel, BorderLayout.SOUTH);

        /* Add ourselves as a cell listener for all of the
         * cells on the cell board.
         */
        _grid.addCellListener(this);

        /* Reset game. */
        resetGrid();
    }

    private void resetGrid() {
        for (Cell s : _grid) {
            s.setCellDead();
        }


        /* Display game start message. */
        _message.setText("Welcome to Life.");
    }

    private void randomizeGrid() {
        for (Cell s : _grid) {
            double randNum = Math.random();
            if (randNum > 0.5) {
                s.setCellAlive();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Reset button
        if (e.getActionCommand().equals("Reset")) {
            resetGrid();
        }

        if (e.getActionCommand().equals("Random")) {
            resetGrid();
            randomizeGrid();
        }

        if (e.getActionCommand().equals("Next")) {
            int testColumn = 2;
            int testRow = 1;
            System.out.println("Before: " + numberOfNeighbors(_grid.getCellAt(testColumn, testRow)));
            System.out.println(_grid.getCellAt(testColumn, testRow).isDead());
            setNextGrid();
            System.out.println("After: " + numberOfNeighbors(_grid.getCellAt(testColumn, testRow)));
            System.out.println(_grid.getCellAt(testColumn, testRow).isDead());
        }


    }

    public int numberOfNeighbors(Cell cell) {
        int cellColumn = cell.getCellX();
        int cellRow = cell.getCellY();

        int neighbors = 0;

        // Check north
        if (cellRow - 1 >= 0 && !_grid.getCellAt(cellColumn, cellRow - 1).isDead()) {
            neighbors += 1;
        }
        // Check northeast
        if (cellRow - 1 >= 0 && cellColumn + 1 < _grid_width && !_grid.getCellAt(cellColumn + 1, cellRow - 1).isDead()) {
            neighbors += 1;
        }
        // Check east
        if (cellColumn + 1 < _grid_width && !_grid.getCellAt(cellColumn + 1, cellRow).isDead()) {
            neighbors += 1;
        }
        // Check southeast
        if (cellColumn + 1 < _grid_width && cellRow + 1 < _grid_height && !_grid.getCellAt(cellColumn + 1, cellRow + 1).isDead()) {
            neighbors += 1;
        }
        // Check south
        if (cellRow + 1 < _grid_height && !_grid.getCellAt(cellColumn, cellRow + 1).isDead()) {
            neighbors += 1;
        }
        // Check southwest
        if (cellColumn - 1 >= 0 && cellRow + 1 < _grid_height && !_grid.getCellAt(cellColumn - 1, cellRow + 1).isDead()) {
            neighbors += 1;
        }
        // Check west
        if (cellColumn - 1 >= 0 && !_grid.getCellAt(cellColumn - 1, cellRow).isDead()) {
            neighbors += 1;
        }
        // Check northwest
        if (cellColumn - 1 >= 0 && cellRow - 1 >= 0 && !_grid.getCellAt(cellColumn - 1, cellRow - 1).isDead()) {
            neighbors += 1;
        }

        return neighbors;
    }

    public void setNextGrid() {
        JCellGrid next_grid = new JCellGrid(_grid_width, _grid_height);

        for (Cell cell : _grid) {
            int cellColumn = cell.getCellX();
            int cellRow = cell.getCellY();

            int neighbors = numberOfNeighbors(cell);

//			1. Any live cell with two or three neighbors survives.
//			2. Any dead cell with three live neighbors becomes a live cell.
//			3. All other live cells die in the next generation. Similarly, all other dead cells stay dead.

            if (!cell.isDead()) {
                if (neighbors == 2 || neighbors == 3) {
                    next_grid.getCellAt(cellColumn, cellRow).setCellAlive();
                } else {
                    next_grid.getCellAt(cellColumn, cellRow).setCellDead();
                }
            } else {
                if (numberOfNeighbors(cell) == 3) {
                    next_grid.getCellAt(cellColumn, cellRow).setCellAlive();
                } else {
                    next_grid.getCellAt(cellColumn, cellRow).setCellDead();
                }
            }
        }

        // Copy new_grid over to _grid
        for (Cell cell : _grid) {
            if (next_grid.getCellAt(cell.getCellX(), cell.getCellY()).isDead()) {
                _grid.getCellAt(cell.getCellX(), cell.getCellY()).setCellDead();
            } else {
                _grid.getCellAt(cell.getCellX(), cell.getCellY()).setCellAlive();
            }

        }
    }

    @Override
    public void cellClicked(Cell s) {
        if (s.isDead()) {
            s.setCellAlive();
        } else {
            s.setCellDead();
        }
    }

    @Override
    public void cellEntered(Cell s) {
        /* Highlight cell if game still going on. */

//		if (_game_won) {
//			return;
//		}
//		s.highlightCell();
    }

    @Override
    public void cellExited(Cell s) {
        /* Unhighlight cell. */

//		s.unhighlightCell();
    }

}