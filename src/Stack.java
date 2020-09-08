import javax.swing.*;
import java.awt.*;

class Stack {
	
	//Knoten
	class Node {
		private Disc disc;
		private Node next;
		
		public Node(Disc disc) {
			this.disc = disc;
		}
		
		public Disc getDisc() {
			return disc;
		}
		
		public void setDisc(Disc disc) {
			this.disc = disc;
		}
		
		public Node getNext() {
			return next;
		}
		
		public void setNext(Node next) {
			this.next = next;
		}
	}
	
	private Node top;
	private int count;
	
	public Stack() {
		top = null;
		count = 0;
	}
	
	// legt neuen Knoten mit disc auf top
	public void push(Disc disc) {
		Node n = new Node(disc);
		if(n != null) {
			n.setNext(top);
			top = n;
			count++;
		}
	}
	
	// gibt obersten Knoten aus
	public Node peek() {
		return top;
	}
	
	// nimmt einen Knoten vom Stapel runter
	public Node pop() {
		Node k = top;
		if(top != null) {
			top = top.getNext();
			count--;
		}
		return k;
	}
	
	// gibt Anzahl der Elemente im Stapel wieder
	public int getCount() {
		return count;
	}
	
	// verweise werden entfernt. Inhalt wird geloescht (Garbage-Collector)
	public void clear(){
		top = null;
		count = 0;
	}

	//zeichnet Scheiben des Turms
	public void drawDiscs(Graphics g, int x){
		Node current = top;
		for (int i = 1; i<= count; i++){
			
			int xDisc = x - (current.getDisc().getLength()/2);
			int yDisc = 520 - ((count-i)*30); 

			g.setColor(new Color(current.getDisc().getRed(),current.getDisc().getGreen(),current.getDisc().getBlue()));
			g.fillRect(xDisc,yDisc,current.getDisc().getLength(),30);

			current = current.getNext();
		}
	}
}
