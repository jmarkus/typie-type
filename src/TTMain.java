
import java.awt.EventQueue;

public class TTMain {
	

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TTController controller = new TTController();
				controller.mainFrame.setVisible(true);
			}
		});
	}

}