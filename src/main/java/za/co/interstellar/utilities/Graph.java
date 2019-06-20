package za.co.interstellar.utilities;

import java.util.ArrayList;
import java.util.List;

import za.co.interstellar.persistence.Edge;
import za.co.interstellar.persistence.Traffic;
import za.co.interstellar.persistence.Vertex;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public class Graph {
	

	    private List<Vertex> verticesList;
	    private List<Edge> edgesList;
	    private List<Traffic> trafficList;
	    private boolean undirectedGraph;
	    private boolean trafficAllowed;

	    public Graph(List<Vertex> vertexes, List<Edge> edges, List<Traffic> trafficList) {
	        this.verticesList = vertexes;
	        this.edgesList = edges;
	        this.trafficList = trafficList;
	    }

	    public List<Traffic> getTraffics() {
	        return trafficList;
	    }

	    public List<Vertex> getVertexes() {
	        return verticesList;
	    }

	    public List<Edge> getEdges() {
	        return edgesList;
	    }

	    public boolean isUndirectedGraph() {
	        return undirectedGraph;
	    }

	    public void setUndirectedGraph(boolean undirectedGraph) {
	        this.undirectedGraph = undirectedGraph;
	    }

	    public boolean isTrafficAllowed() {
	        return trafficAllowed;
	    }

	    public void setTrafficAllowed(boolean trafficAllowed) {
	        this.trafficAllowed = trafficAllowed;
	    }

	    public void processTraffics() {
	        if (trafficList != null && !trafficList.isEmpty()) {
	            for (Traffic traffic : trafficList) {
	                for (Edge edge : edgesList) {
	                    if (checkObjectsEqual(edge.getEdgeId(), traffic.getRouteId())) {
	                        if (checkObjectsEqual(edge.getSource(), traffic.getSource()) && checkObjectsEqual(edge.getDestination(), traffic.getDestination())) {
	                            edge.setTimeDelay(traffic.getDelay());
	                        }
	                    }
	                }
	            }
	        }
	    }

	    public List<Edge> getUndirectedEdges() {
	        List<Edge> undirectedEdges = new ArrayList<>();
	        for (Edge fromEdge : edgesList) {
	            Edge toEdge = copyAdjacentEdge(fromEdge);
	            undirectedEdges.add(fromEdge);
	            undirectedEdges.add(toEdge);
	        }
	        return undirectedEdges;
	    }

	    public Edge copyAdjacentEdge(Edge fromEdge) {
	        Edge toEdge = new Edge();
	        toEdge.setEdgeId(fromEdge.getEdgeId());
	        toEdge.setSource(fromEdge.getDestination());
	        toEdge.setDestination(fromEdge.getSource());
	        toEdge.setDistance(fromEdge.getDistance());
	        toEdge.setTimeDelay(fromEdge.getTimeDelay());
	        return toEdge;
	    }

	    public boolean checkObjectsEqual(Object object, Object otherObject) {
	        if (object == null && otherObject == null) {
	            return true;
	        } else if (object == null || otherObject == null) {
	            return false;
	        } else if (object instanceof String && otherObject instanceof String) {
	            return ((String) object).equalsIgnoreCase((String) otherObject);
	        } else {
	            return object.equals(otherObject);
	        }

	    }

}
