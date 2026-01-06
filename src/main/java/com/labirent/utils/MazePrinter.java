package com.labirent.utils;

import com.labirent.model.Cell;
import com.labirent.model.CellType;
import com.labirent.model.Maze;

public class MazePrinter {

    public static void printMaze(Maze maze) {
        int rows = maze.rows;
        int cols = maze.cols;

        // Üst Çerçeve
        for (int c = 0; c < cols; c++) {
            System.out.print("+---");
        }
        System.out.println("+");

        for (int r = 0; r < rows; r++) {
            // Sol duvar
            System.out.print("|"); 
            
            for (int c = 0; c < cols; c++) {
                Cell cell = maze.getCell(r, c);
                
                // --- GÜNCELLEME BURADA ---
                // Hücrenin tipine göre içerik yazdır
                if (cell.type == CellType.START) {
                    System.out.print(" S ");
                } else if (cell.type == CellType.END) {
                    System.out.print(" E ");
                } else if (cell.type == CellType.PATH) {
                    System.out.print(" . "); // Bulunan yol
                } else {
                    System.out.print("   "); // Boş yol
                }
                // -------------------------

                // Sağ duvar kontrolü
                if (cell.walls[1]) {
                    System.out.print("|");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println(); // Satır sonu

            // Alt duvarlar
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