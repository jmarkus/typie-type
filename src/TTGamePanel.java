import javax.swing.*;
import javax.swing.border.Border;

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
	JLabel currentTypedWordLabel;
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
		
		currentTypedWordLabel = new JLabel("placeholder");
		currentTypedWordLabel.setFont(new java.awt.Font("Courier", 0, 40));
		Dimension currentTypedWordLabelSize = currentTypedWordLabel.getPreferredSize();
		currentTypedWordLabel.setBounds(panelSize.width / 2 -  currentTypedWordLabelSize.width / 2, panelSize.height / 2 - currentTypedWordLabelSize.height/ 2 + 100,
				currentTypedWordLabelSize.width, currentTypedWordLabelSize.height + 4);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
		currentTypedWordLabel.setBorder(border);
		add(currentTypedWordLabel);
		
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
	
	public void setCurrentWordLabel(String word, int correctIndex) {
		currentWord = word;
		
		if (correctIndex == 0) {
			currentWordLabel.setText("<html>" + currentWord + "</html>");
		} else {
			currentWordLabel.setText("<html><font color=green>" + currentWord.substring(0, correctIndex) + "</font><font color=black>" + currentWord.substring(correctIndex) + "</font></html>");
		}
		
	}
	
	public void setCurrentTypedWordLabel(String typedWord, int currentIndex, int correctIndex) {
		String newLabelString = "";
		
		System.out.println(typedWord + " " + currentIndex + " " + correctIndex);
		
		if (currentIndex == 0) {
			newLabelString = "<html>_<span color=white>" + currentWord.substring(1) + "</span></html>";
		} else if (currentIndex == correctIndex) {
			newLabelString = "<html><span style=\"background-color:green\">" + currentWord.substring(0, correctIndex) +
					"</span>_<span color=white>" + currentWord.substring(correctIndex + 1) + "</span></html>";
		} else if (currentIndex > correctIndex) {
			newLabelString = "<html><span style=\"background-color:green\">" + typedWord.substring(0, correctIndex) + 
					"</span><span style=\"background-color:red\">" + typedWord.substring(correctIndex, currentIndex) + 
					"</span>_<span color=white>" + typedWord.substring(currentIndex - 1, typedWord.length() - 1) + "</span></html>";
			if (currentIndex == currentWord.length()) {
				newLabelString = newLabelString.replaceAll("_", "");
			}
		}
		newLabelString = newLabelString.replaceAll(" ", "&nbsp;"); // always print white space
		currentTypedWordLabel.setText(newLabelString);
		
		Dimension currentTypedWordLabelSize = currentTypedWordLabel.getPreferredSize();
		if (currentTypedWordLabelSize.width > currentTypedWordLabel.getWidth()) {
			Dimension panelSize = getParent().getSize();
			currentTypedWordLabel.setBounds(panelSize.width / 2 -  currentTypedWordLabelSize.width / 2, panelSize.height / 2 - currentTypedWordLabelSize.height/ 2 + 100,
					currentTypedWordLabelSize.width, currentTypedWordLabelSize.height + 4);
		}
		
	}
	
	public void setTimeLeftLabel(int timeLeft) {
		timeLeftLabel.setText(String.format("Tid kvar: %ds", timeLeft));
		Dimension size  = timeLeftLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		timeLeftLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
	}
	
	public void updateLabelLayout() {
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
		
		
		Dimension currentTypedWordLabelSize = currentTypedWordLabel.getPreferredSize();
		currentTypedWordLabel.setBounds(panelSize.width / 2 -  currentTypedWordLabelSize.width / 2, panelSize.height / 2 - currentTypedWordLabelSize.height/ 2 + 100,
				currentTypedWordLabelSize.width, currentTypedWordLabelSize.height);
		
	}
	
	public void setCurrentLPMLabel(double lpm) {
		currentLPMLabel.setText(String.format("%.1f rätta bokstäver per minut", lpm));
		Dimension size  = currentLPMLabel.getPreferredSize();
		currentLPMLabel.setBounds(10, 10, size.width, size.height);
	}
	
	
	
}