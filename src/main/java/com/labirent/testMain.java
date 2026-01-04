

package com.labirent;

import com.labirent.algorithms.KruskalMazeGenerator;
import com.labirent.model.Maze;
import com.labirent.utils.MazePrinter;

public class testMain {
    public static void main(String[] args) {
        int rows = 10;
        int cols = 10;
        
        System.out.println(">>> Labirent Oluşturuluyor (" + rows + "x" + cols + ")...");

        Maze maze = new Maze(rows, cols);

        KruskalMazeGenerator generator = new KruskalMazeGenerator();
        generator.generate(maze);

        System.out.println(">>> Labirent Üretildi (Kruskal Algoritması).");
        System.out.println(">>> İşte Sonuç:\n");

        MazePrinter.printMaze(maze);
    }
}