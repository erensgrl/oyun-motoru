package com.labirent.io;

import com.labirent.model.Score;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ScoreCsvReader {

    private static final String CSV_PATH = "/data/scores.csv";

    public static List<Score> readScores() {
        List<Score> scores = new ArrayList<>();

        try {
            InputStream is = ScoreCsvReader.class.getResourceAsStream(CSV_PATH);

            if (is == null) {
                System.err.println("scores.csv bulunamadı!");
                return scores;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length != 3) continue;

                String playerName = parts[0].trim();
                double time = Double.parseDouble(parts[1].trim());
                int steps = Integer.parseInt(parts[2].trim());

                scores.add(new Score(playerName, time, steps));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return scores;
    }
}
