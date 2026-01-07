package com.labirent.io;

import com.labirent.model.Score;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.List;

public class ScoreCsvWriter {

    private static final Path OUTPUT_PATH = Path.of("scores.csv");

    public static void writeScores(List<Score> scores) {

        if (scores == null) {
            System.err.println("CSV yazılamadı: scores listesi null");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(OUTPUT_PATH.toFile()))) {

            pw.println("playerName,time,steps");

            for (Score s : scores) {
                pw.printf("%s,%.2f,%d%n",
                        s.playerName,
                        s.time,
                        s.steps);
            }

            System.out.println("Skorlar CSV'ye yazıldı.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


