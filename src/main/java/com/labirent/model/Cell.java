package com.labirent.model;

public class Cell {
    public int row, col;
    public CellType type;
    public boolean visited;

    public boolean[] walls = {true, true, true, true}; // {üst, sağ, alt, sol} true = duvar var, false = duvar yok

    public double gCost, hCost, fCost;
    public Cell parent;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.type = CellType.EMPTY;
        this.visited = false;
    }

    public void removeWall(int direction){
        if(direction>=0 && direction<=3){
            this.walls[direction] = false;
        }
    }

    public void calculateFCost(){
        this.fCost = this.gCost + this.hCost;
    }
}