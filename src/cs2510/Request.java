
package cs2510;

/**
 * @author ygl2
 * ygl2@cs.pitt.edu
 * ylegall@gmail.com
 */
public class Request {
	int GUID;
	int pathID;
	
	int serverID;
	int from;
	long timestamp;
	boolean isReply;
	int numForwards;

	public Request(int GUID, int pathID, int from, int serverID, long tsp) {
		this.GUID = GUID;
		this.pathID = pathID;
		this.from = from;
		this.serverID = serverID;
		this.timestamp = tsp;
		this.numForwards = 0;
	}

	public String toString() {
		return new StringBuilder("request{")
				.append("ID=").append(GUID).append(", ")
				.append("pathID=").append(pathID).append(", ")
				.append("server=").append(serverID).append(", ")
				.append("reply=").append(isReply).append(" timestamp= ")
				.append(timestamp).append(" Forwards: ").append(numForwards)
				.append('}')
				.toString();
	}
}
