package za.co.interstellar.service;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import za.co.interstellar.persistence.Edge;
import za.co.interstellar.persistence.Vertex;
import za.co.interstellar.utilities.Graph;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Service
public class ShortestDistanceService {
	
	private static final Log log = LogFactory.getLog(ShortestDistanceService.class);

    private List<Vertex> vertices;
    private List<Edge> edges;
    private Set<Vertex> visitedVertices;
    private Set<Vertex> unvisitedVertices;
    private Map<Vertex, Vertex> previousPaths;
    private Map<Vertex, Float> distance;

    public ShortestDistanceService() {
    }

    public ShortestDistanceService(Graph graph) {
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.edges = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.edges = new ArrayList<>(graph.getEdges());
        }
    }

    public void setUpPlanets(Graph graph) {
    	log.debug("setting up the planets in the Universe");
        this.vertices = new ArrayList<>(graph.getVertexes());
        if (graph.isTrafficAllowed()) {
            graph.processTraffics();
        }
        if (graph.isUndirectedGraph()) {
            this.edges = new ArrayList<>(graph.getUndirectedEdges());
        } else {
            this.edges = new ArrayList<>(graph.getEdges());
        }
    }

    public void run(Vertex source) {
        distance = new HashMap<>();
        previousPaths = new HashMap<>();
        visitedVertices = new HashSet<>();
        unvisitedVertices = new HashSet<>();
        distance.put(source, 0f);
        unvisitedVertices.add(source);
        while (unvisitedVertices.size() > 0) {
            Vertex currentVertex = getVertexWithLowestDistance(unvisitedVertices);
            visitedVertices.add(currentVertex);
            unvisitedVertices.remove(currentVertex);
            determineNeighborPlanetsWithMinimalDistances(currentVertex);
        }
    }

    private Vertex getVertexWithLowestDistance(Set<Vertex> vertexes) {
    	log.debug("retrieving the Vertex with the Lowest Distance");
        Vertex lowestVertex = null;
        for (Vertex vertex : vertexes) {
            if (lowestVertex == null) {
                lowestVertex = vertex;
            } else if (getShortestDistance(vertex) < getShortestDistance(lowestVertex)) {
                lowestVertex = vertex;
            }
        }
        return lowestVertex;
    }

    private void determineNeighborPlanetsWithMinimalDistances(Vertex currentVertex) {
    	log.debug("determining the neighbours with minimal distance");
        List<Vertex> adjacentVertices = findNeighborPlanets(currentVertex);
        for (Vertex target : adjacentVertices) {
            float alternateDistance = getShortestDistance(currentVertex) + getDistance(currentVertex, target);
            if (alternateDistance < getShortestDistance(target)) {
                distance.put(target, alternateDistance);
                previousPaths.put(target, currentVertex);
                unvisitedVertices.add(target);
            }
        }
    }

    private List<Vertex> findNeighborPlanets(Vertex currentVertex) {
    	log.debug("finding the neighbouring planets");
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            Vertex destination = fromId(edge.getDestination());
            if (edge.getSource().equals(currentVertex.getVertexId()) && !isVisited(destination)) {
                neighbors.add(destination);
            }
        }
        return neighbors;
    }

    public Vertex fromId(final String str) {
        for (Vertex v : vertices) {
            if (v.getVertexId().equalsIgnoreCase(str)) {
                return v;
            }
        }
        Vertex islandVertex = new Vertex();
        islandVertex.setVertexId(str);
        islandVertex.setName("Island " + str);
        return islandVertex;
    }

    private boolean isVisited(Vertex vertex) {
    	log.debug("checking if vertex has been visited");
        return visitedVertices.contains(vertex);
    }

    private Float getShortestDistance(Vertex destination) {
    	log.debug("finding the shortest distance between points");
        Float d = distance.get(destination);
        if (d == null) {
            return Float.POSITIVE_INFINITY;
        } else {
            return d;
        }
    }

    private float getDistance(Vertex source, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(source.getVertexId()) && edge.getDestination().equals(target.getVertexId())) {
                return edge.getDistance() + edge.getTimeDelay();
            }
        }
        throw new RuntimeException("Error: Failed to get the distance");
    }

    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();
        Vertex step = target;

        if (previousPaths.get(step) == null) {
            return null;
        }
        path.add(step);
        while (previousPaths.get(step) != null) {
            step = previousPaths.get(step);
            path.add(step);
        }

        Collections.reverse(path);
        return path;
    }


}
