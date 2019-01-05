package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;


public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2. Chain is returned as a
	 * sequence of names starting with p1, and ending with p2. Each pair (n1,n2) of
	 * consecutive names in the returned chain is an edge in the graph.
	 * 
	 * @param g  Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no path from p1 to
	 *         p2
	 */
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {

		/** COMPLETE THIS METHOD **/

		ArrayList<String> result = new ArrayList<>();
		HashMap<String, Integer> distance = new HashMap<>();
		HashMap<String, String> prev = new HashMap<>();
		Queue<String> queue = new Queue<String>();
		queue.enqueue(p1);
		distance.put(p1, 0);

		while (!queue.isEmpty()) {
			String v = queue.dequeue();
			// for (Person p : g.members) {
			Person person1 = g.members[g.map.get(v)];
			// Person person2 = g.members[g.map.get(p2)];
			for (Friend nbr = person1.first; nbr != null; nbr = nbr.next) {
				String nbr1 = g.members[nbr.fnum].name;
				// System.out.println(" --> " + nbr1 + ", " + distance);
				if (!distance.containsKey(nbr1)) {
					queue.enqueue(nbr1);

					distance.put(nbr1, 1 + distance.get(v));
					prev.put(nbr1, v);
				}
			}
		}
		boolean hasPath = distance.containsKey(p2);
		if (!hasPath) {
			int max = Integer.MAX_VALUE;
			distance.get(p2);
		}

		Stack<String> path = new Stack<String>();
		while (p2 != null && distance.containsKey(p2)) {
			path.push(p2);
			p2 = prev.get(p2);
		}

		while (!path.isEmpty()) {
			result.add(path.pop());
		}

		System.out.println("shortest chain: " + result);
		return result;
	}

	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g      Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {

		/** COMPLETE THIS METHOD **/
		school = school.toLowerCase();
		if (school == null) {
			return null;
		}

		ArrayList<ArrayList<String>> result = new ArrayList<>();
		ArrayList<String> visited = new ArrayList<>();

		// loop through each of the member list
		// check if visited
		// if not do a depth first search
		for (int i = 0; i < g.members.length; i++) {
			Person person1 = g.members[i];
			ArrayList<String> clicks = new ArrayList<>();

			if (!visited.contains(person1.name)) {
				getCliques(visited, g, school, clicks, i);
				if (!clicks.isEmpty()) {
					result.add(clicks);
				}
			}
			// System.out.println(i+ "\n");
		}
		System.out.println("\n\n" + "cliques: " + result);
		return result;
	}

	// do a recursive search for each person
	// use dfs recursively

	private static void getCliques(ArrayList<String> visited, Graph g, String school, ArrayList<String> click,
			int nextNumber) {
		Person pers = g.members[nextNumber];

		if (!visited.contains(pers.name) && pers.student && pers.school != null && pers.school.equals(school)) {
			System.out.println(
					" --> " + pers.name + " - " + pers.school + " == " + school + "? " + (pers.student ? "Y" : "N"));
			click.add(pers.name);
		}
		visited.add(pers.name);

		// check to see if the linked list has any references
		for (Friend nbr = pers.first; nbr != null; nbr = nbr.next) {
			Person next = g.members[nbr.fnum];
			if (!visited.contains(next.name) && next.student && next.school != null && next.school.equals(school)) {
				getCliques(visited, g, school, click, nbr.fnum);
			}
		}
		// System.out.println(click);
	}

	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */

	public static ArrayList<String> connectors(Graph g) {

		ArrayList<String> connectors = new ArrayList<>();
		int[] dfsArray = new int[g.members.length];
		int[] backArray = new int[g.members.length];
		boolean[] backed = new boolean[g.members.length];
		boolean[] visited = new boolean[g.members.length];

		// loop through each of the member list
		// check if visited
		// if not do a depth first search
		for (int i = 0; i < g.members.length; i++) {
			if (visited[i] != true) {
				getConnectors(g, i, visited, dfsArray, backArray, i, backed, i, connectors);
			}
		}
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		System.out.println(connectors);
		return connectors;
	}

	private static void getConnectors(Graph g, int current, boolean[] visited, int[] dfsArray, int[] backArray,
			int prev, boolean[] backed, int startIndex, ArrayList<String> connectors) {

		if (visited[current]) {
			return;
		}

		visited[current] = true;
		dfsArray[current] = dfsArray[prev] + 1;
		backArray[current] = dfsArray[current];
		Friend pointer = g.members[current].first;

		for (; pointer != null; pointer = pointer.next) {
			if (visited[pointer.fnum]) {
				int minBackDfs = Math.min(backArray[current], dfsArray[pointer.fnum]);
				backArray[current] = minBackDfs;
			}

			else {
				getConnectors(g, pointer.fnum, visited, dfsArray, backArray, current, backed, startIndex, connectors);

				if (dfsArray[current] > backArray[pointer.fnum]) {
					int minBack = Math.min(backArray[current], backArray[pointer.fnum]);
					backArray[current] = minBack;
				}
				if (dfsArray[current] <= backArray[pointer.fnum] && !connectors.contains(g.members[current].name)) {
					if (current != startIndex || backed[current] == true) {
						connectors.add(g.members[current].name);
					}
				}

				backed[current] = true;
			}

		}
	}

	/**
	 * @param args
	 */

}
