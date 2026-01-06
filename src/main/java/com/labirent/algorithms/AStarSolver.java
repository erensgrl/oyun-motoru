package com.labirent.algorithms;

import com.labirent.datastructures.MinHeap;
import com.labirent.model.Cell;
import com.labirent.model.Maze;

public class AStarSolver implements MazeSolver {
    
    @Override
    public boolean solve(Maze maze, Cell start, Cell end){
        maze.resetVisited();

        for(int r=0; r<maze.rows; r++){
            for(int c=0; c<maze.cols; c++){
                maze.getCell(r,c).gCost = Double.MAX_VALUE;
            }
        }


        MinHeap<Cell> openSet = new MinHeap<>(maze.rows * maze.cols);

        start.gCost = 0;
        start.hCost = calculateHeuristic(start,end);
        start.calculateFCost();
        openSet.insert(start);

        while(!openSet.isEmpty()){

            Cell current = openSet.extractMin();

            if(current.row == end.row && current.col == end.col){
                return true;
            }

            if(current.visited) continue;

            addNeighbor(maze, current, current.row-1, current.col, 0, end,openSet);
            addNeighbor(maze, current, current.row+1, current.col, 2, end,openSet);
            addNeighbor(maze, current, current.row, current.col-1, 3, end,openSet);
            addNeighbor(maze, current, current.row, current.col+1, 1, end,openSet);


        }
        return false;
    }

    private void addNeighbor(Maze maze, Cell current, int r, int c, int wallDirection, Cell end, MinHeap<Cell> openSet){

        if(!maze.isValid(r,c)) return;

        Cell neighbor = maze.getCell(r,c);

        if(neighbor.visited) return;

        if(current.walls[wallDirection]) return;

        double tempGCost = current.gCost + 1;

        if(tempGCost < neighbor.gCost){
            neighbor.parent = current;
            neighbor.gCost = tempGCost;
            neighbor.hCost = calculateHeuristic(neighbor, end);
            neighbor.calculateFCost();

            openSet.insert(neighbor);
        }
    }

    private double calculateHeuristic(Cell a, Cell b){
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col); // Manhattan mesafe |x1 - x2| + |y1 - y2| 
    }
}
