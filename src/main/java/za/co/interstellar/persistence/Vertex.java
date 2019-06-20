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
@Table(name = "VERTEX")
public class Vertex implements Serializable{
	
	
	@Id
    @Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
    private String vertexId;
    @Column
    private String name;

    public Vertex() {
    }

    public Vertex(String vertexId, String name) {
        this.vertexId = vertexId;
        this.name = name;
    }

    public String getVertexId() {
        return vertexId;
    }

    public void setVertexId(String vertexId) {
        this.vertexId = vertexId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((vertexId == null) ? 0 : vertexId.hashCode());
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
		Vertex other = (Vertex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (vertexId == null) {
			if (other.vertexId != null)
				return false;
		} else if (!vertexId.equals(other.vertexId))
			return false;
		return true;
	}
    
    

}
