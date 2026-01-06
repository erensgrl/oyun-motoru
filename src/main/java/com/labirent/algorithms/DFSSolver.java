package com.labirent.algorithms;

import com.labirent.datastructures.Stack;
import com.labirent.model.Cell;
import com.labirent.model.Maze;
import com.labirent.model.PathFindingResult;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class DFSSolver implements MazeSolver {
    
    @Override
    public PathFindingResult solve(Maze maze, Cell start, Cell end){
        maze.resetVisited();

        Stack<Cell> stack = new Stack<>();
        List<Cell> visitedOrder = new ArrayList<>();

        start.visited = true;
        visitedOrder.add(start);
        stack.push(start);

        boolean success = false;

        while(!stack.isEmpty()){
            Cell current = stack.pop();

            if(current.row == end.row && current.col == end.col){
                success = true;
                break;
            }

            addNeighbor(maze, current, current.row - 1, current.col, 0, stack, visitedOrder);
            addNeighbor(maze, current, current.row + 1, current.col, 2, stack, visitedOrder);
            addNeighbor(maze, current, current.row, current.col - 1, 3, stack, visitedOrder);
            addNeighbor(maze, current, current.row, current.col + 1, 1, stack, visitedOrder);
        }

        List<Cell> path = new ArrayList<>();
        if(success){
            Cell curr = end;
            while(curr != null){
                path.add(curr);
                curr = curr.parent;
            }
            Collections.reverse(path);
        }

        return new PathFindingResult(success, path, visitedOrder);
    }

    private void addNeighbor(Maze maze, Cell parent, int r, int c, int wallDirection, Stack<Cell> stack, List<Cell> visitedOrder){
        if(!maze.isValid(r,c)) return;

        Cell neighbor = maze.getCell(r,c);

        if(neighbor.visited) return;

        if(parent.walls[wallDirection]) return;

        neighbor.visited = true;
        neighbor.parent = parent;
        visitedOrder.add(neighbor);

        stack.push(neighbor);
    }
}
