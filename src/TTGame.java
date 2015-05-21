import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


/**
 * Model class which handles the game logic.
 * 
 * @author Jonatan Markusson
 *
 */
public class TTGame {
	
	
	ArrayList<String> words;
	Random rand = new Random();
	long startTime;
	int gameTime;
	int correctLetters;
	int correctWords;
	Thread gameOverThread;
	double score;
	
	public TTController controller;
	public String mode;
	public String currentWord;
	public String currentTypedWord;
	public int currentIndex;
	public int currentCorrectIndex;
	public int difficulty;
	public boolean running;
	
	
	/**
	 * Create a new game object with the given mode and difficulty
	 * @param mode The mode which the game should run
	 * @param difficulty The difficulty of the game
	 */
	public TTGame(String mode, int difficulty) {
		running = false;
		words = new ArrayList<String>(100);
		
		// predefined word lists for all difficulties
		switch (difficulty) {
		case 0:
			readInLevelFile(0);
			break;
		case 1:
			readInLevelFile(0);
			readInLevelFile(1);
			break;
		case 2:
			readInLevelFile(1);
			readInLevelFile(2);
			break;
		case 3:
			readInLevelFile(2);
			break;
		default:
			readInLevelFile(0);
			break;
		}
		
		this.mode = mode;
		this.difficulty = difficulty;
	}
	
	/**
	 * Helper method to read in words from the file given
	 * @param level the level of words to be read in
	 */
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
	
	/**
	 * Helper method to change the current word of the game
	 */
	private void changeCurrentWord() {
		String oldWord = currentWord;
		while (oldWord == currentWord) { // not the same word again
			currentWord = words.get(rand.nextInt(words.size()));
		}
		currentTypedWord = "";
		currentIndex = 0;
		currentCorrectIndex = 0;
		//System.out.println("New word: " + currentWord);
	}
	
	/**
	 * Try to match a letter against the current word
	 * @param letter The letter to be matched
	 * @return True if letter is the next unmatched letter in the current word
	 */
	public boolean matchLetter(char letter) {
		
		if (currentIndex < currentWord.length()) {
			
			// special case if difficulty is 0, currentIndex and correctIndex must always have the same value
			if (difficulty == 0) {
				boolean match = currentWord.charAt(currentIndex) == letter;
				if (match) {
					currentIndex++;
					currentCorrectIndex++;
					correctLetters++;
					currentTypedWord += letter;
					if (currentIndex == currentWord.length()) {
						correctWords++;
						changeCurrentWord();
						controller.wordChanged();
					}
				}
				return match;
				
			} else {
				
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
			
			
			
		}
		
		return false;
	}
	
	/**
	 * Called by controller when backspace is typed, removes one letter from the current typed word
	 */
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
	
	/**
	 * Starts the game .
	 * @param time The time the game should run, if in game mode
	 */
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
	
	/**
	 * Terminates the game and notifies the controller
	 * @param byUser True if the game was terminated by the user, otherwise false
	 */
	public void endGame(boolean byUser) {
		running = false;
		score = getLPM();
		if (gameOverThread != null) {
			gameOverThread.interrupt();
		}
		controller.endGame(byUser);
	}
	
	/**
	 * Return the time the current game has been running in milliseconds
	 * @return The time the game has elapsed in milliseconds
	 */
	public long getElapsedTimeMillis() {
		return System.currentTimeMillis() - startTime;
	}
	
	/**
	 * Return the accumulated letter per minute rate of the game
	 * @return The accumulated letter per minute rate of the game
	 */
	public double getLPM() {
		return correctLetters / (getElapsedTimeMillis() / 60000.0);
	}
	/**
	 * Return the time left of the game
	 * @return Time left of the game
	 */
	public long getTimeLeftMillis() {
		return gameTime - getElapsedTimeMillis();
	}
	
	/**
	 * Return the number of words correctly entered in the current game
	 * @return The number of words correctly entered
	 */
	public int getWordCount() {
		return correctWords;
	}
	
	/**
	 * Return the score of the game.
	 * @return The score of the game
	 */
	public double getScore() {
		if (!running) {
			return score;
		}
		return 0;
	}
	

}
