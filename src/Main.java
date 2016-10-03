
public class Main {

	public static Building build1;

	public static void main(String[] args) {
		// create a building (floors, elevators, people)
		build1 = new Building(30, 5, 25);

		// generate a random building with up to x floors, y elevators, and z
		// people
		// build1.generateRandom(x, y, z);

		// list numbers floors elevators and people
		System.out.println("Number of elevators:" + build1.getNumElevators());
		System.out.println("Number of floors:" + build1.getNumFloors());
		System.out.println("Number of people:" + build1.getNumPeople());

		// print and simulate running a building
		printElevatorsAndFloors();
		build1.simulate();
		printElevatorsAndFloors();

		// continue to simulate until everyone is at their final location
		while (!build1.isEveryoneNotDone()) {
			build1.simulate();
			printElevatorsAndFloors();
		}
	}

	private static void printElevatorsAndFloors() {
		// Don't want this printing every time
		/*
		 * System.out.println("Number of elevators:" +
		 * build1.getNumElevators()); System.out.println("Number of floors:" +
		 * build1.getNumFloors()); System.out.println("Number of people:" +
		 * build1.getNumPeople());
		 */

		// elevators
		System.out.println("elevators:");
		for (int i = 0; i < build1.getNumElevators(); i++) {
			System.out.println(build1.getElevators()[i]);
		}

		// people
		System.out.println("people:");
		for (int i = 0; i < build1.getNumPeople(); i++) {

			System.out.println(build1.getPeople().get(i));
		}
		System.out.println();
	}

}
