import java.util.Random;

public class Person {

	// variables
	private String name;
	private int[] stops;
	private int curDestI;
	private int prevFloor;
	private int curFloor;
	private int curDest;
	private int timeOnFloor;
	private boolean onEle;
	private boolean busy;
	private boolean finalLocation;

	// constructors
	public Person(int name, int j) {
		Random rn = new Random();
		// random number of stops
		stops = new int[rn.nextInt(3) + 1];
		for (int i = 0; i < stops.length; i++) {
			stops[i] = rn.nextInt(j) + 1;
		}
		setName("Person#" + (name + 1));
		setPrevFloor(1);
		setCurFloor(1);
		setCurDest(stops[0]);
		setTimeOnFloor(0);
		onEle = false;
		setCurDestI(0);
	}

	public Person(int name, int[] stops) {
		setName("Person#" + name);
		setStops(stops);
		setPrevFloor(1);
		setCurFloor(1);
		onEle = false;
		setCurDestI(0);
		setTimeOnFloor(0);
		setCurDest(stops[0]);
	}

	// getters and setters
	public int[] getStops() {
		return stops;
	}

	public void setStops(int[] stops) {
		this.stops = stops;
	}

	public int getPrevFloor() {
		return prevFloor;
	}

	public void setPrevFloor(int curFloor) {
		this.prevFloor = curFloor;
	}

	public int getCurFloor() {
		return curFloor;
	}

	public void setCurFloor(int curFloor) {
		this.curFloor = curFloor;
	}

	public int getCurDest() {
		return curDest;
	}

	public void setCurDest(int curDest) {
		this.curDest = curDest;
	}

	public boolean isOnEle() {
		return onEle;
	}

	public void setOnEle(boolean onEle) {
		this.onEle = onEle;
	}

	public int getCurDestI() {
		return curDestI;
	}

	public void setCurDestI(int curDestI) {
		this.curDestI = curDestI;
		if (curDestI < stops.length) {
			curDest = stops[curDestI];
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public boolean isFinalLocation() {
		return curFloor == stops[stops.length - 1] && curDestI >= stops.length;
	}

	public boolean getFinalLocation() {
		return finalLocation;
	}

	public void setFinalLocation(boolean finalLocation) {
		this.finalLocation = finalLocation;
	}

	public int getTimeOnFloor() {
		return timeOnFloor;
	}

	public void setTimeOnFloor(int timeOnFloor) {
		this.timeOnFloor = timeOnFloor;
	}

	// toString for printing out the person
	public String toString() {
		String str = name + " stops: ";
		for (int i = 0; i < stops.length; i++) {
			str += stops[i] + " ";
		}
		str += "prevFloor: " + prevFloor + " curDest: " + curDest + " finalLocation: " + finalLocation;
		return str.trim();
	}
}
