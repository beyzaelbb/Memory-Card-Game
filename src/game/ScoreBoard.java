package game;

import java.io.*;
import java.util.*;

public class ScoreBoard {
    private static final String FILE_NAME = "highscores.txt";

    public static void saveHighScore(String name, int score) {
        HashMap<String, Integer> highScores = loadScores();
        highScores.put(name, score);
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, Integer> entry : highScores.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, Integer> loadScores() {
        HashMap<String, Integer> highScores = new HashMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    highScores.put(parts[0], Integer.parseInt(parts[1]));
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    public static List<Map.Entry<String, Integer>> getTop10Scores() {
        HashMap<String, Integer> highScores = loadScores();
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(highScores.entrySet());
        Collections.sort(sortedScores, new Comparator<Map.Entry<String, Integer>>() {
        	
            @Override
            public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                return entry2.getValue().compareTo(entry1.getValue());
            }
            
        });
        return sortedScores.size() > 10 ? sortedScores.subList(0, 10) : sortedScores;
    }
}


















