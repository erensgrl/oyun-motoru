package com.labirent.model;

public class Score implements Comparable<Score> {
    public String playerName;
    public double time;
    public int steps;

    public Score(String playerName, double time, int steps){
        this.playerName = playerName;
        this.time = time;
        this.steps = steps;
    }

    @Override
    public int compareTo(Score other){
        if(this.time < other.time) return -1;
        if(this.time > other.time) return 1;

        return Integer.compare(this.steps, other.steps);
    }

    @Override
    public String toString(){
        return String.format("%s -> %.2fs (%d steps)", playerName, time, steps);
    }
}
