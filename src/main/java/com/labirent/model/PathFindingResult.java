package com.labirent.model;

import java.util.List;

public class PathFindingResult {
    public boolean success;
    public List<Cell> path;
    public List<Cell> visitedOrder;

    public PathFindingResult(boolean success, List<Cell> path, List<Cell> visitedOrder){
        this.success = success;
        this.path = path;
        this.visitedOrder = visitedOrder;
    }
}
