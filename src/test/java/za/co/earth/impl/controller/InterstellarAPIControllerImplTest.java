package za.co.earth.impl.controller;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import za.co.insterstellar.model.ShortestDistanceModel;
import za.co.interstellar.impl.controller.InterstellarAPIControllerImpl;
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
public class InterstellarAPIControllerImplTest {
	
	@Mock
    View mockView;
    @InjectMocks
    private InterstellarAPIControllerImpl controller;
    @Mock
    private EarthEntityManagerService earthEntityManagerService;
    @Mock
    private ShortestDistanceService shortestDistanceService;
    private List<Vertex> verticesList;
    private List<Edge> edgesList;
    private List<Traffic> trafficList;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        Vertex vertex1 = new Vertex("A", "Pluto");
        Vertex vertex2 = new Vertex("B", "Moon");
        Vertex vertex3 = new Vertex("C", "Mercury");
        Vertex vertex4 = new Vertex("D", "Sun");
        Vertex vertex5 = new Vertex("E", "Mars");

        verticesList = new ArrayList<>();
        verticesList.add(vertex1);
        verticesList.add(vertex2);
        verticesList.add(vertex3);
        verticesList.add(vertex4);
        verticesList.add(vertex5);

        Edge edge1 = new Edge(1, "1", "A", "B", 0.44f);
        Edge edge2 = new Edge(2, "2", "A", "C", 1.89f);
        Edge edge3 = new Edge(3, "3", "A", "D", 0.10f);
        Edge edge4 = new Edge(4, "4", "B", "H", 2.44f);
        Edge edge5 = new Edge(5, "5", "B", "E", 3.45f);

        edgesList = new ArrayList<>();
        edgesList.add(edge1);
        edgesList.add(edge2);
        edgesList.add(edge3);
        edgesList.add(edge4);
        edgesList.add(edge5);

        Traffic traffic1 = new Traffic("1", "A", "B", 0.30f);
        Traffic traffic2 = new Traffic("2", "A", "C", 0.90f);
        Traffic traffic3 = new Traffic("3", "A", "D", 0.10f);
        Traffic traffic4 = new Traffic("4", "B", "H", 0.20f);
        Traffic traffic5 = new Traffic("5", "B", "E", 1.30f);

        trafficList = new ArrayList<>();
        trafficList.add(traffic1);
        trafficList.add(traffic2);
        trafficList.add(traffic3);
        trafficList.add(traffic4);
        trafficList.add(traffic5);
        MockitoAnnotations.initMocks(this);
        mockMvc = standaloneSetup(controller)
                .setSingleView(mockView)
                .build();

    }

    @Test
    public void verifyThatListVerticesViewAndModelIsCorrect() throws Exception {
       
        when(earthEntityManagerService.getAllVertices()).thenReturn(verticesList);
        setUpFixture();
        
        mockMvc.perform(get("/vertices"))
                .andExpect(model().attribute("vertices", sameBeanAs(verticesList)))
                .andExpect(view().name("vertices"));
    }

    @Test
    public void verifyThatShowVertexViewAndModelIsCorrect() throws Exception {
       
        Vertex expectedVertex = new Vertex("A", "Pluto");
        when(earthEntityManagerService.getVertexById("vertexId")).thenReturn(expectedVertex);
     
        mockMvc.perform(get("/vertex/vertexId"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vertex", sameBeanAs(expectedVertex)))
                .andExpect(view().name("vertexshow"));
    }

    @Test
    public void verifyThatAddVertexViewAndModelIsCorrect() throws Exception {
        
        Vertex expectedVertex = new Vertex();
        mockMvc.perform(get("/vertex/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vertex", sameBeanAs(expectedVertex)))
                .andExpect(view().name("vertexadd"));
    }

    @Test
    public void verifyThatSaveVertexViewIsCorrect() throws Exception {
       
        Vertex expectedVertex = new Vertex("A", "Pluto");
        when(earthEntityManagerService.vertexExist("A")).thenReturn(false);
        when(earthEntityManagerService.saveVertex(expectedVertex)).thenReturn(expectedVertex);

       
        mockMvc.perform(post("/vertex").param("vertexId", "A").param("name", "Pluto"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/vertex/" + expectedVertex.getVertexId()));

        
        ArgumentCaptor<Vertex> formObjectArgument = ArgumentCaptor.forClass(Vertex.class);
        verify(earthEntityManagerService, times(1)).saveVertex(formObjectArgument.capture());

        Vertex formObject = formObjectArgument.getValue();
        assertThat(formObjectArgument.getValue(), is(sameBeanAs(expectedVertex)));

        assertThat(formObject.getVertexId(), is("A"));
        assertThat(formObject.getName(), is("Pluto"));
    }

    @Test
    public void verifyThatSaveExistingVertexViewAndModelIsCorrect() throws Exception {
       
        Vertex expectedVertex = new Vertex("A", "Pluto");
        when(earthEntityManagerService.vertexExist("A")).thenReturn(true);
        when(earthEntityManagerService.getVertexById("A")).thenReturn(expectedVertex);
        String message = "Planet A already exists as Pluto";
        
        mockMvc.perform(post("/vertex").param("vertexId", "A").param("name", "Pluto"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    @Test
    public void verifyThatEditVertexViewAndModelIsCorrect() throws Exception {
        
        Vertex expectedVertex = new Vertex("A", "Pluto");
        when(earthEntityManagerService.getVertexById("vertexId")).thenReturn(expectedVertex);
        mockMvc.perform(get("/vertex/edit/vertexId"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("vertex", sameBeanAs(expectedVertex)))
                .andExpect(view().name("vertexupdate"));
    }

    @Test
    public void verifyThatUpdateVertexViewIsCorrect() throws Exception {
        
        Vertex expectedVertex = new Vertex("A", "Pluto");
        when(earthEntityManagerService.updateVertex(expectedVertex)).thenReturn(expectedVertex);
        mockMvc.perform(post("/vertexupdate").param("vertexId", "A").param("name", "Pluto"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/vertex/" + expectedVertex.getVertexId()));
    }

    @Test
    public void verifyThatDeleteVertexViewIsCorrect() throws Exception {
        
        when(earthEntityManagerService.deleteVertex("vertexId")).thenReturn(true);
       
        mockMvc.perform(post("/vertex/delete/A"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/vertices"));
    }

    @Test
    public void verifyThatListEdgesViewAndModelIsCorrect() throws Exception {
        
        when(earthEntityManagerService.getAllEdges()).thenReturn(edgesList);
        setUpFixture();
        
        mockMvc.perform(get("/edges"))
                .andExpect(model().attribute("edges", sameBeanAs(edgesList)))
                .andExpect(view().name("edges"));
    }

    @Test
    public void verifyThatShowEdgeViewAndModelIsCorrect() throws Exception {
        
        Edge expectedEdge = new Edge(2, "2", "A", "C", 1.89f);
        long recordId = 2;
        when(earthEntityManagerService.getEdgeById(recordId)).thenReturn(expectedEdge);
        mockMvc.perform(get("/edge/" + recordId))
                .andExpect(status().isOk())
                .andExpect(model().attribute("edge", sameBeanAs(expectedEdge)))
                .andExpect(view().name("edgeshow"));
    }

    @Test
    public void verifyThatDeleteEdgeViewIsCorrect() throws Exception {
        
        long recordId = 2;
        when(earthEntityManagerService.deleteEdge(recordId)).thenReturn(true);
        
        mockMvc.perform(post("/edge/delete/" + recordId))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/edges"));
    }

    @Test
    public void verifyThatAddEdgeViewAndModelIsCorrect() throws Exception {
        Edge expectedEdge = new Edge();
        ShortestDistanceModel shortestDistanceModel = new ShortestDistanceModel();
        when(earthEntityManagerService.getAllVertices()).thenReturn(verticesList);
        mockMvc.perform(get("/edge/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("edge", sameBeanAs(expectedEdge)))
                .andExpect(model().attribute("edgeModel", sameBeanAs(shortestDistanceModel)))
                .andExpect(model().attribute("routeList", sameBeanAs(verticesList)))
                .andExpect(view().name("edgeadd"));
    }

    @Test
    public void verifyThatSaveEdgeViewAndModelIsCorrect() throws Exception {
        
        Edge expectedEdge = new Edge(2, "2", "A", "C", 1.89f);
        long max = 1;
        when(earthEntityManagerService.edgeExists(expectedEdge)).thenReturn(false);
        when(earthEntityManagerService.getEdgeMaxRecordId()).thenReturn(max);
        when(earthEntityManagerService.saveEdge(expectedEdge)).thenReturn(expectedEdge);
        
        mockMvc.perform(post("/edge").param("recordId", "" + max).param("distance", "1.0").param("sourceVertex", "A").param("destinationVertex", "C"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/edge/" + expectedEdge.getRecordId()));
    }

    @Test
    public void verifyThatSaveSameEdgeViewAndModelIsCorrect() throws Exception {
       
        long max = 1;
        when(earthEntityManagerService.getEdgeMaxRecordId()).thenReturn(max);
        String message = "You cannot link a route to itself.";
       
        mockMvc.perform(post("/edge").param("recordId", "" + max).param("distance", "1.0").param("sourceVertex", "A").param("destinationVertex", "A"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    @Test
    public void verifyThatSaveExistingEdgeViewAndModelIsCorrect() throws Exception {
      
        Edge expectedEdge = new Edge(2, "2", "A", "C", 1.89f);
        Vertex source = new Vertex("A", "Pluto");
        long recordId = 1;
        when(earthEntityManagerService.getEdgeMaxRecordId()).thenReturn(recordId);
        when(earthEntityManagerService.edgeExists(any(Edge.class))).thenReturn(true);
        when(earthEntityManagerService.getVertexById("A")).thenReturn(source);
        String message = "The route from Pluto (A) to (C) exists already.";
      
        mockMvc.perform(post("/edge").param("recordId", "" + recordId).param("edgeId", "2").param("sourceVertex", "A").param("destinationVertex", "C").param("distance", "1.89"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("edge", sameBeanAs(expectedEdge)))
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    @Test
    public void verifyThatEditEdgeViewAndModelIsCorrect() throws Exception {
       
        Edge expectedEdge = new Edge(1, "1", "A", "B", 0.44f);
        ShortestDistanceModel shortestDistanceModel = new ShortestDistanceModel();
        when(earthEntityManagerService.getAllVertices()).thenReturn(verticesList);
        when(earthEntityManagerService.getEdgeById(expectedEdge.getRecordId())).thenReturn(expectedEdge);
        shortestDistanceModel.setSourceVertex(expectedEdge.getSource());
        shortestDistanceModel.setDestinationVertex(expectedEdge.getDestination());
        
        mockMvc.perform(get("/edge/edit/" + expectedEdge.getRecordId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("edge", sameBeanAs(expectedEdge)))
                .andExpect(model().attribute("edgeModel", sameBeanAs(shortestDistanceModel)))
                .andExpect(model().attribute("routeList", sameBeanAs(verticesList)))
                .andExpect(view().name("edgeupdate"));
    }

    @Test
    public void verifyThatUpdateEdgeViewAndModelIsCorrect() throws Exception {
        //Set
        Edge expectedEdge = new Edge(2, "2", "A", "B", 1.89f);
        long recordId = 2;
        when(earthEntityManagerService.edgeExists(expectedEdge)).thenReturn(false);
        when(earthEntityManagerService.updateEdge(expectedEdge)).thenReturn(expectedEdge);

        //Test
        mockMvc.perform(post("/edgeupdate").param("recordId", "" + recordId).param("edgeId", "2").param("sourceVertex", "A").param("destinationVertex", "B").param("distance", "1.89"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("edge", sameBeanAs(expectedEdge)))
                .andExpect(view().name("redirect:/edge/" + expectedEdge.getRecordId()));
    }

    @Test
    public void verifyThatUpdateSameEdgeViewAndModelIsCorrect() throws Exception {
        //Set
        long recordId = 1;
        String message = "You cannot link a route to itself.";
        //Verify
        mockMvc.perform(post("/edgeupdate").param("recordId", "" + recordId).param("edgeId", "2").param("sourceVertex", "A").param("destinationVertex", "A").param("distance", "1.89"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    @Test
    public void verifyThatUpdateExistingEdgeViewAndModelIsCorrect() throws Exception {
        //Set
        Edge expectedEdge = new Edge(2, "2", "A", "B", 1.89f);
        Vertex vertex = new Vertex("A", "Moon");
        long recordId = 2;
        when(earthEntityManagerService.edgeExists(any(Edge.class))).thenReturn(true);
        when(earthEntityManagerService.getVertexById("A")).thenReturn(vertex);
        String message = "The route from Moon (A) to (B) exists already.";
        //Verify
        mockMvc.perform(post("/edgeupdate").param("recordId", "" + recordId).param("edgeId", "2").param("sourceVertex", "A").param("destinationVertex", "B").param("distance", "1.89"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("edge", sameBeanAs(expectedEdge)))
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    //Traffic Tests

    @Test
    public void verifyThatListTrafficsViewAndModelIsCorrect() throws Exception {
        //Set
        when(earthEntityManagerService.getAllTraffics()).thenReturn(trafficList);
        setUpFixture();
        //Verify
        mockMvc.perform(get("/traffics"))
                .andExpect(model().attribute("traffics", sameBeanAs(trafficList)))
                .andExpect(view().name("traffics"));
    }

    @Test
    public void verifyThatShowTrafficViewAndModelIsCorrect() throws Exception {
        //Set
        Traffic expectedTraffic = new Traffic("1", "A", "B", 0.30f);
        when(earthEntityManagerService.getTrafficById("1")).thenReturn(expectedTraffic);
        //Verify
        mockMvc.perform(get("/traffic/1"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("traffic", sameBeanAs(expectedTraffic)))
                .andExpect(view().name("trafficshow"));
    }

    @Test
    public void verifyThatDeleteTrafficViewIsCorrect() throws Exception {
        
        when(earthEntityManagerService.deleteTraffic("1")).thenReturn(true);
        
        mockMvc.perform(post("/traffic/delete/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/traffics"));
    }

    @Test
    public void verifyThatAddTrafficViewAndModelIsCorrect() throws Exception {
      
        Traffic expectedTraffic = new Traffic();
        ShortestDistanceModel shortestDistanceModel = new ShortestDistanceModel();
        when(earthEntityManagerService.getAllVertices()).thenReturn(verticesList);
        mockMvc.perform(get("/traffic/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("traffic", sameBeanAs(expectedTraffic)))
                .andExpect(model().attribute("trafficModel", sameBeanAs(shortestDistanceModel)))
                .andExpect(model().attribute("trafficList", sameBeanAs(verticesList)))
                .andExpect(view().name("trafficadd"));
    }

    @Test
    public void verifyThatSaveTrafficViewAndModelIsCorrect() throws Exception {
        
        Traffic expectedTraffic = new Traffic("2", "A", "B", 1.0f);
        long max = 1;
        when(earthEntityManagerService.trafficExists(expectedTraffic)).thenReturn(false);
        when(earthEntityManagerService.getTrafficMaxRecordId()).thenReturn(max);
        when(earthEntityManagerService.saveTraffic(expectedTraffic)).thenReturn(expectedTraffic);

       
        mockMvc.perform(post("/traffic").param("routeId", "1").param("delay", "1.0").param("sourceVertex", "A").param("destinationVertex", "B"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/traffic/" + expectedTraffic.getRouteId()));
    }

    @Test
    public void verifyThatSaveSameTrafficViewAndModelIsCorrect() throws Exception {
       
        long max = 1;
        when(earthEntityManagerService.getTrafficMaxRecordId()).thenReturn(max);
        String message = "You cannot add traffic on the same route origin and destination.";
       
        mockMvc.perform(post("/traffic").param("routeId", "1").param("delay", "1.0").param("sourceVertex", "A").param("destinationVertex", "A"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    @Test
    public void verifyThatSaveExistingTrafficViewAndModelIsCorrect() throws Exception {
      
        Traffic expectedTraffic = new Traffic("2", "A", "B", 2.0f);
        Vertex source = new Vertex("A", "Pluto");
        long recordId = 1;
        when(earthEntityManagerService.getTrafficMaxRecordId()).thenReturn(recordId);
        when(earthEntityManagerService.trafficExists(any(Traffic.class))).thenReturn(true);
        when(earthEntityManagerService.getVertexById("A")).thenReturn(source);
        String message = "The traffic from Pluto (A) to  (B) exists already.";
        
        mockMvc.perform(post("/traffic").param("routeId", "1").param("delay", "2.0").param("sourceVertex", "A").param("destinationVertex", "B"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("traffic", sameBeanAs(expectedTraffic)))
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    @Test
    public void verifyEditTrafficViewAndModelIsCorrect() throws Exception {
        
        Traffic expectedTraffic = new Traffic("2", "A", "B", 2.0f);
        ShortestDistanceModel shortestDistanceModel = new ShortestDistanceModel();
        when(earthEntityManagerService.getAllVertices()).thenReturn(verticesList);
        when(earthEntityManagerService.getTrafficById(expectedTraffic.getRouteId())).thenReturn(expectedTraffic);
        shortestDistanceModel.setSourceVertex(expectedTraffic.getSource());
        shortestDistanceModel.setDestinationVertex(expectedTraffic.getDestination());
        
        mockMvc.perform(get("/traffic/edit/" + expectedTraffic.getRouteId()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("traffic", sameBeanAs(expectedTraffic)))
                .andExpect(model().attribute("trafficModel", sameBeanAs(shortestDistanceModel)))
                .andExpect(model().attribute("trafficList", sameBeanAs(verticesList)))
                .andExpect(view().name("trafficupdate"));
    }

    @Test
    public void verifyThatUpdateTrafficViewAndModelIsCorrect() throws Exception {
        
        Traffic expectedTraffic = new Traffic("2", "A", "B", 1.0f);
        when(earthEntityManagerService.trafficExists(expectedTraffic)).thenReturn(false);
        when(earthEntityManagerService.updateTraffic(expectedTraffic)).thenReturn(expectedTraffic);

       
        mockMvc.perform(post("/trafficupdate").param("routeId", "2").param("source", "A").param("destination", "C").param("sourceVertex", "A").param("destinationVertex", "B").param("delay", "1.0"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("traffic", sameBeanAs(expectedTraffic)))
                .andExpect(view().name("redirect:/traffic/" + expectedTraffic.getRouteId()));
    }

    @Test
    public void verifyUpdateSameTrafficViewAndModelIsCorrect() throws Exception {
        
        String message = "You cannot add traffic on the same route origin and destination.";
      
        mockMvc.perform(post("/trafficupdate").param("routeId", "2").param("source", "A").param("destination", "C").param("sourceVertex", "A").param("destinationVertex", "A").param("delay", "1.0"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("validationMessage", sameBeanAs(message)))
                .andExpect(view().name("validation"));
    }

    

    @Test
    public void verifyShortestDistanceMvcIsCorrect() throws Exception {
        
        Vertex expectedSource = verticesList.get(0);
        when(earthEntityManagerService.getAllVertices()).thenReturn(verticesList);
        ShortestDistanceModel shortestDistanceModel = new ShortestDistanceModel();
        shortestDistanceModel.setVertexName(expectedSource.getName());
       
        mockMvc.perform(get("/shortest"))
                .andExpect(model().attribute("shortest", sameBeanAs(shortestDistanceModel)))
                .andExpect(model().attribute("pathList", sameBeanAs(verticesList)))
                .andExpect(view().name("shortest"));
    }

    @Test
    public void verifyShortestDistanceResultMvcIsCorrect() throws Exception {
       
        StringBuilder path = new StringBuilder();
        Vertex expectedSource = new Vertex("A", "Pluto");
        Vertex step = new Vertex("B", "Moon");
        Vertex expectedDestination = new Vertex("E", "Mars");
        Graph graph = new Graph(verticesList, edgesList, trafficList);
        LinkedList<Vertex> pathList = new LinkedList<>();
        pathList.add(expectedSource);
        pathList.add(step);
        pathList.add(expectedDestination);
        when(earthEntityManagerService.findGraph()).thenReturn(graph);
        when(earthEntityManagerService.getVertexByName("A")).thenReturn(expectedSource);
        when(earthEntityManagerService.getVertexById("E")).thenReturn(expectedDestination);
        when(shortestDistanceService.getPath(expectedDestination)).thenReturn(pathList);

        path.append("Pluto (A)\tMoon (B)\tMars (E)\t");
        ShortestDistanceModel pathModel = new ShortestDistanceModel();
        pathModel.setThePath(path.toString());
        pathModel.setSelectedVertexName(expectedDestination.getName());
        pathModel.setSelectedVertex("E");
        pathModel.setVertexId("A");
        pathModel.setVertexName("Pluto");

      
        mockMvc.perform(post("/shortest").param("vertexId", "A").param("vertexName", "Pluto").param("selectedVertex", "E").param("trafficAllowed", "false").param("undirectedGraph", "false"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("shortest", sameBeanAs(pathModel)))
                .andExpect(view().name("result"));
    }

    public void setUpFixture() {
        mockMvc = standaloneSetup(
                new InterstellarAPIControllerImpl(earthEntityManagerService, shortestDistanceService)
        )
                .setViewResolvers(buildInternalResourceViewResolver())
                .build();
    }

    private InternalResourceViewResolver buildInternalResourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }

}
