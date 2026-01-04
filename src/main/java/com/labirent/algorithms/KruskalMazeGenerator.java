package com.labirent.algorithms;

import com.labirent.datastructures.MinHeap;
import com.labirent.datastructures.UnionFind;
import com.labirent.model.Cell;
import com.labirent.model.Edge;
import com.labirent.model.Maze;

import java.util.Random;

public class KruskalMazeGenerator {
    private Random random = new Random();

    public void generate(Maze maze){
        int rows = maze.rows;
        int cols = maze.cols;

        UnionFind uf = new UnionFind(rows * cols);
        MinHeap<Edge> edgeHeap = new MinHeap<>(rows * cols * 2);

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                Cell current = maze.getCell(r,c);

                if(c+1 < cols){
                    Cell right = maze.getCell(r, c+1);
                    double weight = random.nextDouble();
                    edgeHeap.insert(new Edge(current, right, weight));
                }

                if(r+1 < rows){
                    Cell bottom = maze.getCell(r+1, c);
                    double weight = random.nextDouble();
                    edgeHeap.insert(new Edge(current, bottom, weight));
                }
            }
        }

        while(!edgeHeap.isEmpty()){
            Edge edge = edgeHeap.extractMin();
            Cell u = edge.source;
            Cell v = edge.target;

            int uID = u.row*cols + u.col;
            int vID = v.row*cols + v.col;

            if(uf.find(uID) != uf.find(vID)){
                uf.union(uID, vID);

                removeWallBetween(u,v);
            }
        }
    }

    private void removeWallBetween(Cell a, Cell b){
        if(a.row == b.row){
            if(a.col < b.col){
                a.removeWall(1);
                b.removeWall(3);
            } else {
                a.removeWall(3);
                b.removeWall(1);
            }
        }

        else if(a.col == b.col){
            if(a.row < b.row){
                a.removeWall(2);
                b.removeWall(0);
            } else {
                a.removeWall(0);
                b.removeWall(2);
            }
        }
    }
}
