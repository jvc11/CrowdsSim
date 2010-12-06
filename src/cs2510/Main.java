
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
	static int totalDuration;
	static int shuffleInterval;
	static double avgRespTime;
	private static int clock;

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
		String configFile = "";
		for(int i = 0; i < args.length; i++) 
			if(args[i].equals("-c"))
				if(args.length > (i+1))
					configFile = args[i+1];
		if(configFile == "") configFile = "config.properties";
		shuffleInterval = 1000;		
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

		// main loop
		while (clock < totalDuration) {

			// TODO: this needs to be fixed so that requests
			// do not travel multiple hops in one time unit.
			// this can be done with multiple requestQueues for
			// each jondo

			// simulate new requests
			//Iterator<Jondo> jit = jondos.iterator();
			for(Jondo jondo : jondos) {
				jondo.initiateRequest(random);
				jondo.localClock = clock;
			}

			// simulate forwarding
			//jit = jondos.iterator();
			for(Jondo jondo : jondos) {
				jondo.forwardRequests();
			}

			// simulate server responding
			//Iterator<Server> sit = servers.iterator();
			for(Server server : servers) {
				server.processRequests();
				server.localClock = clock;
			}
			
			if(clock%shuffleInterval == 0)
			{
				System.out.println("Blender Shuffling");
				//blender.shufflePaths();
			}

			clock++;
//			Thread.sleep(SLEEP);
			System.out.println(clock);
		}
		avgRespTime = 0;
		for(Jondo jondo : jondos) {
				System.out.println(jondo);
				avgRespTime += jondo.getAvgRespTime();
		}
		avgRespTime /= ((double)numJondos);
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
		//shuffleInterval = Integer.parseInt(p.getProperty("shuffleInterval"));
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
			out.println("Avg response time: " + avgRespTime);
			out.println();

		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			out.close();
		}
		
	}

}
