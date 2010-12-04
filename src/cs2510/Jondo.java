
package cs2510;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 *
 */
public class Jondo implements Runnable {

	public static final int PATH_END = -1;
	public static final int INVALID_ID = -2;

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
		requestQueue = new LinkedList<Request>();
	}

	public void run() {
		
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
					random.nextInt(Main.numServers));
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

		while (!requestQueue.isEmpty()) {
			Request request = requestQueue.poll();

			System.out.println("jondo " + ID + ": " + request);

			// check if the request going out or coming back
			if (request.isReply) {

				// check if this our message or
				// pass the response to the next node:
				if (request.pathID == ID) {
					// TODO: record response time
				} else {
					Main.jondos[responseTable[request.pathID]].submitRequest(request);
				}
			} else {

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
	
}
