package com.example.asteroids;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HighScores {

    private static final String FILE_NAME = "high_scores.txt";
    private static final int MAX_SCORES = 10;

    public static void addScore(String playerName, int score) {
        // Load existing high scores from file
        List<Score> scores = readScores();
        // Add the new score to the list
        scores.add(new Score(playerName, score));
        Collections.sort(scores, Collections.reverseOrder());
        // Only keep the top MAX_SCORES scores
        if (scores.size() > MAX_SCORES) {
            scores = scores.subList(0, MAX_SCORES);
        }
        // Only keep the top MAX_SCORES scores
        writeScores(scores);
    }

    public static List<Score> getScores() {
        return readScores();
    }

    public static List<Score> readScores() {
        List<Score> scores = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new Score(playerName, score));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return scores;
    }

        static void writeScores(List<Score> scores) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(FILE_NAME)))) {
            for (Score score : scores) {
                writer.println(score.getPlayerName() + "," + score.getScore());
            }
        } catch (IOException e) {
            System.err.println("Error writing scores file: " + e.getMessage());
        }
    }

    public static class Score implements Comparable<Score> {
        private String playerName;
        private int score;

        public Score(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }

        public String getPlayerName() {
            return playerName;
        }

        public int getScore() {
            return score;
        }

        @Override
        public int compareTo(Score o) {
            return Integer.compare(score, o.score);
        }
    }
}
