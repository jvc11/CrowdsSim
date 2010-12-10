
package cs2510;

import java.util.*;

/**
 */
public class Server implements Runnable{

	int ID;
	private Queue<Request> requestQueue;

	public Server(int ID) {
		this.ID = ID;
		requestQueue = new LinkedList<Request>();
	}
	
	
	public void run() {
		
	}
	
	public boolean isDone() {
		return (requestQueue.size() == 0);
	}

	/**
	 * 
	 */
	void processRequests() {
		if(!requestQueue.isEmpty()) {
			Request r = requestQueue.poll();
			System.out.println("server " + ID + ": " + r);
			r.isReply = true;
			Main.jondos[r.from].submitRequest(r);
		}
	}

	/**
	 * 
	 * @param request
	 */
	public void submitRequest(Request request) {
		requestQueue.add(request);
	}
}
