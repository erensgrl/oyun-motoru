package com.labirent.algorithms;

import com.labirent.model.Cell;
import com.labirent.model.Maze;

public interface MazeSolver {
    boolean solve(Maze maze, Cell start, Cell end);
}
