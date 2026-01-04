package com.labirent.datastructures;

public class UnionFind {
    private int[] parent;
    private int[] rank;
    private int count;

    public UnionFind(int size){
        parent = new int[size];
        rank = new int[size];
        count = size;

        for(int i = 0; i < size; i++){
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int i){
        if(parent[i] != i){
            parent[i] = find(parent[i]);
        }
        return parent[i];
    }

    public boolean union(int i, int j){
        int rootI = find(i);
        int rootJ = find(j);

        if(rootI == rootJ){
            return false;
        }

        if(rank[rootI] < rank[rootJ]){
            parent[rootI] = rootJ;
        } else if(rank[rootI] > rank[rootJ]){
            parent[rootJ] = rootI;
        } else {
            parent[rootJ] = rootI;
            rank[rootI]++;
        }
        count--;
        return true;
    }

    public int getCount(){
        return count;
    }
}
