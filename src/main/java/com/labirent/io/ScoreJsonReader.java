package com.labirent.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.labirent.model.Score;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ScoreJsonReader {

    private static final Path INPUT_PATH = Path.of("scores.json");

    public static List<Score> readScores() {

        try {
            if (!Files.exists(INPUT_PATH)) {
                System.out.println("scores.json yok, yeni liste oluşturuldu.");
                return new ArrayList<>();
            }

            String json = Files.readString(INPUT_PATH);

            if (json.isBlank()) {
                return new ArrayList<>();
            }

            Gson gson = new Gson();
            Type type = new TypeToken<List<Score>>() {}.getType();

            List<Score> scores = gson.fromJson(json, type);
            return scores != null ? scores : new ArrayList<>();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}


