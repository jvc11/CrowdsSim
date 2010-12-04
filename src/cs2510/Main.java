
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
	
	private static Server[] servers;
	private static Jondo[] jondos;
	private static Blender blender;

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
		jondos = new Jondo[numJondos];

		// initialize paths
		blender.shufflePaths();

		// main loop
		while (clock < totalDuration) {

			// simulate requests

			// simulate forwarding

			// simulate server responding
			
			clock++;
		}
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
