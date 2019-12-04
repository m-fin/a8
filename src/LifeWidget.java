import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LifeWidget extends JPanel implements ActionListener, CellListener {

    private JCellGrid _grid;
    private JLabel _message;

    private int _grid_width;
    private int _grid_height;

    JButton reset_button;

    private JTextField size_field;

    public LifeWidget() {

        _grid_width = 20;
        _grid_height = 20;

        _grid = new JCellGrid(_grid_width, _grid_height);
        _message = new JLabel();

        setLayout(new BorderLayout());
        add(_grid, BorderLayout.CENTER);

        JPanel sub_panel = new JPanel();
        sub_panel.setLayout(new BorderLayout());

        JButton random_button = new JButton("Random");
        random_button.addActionListener(this);
        sub_panel.add(random_button, BorderLayout.LINE_START);

        JButton next_button = new JButton("Next");
        next_button.addActionListener(this);
        sub_panel.add(next_button, BorderLayout.CENTER);

        JButton reset_button = new JButton("Reset");
        reset_button.addActionListener(this);
        sub_panel.add(reset_button, BorderLayout.LINE_END);

        add(sub_panel, BorderLayout.SOUTH);

        _grid.addCellListener(this);

        resetGrid();
    }

    private void resetGrid() {
        for (Cell s : _grid) {
            s.setCellDead();
        }

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

        if (e.getActionCommand().equals("Reset")) {
            resetGrid();
        }

        if (e.getActionCommand().equals("Random")) {
            resetGrid();
            randomizeGrid();
        }

        if (e.getActionCommand().equals("Next")) {
            setNextGrid();
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
    }

    @Override
    public void cellExited(Cell s) {
    }

}