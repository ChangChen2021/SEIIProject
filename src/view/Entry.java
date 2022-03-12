package view;

/**
 * For the entry
 * @author Chang Chen
 *
 */
public class Entry {
	
	public void init() {
		
		/**
		 * Launch the application.
		 */
		try {
			GalleryWindow frame = new GalleryWindow();
			frame.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Entry().init();
	}
}
