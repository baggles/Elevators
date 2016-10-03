import java.util.LinkedList;

public class Floor {

	// variables
	private int floorNum;
	private LinkedList<Person> people;

	// constructors
	public Floor(int floorNum) {
		this.setFloorNum(floorNum);
		setPeople(new LinkedList<Person>());
	}

	// getters and setters
	public int getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(int floorNum) {
		this.floorNum = floorNum;
	}

	public LinkedList<Person> getPeople() {
		return people;
	}

	public void setPeople(LinkedList<Person> people) {
		this.people = people;
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

	// don't print out floors, so no need for a toString
}
