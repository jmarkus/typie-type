import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class TTGame {
	
	
	
	
	ArrayList<String> words;
	Random rand = new Random();
	long startTime;
	int gameTime;
	int correctLetters;
	int correctWords;
	Thread gameOverThread;
	
	public TTController controller;
	public String mode;
	public String currentWord;
	public String currentTypedWord;
	public int currentIndex;
	public int currentCorrectIndex;
	public int level;
	public boolean running;
	
	
	public TTGame(String mode, int level) {
		running = false;
		words = new ArrayList<String>(100);
		readInLevelFile(level);
		this.mode = mode;
	}
	
	private void readInLevelFile(int level) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(String.format("res/levels/level%d.txt", level)))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       line = line.toUpperCase();
		       //System.out.println("Read in word: " + line);
		       words.add(line);
		    }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void changeCurrentWord() {
		String oldWord = currentWord;
		while (oldWord == currentWord) { // not the same word again
			currentWord = words.get(rand.nextInt(words.size()));
		}
		currentTypedWord = "";
		currentIndex = 0;
		currentCorrectIndex = 0;
		System.out.println("New word: " + currentWord);
	}
	
	public boolean matchLetter(char letter) {
		
		if (currentIndex < currentWord.length()) {
			
			currentTypedWord += letter;
			
			if (currentIndex == currentCorrectIndex) {
				boolean match = currentWord.charAt(currentIndex) == letter;
				currentIndex++;
				if (match) {
					currentCorrectIndex++;
					correctLetters++;
					if (currentCorrectIndex == currentWord.length()) {
						correctWords++;
						changeCurrentWord();
						controller.wordChanged();
					}
				}
				return match;
			} else {
				currentIndex++;
			}
			
			
		}
		
		return false;
	}
	
	public void backspace() {
		if (currentTypedWord.length() > 0) {
			currentTypedWord = currentTypedWord.substring(0, currentTypedWord.length() - 1);
			
			if (currentCorrectIndex == currentIndex) {
				currentCorrectIndex--;
				correctLetters--;
			}
			currentIndex--;
		}
	}
	
	public void startGame(int time) {
		currentWord = "";
		currentTypedWord = "";
		correctLetters = 0;
		changeCurrentWord();
		running = true;
		
		if (mode == "game") {
			gameTime = time * 1000;
			startTime = System.currentTimeMillis();
			
			// end game after some time
			gameOverThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(gameTime);
				} catch (InterruptedException e) {
					//e.printStackTrace();
					System.out.println("Game clock interrupted");
				}
				
				// Continue on main thread
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (running) {
							endGame(false);
						}
					}
				});
			}
			});
			gameOverThread.start();
		}
		
	}
	
	public void endGame(boolean byUser) {
		running = false;
		if (gameOverThread != null) {
			gameOverThread.interrupt();
		}
		controller.endGame(byUser);
	}
	
	public long getEllapsedTimeMillis() {
		return System.currentTimeMillis() - startTime;
	}
	
	public double getLPM() {
		return correctLetters / (getEllapsedTimeMillis() / 60000.0);
	}
	
	public long getTimeLeftMillis() {
		return gameTime - getEllapsedTimeMillis();
	}
	
	public int getWordCount() {
		return correctWords;
	}
	

}
