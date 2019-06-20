package za.co.interstellar.impl.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import za.co.insterstellar.model.ShortestDistanceModel;
import za.co.interstellar.constants.ApplicationConstants;
import za.co.interstellar.constants.BusinessCodes;
import za.co.interstellar.persistence.Edge;
import za.co.interstellar.persistence.Traffic;
import za.co.interstellar.persistence.Vertex;
import za.co.interstellar.service.EarthEntityManagerService;
import za.co.interstellar.service.ShortestDistanceService;
import za.co.interstellar.utilities.Graph;



/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Controller
public class InterstellarAPIControllerImpl {
	
	
	 	
	    private ShortestDistanceService shortestDistanceService;
	    private EarthEntityManagerService earthEntityManagerService;
	    
	    
	    @Autowired
	    public InterstellarAPIControllerImpl(EarthEntityManagerService earthEntityManagerService, ShortestDistanceService shortestDistanceService) {
	        this.shortestDistanceService = shortestDistanceService;
	        this.earthEntityManagerService = earthEntityManagerService;
	    }

	   
	    @RequestMapping(value = "/vertices", method = RequestMethod.GET)
	    public String listVertices(Model model) {
	        List allVertices = earthEntityManagerService.getAllVertices();
	        model.addAttribute("vertices", allVertices);
	        return "vertices";
	    }

	    @RequestMapping("vertex/{vertexId}")
	    public String showVertex(@PathVariable String vertexId, Model model) {
	        model.addAttribute("vertex", earthEntityManagerService.getVertexById(vertexId));
	        return "vertexshow";
	    }

	    @RequestMapping("vertex/new")
	    public String addVertex(Model model) {
	        model.addAttribute("vertex", new Vertex());
	        return "vertexadd";
	    }

	    @RequestMapping(value = "vertex", method = RequestMethod.POST)
	    public String saveVertex(Vertex vertex, Model model) {
	        if (earthEntityManagerService.vertexExist(vertex.getVertexId())) {
	            buildVertexValidation(vertex.getVertexId(), model);
	            return "validation";
	        }
	        earthEntityManagerService.saveVertex(vertex);
	        return "redirect:/vertex/" + vertex.getVertexId();
	    }

	    @RequestMapping("vertex/edit/{vertexId}")
	    public String editVertex(@PathVariable String vertexId, Model model) {
	        model.addAttribute("vertex", earthEntityManagerService.getVertexById(vertexId));
	        return "vertexupdate";
	    }

	    @RequestMapping(value = "vertexupdate", method = RequestMethod.POST)
	    public String updateVertex(Vertex vertex) {
	    	earthEntityManagerService.updateVertex(vertex);
	        return "redirect:/vertex/" + vertex.getVertexId();
	    }

	    @RequestMapping("vertex/delete/{vertexId}")
	    public String deleteVertex(@PathVariable String vertexId) {
	    	earthEntityManagerService.deleteVertex(vertexId);
	        return "redirect:/vertices";
	    }

	    public void buildVertexValidation(String vertexId, Model model) {
	        String vertexName = earthEntityManagerService.getVertexById(vertexId) == null ? "" : earthEntityManagerService.getVertexById(vertexId).getName();
	        String message = "Planet " + vertexId + " already exists as " + vertexName;
	        model.addAttribute("validationMessage", message);
	    }

	   

	    @RequestMapping(value = "/edges", method = RequestMethod.GET)
	    public String listEdges(Model model) {
	        List allEdges = earthEntityManagerService.getAllEdges();
	        model.addAttribute("edges", allEdges);
	        return "edges";
	    }

	    @RequestMapping("edge/{recordId}")
	    public String showEdge(@PathVariable long recordId, Model model) {
	        model.addAttribute("edge", earthEntityManagerService.getEdgeById(recordId));
	        return "edgeshow";
	    }

	    @RequestMapping("edge/delete/{recordId}")
	    public String deleteEdge(@PathVariable long recordId) {
	    	earthEntityManagerService.deleteEdge(recordId);
	        return "redirect:/edges";
	    }

	    @RequestMapping(value = "edge/new", method = RequestMethod.GET)
	    public String addEdge(Model model) {
	        ShortestDistanceModel sh = new ShortestDistanceModel();
	        List allVertices = earthEntityManagerService.getAllVertices();
	        model.addAttribute("edge", new Edge());
	        model.addAttribute("edgeModel", sh);
	        model.addAttribute("routeList", allVertices);
	        return "edgeadd";
	    }

	    @RequestMapping(value = "edge", method = RequestMethod.POST)
	    public String saveEdge(Edge edge, @ModelAttribute ShortestDistanceModel pathModel, Model model) {
	        int id = (int) earthEntityManagerService.getEdgeMaxRecordId() + 1;
	        edge.setRecordId(id);
	        edge.setEdgeId(String.valueOf(id));
	        edge.setSource(pathModel.getSourceVertex());
	        edge.setDestination(pathModel.getDestinationVertex());
	        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
	            buildEdgeValidation(pathModel, model, BusinessCodes.INTERSTELLAR_ROUTE_TO_SELF.toString());
	            return "validation";
	        }
	        if (earthEntityManagerService.edgeExists(edge)) {
	            buildEdgeValidation(pathModel, model, BusinessCodes.INTERSTELLAR_ROUTE_EXISTS.toString());
	            return "validation";
	        }
	        earthEntityManagerService.saveEdge(edge);
	        return "redirect:/edge/" + edge.getRecordId();
	    }

	    @RequestMapping(value = "edge/edit/{recordId}", method = RequestMethod.GET)
	    public String editEdge(@PathVariable long recordId, Model model) {
	        ShortestDistanceModel pathModel = new ShortestDistanceModel();
	        List allVertices = earthEntityManagerService.getAllVertices();
	        Edge edgeToEdit = earthEntityManagerService.getEdgeById(recordId);
	        pathModel.setSourceVertex(edgeToEdit.getSource());
	        pathModel.setDestinationVertex(edgeToEdit.getDestination());
	        model.addAttribute("edge", edgeToEdit);
	        model.addAttribute("edgeModel", pathModel);
	        model.addAttribute("routeList", allVertices);
	        return "edgeupdate";
	    }

	    @RequestMapping(value = "edgeupdate", method = RequestMethod.POST)
	    public String updateEdge(Edge edge, @ModelAttribute ShortestDistanceModel pathModel, Model model) {
	        edge.setSource(pathModel.getSourceVertex());
	        edge.setDestination(pathModel.getDestinationVertex());
	        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
	            buildEdgeValidation(pathModel, model, BusinessCodes.INTERSTELLAR_ROUTE_TO_SELF.toString());
	            return "validation";
	        }

	        if (earthEntityManagerService.edgeExists(edge)) {
	            buildEdgeValidation(pathModel, model, BusinessCodes.INTERSTELLAR_ROUTE_EXISTS.toString());
	            return "validation";
	        }
	        earthEntityManagerService.updateEdge(edge);
	        return "redirect:/edge/" + edge.getRecordId();
	    }

	    public void buildEdgeValidation(@ModelAttribute ShortestDistanceModel pathModel, Model model, String code) {
	        String message = "";
	        BusinessCodes mode = BusinessCodes.fromString(code);
	        if (mode != null) {
	            switch (mode) {
	                case INTERSTELLAR_ROUTE_EXISTS:
	                    String sourceName = earthEntityManagerService.getVertexById(pathModel.getSourceVertex()) == null ? "" : earthEntityManagerService.getVertexById(pathModel.getSourceVertex()).getName();
	                    String sourceDestination = earthEntityManagerService.getVertexById(pathModel.getDestinationVertex()) == null ? "" : earthEntityManagerService.getVertexById(pathModel.getDestinationVertex()).getName();
	                    message = "The route from " + sourceName + " (" + pathModel.getSourceVertex() + ") to " + sourceDestination + "(" + pathModel.getDestinationVertex() + ") exists already.";
	                    break;
	                case INTERSTELLAR_ROUTE_TO_SELF:
	                    message = ApplicationConstants.DUPLICATE_ROUTE;
	                    break;
	                default:
	                    message = ApplicationConstants.INVALID_CODE;
	                    break;
	            }
	        }
	        //
	        model.addAttribute("validationMessage", message);
	    }

	 

	    @RequestMapping(value = "/traffics", method = RequestMethod.GET)
	    public String listTraffics(Model model) {
	        List<Traffic> allTraffics = earthEntityManagerService.getAllTraffics();
	        model.addAttribute("traffics", allTraffics);
	        return "traffics";
	    }

	    @RequestMapping("traffic/{routeId}")
	    public String showTraffic(@PathVariable String routeId, Model model) {
	        model.addAttribute("traffic", earthEntityManagerService.getTrafficById(routeId));
	        return "trafficshow";
	    }

	    @RequestMapping("traffic/delete/{routeId}")
	    public String deleteTraffic(@PathVariable String routeId) {
	    	earthEntityManagerService.deleteTraffic(routeId);
	        return "redirect:/traffics";
	    }

	    @RequestMapping(value = "traffic/new", method = RequestMethod.GET)
	    public String addTraffic(Model model) {
	        ShortestDistanceModel sh = new ShortestDistanceModel();
	        List allVertices = earthEntityManagerService.getAllVertices();
	        model.addAttribute("traffic", new Traffic());
	        model.addAttribute("trafficModel", sh);
	        model.addAttribute("trafficList", allVertices);
	        return "trafficadd";
	    }

	    @RequestMapping(value = "traffic", method = RequestMethod.POST)
	    public String saveTraffic(Traffic traffic, @ModelAttribute ShortestDistanceModel pathModel, Model model) {
	        int id = (int) earthEntityManagerService.getTrafficMaxRecordId() + 1;
	        traffic.setRouteId(String.valueOf(id));
	        traffic.setSource(pathModel.getSourceVertex());
	        traffic.setDestination(pathModel.getDestinationVertex());
	        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
	            buildTrafficValidation(pathModel, model, BusinessCodes.INTERSTELLAR_TRAFFIC_TO_SELF.toString());
	            return "validation";
	        }
	        if (earthEntityManagerService.trafficExists(traffic)) {
	            buildTrafficValidation(pathModel, model, BusinessCodes.INTERSTELLAR_TRAFFIC_EXISTS.toString());
	            return "validation";
	        }
	        earthEntityManagerService.saveTraffic(traffic);
	        return "redirect:/traffic/" + traffic.getRouteId();
	    }

	    @RequestMapping(value = "traffic/edit/{routeId}", method = RequestMethod.GET)
	    public String editTraffic(@PathVariable String routeId, Model model) {
	        ShortestDistanceModel pathModel = new ShortestDistanceModel();
	        List allVertices = earthEntityManagerService.getAllVertices();
	        Traffic trafficToEdit = earthEntityManagerService.getTrafficById(routeId);
	        pathModel.setSourceVertex(trafficToEdit.getSource());
	        pathModel.setDestinationVertex(trafficToEdit.getDestination());
	        model.addAttribute("traffic", trafficToEdit);
	        model.addAttribute("trafficModel", pathModel);
	        model.addAttribute("trafficList", allVertices);
	        return "trafficupdate";
	    }

	    @RequestMapping(value = "trafficupdate", method = RequestMethod.POST)
	    public String updateTraffic(Traffic traffic, @ModelAttribute ShortestDistanceModel pathModel, Model model) {
	        traffic.setSource(pathModel.getSourceVertex());
	        traffic.setDestination(pathModel.getDestinationVertex());
	        if (pathModel.getSourceVertex().equals(pathModel.getDestinationVertex())) {
	            buildTrafficValidation(pathModel, model, BusinessCodes.INTERSTELLAR_TRAFFIC_TO_SELF.toString());
	            return "validation";
	        }
	        if (earthEntityManagerService.trafficExists(traffic)) {
	            buildTrafficValidation(pathModel, model, BusinessCodes.INTERSTELLAR_TRAFFIC_EXISTS.toString());
	            return "validation";
	        }
	        earthEntityManagerService.updateTraffic(traffic);
	        return "redirect:/traffic/" + traffic.getRouteId();
	    }

	    public void buildTrafficValidation(@ModelAttribute ShortestDistanceModel pathModel, Model model, String code) {
	        String message = "";
	        BusinessCodes mode = BusinessCodes.fromString(code);
	        if (mode != null) {
	            switch (mode) {
	                case INTERSTELLAR_TRAFFIC_EXISTS:
	                    String sourceName = earthEntityManagerService.getVertexById(pathModel.getSourceVertex()) == null ? "" : earthEntityManagerService.getVertexById(pathModel.getSourceVertex()).getName();
	                    String sourceDestination = earthEntityManagerService.getVertexById(pathModel.getDestinationVertex()) == null ? "" : earthEntityManagerService.getVertexById(pathModel.getDestinationVertex()).getName();
	                    message = "The traffic from " + sourceName + " (" + pathModel.getSourceVertex() + ") to " + sourceDestination + " (" + pathModel.getDestinationVertex() + ") exists already.";
	                    break;
	                case INTERSTELLAR_TRAFFIC_TO_SELF:
	                    message = ApplicationConstants.DUPLICATE_TRAFFIC;
	                    break;
	                default:
	                    message = ApplicationConstants.INVALID_CODE;
	                    break;
	            }
	        }
	        //
	        model.addAttribute("validationMessage", message);
	    }
	  
	    
	    

	    @RequestMapping(value = "/shortest", method = RequestMethod.GET)
	    public String shortestForm(Model model) {
	        ShortestDistanceModel pathModel = new ShortestDistanceModel();
	        List<Vertex> allVertices = earthEntityManagerService.getAllVertices();
	        if (allVertices == null || allVertices.isEmpty()) {
	            model.addAttribute("validationMessage", ApplicationConstants.NO_PLANET_FOUND);
	            return "validation";
	        }
	        Vertex origin = allVertices.get(0);
	        pathModel.setVertexName(origin.getName());
	        model.addAttribute("shortest", pathModel);
	        model.addAttribute("pathList", allVertices);
	        return "shortest";
	    }

	    @RequestMapping(value = "/shortest", method = RequestMethod.POST)
	    public String shortestSubmit(@ModelAttribute ShortestDistanceModel pathModel, Model model) {

	        StringBuilder path = new StringBuilder();
	        Graph graph = earthEntityManagerService.findGraph();
	        if (pathModel.isTrafficAllowed()) {
	            graph.setTrafficAllowed(true);
	        }
	        if (pathModel.isUndirectedGraph()) {
	            graph.setUndirectedGraph(true);
	        }
	        shortestDistanceService.initializePlanets(graph);
	        Vertex source = earthEntityManagerService.getVertexByName(pathModel.getVertexName());
	        Vertex destination = earthEntityManagerService.getVertexById(pathModel.getSelectedVertex());
	      
	        shortestDistanceService.run(source);
	        LinkedList<Vertex> paths = shortestDistanceService.getPath(destination);
	        if (paths != null) {
	            for (Vertex v : paths) {
	                path.append(v.getName() + " (" + v.getVertexId() + ")");
	                path.append("\t");
	            }
	        } else if (source != null && destination != null && source.getVertexId().equals(destination.getVertexId())) {
	            path.append(ApplicationConstants.PATH_NOT_NEEDED + source.getName());
	        } else {
	            path.append(ApplicationConstants.PATH_NOT_AVAILABLE);
	        }
	        pathModel.setThePath(path.toString());
	        pathModel.setSelectedVertexName(destination.getName());
	        model.addAttribute("shortest", pathModel);
	        return "result";
	    }

	    

}
