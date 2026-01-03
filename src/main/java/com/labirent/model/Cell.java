package com.labirent.model;

public class Cell {
    public int x,y;
    public boolean isWall;
    public boolean isVisited;
    public Cell parent;

    public Cell(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
        this.isVisited = false;
    }
}

