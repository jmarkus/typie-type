import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * View class handling the screen shown when the game is over.
 */

/**
 * @author Jonatan Markusson, Alexander Klingberg
 *
 */
@SuppressWarnings("serial")
public class TTGameOverPanel extends JPanel {
	
	public TTController controller; // pointer to controller object
	
	JLabel gameOverLabel;
	JLabel scoreLabel;
	JLabel writeNameLabel;
	JLabel difficultyLabel;
	
	
	public TTGameOverPanel() {
		setBackground(Color.WHITE);
	}
	
	/**
	 * Initializes all graphical elements with placeholder text. Must be called after the object has been added to a frame.
	 */
	public void setup() {
		
		setLayout(null); // absolute positioning
		
		Dimension panelSize = getParent().getSize();
		Dimension size;
		
		gameOverLabel = new JLabel("Spelet är slut");
		gameOverLabel.setFont(new java.awt.Font("Impact", 0, 80));
		size  = gameOverLabel.getPreferredSize();
		gameOverLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 - 100, size.width, size.height);
		add(gameOverLabel);
		
		scoreLabel = new JLabel("Du skrev ord eller uttryck och fick poäng");
		scoreLabel.setFont(new java.awt.Font("Impact", 0, 50));
		size  = scoreLabel.getPreferredSize();
		scoreLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2, size.width, size.height);
		add(scoreLabel);
		
		writeNameLabel = new JLabel("Försök skriva ditt namn här: BOB");
		writeNameLabel.setFont(new java.awt.Font("Impact", 0, 50));
		size  = writeNameLabel.getPreferredSize();
		writeNameLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 100, size.width, size.height);
		add(writeNameLabel);
		
		difficultyLabel = new JLabel("Svårighetsgrad:");
		difficultyLabel.setFont(new java.awt.Font("Impact", 0, 30));
		size  = difficultyLabel.getPreferredSize();
		difficultyLabel.setBounds(panelSize.width - size.width - 10, 0, size.width, size.height);
		add(difficultyLabel);
		
		
		JButton toStartButton = new JButton("OK");
		toStartButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                controller.submitHighscore();
            }
        });
		toStartButton.setFont(new java.awt.Font("Impact", 0, 30));
		toStartButton.setOpaque(false);
		toStartButton.setContentAreaFilled(false);
		toStartButton.setBorderPainted(false);
		size  = toStartButton.getPreferredSize();
		toStartButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 300, size.width, size.height);
        add(toStartButton);
        
	}
	
	/**
	 * Sets the label showing the user's score. 
	 * @param nWords The number of correct words the user entered.
	 * @param score The user's score
	 */
	public void setScoreLabel(int nWords, double score) {
		scoreLabel.setText(String.format("<html>Du skrev %d ord eller uttryck och fick <span color=\"green\">%.1f</span> poäng</html>", nWords, score));
		scoreLabel.setFont(new java.awt.Font("Impact", 0, 50));
		Dimension size  = scoreLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		scoreLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2, size.width, size.height);
		add(scoreLabel);
	}
	
	/**
	 * Sets the label showing the name that the user has currently typed in.
	 */
	public void setName(String name) {
		writeNameLabel.setText("<html>Försök skriva ditt namn här: <span color=\"green\">" + name + "</span></html>");
		Dimension size  = writeNameLabel.getPreferredSize();
		writeNameLabel.setBounds(writeNameLabel.getX(), writeNameLabel.getY(), size.width, writeNameLabel.getHeight());
	}
	
	/**
	 * Sets the label showing the difficulty of the finished game.
	 * @param difficulty The difficulty of the finished game
	 */
	public void setDifficultyLabel(String difficulty) {
		difficultyLabel.setText("Svårighetsgrad: " + difficulty);
		Dimension size  = difficultyLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		difficultyLabel.setBounds(panelSize.width - size.width - 10, 0, size.width, size.height);
	}
	
	
}
