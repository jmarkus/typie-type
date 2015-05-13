import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class TTStartPanel extends JPanel {
	
	
	public TTController controller;
	
	
	public TTStartPanel() {
		setBackground(Color.WHITE);
	}
	
	public void setupStartPanel() {
		
		setLayout(null);
		
		Dimension panelSize = getParent().getSize();
		Dimension size;
		
		JLabel titleLabel = new JLabel("TypieType¡");
		titleLabel.setFont(new java.awt.Font("Impact", 0, 90));
		size  = titleLabel.getPreferredSize();
		titleLabel.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2 - 100, size.width, size.height);
		add(titleLabel);
		
		JButton startButton = new JButton("Starta");
        startButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                controller.startGame(1);
            }
        });
        size  = startButton.getPreferredSize();
        startButton.setBounds(panelSize.width / 2 -  size.width / 2, panelSize.height / 2 - size.height/ 2, size.width, size.height);
        add(startButton);
	}

}
