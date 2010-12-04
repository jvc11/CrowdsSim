
package cs2510;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 */
public class Blender {

	private Random random;
	private Map<Integer, List<Integer>> pathMap;

	public Blender() {
		pathMap = new HashMap<Integer, List<Integer>>();
		random = Main.random;
	}

	/**
	 * 
	 */
	void shufflePaths() {
		for (int i=0; i < Main.numJondos; i++) {
			pathMap.put(i, getRandomPath(i));
			Main.jondos[i].routingTable = new int[Main.numJondos];
			Arrays.fill(Main.jondos[i].routingTable, Jondo.INVALID_ID);
		}

		for (int i=0; i < Main.numJondos; i++) {
			List<Integer> path = pathMap.get(i);
			int pathLength = path.size();
			for (int j = 0; j < pathLength; j++) {
				Jondo jondo = Main.jondos[path.get(j)];
				int next = Jondo.PATH_END;
				if (j < pathLength-1) {
					next = path.get(j+1);
				}
				jondo.routingTable[i] = next;
			}
		}

		DEBUG();
	}

	/**
	 *
	 * @param startID
	 * @return
	 */
	private List<Integer> getRandomPath(int startID) {
		double prob;
		int next = startID;
		List<Integer> path = new ArrayList<Integer>();

		do {
			path.add(next);
			prob = random.nextDouble();
			next = random.nextInt(Main.numJondos);
		} while (prob < Main.probForward);
		return path;
	}

	private void DEBUG() {
		System.out.println(pathMap);
		System.out.println();
		for (Jondo j : Main.jondos) {
			System.out.println(Arrays.toString(j.routingTable));
		}
	}
	
}
