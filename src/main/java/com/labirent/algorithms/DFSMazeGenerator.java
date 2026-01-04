package com.labirent.algorithms;

import com.labirent.core.IMazeGenerator;
import com.labirent.model.Cell;
import java.util.Stack;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DFSMazeGenerator implements IMazeGenerator {

    @Override
    public Cell[][] generate(int width, int height){
        Cell[][] grid = new Cell[width][height];
        for(int x=0; x<width; x++){
            for(int y=0; y<height; y++){
                grid[x][y] = new Cell(x, y, true);
            }
        }
        Stack<Cell> stack = new Stack<>();
        Random rand = new Random();

        Cell current = grid[1][1];
        current.isVisited = true;
        current.isWall = false;
        stack.push(current);

        while(!stack.isEmpty()){
            current = stack.pop();

            List<Cell> unvisitedNeighbors = getUnvisitedNeighbors(current, grid, width, height);

            if(!unvisitedNeighbors.isEmpty()){
                stack.push(current);
                
                Cell next = unvisitedNeighbors.get(rand.nextInt(unvisitedNeighbors.size()));

                removeWalls(current, next, grid);

                next.isVisited = true;
                next.isWall = false;
                stack.push(next);

            }
        }
        
        resetVisitedFlags(grid);

        grid[1][0].isWall = false;
        grid[width-1][height-2].isWall = false;

        return grid;
    }

    private List<Cell> getUnvisitedNeighbors(Cell c, Cell[][] grid, int width, int height){
        List<Cell> neighbors = new ArrayList<>();

        int[][] directions = {
            {0, -2}, 
            {0, 2}, 
            {-2, 0},
            {2, 0} 
        };

        for(int[] dir : directions){
            int nx = c.x + dir[0];
            int ny = c.y + dir[1];

            if(nx > 0 && nx < width-1 && ny > 0 && ny<height-1){
                if(!grid[nx][ny].isVisited){
                    neighbors.add(grid[nx][ny]);
                }
            }
        }
        return neighbors;
    }

    private void removeWalls(Cell current, Cell next, Cell[][] grid){
        int wallX = (current.x + next.x) / 2;
        int wallY = (current.y + next.y) / 2;

        grid[wallX][wallY].isWall = false;
        grid[wallX][wallY].isVisited = true;
    }
    
    private void resetVisitedFlags(Cell[][] grid){
        for(int i=0; i<grid.length; i++){
            for(int j=0; j < grid[0].length; j++){
                grid[i][j].isVisited = false;
                grid[i][j].parent = null;
            }
        }
    }
}

