package com.labirent.algorithms;

import com.labirent.model.Cell;
import com.labirent.model.Maze;
import com.labirent.model.PathFindingResult;

public interface MazeSolver {
    PathFindingResult solve(Maze maze, Cell start, Cell end);
}
