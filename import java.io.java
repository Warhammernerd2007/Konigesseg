import java.io.*;
import java.util.*;

public class HighScoreManager {
    private static final String FILE_NAME = "highscores.txt";
    private static final int MAX_SCORES = 10;
    private List<ScoreEntry> highScores;

    public HighScoreManager() {
        highScores = new ArrayList<>();
        loadScores();
    }

    public void addScore(String name, int score) {
        highScores.add(new ScoreEntry(name, score, new Date()));
        highScores.sort((a, b) -> Integer.compare(b.score, a.score));
        if (highScores.size() > MAX_SCORES) {
            highScores.remove(highScores.size() - 1);
        }
        saveScores();
    }

    public List<ScoreEntry> getHighScores() {
        return highScores;
    }

    private void loadScores() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    Date date = new Date(Long.parseLong(parts[2]));
                    highScores.add(new ScoreEntry(name, score, date));
                }
            }
            highScores.sort((a, b) -> Integer.compare(b.score, a.score));
        } catch (IOException | NumberFormatException e) {
            System.out.println("No previous scores found or file corrupted.");
        }
    }

    private void saveScores() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (ScoreEntry entry : highScores) {
                bw.write(entry.name + "," + entry.score + "," + entry.date.getTime());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ScoreEntry {
        String name;
        int score;
        Date date;

        public ScoreEntry(String name, int score, Date date) {
            this.name = name;
            this.score = score;
            this.date = date;
        }
    }

    public static void main(String[] args) {
        HighScoreManager manager = new HighScoreManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter player name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter score: ");
        int score = scanner.nextInt();
        
        manager.addScore(name, score);
        
        System.out.println("High Scores:");
        for (ScoreEntry entry : manager.getHighScores()) {
            System.out.println(entry.name + " - " + entry.score + " - " + entry.date);
        }
    }
}
