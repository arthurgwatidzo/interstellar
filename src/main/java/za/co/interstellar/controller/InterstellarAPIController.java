package za.co.interstellar.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import za.co.insterstellar.model.ShortestDistanceModel;
import za.co.interstellar.persistence.Edge;
import za.co.interstellar.persistence.Vertex;

/**
 * @Author Arthur Gwatidzo  
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
public interface InterstellarAPIController {

	public String listVertices(Model model);

	public String showVertex(@PathVariable String vertexId, Model model);

	public String addVertex(Model model);

	public String saveVertex(Vertex vertex, Model model);

	public String editVertex(@PathVariable String vertexId, Model model);

	public String updateVertex(Vertex vertex);
	
	 public String deleteVertex(@PathVariable String vertexId);
	 
	 public void buildVertexValidation(String vertexId, Model model);
	 
	 public String listEdges(Model model);
	 
	 public String showEdge(@PathVariable long recordId, Model model);
	 
	 public String deleteEdge(@PathVariable long recordId);
	 
	 public String addEdge(Model model);
	 
	 public String saveEdge(Edge edge, @ModelAttribute ShortestDistanceModel pathModel, Model model);

}
