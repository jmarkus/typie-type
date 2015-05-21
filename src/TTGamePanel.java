import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * View class handling the main game screen.
 */

/**
 * @author Jonatan Markusson, Alexander Klingberg
 *
 */
@SuppressWarnings("serial")
public class TTGamePanel extends JPanel {
	
	
	public TTController controller; // pointer to controller object
	
	private static final int DEFAULT_WORDLABEL_FONT_SIZE = 80; //default font size of current word
	
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
	
	/**
	 * Initializes all graphical elements with placeholder text. Must be called after the object has been added to a frame.
	 */
	public void setup() {
		
		setLayout(null); // absolute positioning
		
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
	
	/**
	 * Updates the label showing the current word. Marks all letters from index 0 up to correctIndex excluding correctIndex green. Should be updated when a correct letter is entered or if the current word changes.
	 * @param word The string to be displayed as the current word
	 * @param correctIndex The index up to which the letters in the word should be marked as green
	 */
	public void setCurrentWordLabel(String word, int correctIndex) {
		currentWord = word;
		
		// no green color if correct index == 0
		if (correctIndex == 0) {
			currentWordLabel.setText("<html>" + currentWord + "</html>");
		} else {
			currentWordLabel.setText("<html><font color=green>" + currentWord.substring(0, correctIndex) + "</font><font color=black>" + currentWord.substring(correctIndex) + "</font></html>");
		}
		
	}
	
	/**
	 * Updates the label showing the current typed word. Marks all letters from index 0 up to correctIndex excluding correctIndex with green background. 
	 * If currentIndex > correctIndex, the letters with indexes between them is marked with red backgound.
	 * If currentIndex < number of letters in the current word, this method prints a underscore after the current typed letters.
	 * Should be updated when a letter is entered or if the current word changes.
	 * @param typedWord The string to be displayed as the current typed word
	 * @param correctIndex The index up to which the letters in the word should be marked as green
	 * @param currentIndex The index up to which the letters in the word should be marked as red if currentIndex > correctIndex
	 */
	public void setCurrentTypedWordLabel(String typedWord, int correctIndex, int currentIndex) {
		String newLabelString = "";
		
		// basically adjust the size of the label (with border) if no letter is typed yet
		if (currentIndex == 0) {
			newLabelString = "<html>_<span color=white>" + currentWord.substring(1) + "</span></html>";
			
			// only mark correct letters
		} else if (currentIndex == correctIndex) {
			newLabelString = "<html><span style=\"background-color:green\">" + currentWord.substring(0, correctIndex) +
					"</span>_<span color=white>" + currentWord.substring(correctIndex + 1) + "</span></html>";
			
			// mark correct and incorrect letters
		} else if (currentIndex > correctIndex) {
			newLabelString = "<html><span style=\"background-color:green\">" + typedWord.substring(0, correctIndex) + 
					"</span><span style=\"background-color:red\">" + typedWord.substring(correctIndex, currentIndex) + 
					"</span>_<span color=white>" + typedWord.substring(currentIndex - 1, typedWord.length() - 1) + "</span></html>";
			
			// remove the underscore if the correct number of letters have been entered but at least one is wrong
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
	
	/**
	 * Updates the label showing the current time left in seconds. Should be called continuously through the game.
	 * @param timeLeft Time left in seconds
	 */
	public void setTimeLeftLabel(int timeLeft) {
		timeLeftLabel.setText(String.format("Tid kvar: %ds", timeLeft));
		Dimension size  = timeLeftLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		timeLeftLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
	}
	
	/**
	 * Updates the label showing the number of correct words entered. Should be called when a word is correctly entered.
	 * @param count The current number of correct words
	 */
	public void setCurrentWordCount(int count) {
		
		// Alter text based on difficulty of game
		if (difficulty <= 1) {
			currentWordCountLabel.setText(String.format("Antal ord: %d", count));
		} else if (difficulty == 2 ){
			currentWordCountLabel.setText(String.format("Antal ord eller fraser: %d", count));
		} else {
			currentWordCountLabel.setText(String.format("Antal fraser: %d", count));
		}
		
		Dimension size  = currentWordCountLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		currentWordCountLabel.setBounds(10, panelSize.height - size.height, size.width, size.height);
	}
	
	/**
	 * Set the layout of the graphical elements based on game mode and difficulty. Should be called just before the game starts.
	 * @param mode The game mode to be displayed
	 * @param difficulty The difficulty of the game to be displayed
	 */
	public void setLabelLayout(String mode, int difficulty) {
		this.mode = mode;
		this.difficulty = difficulty;
		
		if (mode == "game") {
			timeLeftLabel.setVisible(true);
			currentLPMLabel.setVisible(true);
			currentWordCountLabel.setVisible(false);
			
		} else if (mode == "practice") {
			timeLeftLabel.setVisible(false);
			currentLPMLabel.setVisible(false);
			currentWordCountLabel.setVisible(true);
			currentTypedWordLabel.setVisible(true);
		}
		
		// if difficulty == 3, show no current typed word label
		if (difficulty >= 3) {
			currentTypedWordLabel.setVisible(false);
		} else {
			currentTypedWordLabel.setVisible(true);
		}
		
		// set the difficulty label
		difficultyLabel.setText("Svårighetsgrad: " + TTController.DIFFICULTIES[difficulty]);
		Dimension size  = difficultyLabel.getPreferredSize();
		Dimension panelSize = getParent().getSize();
		difficultyLabel.setBounds(panelSize.width - size.width - 10, 0, size.width, size.height);
		
		updateLabelLayout(); // update current word and current typed word labels sizes and positions
	}
	
	/**
	 * Updates the size and position of the current word label and the current typed word label. Should be called when a new word is chosen and displayed.
	 */
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
		
		// if difficulty == 3, show no current typed word label
		if (difficulty <= 2) {
			Dimension currentTypedWordLabelSize = currentTypedWordLabel.getPreferredSize();
			currentTypedWordLabel.setBounds(panelSize.width / 2 -  currentTypedWordLabelSize.width / 2, panelSize.height / 2 - currentTypedWordLabelSize.height/ 2 + 100,
					currentTypedWordLabelSize.width, currentTypedWordLabelSize.height);
		}
	}
	
	/**
	 * Updates the label showing the current letter per minute rate. Should be called continuously through a game.
	 * @param lpm The current letter per minute rate
	 */
	public void setCurrentLPMLabel(double lpm) {
		currentLPMLabel.setText(String.format("%.1f rätta bokstäver per minut", lpm));
		Dimension panelSize = getParent().getSize();
		Dimension size  = currentLPMLabel.getPreferredSize();
		currentLPMLabel.setBounds(panelSize.width - size.width - 10, panelSize.height - size.height, size.width, size.height);
	}
	
	/**
	 * Print a message to the user with a hint on how to play the game.
	 */
	public void alertUser() {
		JLabel alert = new JLabel("TIPS! Kopiera ordet eller frasen ovan till rutan här under så snabbt du kan : )");
		alert.setFont(new java.awt.Font("Impact", 0, 30));
		Dimension panelSize = getParent().getSize();
		Dimension size  = alert.getPreferredSize();
		alert.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 30, size.width, size.height);
		add(alert);
		repaint();
	}
	
	
	
}