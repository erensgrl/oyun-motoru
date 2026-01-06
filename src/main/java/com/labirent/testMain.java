package com.labirent;

import com.labirent.algorithms.BFSSolver;
import com.labirent.algorithms.DFSSolver;
import com.labirent.algorithms.KruskalMazeGenerator;
import com.labirent.algorithms.MazeSolver;
import com.labirent.model.Cell;
import com.labirent.model.CellType;
import com.labirent.model.Maze;
import com.labirent.utils.MazePrinter;
import com.labirent.algorithms.AStarSolver;

public class testMain {
    public static void main(String[] args) {
        // 1. Labirent Oluştur
        int rows = 10;
        int cols = 10;
        Maze maze = new Maze(rows, cols);
        
        KruskalMazeGenerator generator = new KruskalMazeGenerator();
        generator.generate(maze); // 

        // 2. Başlangıç ve Bitiş Noktalarını Belirle
        Cell start = maze.getCell(0, 0);
        Cell end = maze.getCell(rows - 1, cols - 1);

        start.type = CellType.START;
        end.type = CellType.END;

        System.out.println(">>> Labirent Oluşturuldu. BFS Çözümü Başlıyor...");

        // 3. A* ile Çöz
        MazeSolver solver = new AStarSolver(); // 
        boolean solved = solver.solve(maze, start, end);

        if (solved) {
            System.out.println(">>> Çözüm BULUNDU! Yol işaretleniyor...");
            
            // --- BACKTRACKING (Yolu Geri Çizme) ---
            // Bitişten geriye doğru parent'ları takip et
            Cell current = end.parent;
            while (current != null && current != start) {
                current.type = CellType.PATH;
                current = current.parent;
            }
            // --------------------------------------
            
        } else {
            System.out.println(">>> Çözüm BULUNAMADI (Bu teorik olarak Kruskal ile imkansızdır).");
        }

        // 4. Sonucu Yazdır
        MazePrinter.printMaze(maze);
    }
}