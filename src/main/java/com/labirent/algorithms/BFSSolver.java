package com.labirent.algorithms;

import com.labirent.datastructures.Queue;
import com.labirent.model.Cell;
import com.labirent.model.Maze;

public class BFSSolver implements MazeSolver {

    @Override
    public boolean solve(Maze maze, Cell start, Cell end){
        maze.resetVisited();

        Queue<Cell> queue = new Queue<>();

        start.visited = true;
        queue.enqueue(start);

        while (!queue.isEmpty()){
            Cell current = queue.dequeue();

            if(current.row == end.row && current.col == end.col){
                return true;
            }

            addNeighbor(maze, current, current.row - 1, current.col, 0, queue);
            addNeighbor(maze, current, current.row + 1, current.col, 2, queue);
            addNeighbor(maze, current, current.row, current.col - 1, 3, queue);
            addNeighbor(maze, current, current.row, current.col + 1, 1, queue);

        }

        return false;
    }

    private void addNeighbor(Maze maze, Cell parent, int r, int c, int wallDirection, Queue<Cell> queue){
        if(!maze.isValid(r,c)) return;

        Cell neighbor = maze.getCell(r,c);

        if(neighbor.visited) return;
        
        if(parent.walls[wallDirection]) return;

        neighbor.visited = true;
        neighbor.parent = parent;
        queue.enqueue(neighbor);
}
}

