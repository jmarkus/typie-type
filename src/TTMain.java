import java.awt.EventQueue;

/**
 * Launch point of the application.
 */

/**
 * @author Jonatan Markusson
 *
 */
public class TTMain {
	

	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				@SuppressWarnings("unused")
				TTController controller = new TTController();
			}
		});
	}

}