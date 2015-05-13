import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class TTStartPanel extends JPanel {
	
	
	public TTController controller;
	
	
	public TTStartPanel() {
		
		setLayout(new GridBagLayout());
		
		JLabel titleLabel = new JLabel("TypieType¡");
		titleLabel.setFont(new java.awt.Font("Impact", 0, 90));
		//titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		add(titleLabel);
		
		JButton startButton = new JButton("Starta");
		//startButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        startButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent event) {
                controller.startGame(1);
            }
        });
        add(startButton);
		
	}

}
