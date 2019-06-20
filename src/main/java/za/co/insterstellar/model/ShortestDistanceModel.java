package za.co.insterstellar.model;

import java.io.Serializable;
/**
* @Author Arthur Gwatidzo - Email: arthur.gwatidzo@gmail.com, Cell: 076-898-3930
*/
public class ShortestDistanceModel implements Serializable {

	private String destinationVertex;
	private String selectedVertex;
	private String selectedVertexName;
	private String thePath;
	private String sourceVertex;
	private boolean trafficAllowed;
	private String vertexId;
	private String vertexName;
	private boolean undirectedGraph;

	public String getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(String destinationVertex) {
		this.destinationVertex = destinationVertex;
	}

	public String getSelectedVertex() {
		return selectedVertex;
	}

	public void setSelectedVertex(String selectedVertex) {
		this.selectedVertex = selectedVertex;
	}

	public String getSelectedVertexName() {
		return selectedVertexName;
	}

	public void setSelectedVertexName(String selectedVertexName) {
		this.selectedVertexName = selectedVertexName;
	}

	public String getThePath() {
		return thePath;
	}

	public void setThePath(String thePath) {
		this.thePath = thePath;
	}

	public String getSourceVertex() {
		return sourceVertex;
	}

	public void setSourceVertex(String sourceVertex) {
		this.sourceVertex = sourceVertex;
	}

	public boolean isTrafficAllowed() {
		return trafficAllowed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationVertex == null) ? 0 : destinationVertex.hashCode());
		result = prime * result + ((selectedVertex == null) ? 0 : selectedVertex.hashCode());
		result = prime * result + ((selectedVertexName == null) ? 0 : selectedVertexName.hashCode());
		result = prime * result + ((sourceVertex == null) ? 0 : sourceVertex.hashCode());
		result = prime * result + ((thePath == null) ? 0 : thePath.hashCode());
		result = prime * result + (trafficAllowed ? 1231 : 1237);
		result = prime * result + (undirectedGraph ? 1231 : 1237);
		result = prime * result + ((vertexId == null) ? 0 : vertexId.hashCode());
		result = prime * result + ((vertexName == null) ? 0 : vertexName.hashCode());
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
		ShortestDistanceModel other = (ShortestDistanceModel) obj;
		if (destinationVertex == null) {
			if (other.destinationVertex != null)
				return false;
		} else if (!destinationVertex.equals(other.destinationVertex))
			return false;
		if (selectedVertex == null) {
			if (other.selectedVertex != null)
				return false;
		} else if (!selectedVertex.equals(other.selectedVertex))
			return false;
		if (selectedVertexName == null) {
			if (other.selectedVertexName != null)
				return false;
		} else if (!selectedVertexName.equals(other.selectedVertexName))
			return false;
		if (sourceVertex == null) {
			if (other.sourceVertex != null)
				return false;
		} else if (!sourceVertex.equals(other.sourceVertex))
			return false;
		if (thePath == null) {
			if (other.thePath != null)
				return false;
		} else if (!thePath.equals(other.thePath))
			return false;
		if (trafficAllowed != other.trafficAllowed)
			return false;
		if (undirectedGraph != other.undirectedGraph)
			return false;
		if (vertexId == null) {
			if (other.vertexId != null)
				return false;
		} else if (!vertexId.equals(other.vertexId))
			return false;
		if (vertexName == null) {
			if (other.vertexName != null)
				return false;
		} else if (!vertexName.equals(other.vertexName))
			return false;
		return true;
	}

	public void setTrafficAllowed(boolean trafficAllowed) {
		this.trafficAllowed = trafficAllowed;
	}

	public String getVertexId() {
		return vertexId;
	}

	public void setVertexId(String vertexId) {
		this.vertexId = vertexId;
	}

	public String getVertexName() {
		return vertexName;
	}

	public void setVertexName(String vertexName) {
		this.vertexName = vertexName;
	}

	public boolean isUndirectedGraph() {
		return undirectedGraph;
	}

	public void setUndirectedGraph(boolean undirectedGraph) {
		this.undirectedGraph = undirectedGraph;
	}

}
