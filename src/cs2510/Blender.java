
package cs2510;

import java.util.HashMap;
import java.util.LinkedList;
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
		}
//		System.out.println(pathMap);	//DEBUG
	}

	/**
	 *
	 * @param startID
	 * @return
	 */
	private List<Integer> getRandomPath(int startID) {
		double prob;
		int next = startID;
		List<Integer> path = new LinkedList<Integer>();

		do {
			path.add(next);
			prob = random.nextDouble();
			next = random.nextInt(Main.numJondos);
		} while (prob < Main.probForward);
		return path;
	}
	
}
