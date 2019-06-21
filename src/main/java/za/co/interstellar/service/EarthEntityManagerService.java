package za.co.interstellar.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.interstellar.dao.EdgeDao;
import za.co.interstellar.dao.TrafficDao;
import za.co.interstellar.dao.VertexDao;
import za.co.interstellar.exception.InterstellarApplicationException;
import za.co.interstellar.persistence.Edge;
import za.co.interstellar.persistence.Traffic;
import za.co.interstellar.persistence.Vertex;
import za.co.interstellar.utilities.Graph;
import za.co.interstellar.utilities.XlsxHandler;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Service
public class EarthEntityManagerService {
	
	
	private static final String EXCEL_FILENAME = "/interstellar.xlsx";
	private static final Log log = LogFactory.getLog(EarthEntityManagerService.class);

	private VertexDao vertexDao;
	private EdgeDao edgeDao;
	private TrafficDao trafficDao;

    @Autowired
    public EarthEntityManagerService(VertexDao vertexDao, EdgeDao edgeDao, TrafficDao trafficDao) {
        this.vertexDao = vertexDao;
        this.edgeDao = edgeDao;
        this.trafficDao = trafficDao;
    }

    public void saveGraph() {
    	log.debug("saving a graph");
        URL resource = getClass().getResource(EXCEL_FILENAME);
        File file1;
        try {
            file1 = new File(resource.toURI());
            saveGraph(file1);
        } catch (URISyntaxException e) {
            //TODO: Add proper logging of this exception using log4j
        	log.error("Error - while saving a graph" + e.getMessage());
        }
    }

    public void saveGraph(File file) {
    	log.debug("saving a graph using a file");
        XlsxHandler handler = new XlsxHandler(file);
        
        List<Vertex> vertices = handler.readVertexes();
        if (vertices != null && !vertices.isEmpty()) {
            for (Vertex v : vertices) {
                vertexDao.save(v);
            }
        }
        List<Edge> edges = handler.readEdges();
        if (edges != null && !edges.isEmpty()) {
            for (Edge e : edges) {
                edgeDao.save(e);
            }
        }
        List<Traffic> traffic = handler.readTraffics();
        if (edges != null && !edges.isEmpty()) {
            for (Traffic t : traffic) {
                trafficDao.save(t);
            }
        } 
    }

    public Graph findGraph() {
    	log.debug("finding a graph");
        List<Vertex> vertices = vertexDao.findAllVertices();
        List<Edge> edges = edgeDao.selectAll();
        List<Traffic> traffics = trafficDao.findAllTraffic();

        Graph graph = new Graph(vertices, edges, traffics);

        return graph;
    }

    public Vertex saveVertex(Vertex vertex) {
        vertexDao.save(vertex);
        return vertex;
    }

    public Vertex updateVertex(Vertex vertex) {
    	log.debug("saving a vertex");
        vertexDao.update(vertex);
        return vertex;
    }

    public boolean deleteVertex(String vertexId) {
    	log.debug("deleting a vertex");
        vertexDao.delete(vertexId);
        return true;
    }

    public List<Vertex> getAllVertices() {
    	log.debug("retrieving all vertices");
        return vertexDao.findAllVertices();
    }

    public Vertex getVertexByName(String name) {
    	log.debug("getting a vertex by name");
        return vertexDao.findUniqueVertexByName(name);
    }

    public Vertex getVertexById(String vertexId) {
    	log.debug("getting a vertex by Vertex ID");
        return vertexDao.findUniqueVertex(vertexId);
    }

    public boolean vertexExist(String vertexId) {
    	log.debug("checking if vertex exists");
        Vertex vertex = vertexDao.findUniqueVertex(vertexId);
        return vertex != null;
    }

    public Edge saveEdge(Edge edge) {
    	log.debug("saving an edge");
        edgeDao.save(edge);
        return edge;
    }

    public Edge updateEdge(Edge edge) {
    	log.debug("updating an edge");
        edgeDao.update(edge);
        return edge;
    }

    public boolean deleteEdge(long recordId) {
    	log.debug("deleting an edge by record Id");
        edgeDao.delete(recordId);
        return true;
    }

    public List<Edge> getAllEdges() {
    	log.debug("retrieving all edges");
        return edgeDao.selectAll();
    }

    public Edge getEdgeById(long recordId) {
    	log.debug("retrieving an edge by ID");
        return edgeDao.selectUnique(recordId);
    }

    public long getEdgeMaxRecordId() {
    	log.debug("retrieving the maximum edge record ID");
        return edgeDao.selectMaxRecordId();
    }

    public boolean edgeExists(Edge edge) {
    	log.debug("checking if edge exists");
        List<Edge> edges = edgeDao.edgeExists(edge);
        return !edges.isEmpty();
    }

    public Traffic saveTraffic(Traffic traffic) {
    	log.debug("saving this traffic item");
        trafficDao.save(traffic);
        return traffic;
    }

    public Traffic updateTraffic(Traffic traffic) {
    	log.debug("updating traffic item");
        trafficDao.update(traffic);
        return traffic;
    }

    public boolean deleteTraffic(String routeId) {
    	log.debug("deleting traffic item");
        trafficDao.delete(routeId);
        return true;
    }

    public List<Traffic> getAllTraffics() {
    	log.debug("retrieving all traffic items");
        return trafficDao.findAllTraffic();
    }

    public Traffic getTrafficById(String routeId) {
    	log.debug("retrieving traffic item using route ID");
        return trafficDao.findUniqueTraffic(routeId);
    }

    public long getTrafficMaxRecordId() {
    	log.debug("retrieving the maximum traffic record ID");
        return trafficDao.findMaxTrafficRecordId();
    }

    public boolean trafficExists(Traffic traffic) {
    	log.debug("check if traffic item already exists");
        List<Traffic> trafficList = trafficDao.trafficExists(traffic);
        return !trafficList.isEmpty();
    }

}
