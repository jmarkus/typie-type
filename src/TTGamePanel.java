import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	JLabel currentWordCountLabel;
	JLabel difficultyLabel;
	
	String mode;
	int difficulty;
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
		currentLPMLabel.setBounds(panelSize.width - size.width - 10, panelSize.height - size.height, size.width, size.height);
		add(currentLPMLabel);
		
		timeLeftLabel = new JLabel("Tid kvar:");
		timeLeftLabel.setFont(new java.awt.Font("Impact", 0, 40));
		size  = timeLeftLabel.getPreferredSize();
		timeLeftLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
		add(timeLeftLabel);
		
		currentWordCountLabel = new JLabel("Antal ord:");
		currentWordCountLabel.setFont(new java.awt.Font("Impact", 0, 40));
		size  = currentWordCountLabel.getPreferredSize();
		currentWordCountLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
		add(currentWordCountLabel);
		
		difficultyLabel = new JLabel("Svårighetsgrad:");
		difficultyLabel.setFont(new java.awt.Font("Impact", 0, 30));
		size  = difficultyLabel.getPreferredSize();
		difficultyLabel.setBounds(panelSize.width - size.width - 10, 0, size.width, size.height);
		add(difficultyLabel);
		
		
		JButton backButton = new JButton("< Tillbaka till menyn");
		backButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                controller.backToStart();
            }
        });
		backButton.setFont(new java.awt.Font("Impact", 0, 30));
		backButton.setOpaque(false);
		backButton.setContentAreaFilled(false);
		backButton.setBorderPainted(false);
        size  = backButton.getPreferredSize();
        backButton.setBounds(-10, 0, size.width, size.height);
        add(backButton);
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
		
		/* adjust if current typed word is longer than label not needed with monospaced font
		Dimension currentTypedWordLabelSize = currentTypedWordLabel.getPreferredSize();
		if (currentTypedWordLabelSize.width > currentTypedWordLabel.getWidth()) {
			Dimension panelSize = getParent().getSize();
			currentTypedWordLabel.setBounds(panelSize.width / 2 -  currentTypedWordLabelSize.width / 2, panelSize.height / 2 - currentTypedWordLabelSize.height/ 2 + 100,
					currentTypedWordLabelSize.width, currentTypedWordLabelSize.height + 4);
		}
		*/
		
	}
	
	public void setTimeLeftLabel(int timeLeft) {
		timeLeftLabel.setText(String.format("Tid kvar: %ds", timeLeft));
		Dimension size  = timeLeftLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		timeLeftLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
	}
	
	public void setCurrentWordCount(int count) {
		currentWordCountLabel.setText(String.format("Antal ord: %d", count));
		Dimension size  = currentWordCountLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		currentWordCountLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
	}
	
	public void setLabelLayout(String mode, int difficulty) {
		this.mode = mode;
		this.difficulty = difficulty;
		
		if (mode == "game") {
			timeLeftLabel.setVisible(true);
			currentLPMLabel.setVisible(true);
			currentWordCountLabel.setVisible(false);
			if (difficulty >= 3) {
				currentTypedWordLabel.setVisible(false);
			} else {
				currentTypedWordLabel.setVisible(true);
			}
		} else if (mode == "practice") {
			timeLeftLabel.setVisible(false);
			currentLPMLabel.setVisible(false);
			currentWordCountLabel.setVisible(true);
			currentTypedWordLabel.setVisible(true);
		}
		
		difficultyLabel.setText("Svårighetsgrad: " + TTController.DIFFICULTIES[difficulty]);
		Dimension size  = difficultyLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		difficultyLabel.setBounds(panelSize.width - size.width - 10, 0, size.width, size.height);
		
		updateLabelLayout();
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
		
		if (difficulty <= 2) {
			Dimension currentTypedWordLabelSize = currentTypedWordLabel.getPreferredSize();
			currentTypedWordLabel.setBounds(panelSize.width / 2 -  currentTypedWordLabelSize.width / 2, panelSize.height / 2 - currentTypedWordLabelSize.height/ 2 + 100,
					currentTypedWordLabelSize.width, currentTypedWordLabelSize.height);
		}
	}
	
	public void setCurrentLPMLabel(double lpm) {
		currentLPMLabel.setText(String.format("%.1f rätta bokstäver per minut", lpm));
		Dimension panelSize = getParent().getSize();
		Dimension size  = currentLPMLabel.getPreferredSize();
		currentLPMLabel.setBounds(panelSize.width - size.width - 10, panelSize.height - size.height, size.width, size.height);
	}
	
	public void alertUser() {
		JLabel alert = new JLabel("TIPS! Kopiera ordet ovan till rutan här under så snabbt du kan : )");
		alert.setFont(new java.awt.Font("Impact", 0, 30));
		Dimension panelSize = getParent().getSize();
		Dimension size  = alert.getPreferredSize();
		alert.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 30, size.width, size.height);
		add(alert);
		repaint();
	}
	
	
	
}