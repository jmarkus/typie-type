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
	public String currentWord;
	
	private JLabel currentWordLabel;
	
	public TTGamePanel() {
		
		setLayout(new GridBagLayout());
		
		currentWordLabel = new JLabel("placeholder");
		currentWordLabel.setFont(new java.awt.Font("Arial", 0, 80));
		add(currentWordLabel);
		
	}
	
	public void setCurrentWord(String word, int currentIndex) {
		
		if (currentIndex == 0) {
			currentWordLabel.setText("<html>" + word + "</html>");
		} else {
			currentWordLabel.setText("<html><font color=green>" + word.substring(0, currentIndex) + "</font><font color=black>" + word.substring(currentIndex) + "</font></html>");
		}
		
	}
	
	
	
}