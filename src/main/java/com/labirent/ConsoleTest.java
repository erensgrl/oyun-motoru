package com.labirent;

import com.labirent.algorithms.DFSMazeGenerator;
import com.labirent.model.Cell;

public class ConsoleTest {
    public static void main(String[] args) {
        // 1. Üreticiyi oluştur
        DFSMazeGenerator generator = new DFSMazeGenerator();

        // 2. Labirent boyutunu belirle (Tek sayı olması estetik açıdan iyidir)
        int width = 21;
        int height = 21;

        System.out.println("Labirent Üretiliyor (" + width + "x" + height + ")...\n");
        
        // 3. Labirenti üret
        Cell[][] maze = generator.generate(width, height);

        // 4. Ekrana Çiz (ASCII Sanatı)
        printMazeToConsole(maze, width, height);
    }

    private static void printMazeToConsole(Cell[][] maze, int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = maze[x][y];
                if (cell.isWall) {
                    System.out.print("██"); // Duvar (İki karakter genişliğinde daha kare durur)
                } else {
                    System.out.print("  "); // Yol (Boşluk)
                }
            }
            System.out.println(); // Satır sonu
        }
    }
}