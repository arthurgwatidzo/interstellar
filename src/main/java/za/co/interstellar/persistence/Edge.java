package za.co.interstellar.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "EDGE")
public class Edge implements Serializable{
	
		@Id
		@Column(name="id")
		private String edgeId;
	    @Column
	    private long recordId;
	    @Column
	    private String source;
	    @Column
	    private String destination;
	    @Column
	    private float distance;
	    @Column
	    private float timeDelay;

	    public Edge() {
	    }

	    public Edge(long recordId, String edgeId, String source, String destination, float distance) {
	        this.recordId = recordId;
	        this.edgeId = edgeId;
	        this.source = source;
	        this.destination = destination;
	        this.distance = distance;
	    }

	    public Edge(long recordId, String edgeId, String source, String destination, float distance, float timeDelay) {
	        this.recordId = recordId;
	        this.edgeId = edgeId;
	        this.source = source;
	        this.destination = destination;
	        this.distance = distance;
	        this.timeDelay = timeDelay;
	    }

	    public long getRecordId() {
	        return recordId;
	    }

	    public void setRecordId(long recordId) {
	        this.recordId = recordId;
	    }

	    public String getEdgeId() {
	        return edgeId;
	    }

	    public void setEdgeId(String edgeId) {
	        this.edgeId = edgeId;
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

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((destination == null) ? 0 : destination.hashCode());
			result = prime * result + Float.floatToIntBits(distance);
			result = prime * result + ((edgeId == null) ? 0 : edgeId.hashCode());
			result = prime * result + (int) (recordId ^ (recordId >>> 32));
			result = prime * result + ((source == null) ? 0 : source.hashCode());
			result = prime * result + Float.floatToIntBits(timeDelay);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Edge other = (Edge) obj;
			if (destination == null) {
				if (other.destination != null)
					return false;
			} else if (!destination.equals(other.destination))
				return false;
			if (Float.floatToIntBits(distance) != Float.floatToIntBits(other.distance))
				return false;
			if (edgeId == null) {
				if (other.edgeId != null)
					return false;
			} else if (!edgeId.equals(other.edgeId))
				return false;
			if (recordId != other.recordId)
				return false;
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.equals(other.source))
				return false;
			if (Float.floatToIntBits(timeDelay) != Float.floatToIntBits(other.timeDelay))
				return false;
			return true;
		}

	
	    
	    
	    

}
