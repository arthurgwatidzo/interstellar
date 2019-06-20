package za.co.earth.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static com.shazam.shazamcrest.MatcherAssert.assertThat;
import static com.shazam.shazamcrest.matcher.Matchers.sameBeanAs;
import static org.junit.Assert.assertEquals;

import za.co.interstellar.impl.dao.EdgeDaoImpl;
import za.co.interstellar.persistence.Edge;
//import za.co.stars.configuration.DataSourceComponent;
import za.co.stars.configuration.PersistenceComponent;
/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Edge.class, EdgeDaoImpl.class, PersistenceComponent.class},loader = AnnotationConfigContextLoader.class)
public class EdgeDaoImplTest {
	
	
	@Autowired
    private SessionFactory sessionFactory;
    private EdgeDaoImpl edgeDao;

    @Before
    public void setUp() throws Exception {
        edgeDao = new EdgeDaoImpl(sessionFactory);
    }

    @Test
    public void verifySaveEdgeIsCorrect() throws Exception {
        
        Session session = sessionFactory.getCurrentSession();
        Edge edge = new Edge(1, "2", "SAVE A", "SAVE B", 2f);
        Edge expectedEdge = new Edge(1, "2", "SAVE A", "SAVE B", 2f);

       
        edgeDao.save(edge);
        Edge persistedEdge = edgeDao.selectUnique(expectedEdge.getRecordId());

       
        assertThat(persistedEdge, sameBeanAs(expectedEdge));
        assertEquals("SAVE A", edge.getSource());
       
        session.getTransaction().rollback();
    }

    @Test
    public void verifyUpdateEdgeIsCorrect() throws Exception {
       
        Session session = sessionFactory.getCurrentSession();
        Edge edge = new Edge(1, "2", "UPDATE A", "UPDATE B", 20f);
        session.save(edge);

        Edge expectedEdge = new Edge(1, "2", "UPDATED A", "UPDATED B", 20f);
        List<Edge> expectedEdges = new ArrayList<>();
        expectedEdges.add(expectedEdge);

        
        edgeDao.update(expectedEdge);
        List<Edge> persistedEdges = edgeDao.selectAllByRecordId(expectedEdge.getRecordId());

        
        assertThat(persistedEdges, sameBeanAs(expectedEdges));

        
        session.getTransaction().rollback();
    }

    

    @Test
    public void verifySelectUniqueEdgeIsCorrect() {
        
        Session session = sessionFactory.getCurrentSession();
        Edge edge = new Edge(8, "5", "UNIQUE A", "UNIQUE B", 0.5f);
        Edge expectedEdge = new Edge(9, "7", "UNIQUE C", "UNIQUE D", 0.7f);
        session.save(edge);
        session.save(expectedEdge);

       
        Edge persistedEdge = edgeDao.selectUnique(expectedEdge.getRecordId());

       
        assertThat(persistedEdge, sameBeanAs(expectedEdge));
       
        session.getTransaction().rollback();
    }

    @Ignore
    @Test
    public void verifySelectAllEdgesByIdIsCorrect() {
        
        Session session = sessionFactory.getCurrentSession();
        Edge e1 = new Edge(2, "30", "EDGE K", "EDGE F", 0.17f);
        Edge e2 = new Edge(3, "30", "EDGE C", "EDGE D", 0.19f);
        session.save(e1);
        session.save(e2);
        List<Edge> expectedEdges = new ArrayList<>();
        expectedEdges.add(e1);
        expectedEdges.add(e2);

        
        List<Edge> persistedEdge = edgeDao.selectAllByEdgeId(e1.getEdgeId());

       //TODO: Finish up assertion that persisted Edges give the same bean as the Edges built in this verifySelectAllEdgesByIdIsCorrect method
    }

    @Test
    public void verifySelectAllEdgesIsCorrect() {
        
        Session session = sessionFactory.getCurrentSession();
        Edge e1 = new Edge(2, "30", "ALL K", "ALL F", 0.17f);
        Edge e2 = new Edge(3, "19", "ALL C", "ALL D", 0.19f);
        session.save(e1);
        session.save(e2);
        List<Edge> expectedEdges = new ArrayList<>();
        expectedEdges.add(e1);
        expectedEdges.add(e2);

       
        List<Edge> persistedEdge = edgeDao.selectAll();

        assertThat(persistedEdge, sameBeanAs(expectedEdges));
        
        session.getTransaction().rollback();
    }

    @Test
    public void verifySelectEdgeMaxRecordIsCorrect() {
        
        Session session = sessionFactory.getCurrentSession();
        Edge e1 = new Edge(1, "30", "ALL K", "ALL F", 0.17f);
        Edge e2 = new Edge(2, "19", "ALL C", "ALL D", 0.19f);
        session.save(e1);
        session.save(e2);
        long expectedMax = 2;

        
        long returnMax = edgeDao.selectMaxRecordId();

        
        assertThat(returnMax, sameBeanAs(expectedMax));
       
        session.getTransaction().rollback();
    }

    @Test
    public void verifyEdgeExistsSelectionIsCorrect() {
       
        Session session = sessionFactory.getCurrentSession();
        Edge e1 = new Edge(1, "1", "A", "B", 0.17f);
        Edge e2 = new Edge(2, "2", "A", "C", 0.19f);
        session.save(e1);
        session.save(e2);

        Edge edgeToCommit = new Edge(3, "3", "A", "C", 3.0f);
        List<Edge> expectedEdges = new ArrayList<>();
        expectedEdges.add(e2);

        
        List<Edge> returnedEdges = edgeDao.edgeExists(edgeToCommit);
        assertThat(returnedEdges, sameBeanAs(expectedEdges));
        session.getTransaction().rollback();
    }


}
