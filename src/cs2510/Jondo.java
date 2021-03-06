
package cs2510;

import java.util.*;

/**
 *
 */
public class Jondo implements Runnable {

	public static final int PATH_END = -1;
	public static final int PATH_NONE = -2;
	
	public boolean malicious;
	long responseTime;
	long forwardTotal;
	long maxResponseTime;
	long maxForwards;
	private long requestCount;

	int ID;	// same as the pathID for simplicity

	// the routing table is an array of IDs for forwarding
	// the index of the table corresponds to the path ID
	// which is in the request message.
	int[] routingTable;

	// same as the routing table but in the reverse direction
	int[] responseTable;

	private Queue<Request> requestQueue;

	public Jondo(int ID) {
		this.ID = ID;
		this.responseTime = 0;
		this.requestCount = 0;
		requestQueue = new LinkedList<Request>();
	}
	
	public Jondo(int ID, boolean mal) {
		this.ID = ID;
		this.responseTime = 0;
		this.requestCount = 0;
		requestQueue = new LinkedList<Request>();
		malicious = mal;
	}

	public void run() {
		
	}
	
	public boolean isDone() {
		return (requestQueue.size() == 0);
	}

	/**
	 * 
	 * @param random
	 */
	void initiateRequest(Random random) {
		if (random.nextDouble() < Main.probRequest) {
			Request request = new Request(
					Main.getUniqueID(),
					ID,
					ID,
					random.nextInt(Main.numServers),
					Main.clock
				);
			requestQueue.add(request);
		}
	}

	/**
	 * 
	 * @param request
	 */
	public void submitRequest(Request request) {
		requestQueue.add(request);
	}

	/**
	 * processes requests in the message
	 * queue and forwards them appropriately.
	 */
	void forwardRequests() {

		if(!requestQueue.isEmpty()) {
			Request request = requestQueue.poll();

			System.out.println("jondo " + ID + ": " + request);

			// check if the request going out or coming back
			if (request.isReply) {

				// check if this is our message or
				// pass the response to the next node:
				if (request.pathID == ID) {
					// TODO: record response time... DONE
					requestCount++;

					responseTime += (Main.clock - request.timestamp);
					forwardTotal += (request.numForwards);
					// record the max forwards
					if (request.numForwards > maxForwards) {
						maxForwards = request.numForwards;
					}
					if ((Main.clock - request.timestamp) > maxResponseTime) {
						maxResponseTime = (Main.clock - request.timestamp);
					}
					
				} else {
					Main.jondos[responseTable[request.pathID]].submitRequest(request);
				}

			} else {
				
				// request is going out:
				request.numForwards++;
				int next = routingTable[request.pathID];
				request.from = ID;
				if (next == PATH_END) {
					Main.servers[request.serverID].submitRequest(request);
				} else {
					Main.jondos[next].submitRequest(request);
				}
			}
		}
	}

	public double getAvgRespTime() {
		return ((double) responseTime) / ((double) requestCount);
	}
	
	public double getAvgForwardCount() {
		return ((double)forwardTotal)/((double) requestCount);
	}

	public String toString() {
		return "Jondo " + ID + " avg response time: " + getAvgRespTime() + "\n";
	}
}
