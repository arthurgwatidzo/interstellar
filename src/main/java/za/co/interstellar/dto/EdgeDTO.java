package za.co.interstellar.dto;

import java.io.Serializable;

import javax.persistence.Column;

/**
 * @Author Arthur Gwatidzo
 * 
 *         Email: arthur.gwatidzo@gmail.com,
 * 
 *         Cell: 076-898-3930
 * 
 */
public class EdgeDTO implements Serializable {

	private String edgeId;
	private long recordId;
	private String source;
	private String destination;
	private float distance;
	private float timeDelay;

	public String getEdgeId() {
		return edgeId;
	}

	public void setEdgeId(String edgeId) {
		this.edgeId = edgeId;
	}

	public long getRecordId() {
		return recordId;
	}

	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}

	public float getTimeDelay() {
		return timeDelay;
	}

	public void setTimeDelay(float timeDelay) {
		this.timeDelay = timeDelay;
	}

}
