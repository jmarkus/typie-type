import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class TTStartPanel extends JPanel {
	
	
	public TTController controller;
	
	JButton gameButton;
	JButton practiceButton;
	JButton easyButton;
	JButton mediumButton;
	JButton hardButton;
	
	
	public TTStartPanel() {
		setBackground(Color.WHITE);
	}
	
	public void setup() {
		
		setLayout(null);
		
		Dimension panelSize = getParent().getSize();
		Dimension size;
		
		JLabel titleLabel = new JLabel("TypieType¡");
		titleLabel.setFont(new java.awt.Font("Impact", 0, 90));
		size  = titleLabel.getPreferredSize();
		titleLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 - 100, size.width, size.height);
		add(titleLabel);
		
		
		// Buttons
		
		gameButton = new JButton("Spela");
		gameButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
        		repaint();
            }
        });
		gameButton.setFont(new java.awt.Font("Impact", 0, 30));
		gameButton.setOpaque(false);
		gameButton.setContentAreaFilled(false);
		gameButton.setBorderPainted(false);
        size  = gameButton.getPreferredSize();
        gameButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 45, size.width, size.height);
        add(gameButton);
        
        practiceButton = new JButton("Öva");
        practiceButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		controller.startPracticeGame();;
            }
        });
        practiceButton.setFont(new java.awt.Font("Impact", 0, 30));
        practiceButton.setOpaque(false);
        practiceButton.setContentAreaFilled(false);
        practiceButton.setBorderPainted(false);
        size  = practiceButton.getPreferredSize();
        practiceButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 105, size.width, size.height);
        add(practiceButton);
        
        easyButton = new JButton("Starta lätt");
		easyButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
                controller.startGame(0);
            }
        });
		easyButton.setFont(new java.awt.Font("Impact", 0, 30));
		easyButton.setOpaque(false);
		easyButton.setContentAreaFilled(false);
		easyButton.setBorderPainted(false);
        size  = easyButton.getPreferredSize();
        easyButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 30, size.width, size.height);
        easyButton.setVisible(false);
        add(easyButton);
		
        mediumButton = new JButton("Starta medelsvår");
        mediumButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
                controller.startGame(1);
            }
        });
        mediumButton.setFont(new java.awt.Font("Impact", 0, 30));
        mediumButton.setOpaque(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setBorderPainted(false);
        size  = mediumButton.getPreferredSize();
        mediumButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 90, size.width, size.height);
        mediumButton.setVisible(false);
        add(mediumButton);
        
        hardButton = new JButton("Starta svår");
        hardButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
        		toggleButtons();
                controller.startGame(2);
            }
        });
        hardButton.setFont(new java.awt.Font("Impact", 0, 30));
        hardButton.setOpaque(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setBorderPainted(false);
        size  = hardButton.getPreferredSize();
        hardButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 + 150, size.width, size.height);
        hardButton.setVisible(false);
        add(hardButton);
	}
	
	private void toggleButtons() {
		
		if (easyButton.isVisible()) {
			easyButton.setVisible(false);
			mediumButton.setVisible(false);
			hardButton.setVisible(false);
			
			gameButton.setVisible(true);
    		practiceButton.setVisible(true);
		} else {
			easyButton.setVisible(true);
			mediumButton.setVisible(true);
			hardButton.setVisible(true);
			
			gameButton.setVisible(false);
    		practiceButton.setVisible(false);
		}
        
	}

}
