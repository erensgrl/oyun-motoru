package com.labirent.algorithms;

import com.labirent.datastructures.Queue;
import com.labirent.model.Cell;
import com.labirent.model.Maze;
import com.labirent.model.PathFindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class BFSSolver implements MazeSolver {

    @Override
    public PathFindingResult solve(Maze maze, Cell start, Cell end){
        maze.resetVisited();

        Queue<Cell> queue = new Queue<>();
        List<Cell> visitedOrder = new ArrayList<>();

        start.visited = true;
        queue.enqueue(start);
        visitedOrder.add(start);

        boolean success = false;

        while (!queue.isEmpty()){
            Cell current = queue.dequeue();

            if(current.row == end.row && current.col == end.col){
                success = true;
                break;
            }

            addNeighbor(maze, current, current.row - 1, current.col, 0, queue, visitedOrder);
            addNeighbor(maze, current, current.row + 1, current.col, 2, queue, visitedOrder);
            addNeighbor(maze, current, current.row, current.col - 1, 3, queue, visitedOrder);
            addNeighbor(maze, current, current.row, current.col + 1, 1, queue, visitedOrder);

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

    private void addNeighbor(Maze maze, Cell parent, int r, int c, int wallDirection, Queue<Cell> queue, List<Cell> visitedOrder){
        if(!maze.isValid(r,c)) return;

        Cell neighbor = maze.getCell(r,c);

        if(neighbor.visited) return;
        
        if(parent.walls[wallDirection]) return;

        neighbor.visited = true;
        neighbor.parent = parent;
        visitedOrder.add(neighbor);

        queue.enqueue(neighbor);
}
}

