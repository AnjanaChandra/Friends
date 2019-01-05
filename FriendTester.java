package friends;

import java.io.File;
import java.util.Scanner;
import friends.*;

public class FriendTester {
	public static void main(String[] args) {
		try {
			// TODO Auto-generated method stub
			Scanner sc = new Scanner(System.in);
//			System.out.print("Enter graph input file name: ");
//			String file = sc.nextLine();
			Scanner sc1 = new Scanner(new File("names3.txt"));
			Graph graph = new Graph(sc1);
			// graph.print();
			System.out.println("\n\n");
			Friends.shortestChain(graph, "nissan", "ford");
			Friends.cliques(graph, "rutgers");
			Friends.cliques(graph, "penn state");
			Friends.cliques(graph, "rowan");
			Friends.cliques(graph, "ucla");
			Friends.cliques(graph, "columbia");
			Friends.connectors(graph);
			sc.close();
			sc1.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

	}
}
