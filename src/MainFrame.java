import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame{
	
	private final int width = 1200;
	private final int height = width/16*9;

	private Panel panel;
	//private JSlider slider;

	/* Konstruktor */
	public MainFrame(){
		
		super("Hanoi");

		config();

	
	}
	
	public void config(){
		setLayout(null);
		setSize(width, height);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new Panel();
		//slider = panel.getSlider();
		setContentPane(panel);
		
		setVisible(true);
	}
	
}
