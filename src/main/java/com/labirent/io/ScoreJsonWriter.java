package com.labirent.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.labirent.model.Score;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ScoreJsonWriter {

    private static final Path OUTPUT_PATH = Path.of("scores.json");

    public static void writeScores(List<Score> scores) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.writeString(
                    OUTPUT_PATH,
                    gson.toJson(scores)
            );
            System.out.println("JSON dosyasına yazıldı: " + OUTPUT_PATH.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

