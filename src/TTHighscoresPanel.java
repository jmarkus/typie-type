import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * View class handling the display of the highscore lists.
 */

/**
 * @author Jonatan Markusson
 *
 */
@SuppressWarnings("serial")
public class TTHighscoresPanel extends JPanel {
	
	
	public TTController controller; // pointer to controller object
	
	
	public TTHighscoresPanel() {
		setBackground(Color.WHITE);
	}
	
	/**
	 * Initializes all graphical elements with placeholder text. Must be called after the object has been added to a frame.
	 */
	public void setup() {
		
		setLayout(null); // absolute positioning
		
		Dimension panelSize = getParent().getSize();
		Dimension size;
		
		JButton toStartButton = new JButton("OK");
		toStartButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                controller.toStart();
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
	 * Prints the 10 best highscores in each difficulty from the highscores map. If latestScore != null, mark this as green.
	 * @param highscores A map containing the highscores to be displayed
	 * @param latestScore A score to mark with green color, if null nothing is changed
	 */
	public void printHighscores(HashMap<Integer, ArrayList<TTHighscore>> highscores, TTHighscore latestScore) {
		
		removeAll();
		setup();
		
		
		Dimension panelSize = getParent().getSize();
		
		int nr = 0;
		for (Integer i : highscores.keySet()) {
			
			//calculate column center
			int colXCenter = 40 + ((panelSize.width - 80) / highscores.size()) / 2 + nr * (panelSize.width - 80) / highscores.size();
			
			JLabel currentLevel = new JLabel(TTController.DIFFICULTIES[i]);
			
			currentLevel.setFont(new java.awt.Font("Impact", 0, 40));
			Dimension size  = currentLevel.getPreferredSize();
			currentLevel.setBounds(colXCenter - size.width / 2, 50, size.width, size.height);
			add(currentLevel);
			
			for (int j = 0; j < 10; j++) {
				if (highscores.get(i).size() > j) {
					
					String currentString = String.format("%d. %s - %.1f", j + 1, highscores.get(i).get(j).name, highscores.get(i).get(j).score);
					
					// mark latestScore green
					if (highscores.get(i).get(j) == latestScore) {
						currentString = "<html><span color=\"green\">" + currentString + "</span></html>";
					}
					
					JLabel current = new JLabel(currentString);
					current.setFont(new java.awt.Font("Impact", 0, 27));
					size  = current.getPreferredSize();
					current.setBounds(colXCenter - size.width / 2, 140 + j * 48, size.width, size.height);
					add(current);
				} else {
					break;
				}
				
			}
			
			
			nr++;
		}
		
	}

}
