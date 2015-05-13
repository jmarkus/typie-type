
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
	
	static final int WINDOW_WIDTH = 1000;
	static final int WINDOW_HEIGHT = 800;
	
	
	public JFrame mainFrame;
	
	TTStartPanel startPanel;
	TTGamePanel gamePanel;
	TTGame game;
	
	
	
	public TTController() {
		setupGUI();
	}
	
	private void setupGUI() {
		mainFrame = new JFrame();
		mainFrame.setTitle("TypieType¡");
		mainFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setResizable(false);
		
		mainFrame.setFocusable(true);
		mainFrame.addKeyListener(new MyKeyListener());
		mainFrame.requestFocusInWindow();
		
		
		startPanel = new TTStartPanel();
		startPanel.controller = this;
		mainFrame.add(startPanel);
		
		
	}
	
	
	public void startGame(int level) {
		System.out.println("Startar...");
		startPanel.setVisible(false);
		
		
		gamePanel = new TTGamePanel();
		//gamePanel.controller = this;
		mainFrame.add(gamePanel);
		gamePanel.setupGamePanel();
		
		game = new TTGame(level);
		game.startGame();
		gamePanel.setCurrentWord(game.currentWord, game.currentIndex);
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
			
			String prevWord = game.currentWord;
			
			if (game.matchLetter(letterPressed)) {
				gamePanel.setCurrentWord(game.currentWord, game.currentIndex);
				
				if (prevWord != game.currentWord) {
					playSound("res/sounds/ding.wav");
				}
				
			} else {
				playSound("res/sounds/incorrect.aiff");
			}
			
			System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()) + " code: " + e.getKeyCode());
			
			
			gamePanel.setCurrentLPMLabel(game.getLPM()); // temp, kommer uppdateras utan att en knapp tryckts senare
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
