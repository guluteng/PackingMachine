package parcel;
import java.util.*;

class Box{
	private int id;
	private String name;
	private int width, length, height;
	private double price;
	private List<Thing> things;
	private int[] sideLength;

	public Box(String n, int w, int l, int h, double p) {
		constr(n,w,l,h,p);
	}
	
	public Box(String s){
		if(s.equals("Large"))
			constr(MailBox.EM3.getName(),MailBox.EM3.getW(),MailBox.EM3.getL(),MailBox.EM3.getH(),MailBox.EM3.getPrice());
		else if(s.equals("Middle"))
			constr(MailBox.EM2.getName(),MailBox.EM2.getW(),MailBox.EM2.getL(),MailBox.EM2.getH(),MailBox.EM2.getPrice());
		else 
			constr(MailBox.EM1.getName(),MailBox.EM1.getW(),MailBox.EM1.getL(),MailBox.EM1.getH(),MailBox.EM1.getPrice());
	}
	
	public void constr(String n, int w, int l, int h, double p){
		this.id = id;
		this.name = n;
		this.width = w;
		this.length = l;
		this.height = h;
		this.price = p;	
		this.things = new ArrayList<Thing>();
		sideLength= new int[3];
		sideLength[0]=w;
		sideLength[1]=l;
		sideLength[2]=h;
		Arrays.sort(sideLength);
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

	public void putThing(Thing t) {
		things.add(t);
	}
	
	public List<Thing> getThings(){
		return things;
	}
	
	public int getSize(){
		return width*length*height;
	}

	public String getInfo() {
		char times = 'x';
		String in = "";
		for(Thing e:things){
			in +=e.getinfo()+", ";
		}
		return "Box" + this.id + "(" + this.width + times + this.length + times + this.height + "): "+in;
	}
	
}


