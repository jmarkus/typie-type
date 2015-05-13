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
	int correctLetters;
	Thread gameOverThread;
	
	public TTController controller;
	public String currentWord;
	public int currentIndex;
	public int level;
	public boolean running;
	
	
	public TTGame(int level) {
		words = new ArrayList<String>(100);
		readInLevelFile(level);
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
		while (oldWord == currentWord) {
			currentWord = words.get(rand.nextInt(words.size()));
		}
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
				controller.wordChanged();
			}
		}
		
		return match;
	}
	
	public void startGame(int time) {
		currentWord = "";
		correctLetters = 0;
		changeCurrentWord();
		running = true;
		startTime = System.currentTimeMillis();
		
		// end game after 1 minute
		gameOverThread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				Thread.sleep(time * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Continue on main thread
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					endGame();
				}
			});
		}
		});
		gameOverThread.start();
	}
	
	public void endGame() {
		running = false;
		gameOverThread.interrupt();
		controller.endGame();
	}
	
	public long getEllapsedTimeMillis() {
		return System.currentTimeMillis() - startTime;
	}
	
	public double getLPM() {
		return correctLetters / (getEllapsedTimeMillis() / 60000.0);
	}
	

}
