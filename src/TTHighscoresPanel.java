import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class TTHighscoresPanel extends JPanel {
	
	
	public TTController controller;
	
	
	public TTHighscoresPanel() {
		setBackground(Color.WHITE);
	}
	
	public void setup() {
		
		setLayout(null);
		
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
	
	public void printHighscores(HashMap<Integer, ArrayList<TTHighscore>> highscores, TTHighscore latestScore) {
		
		removeAll();
		setup();
		
		
		Dimension panelSize = getParent().getSize();
		
		int nr = 0;
		for (Integer i : highscores.keySet()) {
			int colXCenter = 40 + ((panelSize.width - 80) / highscores.size()) / 2 + nr * (panelSize.width - 80) / highscores.size();
			
			JLabel currentLevel = new JLabel();
			
			switch (i) {
			case 0:
				currentLevel.setText("Inte svårt");
				break;
			case 1:
				currentLevel.setText("Lite svårt");
				break;
			case 2:
				currentLevel.setText("Ganska svårt");
				break;
			case 3:
				currentLevel.setText("Väldigt svårt");
				break;
			default:
				currentLevel.setText("Okänt svårt");
				break;
			}
			
			currentLevel.setFont(new java.awt.Font("Impact", 0, 40));
			Dimension size  = currentLevel.getPreferredSize();
			currentLevel.setBounds(colXCenter - size.width / 2, 50, size.width, size.height);
			add(currentLevel);
			
			for (int j = 0; j < 10; j++) {
				if (highscores.get(i).size() > j) {
					
					String currentString = String.format("%d. %s - %.1f", j + 1, highscores.get(i).get(j).name, highscores.get(i).get(j).score);
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
