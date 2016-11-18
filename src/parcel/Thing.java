package parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Thing implements Comparable<Thing>{
	private String item_name;
	private double width, length, height;
	private double[] sideLength;

	public Thing(String name, double w, double l, double h) {
		this.item_name = name;
		this.width = w;
		this.length = l;
		this.height = h;
		sideLength= new double[3];
		sideLength[0]=w;
		sideLength[1]=l;
		sideLength[2]=h;
		Arrays.sort(sideLength);
	}
	
	public Thing(double w,double l,double h){
		this.item_name = null;
		this.width = w;
		this.length = l;
		this.height = h;
		sideLength= new double[3];
		sideLength[0]=w;
		sideLength[1]=l;
		sideLength[2]=h;
		Arrays.sort(sideLength);
	}
	
	public String getItemName() {
		return this.item_name;
	}

	public double getW() {
		return this.width;
	}

	public double getL() {
		return this.length;
	}

	public double getH() {
		return this.height;
	}
	
	public double getVolume() {
		return width*length*height;
	}

	public double[] getSideLength() {
		return sideLength;
	}
	
	public double getSize(){
		return width*length*height;
	}
	
	@Override
	public int compareTo(Thing another){
		if(this.getSize()>=another.getSize())
			return 1;
		else
			return -1;
	}
	
	public String getinfo(){
		String s="";
		s += this.item_name+"("+getL()+", "+getW()+", "+getH()+")";
		return s;
	}
}
