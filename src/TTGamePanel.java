import javax.swing.*;

import java.awt.*;

/**
 * 
 */

/**
 * @author Jonatan
 *
 */
@SuppressWarnings("serial")
public class TTGamePanel extends JPanel {
	
	
	public TTController controller;
	
	private static final int DEFAULT_WORDLABEL_FONT_SIZE = 80;
	
	JLabel currentWordLabel;
	JLabel currentLPMLabel;
	JLabel timeLeftLabel;
	
	String currentWord;
	
	public TTGamePanel() {
		setBackground(Color.WHITE);
	}
	
	public void setup() {
		
		setLayout(null);
		
		Dimension panelSize = getParent().getSize();
		Dimension size;
		
		currentWordLabel = new JLabel("placeholder");
		currentWordLabel.setFont(new java.awt.Font("Arial", 0, DEFAULT_WORDLABEL_FONT_SIZE));
		size  = currentWordLabel.getPreferredSize();
		currentWordLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 - 100, size.width, size.height);
		add(currentWordLabel);
		
		currentLPMLabel = new JLabel("LPM");
		currentLPMLabel.setFont(new java.awt.Font("Impact", 0, 40));
		size  = currentLPMLabel.getPreferredSize();
		currentLPMLabel.setBounds(10, 10, size.width, size.height);
		add(currentLPMLabel);
		
		timeLeftLabel = new JLabel("Tid kvar:");
		timeLeftLabel.setFont(new java.awt.Font("Impact", 0, 40));
		size  = timeLeftLabel.getPreferredSize();
		timeLeftLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
		add(timeLeftLabel);
	}
	
	public void setCurrentWordLabel(String word, int currentIndex) {
		currentWord = word;
		
		if (currentIndex == 0) {
			currentWordLabel.setText("<html>" + currentWord + "</html>");
		} else {
			currentWordLabel.setText("<html><font color=green>" + currentWord.substring(0, currentIndex) + "</font><font color=black>" + currentWord.substring(currentIndex) + "</font></html>");
		}
		
	}
	
	public void setTimeLeftLabel(int timeLeft) {
		timeLeftLabel.setText(String.format("Tid kvar: %ds", timeLeft));
		Dimension size  = timeLeftLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		timeLeftLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
	}
	
	public void updateCurrentWordLabel() {
		currentWordLabel.setFont(new java.awt.Font("Arial", 0, DEFAULT_WORDLABEL_FONT_SIZE));
		Dimension size  = currentWordLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		
		int fontSize = DEFAULT_WORDLABEL_FONT_SIZE;
		while (size.width > 0.9 * panelSize.width) {
			fontSize -= 2;
			currentWordLabel.setFont(new java.awt.Font("Arial", 0, fontSize));
			size  = currentWordLabel.getPreferredSize();
			System.out.println("adjusting font, now: " + fontSize);
		}
		
		currentWordLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 - 100, size.width, size.height);
	}
	
	public void setCurrentLPMLabel(double lpm) {
		currentLPMLabel.setText(String.format("%.1f rätta bokstäver per minut", lpm));
		Dimension size  = currentLPMLabel.getPreferredSize();
		currentLPMLabel.setBounds(10, 10, size.width, size.height);
	}
	
	
	
}