import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 
 */

/**
 * @author Jonatan
 *
 */
@SuppressWarnings("serial")
public class TTGameOverPanel extends JPanel {
	
	public TTController controller;
	
	JLabel gameOverLabel;
	JLabel scoreLabel;
	JLabel writeNameLabel;
	
	
	public TTGameOverPanel() {
		setBackground(Color.WHITE);
	}
	
	public void setup() {
		
		setLayout(null);
		
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
		
		writeNameLabel = new JLabel("Skriv ditt namn här: BOB");
		writeNameLabel.setFont(new java.awt.Font("Impact", 0, 50));
		size  = writeNameLabel.getPreferredSize();
		writeNameLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 100, size.width, size.height);
		add(writeNameLabel);
		
		
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
		toStartButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 200, size.width, size.height);
        add(toStartButton);
        
	}
	
	public void setScoreLabel(int nWords, double score) {
		scoreLabel.setText(String.format("Du sekrev %d ord eller uttryck och fick %.1f poäng", nWords, score));
		scoreLabel.setFont(new java.awt.Font("Impact", 0, 50));
		Dimension size  = scoreLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		scoreLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2, size.width, size.height);
		add(scoreLabel);
	}
	
	public void setName(String name) {
		writeNameLabel.setText("Skriv ditt namn här: " + name);
		Dimension size  = writeNameLabel.getPreferredSize();
		writeNameLabel.setBounds(writeNameLabel.getX(), writeNameLabel.getY(), size.width, writeNameLabel.getHeight());
	}
	
	
}
