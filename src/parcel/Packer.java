package parcel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Packer {
	private List<Box> boxes;
	private List<Thing> items;
	private List<Thing> oversized;
	private List<Thing> normal;
	private int estimationA;
	private int estimationB;
	private int id;

	public Packer(List<Thing> items) {
		this.boxes = new ArrayList<Box>();
		this.items = new ArrayList<Thing>();
		this.oversized = new ArrayList<Thing>();
		this.normal = new ArrayList<Thing>();
		this.items = items;
		this.id = 0;
	}

	public void packStuff() {
		sortItems();
		clearOversizedItems();
		pack();
		generateReport();
	}
	
	public void getEstimationA(List<Thing> things){
		estimationA=(int)(getTotalSize(things)/MailBox.EM3.getSize());
		
	}
	
	public void getEstimationB(List<Thing> things){
		estimationA=(int)(getTotalSize(things)/MailBox.EM2.getSize());
	}

	private void sortItems() {
		Collections.sort(items);
	}
	
	private boolean match(Thing thing,Box box){
		int[] boxSide=box.getSideLength();
		double[] thingSide=thing.getSideLength();
		for(int i=0;i<3;i++){
			if(boxSide[i]<thingSide[i])
				return false;
		}
		return true;
	}
	
	private boolean match(Thing thing,MailBox box){
		int[] boxSide=box.getSideLength();
		double[] thingSide=thing.getSideLength();
		for(int i=0;i<3;i++){
			if(boxSide[i]<thingSide[i])
				return false;
		}
		return true;
	}

	private void clearOversizedItems() {
		
		for(Thing e:items){
			if(match(e,MailBox.EM3))
				normal.add(e);
			else
				oversized.add(e);
		}
	}
	

	private void pack() {
		// basic description of the algorithm:
		// 1.	Choose the largest box first
		// 2.	Put the largest item in the box
		// 3.	Continuously try to put smaller items in the box
		// 4.	For remaining items, restart the process in a new box
		// 5.	For packed box, try to put the items in a smaller box
		// 6.	If the items in a box cannot further put in a smaller box, this box's configuration is said to be optimized
		// 7.	After all boxes' configs are optimized, the "boxes" list should contain the boxes needed to put all the items
		
		// Note: To create a box object, simply get the box info by calling the objects defined in MailBox enum
		// i.e.	MailBox mb = MailBox.EM1;
		// 		Box b = new Box(getId(), mb.getName(), mb.getW(), mb.getL(), mb.getH(), mb.getPrice());
		
	}
	
	private void packUp(){
		clearOversizedItems();
		getEstimationA(normal);
		
		for(Thing e:normal){
			if(estimationA<=0){
				getEstimationB(normal);
				if(estimationB<=0){
					//use small box firstly
					if(boxes.size()==0){
						if(match(e,new Box("Small"))){
							boxes.add(new Box("Small"));
							boxes.get(0).putThing(e);
						}
						else if(match(e,new Box("Middle"))){
							boxes.add(new Box("Middle"));
							boxes.get(0).putThing(e);
						}
						else{
							boxes.add(new Box("Large"));
							boxes.get(0).putThing(e);
						}
					}
					else{
						//check all box
						//if not successful: add new box(check from smallest)
						if(!checkAllBox(e)){
							if(match(e,new Box("Small"))){
								boxes.add(new Box("Small"));
								boxes.get(boxes.size()-1).putThing(e);
							}
							else if(match(e,new Box("Middle"))){
								boxes.add(new Box("Middle"));
								boxes.get(boxes.size()-1).putThing(e);
							}
							else{
								boxes.add(new Box("Largest"));
								boxes.get(boxes.size()-1).putThing(e);
							}
						}
							
					}
				}
				else{
					//use middle box firstly
					if(boxes.size()==0){
						if(match(e,new Box("Middle"))){
							boxes.add(new Box("Large"));
							estimationB--;
							boxes.get(0).putThing(e);
						}
						else{
							boxes.add(new Box("Large"));
							estimationB--;
							boxes.get(0).putThing(e);
						}
					}
					else{
						//check all box
						//if not successful: add new box(check middle box if not ok use large box b--)
						if(!checkAllBox(e)){
							if(match(e,new Box("Middle"))){
								boxes.add(new Box("Middle"));
								boxes.get(boxes.size()-1).putThing(e);
								estimationB--;
							}
							else{
								boxes.add(new Box("Largest"));
								boxes.get(boxes.size()-1).putThing(e);
								estimationB--;
							}
						}
					}
				}
			}
			else{
				if(boxes.size()==0){
					boxes.add(new Box("Large"));
					boxes.get(0).putThing(e);
				}
				else{
					//check all box
					//if not successful:add large box,a--
					//next time check A again
					if(!checkAllBox(e)){
						boxes.add(new Box("Largest"));
						boxes.get(boxes.size()-1).putThing(e);
						estimationA--;
					}
				}
			}
		}
	}
	//6*3*items_in_box situation
	private Thing combineSide(double[] one,double[] another){
		double MaxA=one[0]+another[0];
		double MaxB=max(one[1],another[1]);
		double MaxC=max(one[2],another[2]);
		return new Thing(MaxA,MaxB,MaxC);
	}
	
	private double max(double d, double e) {
		if(d>e)
			return d;
		else 
			return e;
	}

	private List<double[]> getPermutation(double[] arr){
		Permutation p=new Permutation();
        List<double[]> allSorts=p.getPermutation(arr);
        return allSorts;
	}
	
	
	private Thing combineTwoThing(Thing thing,Thing another){
		double maxSize=0;
		Thing smallest = null;
		double[] sideLength=thing.getSideLength();
		double[] sideLength2=another.getSideLength();
		List<double[]> allSorts=getPermutation(sideLength);
		List<double[]> allSorts2=getPermutation(sideLength2);
		for(double[] e:allSorts){
			for(double[] a:allSorts2){
				Thing t=combineSide(e,a);
				if(maxSize<t.getSize()){
					maxSize=t.getSize();
					smallest=t;
				}
			}
		}
		return smallest;
	}
	
	
	private boolean checkAllBox(Thing t){
		for(Box b:boxes){
			List<Thing> things=b.getThings();
			Thing temp=t;
			//combine from smallest to largest 
			for(int i=things.size()-1;i>=0;i--){
				temp=combineTwoThing(things.get(i),temp);
			}
			if(match(temp,b)){
				b.putThing(t);;
				return true;
			}
		}
		return false;
	}
	
	
	
	public double getTotalSize(List<Thing> things){
		double sum=0;
		for(Thing e:things){
			sum+=e.getSize();
		}
		return sum;
	}

	private void generateReport() {
		System.out.println("Order ID("+ getId()+"):\n");
		for(Box e:boxes){
			System.out.println(e.getInfo());
		}
		System.out.print("Oversized Item: ");
		for(Thing e:oversized){
			System.out.print(e.getinfo());
		}
	}

	private int getId() {
		id++;
		return id;
	}
	
	public static void main(String[] args) {
		
		System.out.println("Please input the size of your items.");
		System.out.println("(Input \"end\" to exit)");
		List<Thing> items = new ArrayList<Thing>();
		Scanner sc=new Scanner(System.in);
		for(;;){
			String n = sc.next();
			if(n.equals("end")){
				System.out.println("You have finish your packing\n");
				break;
			}
			else{
				double l = sc.nextDouble();
				double w = sc.nextDouble();
				double h = sc.nextDouble();
				items.add(new Thing(n,l,w,h));
			}
		}
		Packer packer = new Packer(items);
		packer.packUp();
		packer.generateReport();
		sc.close();
    }
}
