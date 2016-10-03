import java.util.LinkedList;

public class Elevator {

	// variables
	private int capacity;
	// true for up, false for down
	private boolean goingUp;
	private boolean waiting;
	private int curFloor;
	private int curDest;
	private boolean busy;
	private LinkedList<Person> people;

	// constructors
	public Elevator() {
		setCurFloor(1);
		setCapacity(5);
		setWaiting(true);
		setGoingUp(true);
		setPeople(new LinkedList<Person>());
	}

	// getters and setters

	// elevators can only hold so many, and once they're at capacity, don't stop
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isGoingUp() {
		return goingUp;
	}

	// true for up, false for down
	public void setGoingUp(boolean goingUp) {
		this.goingUp = goingUp;
	}

	// if it's waiting do nothing
	public boolean isWaiting() {
		return waiting;
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	public int getCurFloor() {
		return curFloor;
	}

	public void setCurFloor(int curFloor) {
		this.curFloor = curFloor;
	}

	public LinkedList<Person> getPeople() {
		return people;
	}

	public void setPeople(LinkedList<Person> people) {
		this.people = people;
	}

	public int getCurDest() {
		return curDest;
	}

	public void setCurDest(int curDest) {
		this.curDest = curDest;
	}

	public boolean isBusy() {
		return busy;
	}

	public void setBusy(boolean busy) {
		this.busy = busy;
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public Person removePerson(Person person) {
		if (people.remove(person)) {
			return person;
		}
		return null;
	}

	public void removePerson(LinkedList<Person> peopleToRemove) {
		while (peopleToRemove.size() > 0) {
			people.remove(peopleToRemove.getFirst());
			peopleToRemove.removeFirst();
		}
	}

	// toString for printing out information
	public String toString() {
		return "going up? " + goingUp + " waiting: " + waiting + " curFloor: " + curFloor + " capacity: "
				+ capacity + " current load: " + people.size();
	}
}
