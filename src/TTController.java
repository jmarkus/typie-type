
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class TTController {
	
	static final int WINDOW_WIDTH = 1200;
	static final int WINDOW_HEIGHT = 800;
	
	static final String[] DIFFICULTIES = {"Inte svårt", "Lite svårt", "Ganska svårt", "Väldigt svårt"};
	
	
	public JFrame mainFrame;
	
	TTStartPanel startPanel;
	TTGamePanel gamePanel;
	TTGameOverPanel gameOverPanel;
	TTHighscoresPanel highscoresPanel;
	TTGame game;
	
	String playerName;
	TTHighscore latestScore;
	
	Thread LPMThread;
	
	HashMap<Integer, ArrayList<TTHighscore>> highscores;
	
	
	public TTController() {
		setupGUI();
	}
	
	private void setupGUI() {
		mainFrame = new JFrame();
		mainFrame.setTitle("TypieType¡");
		mainFrame.setBackground(Color.BLACK);
		mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		mainFrame.setFocusable(true);
		mainFrame.addKeyListener(new MyKeyListener());
		mainFrame.requestFocusInWindow();
		mainFrame.setVisible(true);
		
		toStart();
	}
	
	public void toStart() {
		if (game != null) {
			game.controller = null;
			game = null;
		}
		
		if (gamePanel != null) {
			gamePanel.setVisible(false);
		}
		
		if (gameOverPanel != null) {
			gameOverPanel.setVisible(false);
		}
		
		if (highscoresPanel != null) {
			highscoresPanel.setVisible(false);
		}
		
		if (startPanel == null) {
			startPanel = new TTStartPanel();
			startPanel.controller = this;
			mainFrame.add(startPanel);
			startPanel.setup();
		} else {
			startPanel.setVisible(true);
		}
		
	}
	
	private void updateLabels() {
		if (game.mode == "practice") {
			gamePanel.setCurrentWordCount(game.getWordCount());
		}
		
		gamePanel.setCurrentWordLabel(game.currentWord, game.currentCorrectIndex);
		if (game.difficulty <= 2) {
			gamePanel.setCurrentTypedWordLabel(game.currentTypedWord, game.currentIndex, game.currentCorrectIndex);
		}
	}
	
	
	public void startGame(int level) {
		startPanel.setVisible(false);
		
		if (gamePanel == null) {
			gamePanel = new TTGamePanel();
			gamePanel.controller = this;
			mainFrame.add(gamePanel);
			gamePanel.setup();
		} else {
			gamePanel.setVisible(true);
		}
		
		game = new TTGame("game", level);
		game.controller = this;
		game.startGame(30);
		
		updateLabels();
		gamePanel.setLabelLayout("game", level);
		
		
		// update LPM and time left label continuously
		LPMThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (game != null && game.running) {
		    		gamePanel.setCurrentLPMLabel(game.getLPM());
		    		gamePanel.setTimeLeftLabel((int)game.getTimeLeftMillis() / 1000 + 1);
		    		try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
		    	}
				Thread.currentThread().interrupt();
			}
		});
		LPMThread.start();
	}
	
	public void startPracticeGame(int level) {
		startPanel.setVisible(false);
		
		if (gamePanel == null) {
			gamePanel = new TTGamePanel();
			gamePanel.controller = this;
			mainFrame.add(gamePanel);
			gamePanel.setup();
		} else {
			gamePanel.setVisible(true);
		}
		
		game = new TTGame("practice", level);
		game.controller = this;
		game.startGame(0);
		
		updateLabels();
		gamePanel.setLabelLayout("practice", level);
	}
	
	public void endGame(boolean byUser) {
		if (!byUser) {
			System.out.println("Game Over");
			gamePanel.setVisible(false);
			
			if (gameOverPanel == null) {
				gameOverPanel = new TTGameOverPanel();
				gameOverPanel.controller = this;
				mainFrame.add(gameOverPanel);
				gameOverPanel.setup();
				playerName = "";
			} else {
				gameOverPanel.setVisible(true);
			}
			
			gameOverPanel.setScoreLabel(game.getWordCount(), game.getScore());
			gameOverPanel.setName(playerName);
			gameOverPanel.setDifficultyLabel(DIFFICULTIES[game.difficulty]);
		}
		
	}
	
	public void backToStart() {
		game.endGame(true);
		toStart();
	}
	
	public void showHighscores() {
		startPanel.setVisible(false);
			
		if (gameOverPanel != null) {
			gameOverPanel.setVisible(false);
		}
			
		
		if (highscoresPanel == null) {
			highscoresPanel = new TTHighscoresPanel();
			highscoresPanel.controller = this;
			mainFrame.add(highscoresPanel);
		} else {
			highscoresPanel.setVisible(true);
		}
		
		if (highscores == null) {
			readInHighscores();
		}
		
		highscoresPanel.printHighscores(highscores, latestScore);
		latestScore = null;
	}
	
	public void submitHighscore() {
		if (playerName.length() > 0) {
			if (highscores == null) {
				readInHighscores();
			}
			
			if (highscores.get(game.difficulty) == null) {
				highscores.put(game.difficulty, new ArrayList<TTHighscore>(10));
			}
			
			ArrayList<TTHighscore> list = highscores.get(game.difficulty);
			int insertAt = list.size();
			for (int i = insertAt - 1; i >= 0; i--) {
				if (game.getScore() > list.get(i).score) {
					insertAt = i;
				}
			}
			latestScore = new TTHighscore(playerName, game.getScore());
			list.add(insertAt, latestScore);
			
			saveHighscores();
			showHighscores();
		} else {
			mainFrame.requestFocusInWindow();
		}
	}
	
	public void wordChanged() {
		playSound("res/sounds/ding.wav");
		updateLabels();
		gamePanel.updateLabelLayout();
	}
	
	private class MyKeyListener extends KeyAdapter {
		@Override
	    public void keyPressed(KeyEvent e) {
			
			char letterPressed = KeyEvent.getKeyText(e.getKeyCode()).charAt(0);
			
			switch (e.getKeyCode()) {
			case 222:
				letterPressed = 'Ä';
				break;
			case 59:
				letterPressed = 'Ö';
				break;
			case 91:
				letterPressed = 'Å';
				break;
			case 32:
				letterPressed = ' ';
				break;
			case 8:
				if (game != null && game.running) {
					game.backspace();
					updateLabels();
					return;
					
				} else if (gameOverPanel != null && gameOverPanel.isVisible()) {
					if (playerName.length() >= 1) {
						playerName = playerName.substring(0, playerName.length() - 1);
						gameOverPanel.setName(playerName);
					}
					return;
				}
			}
			
			if (game != null && game.running) {
				
				if (game.matchLetter(letterPressed)) {
					
				} else {
					playSound("res/sounds/incorrect.aiff");
				}
				
				updateLabels(); 
				
			} else if (gameOverPanel != null && gameOverPanel.isVisible()) {
				if (playerName.length() < 3) {
					playerName = playerName + letterPressed;
					gameOverPanel.setName(playerName);
				}
			}
			
			//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()) + " code: " + e.getKeyCode());
		}
	}
	
	private void playSound(String path) {
		File ding = new File(path);
		
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(ding));
			clip.start();
		} catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    } catch (LineUnavailableException e) {
	         e.printStackTrace();
	    }
	}
	
	private void readInHighscores() {
		if (highscores == null) {
	    	highscores = new HashMap<Integer, ArrayList<TTHighscore>>(4);
	    }
		
		try (BufferedReader br = new BufferedReader(new FileReader("scores.txt"))) {
		    String line;
		    int level = 0;
		    
		    while ((line = br.readLine()) != null) {
		    	
		    	System.out.println(line);
		    	
		    	if (line.startsWith("level")) {
		    		level = Integer.parseInt(line.substring(line.length() - 1));
		    		if (highscores.get(level) == null) {
		    			highscores.put(level, new ArrayList<TTHighscore>(10));
		    		}
		    		continue;
		    	}
		    	
		    	int separatorIndex = line.indexOf(' ');
		    	
		    	if (separatorIndex != -1) {
		    		TTHighscore score = new TTHighscore(line.substring(0, separatorIndex), Double.parseDouble(line.substring(separatorIndex)));
		    		highscores.get(level).add(score);
		    	}
		    	
		    	System.out.println(highscores);
		    	
		    }
		} catch (FileNotFoundException e) {
			try {
				PrintWriter writer = new PrintWriter("scores.txt", "UTF-8");
				writer.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void saveHighscores() {
		if (highscores != null) {
			try {
				PrintWriter writer = new PrintWriter("scores.txt", "UTF-8");
				for (Integer k : highscores.keySet()) {
					writer.println("level" + k);
					for (TTHighscore s : highscores.get(k)) {
						writer.println(s.name + " " + s.score);
					}
				}
				writer.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

}
