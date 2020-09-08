public class Disc {
	
/*Attribute*/
	private int length;
	
	//Farbe
	private int red;
	private int green;
	private int blue;

/*Konstruktor*/
	public Disc(int length, int red, int green, int blue){
		this.length = length;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

/*Methoden*/
	public boolean isSmaller(Stack tower){
		return tower.getCount() == 0 || tower.peek().getDisc().getLength() > this.getLength();
	}
/*getter und setter*/
	public int getLength(){
		return length;
	}

	public void setLength(int length){
		this.length = length;
	}

	public int getRed(){
		return red;
	}

	public int getGreen(){
		return green;
	}

	public int getBlue(){
		return blue;
	}
}

