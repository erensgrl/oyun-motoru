package com.labirent.algorithms;

import com.labirent.datastructures.Stack;
import com.labirent.model.Cell;
import com.labirent.model.Maze;

public class DFSSolver implements MazeSolver {
    
    @Override
    public boolean solve(Maze maze, Cell start, Cell end){
        maze.resetVisited();

        Stack<Cell> stack = new Stack<>();

        start.visited = true;
        stack.push(start);

        while(!stack.isEmpty()){
            Cell current = stack.pop();

            if(current.row == end.row && current.col == end.col){
                return true;
            }

            addNeighbor(maze, current, current.row - 1, current.col, 0, stack);
            addNeighbor(maze, current, current.row + 1, current.col, 2, stack);
            addNeighbor(maze, current, current.row, current.col - 1, 3, stack);
            addNeighbor(maze, current, current.row, current.col + 1, 1, stack);
        }
        return false;
    }

    private void addNeighbor(Maze maze, Cell parent, int r, int c, int wallDirection, Stack<Cell> stack){
        if(!maze.isValid(r,c)) return;

        Cell neighbor = maze.getCell(r,c);

        if(neighbor.visited) return;

        if(parent.walls[wallDirection]) return;

        neighbor.visited = true;
        neighbor.parent = parent;
        stack.push(neighbor);
    }
}
