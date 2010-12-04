
package cs2510;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;

/**
 */
public class Main {

	static double probForward;
	static double probRequest;
	static int numJondos;
	static int numAttackers;
	static int numServers;
	static int totalDuration;
	private static int clock;

	private static int SEED;
	static Random random;
	private static int IDcounter;
	
	static Server[] servers;
	static Jondo[] jondos;
	static Blender blender;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {

		loadConfiguration("config.properties");

		runSimulation();

		writeResults();
	}

	/**
	 * 
	 */
	private static void runSimulation() {

		clock = 0;
		random = new Random(SEED);
		blender = new Blender();
		servers = new Server[numServers];
		
		for (int i=0; i < numServers; i++) {
			servers[i] = new Server(i);
		}

		jondos = new Jondo[numJondos];
		for (int i=0; i < numJondos; i++) {
			jondos[i] = new Jondo(i);
		}

		// initialize paths
		blender.shufflePaths();
		System.exit(0);

		// main loop
		while (clock < totalDuration) {

			// simulate requests
			for (Jondo jondo : jondos) {
				jondo.initiateRequest(random);
			}

			// simulate forwarding

			// simulate server responding
			
			clock++;
		}
	}

	static int getUniqueID() {
		return ++IDcounter;
	}

	/**
	 * 
	 * @param filename
	 * @throws IOException
	 */
	private static void loadConfiguration(String filename) throws IOException {
		Properties p = new Properties();
		p.load(new FileReader(filename));

		numJondos = Integer.parseInt(p.getProperty("numJondos"));
		numAttackers = Integer.parseInt(p.getProperty("numAttackers"));
		numServers = Integer.parseInt(p.getProperty("numServers"));
		totalDuration = Integer.parseInt(p.getProperty("totalDuration"));
		SEED = Integer.parseInt(p.getProperty("rand.seed"));
		probForward = Double.parseDouble(p.getProperty("probForward"));
		probRequest = Double.parseDouble(p.getProperty("probRequest"));
	}

	/**
	 *
	 */
	private static void writeResults() {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter("output.txt"));

			out.println("== CrowdSim Statistics ==");
			out.println(Calendar.getInstance().getTime());
			out.println();

		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			out.close();
		}
		
	}

}
