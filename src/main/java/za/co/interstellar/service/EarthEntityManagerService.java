package za.co.interstellar.service;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import za.co.interstellar.dao.EdgeDao;
import za.co.interstellar.dao.TrafficDao;
import za.co.interstellar.dao.VertexDao;
import za.co.interstellar.impl.dao.EdgeDaoImpl;
import za.co.interstellar.impl.dao.TrafficDaoImpl;
import za.co.interstellar.impl.dao.VertexDaoImpl;
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
        URL resource = getClass().getResource(EXCEL_FILENAME);
        File file1;
        try {
            file1 = new File(resource.toURI());
            saveGraph(file1);
        } catch (URISyntaxException e) {
            //TODO: Add proper logging of this exception using log4j
        }
    }

    public void saveGraph(File file) {
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
        List<Vertex> vertices = vertexDao.selectAll();
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
        vertexDao.update(vertex);
        return vertex;
    }

    public boolean deleteVertex(String vertexId) {
        vertexDao.delete(vertexId);
        return true;
    }

    public List<Vertex> getAllVertices() {
        return vertexDao.selectAll();
    }

    public Vertex getVertexByName(String name) {
        return vertexDao.selectUniqueByName(name);
    }

    public Vertex getVertexById(String vertexId) {
        return vertexDao.selectUnique(vertexId);
    }

    public boolean vertexExist(String vertexId) {
        Vertex vertex = vertexDao.selectUnique(vertexId);
        return vertex != null;
    }

    public Edge saveEdge(Edge edge) {
        edgeDao.save(edge);
        return edge;
    }

    public Edge updateEdge(Edge edge) {
        edgeDao.update(edge);
        return edge;
    }

    public boolean deleteEdge(long recordId) {
        edgeDao.delete(recordId);
        return true;
    }

    public List<Edge> getAllEdges() {
        return edgeDao.selectAll();
    }

    public Edge getEdgeById(long recordId) {
        return edgeDao.selectUnique(recordId);
    }

    public long getEdgeMaxRecordId() {
        return edgeDao.selectMaxRecordId();
    }

    public boolean edgeExists(Edge edge) {
        List<Edge> edges = edgeDao.edgeExists(edge);
        return !edges.isEmpty();
    }

    public Traffic saveTraffic(Traffic traffic) {
        trafficDao.save(traffic);
        return traffic;
    }

    public Traffic updateTraffic(Traffic traffic) {
        trafficDao.update(traffic);
        return traffic;
    }

    public boolean deleteTraffic(String routeId) {
        trafficDao.delete(routeId);
        return true;
    }

    public List<Traffic> getAllTraffics() {
        return trafficDao.findAllTraffic();
    }

    public Traffic getTrafficById(String routeId) {
        return trafficDao.findUniqueTraffic(routeId);
    }

    public long getTrafficMaxRecordId() {
        return trafficDao.findMaxTrafficRecordId();
    }

    public boolean trafficExists(Traffic traffic) {
        List<Traffic> trafficList = trafficDao.trafficExists(traffic);
        return !trafficList.isEmpty();
    }

}
