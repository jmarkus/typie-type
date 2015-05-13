import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class TTGame {
	
	TTController controller;
	
	ArrayList<String> words;
	
	Random rand = new Random();
	int level;
	long startTime;
	int correctLetters;
	
	
	public String currentWord;
	public int currentIndex;
	
	
	public TTGame(int level) {
		words = new ArrayList<String>(100);
		readInLevelFile(level);
	}
	
	private void readInLevelFile(int level) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(String.format("res/levels/level%d.txt", level)))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       line = line.toUpperCase();
		       System.out.println("Read in word: " + line);
		       words.add(line);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void changeCurrentWord() {
		currentWord = words.get(rand.nextInt(words.size()));
		currentIndex = 0;
		System.out.println("New word: " + currentWord);
	}
	
	public boolean matchLetter(char letter) {
		boolean match = currentWord.charAt(currentIndex) == letter;
		if (match) {
			currentIndex++;
			correctLetters++;
			if (currentIndex == currentWord.length()) {
				changeCurrentWord();
			}
		}
		
		return match;
	}
	
	public void startGame() {
		currentWord = "";
		correctLetters = 0;
		changeCurrentWord();
		startTime = System.currentTimeMillis();
		System.out.println("Start time: " + startTime);
	}
	
	public long getEllapsedTimeMillis() {
		return System.currentTimeMillis() - startTime;
	}
	
	public double getLPM() {
		return correctLetters / (getEllapsedTimeMillis() / 6000.0);
	}
	

}
