package com.labirent.model;

public class Edge implements Comparable<Edge>  {
    public Cell source;
    public Cell target;
    public double weight;

    public Edge(Cell source, Cell target, double weight){
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other){
        return Double.compare(this.weight, other.weight);
    }
}
