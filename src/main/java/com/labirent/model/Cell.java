package com.labirent.model;

public class Cell implements Comparable<Cell> {
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

    @Override
    public int compareTo(Cell other){
        if(this.fCost < other.fCost) return -1;
        if(this.fCost > other.fCost) return 1;

        if(this.hCost < other.hCost) return -1;
        if(this.hCost > other.hCost) return 1;

        return 0;
    }
}