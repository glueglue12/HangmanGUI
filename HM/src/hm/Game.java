package hm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    private List<String> words = new ArrayList<>();
    private String currentWord;
    private int wrongGuesses = 0;
    private StringBuilder currentGuess;

    public Game() {
        loadWordsFromFile("words.txt");
        reset();
    }

    private void loadWordsFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void reset() {
        if (words.isEmpty()) {
            throw new IllegalStateException("단어 목록이 비어 있습니다.");
        }
        Random rand = new Random();
        currentWord = words.get(rand.nextInt(words.size()));
        wrongGuesses = 0;
        currentGuess = new StringBuilder("_".repeat(currentWord.length()));
    }


    public boolean guess(char letter) {
        boolean isCorrect = false;
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == letter) {
                currentGuess.setCharAt(i, letter);
                isCorrect = true;
            }
        }
        if (!isCorrect) {
            wrongGuesses++;
        }
        return isCorrect;
    }

    public boolean isGameOver() {
        return wrongGuesses >= 6 || currentGuess.toString().equals(currentWord);
    }

    public String getCurrentGuess() {
        return currentGuess.toString();
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public String getCurrentWord() {
        return currentWord;
    }
}
