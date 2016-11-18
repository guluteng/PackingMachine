package parcel;

import java.util.Arrays;

enum MailBox {
	EM1("EM1",370,250,210,13.0),
	EM2("EM2",430,350,260,17.0),
	EM3("EM3",455,425,335,26.0);
	
	private final int width, length, height;
	private final String name;
	private final double price;
	private int[] sideLength;
	private final int size;
	
	private MailBox(String name, int w, int l, int h, double price) {
		this.name = name;
		this.width = w;
		this.length = l;
		this.height = h;
		this.size=w*l*h;
		this.price = price;
		sideLength= new int[3];
		sideLength[0]=w;
		sideLength[1]=l;
		sideLength[2]=h;
		Arrays.sort(sideLength);
	}
	
	public String getName() {
		return name;
	}

	public int getW() {
		return width;
	}

	public int getL() {
		return length;
	}

	public int getH() {
		return height;
	}
	
	public int[] getSideLength(){
		return sideLength;
	}

	public double getPrice() {
		return price;
	}
	
	public int getSize(){
		return size;
	}
}