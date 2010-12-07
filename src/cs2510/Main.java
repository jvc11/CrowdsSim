
package cs2510;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 */
public class Main {

	static double probForward;
	static double probRequest;
	static int numJondos;
	static int numAttackers;
	static int numServers;
	static int shuffleInterval;
	static int clock;

	static double avgRespTime;
	static int maxSimulationTime;
	static long totalDuration;

	private static int SEED;
//	private static final int SLEEP = 200;
	static Random random;
	private static int IDcounter;
	
	public static Server[] servers;
	public static Jondo[] jondos;
	public static Blender blender;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String configFile = "config.properties";
		for(int i = 0; i < args.length; i++) {
			if (args[i].equals("-c") && (i < args.length-1) ) {
				configFile = args[i+1];
			}
		}

		loadConfiguration(configFile);

		runSimulation();

		writeResults();
	}

	/**
	 * 
	 */
	private static void runSimulation() throws Exception {

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
		totalDuration = System.currentTimeMillis();

		// main loop
		while (clock < maxSimulationTime) {

			// TODO: this needs to be fixed so that requests
			// do not travel multiple hops in one time unit.
			// this can be done with multiple requestQueues for
			// each jondo

			// simulate new requests
			for(Jondo jondo : jondos) {
				jondo.initiateRequest(random);
			}

			// simulate forwarding
			for(Jondo jondo : jondos) {
				jondo.forwardRequests();
			}

			// simulate server responding
			for(Server server : servers) {
				server.processRequests();
			}
			
//			if(clock%shuffleInterval == 0) {
//				System.out.println("Blender Shuffling");
//				//blender.shufflePaths();
//			}

			clock++;
//			Thread.sleep(SLEEP);
			System.out.println(clock);
		}
		totalDuration = System.currentTimeMillis() - totalDuration;
	}

	/**
	 * @return
	 */
	private static double getAvgRespTime() {
		avgRespTime = 0;
		for(Jondo jondo : jondos) {
			System.out.println(jondo);	// DEBUG
			avgRespTime += jondo.getAvgRespTime();
		}
		avgRespTime /= ((double)numJondos);
		return avgRespTime;
	}

	/**
	 * 
	 * @return
	 */
	private static int getMaxResponseTime() {
		int max = Main.jondos[0].maxResponseTime;
		for (Jondo j : Main.jondos) {
			max = (j.maxResponseTime > max)? j.maxResponseTime : max;
		}
		return max;
	}

	/**
	 * calculates the probable innocence. more positive is better.
	 * @return
	 */
	static double probableInnocence() {
		return numJondos - (((probForward)/(probForward - 0.5d))*(numAttackers + 1));
	}

	/**
	 * @return
	 */
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
		maxSimulationTime = Integer.parseInt(p.getProperty("totalDuration"));
		shuffleInterval = Integer.parseInt(p.getProperty("shuffleInterval"));
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

			out.println("=== CrowdSim Statistics ===");
			out.println(Calendar.getInstance().getTime());
			out.println("total duration: " + totalDuration);
			out.println("crowd size: " + numJondos);
			out.println("number of attackers: " + numAttackers);

			out.println("Avg response time: " + getAvgRespTime());
			out.println("max response time: " + getMaxResponseTime());
			out.println("probable innocence: " + probableInnocence());
			
			out.println();

		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			out.close();
		}
		
	}

}
