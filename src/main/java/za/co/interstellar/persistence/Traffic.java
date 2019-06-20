package za.co.interstellar.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Entity
@Table(name = "TRAFFIC")
public class Traffic implements Serializable{
	
		@Id
		@Column(name = "id", updatable = false, nullable = false)
		@GeneratedValue(strategy = GenerationType.AUTO)
	    private String routeId;
	    @Column
	    private String source;
	    @Column
	    private String destination;
	    @Column
	    private float delay;

	    public Traffic() {
	    }

	    public Traffic(String routeId, String source, String destination, float delay) {
	        this.routeId = routeId;
	        this.source = source;
	        this.destination = destination;
	        this.delay = delay;
	    }

	    public String getRouteId() {
	        return routeId;
	    }

	    public void setRouteId(String routeId) {
	        this.routeId = routeId;
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

	    public float getDelay() {
	        return delay;
	    }

	    public void setDelay(float delay) {
	        this.delay = delay;
	    }

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Float.floatToIntBits(delay);
			result = prime * result + ((destination == null) ? 0 : destination.hashCode());
			result = prime * result + ((routeId == null) ? 0 : routeId.hashCode());
			result = prime * result + ((source == null) ? 0 : source.hashCode());
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
			Traffic other = (Traffic) obj;
			if (Float.floatToIntBits(delay) != Float.floatToIntBits(other.delay))
				return false;
			if (destination == null) {
				if (other.destination != null)
					return false;
			} else if (!destination.equals(other.destination))
				return false;
			if (routeId == null) {
				if (other.routeId != null)
					return false;
			} else if (!routeId.equals(other.routeId))
				return false;
			if (source == null) {
				if (other.source != null)
					return false;
			} else if (!source.equals(other.source))
				return false;
			return true;
		}
	    
	    

}
