import javax.swing.*;
public class Main {
	
	private static Panel panel = new Panel();

	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				MainFrame frame = new MainFrame();
			}
		});

	}
}
