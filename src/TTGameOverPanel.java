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
		
		JButton toStartButton = new JButton("OK");
		toStartButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                controller.toStart();
            }
        });
		size  = toStartButton.getPreferredSize();
		toStartButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 200, size.width, size.height);
        add(toStartButton);
	}
	
	public void setScoreLabel(double score) {
		scoreLabel = new JLabel(String.format("Du fick %.1f poäng", score));
		scoreLabel.setFont(new java.awt.Font("Impact", 0, 60));
		Dimension size  = scoreLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		scoreLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2, size.width, size.height);
		add(scoreLabel);
	}
	
	
}
