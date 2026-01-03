package com.labirent.core;

import com.labirent.model.Cell;
import java.util.List;

public interface IPathFinder {
    List<Cell> findPath(Cell[][] map, Cell start, Cell end);
}
