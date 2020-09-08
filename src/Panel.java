import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Math.*;

public class Panel extends JPanel {

	// Stapel auf denen Scheiben gelegt werden
	private Stack towerA = new Stack();
	private Stack towerB = new Stack();
	private Stack towerC = new Stack();
	
	// Erzeugen von Slider und Button
	private JSlider numberOfDiscs = new JSlider(0,10,5);
	private int valueOfSlider = 5;
	private JSlider solvingSpeed = new JSlider(1, 1000, 1000);
	private JButton solve = new JButton("solve");

	// Scheibe die sich bei Drag unter Maus befindet
	private Disc dragDisc;
	private Stack lastTower;// Stapel auf dem DragDisc vor drag war

	// Mausposition
	private int mouseX, mouseY;
	private char mouseOn;

	// Anzahl der benoetigten Zuege
	private int moves;

	//Attribute fuer hanoi animation
	private Timer timer;
	private Stack[] from;	//from und to speichern zuege fuer hanoi loesung
	private Stack[] to;		//
	private int steps;
	ActionListener animation = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				
				if(moves >= hanoi(valueOfSlider)-1){
					Timer t = (Timer) e.getSource();
					t.stop();//Animation stoppt
					solving = false;
				}	

				to[moves].push(from[moves].peek().getDisc());
				from[moves].pop();
				moves++;
				repaint();
				if(towerC.getCount() == valueOfSlider || towerB.getCount() == valueOfSlider){
						//wenn alle Scheiben auf anderen Stapel sitzen erscheint Nachricht
						String output = "Scheiben: " + valueOfSlider;
						output += "\nZüge: " + moves;
						output += "\nMindestanzahl Züge: " + hanoi(valueOfSlider);
						JOptionPane.showMessageDialog(null, output);
						createDiscs(valueOfSlider);
				}
				repaint();
		}


	};

	private boolean illegal = false;
	private boolean solving = false;

/* Konstruktor */
	public Panel(){
		sliderAndButton();

		timer = new Timer(1000,animation);
		timer.setRepeats(true);

		createDiscs(5);

		//Slider für Anzahl der Scheiben
		numberOfDiscs.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				valueOfSlider = numberOfDiscs.getValue();
				createDiscs(valueOfSlider);
				repaint();
			}
		});

		//Slider fuer solve geschwindigkeit
		solvingSpeed.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				if(timer.isRunning()){
					timer.stop();
					timer = new Timer(solvingSpeed.getValue(),animation);
					timer.start();
				}else{
					timer = new Timer(solvingSpeed.getValue(),animation);
				}
			}
		});

		//solve Button ruft hanoi auf und animation spielt ab
		solve.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getSource() == solve && valueOfSlider>0){
					solving = true;
					createDiscs(valueOfSlider);
					from = new Stack[steps];
					to = new Stack[steps];
					hanoi(valueOfSlider, towerA, towerC, towerB);
					createDiscs(valueOfSlider);
					repaint();
					timer.start();//animation startet
				}
			}
		});
		
		//Drag and Drop der Scheiben
		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (!solving){
				switch(mouseOn){
					case 'a':
						if(towerA.getCount() != 0){
							dragDisc = towerA.peek().getDisc();
							lastTower = towerA;
							towerA.pop();
						}
						break;
					case 'b':
						if(towerB.getCount() != 0){
							dragDisc = towerB.peek().getDisc();
							lastTower = towerB;
							towerB.pop();
						}
						break;
					case 'c':
						if(towerC.getCount() != 0){
							dragDisc = towerC.peek().getDisc();
							lastTower = towerC;
							towerC.pop();
						}
						break;
				}
				repaint();
				}
			}
			public void mouseReleased(MouseEvent e){
				if(dragDisc!=null){
				switch(mouseOn){
					case 'a':
						if(dragDisc.isSmaller(towerA) && lastTower != towerA){
						//disc auf Stapel muss laenger sein als dragDisc
							towerA.push(dragDisc);
							moves++;
						} else { 
							lastTower.push(dragDisc);
							illegal = true;
						}
						break;
					case 'b':
						if(dragDisc.isSmaller(towerB) && lastTower != towerB){
						//disc auf Stapel muss laenger sein als dragDisc
							towerB.push(dragDisc);
							moves++;
						} else {
							lastTower.push(dragDisc);
							illegal = true;
						}
						break;
					case 'c':
						if(dragDisc.isSmaller(towerC) && lastTower != towerC){
						//disc auf Stapel muss laenger sein als dragDisc
							towerC.push(dragDisc);
							moves++;
						} else {
							lastTower.push(dragDisc);
							illegal = true;
						}
						break;
					default:
						lastTower.push(dragDisc);
						illegal = true;
						break;
				}
					dragDisc = null;
					repaint();
					if(towerC.getCount() == valueOfSlider || towerB.getCount() == valueOfSlider){
						//wenn alle Scheiben auf anderen Stapel sitzen erscheint Nachricht
						String output = "Scheiben: " + valueOfSlider;
						output += "\nZüge: " + moves;
						output += "\nMindestanzahl Züge: " + hanoi(valueOfSlider);
						JOptionPane.showMessageDialog(null, output);
						createDiscs(valueOfSlider);
					}
					repaint();
				}
			}
		});

		addMouseMotionListener(new MouseMotionListener(){
			public void mouseDragged(MouseEvent e){
				
				//mouseOn = Character des turms auf dem Maus ist
				if(e.getX() > 115 && e.getX() < 365 && e.getY() > 150 && e.getY() < 600){
					mouseOn = 'a';
				}else if(e.getX() > 475 && e.getX() < 725 && e.getY() > 150 && e.getY() < 600){
					mouseOn = 'b';
				}else if(e.getX() > 835 && e.getX() < 1085 && e.getY() > 150 && e.getY() < 600){
					mouseOn = 'c';
				}else{
					mouseOn = 0;
				}

				mouseX = e.getX();
				mouseY = e.getY();
				repaint();
			}

			public void mouseMoved(MouseEvent e){
				mouseX = e.getX();
				mouseY = e.getY();

				//mouseOn = Character des turms auf dem Maus ist
				if(e.getX() > 115 && e.getX() < 365 && e.getY() > 150 && e.getY() < 600){
					mouseOn = 'a';
				}else if(e.getX() > 475 && e.getX() < 725 && e.getY() > 150 && e.getY() < 600){
					mouseOn = 'b';
				}else if(e.getX() > 835 && e.getX() < 1085 && e.getY() > 150 && e.getY() < 600){
					mouseOn = 'c';
				}else{
					mouseOn = 0;
				}
			}
		});
	}
	
	//Konfiguration von Slider und Button
	public void sliderAndButton(){
		numberOfDiscs.setPaintTrack(true);
		numberOfDiscs.setPaintTicks(true);
		numberOfDiscs.setPaintLabels(true);
		numberOfDiscs.setSnapToTicks(true);
		
		numberOfDiscs.setMajorTickSpacing(5);
		numberOfDiscs.setMinorTickSpacing(1);

		solvingSpeed.setInverted(true);

		add(numberOfDiscs);
		add(solve);
		add(solvingSpeed);
	}
	
	// Methode setzt discs auf ersten Stapel
	public void createDiscs(int value){
		timer.stop();
		towerA.clear();
		towerB.clear();
		towerC.clear();
		moves = 0;
		steps = hanoi(valueOfSlider);
		
		for (int i = 1; i<= value; i++){
			// jede Disc hat	  laenge und		   RGB-Werte
			towerA.push(new Disc(250-(i*20), 0, (50+(i*20))%256, (255-(i*10))%256));
		}
	}
	
/* Methode in der gezeichnet wird */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Zeichne Objekte
		
		g.setColor(Color.LIGHT_GRAY);
		
		//Tuerme
		//towerA
		g.fillRect(1200/5-15, 230, 30, 320);
		g.drawString("A", 1200/5-5, 580);
		//towerB
		g.fillRect(1200/2-15, 230, 30, 320);
		g.drawString("B", 1200/2-5, 580);
		//towerC
		g.fillRect(1200/5*4-15, 230, 30, 320);
		g.drawString("C", 1200/5*4-5, 580);
		
		//Anzeigen (Strings)
		g.setColor(Color.BLACK);
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 10));
		//schrift ueber slider
		g.drawString("(solving speed)",700,12);
		g.drawString("(number of discs)",420,12);
		//moves
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 15));
		g.drawString("moves: "+moves,20,30);

		if(illegal){
			g.setColor(Color.RED);
			g.setFont(new Font(g.getFont().getFontName(),Font.ITALIC, 12));
			g.drawString("illegal move!",562,100);
			illegal = false;
		}

		//Scheiben
		towerA.drawDiscs(g,240);
		towerB.drawDiscs(g,600);
		towerC.drawDiscs(g,960);

		//Auswahl (drag)
		if(dragDisc!=null){
			g.setColor(new Color(dragDisc.getRed(),dragDisc.getGreen(),dragDisc.getBlue()));
			g.fillRect(mouseX-dragDisc.getLength()/2,mouseY-15,dragDisc.getLength(),30);
		}
	}
	
	// rekursive Methode die Loesungsschritte in die arrays to und from abspeichert
	public void hanoi(int n, Stack fromTower, Stack toTower, Stack auxTower){
		if(n==1){//Anker
			from[moves] = fromTower;
			to[moves] = toTower;
			moves++;
			return;
		}
		hanoi(n-1, fromTower, auxTower, toTower);
		from[moves] = fromTower;
		to[moves] = toTower;
		moves++;
		hanoi(n-1, auxTower, toTower, fromTower);
	}
	
	// gibt mindestanzahl der zuege fuer stapelgroesse n zurück
	public int hanoi(int n){
		return (int) Math.pow(2, n)-1;
	}
}
