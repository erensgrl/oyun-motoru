package com.labirent.ui;

import com.labirent.model.Cell;
import com.labirent.model.Maze;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MazeUI {

    private static final int CELL_SIZE = 30;
    private Rectangle[][] cells;

    public GridPane createMazeView(Maze maze) {
        int rows = maze.getRows();
        int cols = maze.getCols();

        cells = new Rectangle[rows][cols];
        GridPane grid = new GridPane();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setStroke(Color.GRAY);

                Cell cell = maze.getCell(r, c);
                rect.setFill(getColor(cell));

                cells[r][c] = rect;
                grid.add(rect, c, r);
            }
        }
        return grid;
    }

    private Color getColor(Cell cell) {
        switch (cell.type) {
            case WALL:
                return Color.BLACK;
            case START:
                return Color.GREEN;
            case END:
                return Color.RED;
            case EMPTY:
            default:
                return Color.WHITE;
        }
    }
}

