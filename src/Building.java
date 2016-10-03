import java.util.LinkedList;
import java.util.Random;

public class Building {

	// variables
	private int numFloors;
	private int numElevators;
	private int numPeople;
	private LinkedList<Person> people;
	private Elevator[] elevators;
	private Floor[] floors;

	// constructors
	public Building(int floors, int ele, int people) {
		setNumFloors(floors);
		setNumElevators(ele);
		setNumPeople(people);
		generateElevators();
		generateFloors();
		generatePeople(getNumFloors());
	}

	public void generateRandom(int floors, int ele, int people) {
		Random rn = new Random();
		setNumFloors(rn.nextInt(floors) + 1);
		setNumElevators(rn.nextInt(ele) + 1);
		setNumPeople(rn.nextInt(people) + 1);
		generatePeople(getNumFloors());
		generateElevators();
	}

	// getters and setters
	public int getNumFloors() {
		return numFloors;
	}

	public int getNumElevators() {
		return numElevators;
	}

	public void setNumFloors(int numFloors) {
		this.numFloors = numFloors;
	}

	public void setNumElevators(int numElevators) {
		this.numElevators = numElevators;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public LinkedList<Person> getPeople() {
		return people;
	}

	public void setPeople(LinkedList<Person> people) {
		this.people = people;
	}

	public Elevator[] getElevators() {
		return elevators;
	}

	public void setElevators(Elevator[] elevators) {
		this.elevators = elevators;
	}

	public Floor[] getFloors() {
		return floors;
	}

	public void setFloors(Floor[] floors) {
		this.floors = floors;
	}

	public boolean isEveryoneNotDone() {
		boolean finished = true;
		for (Person p : people) {
			if (!p.isFinalLocation()) {
				finished = false;
			}
		}
		return finished;
	}

	private void generateElevators() {
		elevators = new Elevator[numElevators];
		for (int i = 0; i < numElevators; i++) {
			elevators[i] = new Elevator();
		}
	}

	// some specific test cases that gave me some issues,
	@SuppressWarnings("unused")
	private void testCase1() {
		int[] test = { 4, 2 };
		int[] test1 = { 4, 2 };
		int[] test2 = { 2 };
		int[] test3 = { 5 };
		Person p = new Person(0, test);
		Person p1 = new Person(1, test1);
		Person p2 = new Person(2, test2);
		Person p3 = new Person(3, test3);
		people.add(p);
		people.add(p1);
		people.add(p2);
		people.add(p3);
		floors[1].addPerson(p);
		floors[1].addPerson(p1);
		floors[1].addPerson(p2);
		floors[1].addPerson(p3);
	}

	@SuppressWarnings("unused")
	private void testCase2() {
		int[] test = { 2, 4 };
		int[] test1 = { 2, 1 };
		int[] test2 = { 1 };
		int[] test3 = { 5 };
		Person p = new Person(0, test);
		Person p1 = new Person(1, test1);
		Person p2 = new Person(2, test2);
		Person p3 = new Person(3, test3);
		people.add(p);
		people.add(p1);
		people.add(p2);
		people.add(p3);
		floors[1].addPerson(p);
		floors[1].addPerson(p1);
		floors[1].addPerson(p2);
		floors[1].addPerson(p3);
	}

	// do after generating floors
	private void generatePeople(int j) {
		people = new LinkedList<Person>();

		for (int i = 0; i < numPeople; i++) {
			Person p = new Person(i, j);
			people.add(p);
			floors[1].addPerson(p);
		}
	}

	private void generateFloors() {
		floors = new Floor[numFloors + 1];
		for (int i = 1; i <= numFloors; i++) {
			floors[i] = new Floor(i);
		}
	}

	public void simulate() {
		// reset busy status, if they're empty set them back to waiting (the
		// person who was waiting may have been picked up)
		for (Elevator e : elevators) {
			e.setBusy(false);
			if (e.getPeople().size() == 0) {
				e.setWaiting(true);
			}
		}

		// an elevator can do 1 thing during each simulation period, it can
		// either load, unload, or move, once they've done 1 of the 3 they are
		// considered busy an can do nothing else for the remainder of the
		// simulation step
		unloadElevators();

		// people usually go to a floor for a reason, this method causes them to
		// take up time, and not get on elevators
		processFloor();
		loadElevators();

		findElevators();
		// used to have the elevators find people, but in order to make the
		// elevators slightly smarter as to which direction to go, the people
		// now "find" elevators
		// findPeople(); deprecated
		moveElevators();
	}

	private void processFloor() {
		for (Person p : people) {
			if (!p.isOnEle() && p.getCurDest() == p.getCurFloor() && !p.getFinalLocation()) {
				p.setBusy(true);
				p.setPrevFloor(p.getCurDest());
				p.setCurDestI(p.getCurDestI() + 1);

				if (p.isFinalLocation()) {
					p.setFinalLocation(true);
					floors[p.getCurFloor()].removePerson(p);
				}
			}
			// stay on a floor for 5 simulation periods
			if (p.getTimeOnFloor() > 5) {
				p.setBusy(false);
			} else {
				p.setTimeOnFloor(p.getTimeOnFloor() + 1);
			}
		}
	}

	@SuppressWarnings("unused")
	@Deprecated
	// elevators dont't search for people anymore
	private void findPeople() {
		for (Elevator e : elevators) {
			if (e.isWaiting() || e.getPeople().size() == 0) {
				int curFloor = e.getCurFloor();
				int i = 1;
				boolean peopleFound = false;
				while (i < numFloors && !peopleFound) {
					if (curFloor + i <= numFloors) {
						if (floors[curFloor + i].getPeople().size() > 0) {
							peopleFound = true;
							e.setWaiting(false);
							e.setGoingUp(true);
						}
					}
					if (curFloor - i > 0 && e.isWaiting()) {
						if (floors[curFloor - i].getPeople().size() > 0) {
							peopleFound = true;
							e.setWaiting(false);
							e.setGoingUp(false);
						}
					}
					i++;
				}
				if (!peopleFound)
					e.setWaiting(true);
			}
		}
	}

	// people who are waiting on elevators search for the closest elevator
	private void findElevators() {
		for (Person p : people) {
			if (!p.isBusy() && !p.isOnEle() && !p.isFinalLocation()) {
				Elevator closest = null;
				for (Elevator e : elevators) {
					if (e.isWaiting() && closest != null && Math.abs(e.getCurFloor() - p.getCurFloor()) < Math
							.abs(closest.getCurFloor() - p.getCurFloor())) {
						closest = e;
					} else if (e.isWaiting() && closest == null) {
						closest = e;
					}
				}
				if (closest != null) {
					closest.setWaiting(false);
					closest.setGoingUp(closest.getCurFloor() < p.getCurFloor());
				}
			}
		}
	}

	private void moveElevators() {
		for (Elevator e : elevators) {
			if (!e.isWaiting() && !e.isBusy()) {
				e.setBusy(true);
				int curFloor = e.getCurFloor();
				if (e.isGoingUp()) {
					e.setCurFloor(curFloor + 1);
				} else {
					e.setCurFloor(curFloor - 1);
				}
			}
		}
	}

	private void loadElevators() {
		// only check the floors with elevators
		Floor[] eleFloors = new Floor[numElevators];
		for (int i = 0; i < numElevators; i++) {
			eleFloors[i] = floors[elevators[i].getCurFloor()];
		}
		for (Floor f : eleFloors) {
			LinkedList<Person> peopleToRemove = new LinkedList<Person>();
			// some overlap here, but it's negligible
			for (Elevator e : elevators) {
				if (e.getCurFloor() == f.getFloorNum() && !e.isBusy()) {
					for (Person p : f.getPeople()) {
						if (e.getPeople().size() < e.getCapacity() && !p.isBusy()
								&& ((p.getCurDest() > p.getPrevFloor() && (e.isGoingUp() || e.isWaiting()))
										|| (p.getCurDest() < p.getPrevFloor()
												&& (!e.isGoingUp() || e.isWaiting())))) {
							e.addPerson(p);
							e.setBusy(true);
							p.setOnEle(true);
							p.setPrevFloor(f.getFloorNum());
							peopleToRemove.add(p);
							if (e.isWaiting()) {
								e.setWaiting(false);
								e.setGoingUp(e.getCurFloor() < p.getCurDest());
							}
						}
					}
				}
				// remove people from the floor
				f.removePerson(peopleToRemove);
			}
		}
	}

	private void unloadElevators() {
		for (Elevator e : elevators) {
			LinkedList<Person> peopleToRemove = new LinkedList<Person>();
			for (Person p : e.getPeople()) {
				if (p.getCurDest() == e.getCurFloor()) {
					floors[e.getCurFloor()].addPerson(p);
					peopleToRemove.add(p);
					p.setOnEle(false);
					p.setCurFloor(e.getCurFloor());
					e.setBusy(true);
					p.setTimeOnFloor(0);
					while (p.getPrevFloor() == p.getCurDest() && p.getCurDestI() < p.getStops().length) {
						p.setCurDestI(p.getCurDestI() + 1);
					}
				}
			}
			// remove people from elevator and set it to waiting if it's empty
			e.removePerson(peopleToRemove);
			if (e.getPeople().size() == 0) {
				e.setWaiting(true);
			}
		}

	}
}
