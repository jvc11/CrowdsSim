
package cs2510;

import java.util.Random;

/**
 *
 */
public class Jondo implements Runnable {

	public static final int PATH_END = -1;
	public static final int INVALID_ID = -2;

	int ID;

	// the routing table is an array of IDs for forwarding
	// the index of the table corresponds to the path ID
	// which is in the request message.
	int[] routingTable;

	public Jondo(int ID) {
		this.ID = ID;
	}

	public void run() {
		
	}

	void initiateRequest(Random random) {
		if (random.nextDouble() < Main.probRequest) {
			Request request = new Request(
					Main.getUniqueID(),
					ID,
					routingTable[ID],
					random.nextInt(Main.numServers));
		}
		
	}
	
}
