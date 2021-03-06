import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * View class handling the start screen and main menu of the application.
 */

/**
 * @author Jonatan Markusson, Alexander Klingberg
 *
 */
@SuppressWarnings("serial")
public class TTStartPanel extends JPanel {
	
	
	public TTController controller; // pointer to controller object
	
	JButton gameButton;
	JButton practiceButton;
	JButton highscoresButton;
	JButton easyButton;
	JButton mediumButton;
	JButton hardButton;
	JButton extremeButton;
	JButton backButton;
	
	String mode;
	
	
	public TTStartPanel() {
		setBackground(Color.WHITE);
	}
	
	/**
	 * Initializes all graphical elements. Must be called after the object has been added to a frame.
	 */
	public void setup() {
		
		setLayout(null); // absolute positioning
		
		Dimension panelSize = getParent().getSize();
		Dimension size;
		
		JLabel titleLabel = new JLabel("TypieType¡");
		titleLabel.setFont(new java.awt.Font("Impact", 0, 90));
		size  = titleLabel.getPreferredSize();
		titleLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 - 100, size.width, size.height);
		add(titleLabel);
		
		
		// Buttons
		
		gameButton = new JButton("Spela");
		gameButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		mode = "game";
        		toggleButtons();
        		repaint();
            }
        });
		gameButton.setFont(new java.awt.Font("Impact", 0, 30));
		gameButton.setOpaque(false);
		gameButton.setContentAreaFilled(false);
		gameButton.setBorderPainted(false);
        size  = gameButton.getPreferredSize();
        gameButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 45, size.width, size.height);
        add(gameButton);
        
        practiceButton = new JButton("Öva");
        practiceButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		mode = "practice";
        		toggleButtons();
        		repaint();
            }
        });
        practiceButton.setFont(new java.awt.Font("Impact", 0, 30));
        practiceButton.setOpaque(false);
        practiceButton.setContentAreaFilled(false);
        practiceButton.setBorderPainted(false);
        size  = practiceButton.getPreferredSize();
        practiceButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 105, size.width, size.height);
        add(practiceButton);
        
        highscoresButton = new JButton("Topplista");
        highscoresButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		controller.showHighscores();
            }
        });
        highscoresButton.setFont(new java.awt.Font("Impact", 0, 30));
        highscoresButton.setOpaque(false);
        highscoresButton.setContentAreaFilled(false);
        highscoresButton.setBorderPainted(false);
        size  = highscoresButton.getPreferredSize();
        highscoresButton.setBounds(panelSize.width - size.width - 10, panelSize.height - size.height, size.width, size.height);
        add(highscoresButton);
        
        easyButton = new JButton(TTController.DIFFICULTIES[0]);
		easyButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
        		if (mode == "game") {
        			controller.startGame(0);
        		} else if (mode == "practice") {
        			controller.startPracticeGame(0);
        		}
            }
        });
		easyButton.setFont(new java.awt.Font("Impact", 0, 30));
		easyButton.setOpaque(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setBorderPainted(false);
        size  = easyButton.getPreferredSize();
        easyButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 30, size.width, size.height);
        easyButton.setVisible(false);
        add(easyButton);
		
        mediumButton = new JButton(TTController.DIFFICULTIES[1]);
        mediumButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
        		if (mode == "game") {
        			controller.startGame(1);
        		} else if (mode == "practice") {
        			controller.startPracticeGame(1);
        		}
            }
        });
        mediumButton.setFont(new java.awt.Font("Impact", 0, 30));
        mediumButton.setOpaque(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setBorderPainted(false);
        size  = mediumButton.getPreferredSize();
        mediumButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 90, size.width, size.height);
        mediumButton.setVisible(false);
        add(mediumButton);
        
        hardButton = new JButton(TTController.DIFFICULTIES[2]);
        hardButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
        		if (mode == "game") {
        			controller.startGame(2);
        		} else if (mode == "practice") {
        			controller.startPracticeGame(2);
        		}
            }
        });
        hardButton.setFont(new java.awt.Font("Impact", 0, 30));
        hardButton.setOpaque(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setBorderPainted(false);
        size  = hardButton.getPreferredSize();
        hardButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 150, size.width, size.height);
        hardButton.setVisible(false);
        add(hardButton);
        
        extremeButton = new JButton(TTController.DIFFICULTIES[3]);
        extremeButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
        		if (mode == "game") {
        			controller.startGame(3);
        		} else if (mode == "practice") {
        			controller.startPracticeGame(3);
        		}
            }
        });
        extremeButton.setFont(new java.awt.Font("Impact", 0, 30));
        extremeButton.setOpaque(false);
        extremeButton.setContentAreaFilled(false);
        extremeButton.setBorderPainted(false);
        size  = extremeButton.getPreferredSize();
        extremeButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 210, size.width, size.height);
        extremeButton.setVisible(false);
        add(extremeButton);
        
        backButton = new JButton("< Tillbaka");
        backButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
            }
        });
        backButton.setFont(new java.awt.Font("Impact", 0, 30));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        size  = backButton.getPreferredSize();
        backButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 270, size.width, size.height);
        backButton.setVisible(false);
        add(backButton);
        
        
	}
	
	/**
	 * Toggle whether the main menu buttons or the difficulty buttons should be visible
	 */
	private void toggleButtons() {
		
		if (easyButton.isVisible()) {
			easyButton.setVisible(false);
			mediumButton.setVisible(false);
			hardButton.setVisible(false);
			extremeButton.setVisible(false);
			backButton.setVisible(false);
			
			gameButton.setVisible(true);
    		practiceButton.setVisible(true);
		} else {
			easyButton.setVisible(true);
			mediumButton.setVisible(true);
			hardButton.setVisible(true);
			extremeButton.setVisible(true);
			backButton.setVisible(true);
			
			gameButton.setVisible(false);
    		practiceButton.setVisible(false);
		}
        
	}

}
