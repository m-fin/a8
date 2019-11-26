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

//		/* If game already won, do nothing. */
//		if (_game_won) {
//			return;
//		}

//		if (_next_to_play == Player.BLUE) {
//			player_color = Color.BLUE;
//			player_name = "Blue";
//			next_player_name = "Green";
//			_next_to_play = Player.GREEN;
//		} else {
//			player_color = Color.GREEN;
//			player_name = "Green";
//			next_player_name = "Blue";
//			_next_to_play = Player.BLUE;
//		}


        /* Set color of cell clicked and toggle. */
//		s.setCellColor(player_color);
//		s.toggleCell();

        /* Check if cell clicked is secret cell.
         * If so, mark game as won and update background
         * of cell to show that it was the secret cell.
         */

//		_game_won = (s == _secret_cell);
//		if (_game_won) {
//			s.setBackground(Color.RED);
//		}

        /* Update the message depending on what happened.
         * If cell is empty, then we must have just cleared the cell. Update message accordingly.
         * If cell is not empty and the game is won, we must have
         * just won. Calculate score and display as part of game won message.
         * If cell is not empty and the game is not won, update message to
         * report cell coordinates and indicate whose turn is next.
         */

//		if (s.isDead()) {
//			_message.setText(player_name + " cleared the cell at " + s.getCoordString() + ". " + next_player_name + " to play.");
//		} else {
//			if (_game_won)  {
//				int score = _grid.getCellWidth() * _grid.getCellHeight();
//				for (Cell board_cell : _grid) {
//					if (!board_cell.isDead()) {
//						if (board_cell.getCellColor() == player_color) {
//							score -= 1;
//						} else {
//							score += 1;
//						}
//					}
//				}
//				_message.setText(player_name + " found the secret cell at " + s.getCoordString() + ". " +
//				                 "Score: " + score + ". Game over.");
//			} else {
//				_message.setText("Cell at " + s.getCoordString() + " is not the cell. Better luck next time. " + next_player_name + " to play.");
//			}
//		}
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