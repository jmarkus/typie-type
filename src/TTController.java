
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;


public class TTController {
	
	static final int WINDOW_WIDTH = 1200;
	static final int WINDOW_HEIGHT = 800;
	
	
	public JFrame mainFrame;
	
	TTStartPanel startPanel;
	TTGamePanel gamePanel;
	TTGameOverPanel gameOverPanel;
	TTGame game;
	
	Thread LPMThread;
	
	
	
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
		
		if (gamePanel != null) {
			gamePanel.setVisible(false);
		}
		
		if (gameOverPanel != null) {
			gameOverPanel.setVisible(false);
		}
		
		if (startPanel == null) {
			startPanel = new TTStartPanel();
			startPanel.controller = this;
			mainFrame.add(startPanel);
			startPanel.setup();
		} else {
			startPanel.setVisible(true);
		}
		
		
		System.out.println(mainFrame.getContentPane().getComponentCount());
		for (Component c : mainFrame.getContentPane().getComponents()) {
			System.out.println(c);
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
		
		game = new TTGame(level);
		game.controller = this;
		game.startGame(2);
		gamePanel.setCurrentWord(game.currentWord, game.currentIndex);
		gamePanel.updateCurrentWordLabel();
		
		
		// update LPM label continuously
		LPMThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (game.running) {
		    		gamePanel.setCurrentLPMLabel(game.getLPM());
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
	
	public void endGame() {
		System.out.println("Game Over");
		gamePanel.setVisible(false);
		
		if (gameOverPanel == null) {
			gameOverPanel = new TTGameOverPanel();
			gameOverPanel.controller = this;
			mainFrame.add(gameOverPanel);
			gameOverPanel.setup();
		} else {
			gameOverPanel.setVisible(true);
		}
		
		gameOverPanel.setScoreLabel(game.getLPM());
	}
	
	public void wordChanged() {
		playSound("res/sounds/ding.wav");
		gamePanel.setCurrentWord(game.currentWord, game.currentIndex);
		gamePanel.updateCurrentWordLabel();
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
			}
			
			if (game.running) {
				
				if (game.matchLetter(letterPressed)) {
					gamePanel.setCurrentWord(game.currentWord, game.currentIndex);
					
				} else {
					playSound("res/sounds/incorrect.aiff");
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

}
