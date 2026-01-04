package com.labirent.model;

public class Maze {
    public int rows;
    public int cols;
    public Cell[][] grid;

    public Maze(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        initializeGrid();
    }

    private void initializeGrid(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Cell(i, j);
            }
        }
    }

    public Cell getCell(int r, int c){
        if(isValid(r,c)){
            return grid[r][c];
        }
        return null;
    }

    public boolean isValid(int r, int c){
        return r >= 0 && r < rows && c >= 0 && c < cols;
    }

    public void resetVisited(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                grid[i][j].visited = false;
                grid[i][j].parent = null;
                grid[i][j].gCost = 0;
                grid[i][j].hCost = 0;
                grid[i][j].fCost = 0;

                if(grid[i][j].type == CellType.PATH){
                    grid[i][j].type = CellType.EMPTY;
                }
            }
        }
    }

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }
}
