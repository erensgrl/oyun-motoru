package com.labirent.utils;

import com.labirent.model.Cell;
import com.labirent.model.Maze;

public class MazePrinter {

    public static void printMaze(Maze maze) {
        int rows = maze.rows;
        int cols = maze.cols;

        for (int c = 0; c < cols; c++) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int r = 0; r < rows; r++) {
      
            System.out.print("|"); 
            
            for (int c = 0; c < cols; c++) {
                Cell cell = maze.getCell(r, c);
                

                System.out.print("   "); 

             
                if (cell.walls[1]) {
                    System.out.print("|");
                } else {
                    System.out.print(" "); 
                }
            }
            System.out.println(); 

         
            System.out.print("+"); 
            for (int c = 0; c < cols; c++) {
                Cell cell = maze.getCell(r, c);

                
                if (cell.walls[2]) {
                    System.out.print("---");
                } else {
                    System.out.print("   "); 
                }
                System.out.print("+"); 
            }
            System.out.println(); 
        }
    }
}