package za.co.earth.dao;

import org.hibernate.Criteria;
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

import java.util.ArrayList;
import java.util.List;

import za.co.interstellar.configuration.HibernateUtilConfiguration;
import za.co.interstellar.impl.dao.TrafficDaoImpl;
import za.co.interstellar.persistence.Traffic;

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
@ContextConfiguration(classes = {Traffic.class, TrafficDaoImpl.class, HibernateUtilConfiguration.class},loader = AnnotationConfigContextLoader.class)
public class TrafficDaoImplTest {
	
	
	//TODO:	 Unit Tests
	
	
	@Autowired
    private SessionFactory sessionFactory;
    private TrafficDaoImpl trafficDao;

    @Before
    public void setUp() throws Exception {
        trafficDao = new TrafficDaoImpl(sessionFactory);
    }

  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifySaveTrafficIsCorrect() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic = new Traffic("1", "A", "B", 4f);
        List<Traffic> expectedTraffics = new ArrayList<>();
        expectedTraffics.add(traffic);

        trafficDao.save(traffic);
        Criteria criteria = session.createCriteria(Traffic.class);
        List<Traffic> persistedTraffics = (List<Traffic>) criteria.list();

        assertThat(persistedTraffics, sameBeanAs(expectedTraffics));
        session.getTransaction().rollback();
    }

    
  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifyUpdateTrafficIsCorrect() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic = new Traffic("1", "A", "B", 4f);
        session.save(traffic);

        Traffic trafficToUpdate = new Traffic("1", "F", "M", 4f);

        List<Traffic> expectedTraffic = new ArrayList<>();
        expectedTraffic.add(trafficToUpdate);

        trafficDao.update(trafficToUpdate);
        Criteria criteria = session.createCriteria(Traffic.class);
        List<Traffic> persistedTraffics = (List<Traffic>) criteria.list();

        assertThat(persistedTraffics, sameBeanAs(expectedTraffic));

        session.getTransaction().rollback();
    }

  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifyDeleteTrafficIsCorrect() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic1 = new Traffic("1", "A", "B", 4f);
        Traffic traffic2 = new Traffic("2", "G", "V", 2f);
        List<Traffic> expectedTraffics = new ArrayList<>();
        expectedTraffics.add(traffic1);
        session.save(traffic1);
        session.save(traffic2);

        trafficDao.delete(traffic2.getRouteId());
        Criteria criteria = session.createCriteria(Traffic.class);
        List<Traffic> persistedTraffics = (List<Traffic>) criteria.list();

        assertThat(persistedTraffics, sameBeanAs(expectedTraffics));

        session.getTransaction().rollback();
    }

  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifySelectUniqueTrafficIsCorrect() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic = new Traffic("100", "A", "B", 4f);
        Traffic expected = new Traffic("5", "M", "C", 4f);
        session.save(traffic);
        session.save(expected);

        Traffic persisted = trafficDao.findUniqueTraffic(expected.getRouteId());

        assertThat(persisted, sameBeanAs(expected));
        session.getTransaction().rollback();
    }

  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifySelecteAllTrafficsIsCorrect() throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic1 = new Traffic("1", "A", "B", 4f);
        Traffic traffic2 = new Traffic("2", "C", "D", 4f);
        Traffic traffic3 = new Traffic("3", "D", "F", 4f);
        Traffic traffic4 = new Traffic("4", "B", "F", 4f);
        session.save(traffic1);
        session.save(traffic2);
        session.save(traffic3);
        session.save(traffic4);
        List<Traffic> expectedTraffics = new ArrayList<>();
        expectedTraffics.add(traffic1);
        expectedTraffics.add(traffic2);
        expectedTraffics.add(traffic3);
        expectedTraffics.add(traffic4);

        List<Traffic> persistedTraffics = trafficDao.findAllTraffic();

        assertThat(persistedTraffics, sameBeanAs(expectedTraffics));
        session.getTransaction().rollback();
    }
    
  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifySelectEdgeMaxRecordIsCorrect() {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic1 = new Traffic("1", "A", "B", 4f);
        Traffic traffic2 = new Traffic("2", "C", "D", 4f);
        session.save(traffic1);
        session.save(traffic2);
        long expectedMax = 2;

        long returnMax = trafficDao.findMaxTrafficRecordId();

        assertThat(returnMax, sameBeanAs(expectedMax));
        session.getTransaction().rollback();
    }

  //TODO:	 Revisit the test method below and figure out what the problem is
    @Ignore
    @Test
    public void verifyEdgeExistsSelectionIsCorrect() {
        Session session = sessionFactory.getCurrentSession();
        Traffic traffic1 = new Traffic("1", "A", "B", 4f);
        Traffic traffic2 = new Traffic("2", "C", "D", 4f);
        session.save(traffic1);
        session.save(traffic2);

        Traffic trafficToCommit = new Traffic("3", "A", "B", 1.9f);
        List<Traffic> expectedTraffics = new ArrayList<>();
        expectedTraffics.add(traffic1);

        List<Traffic> returnedEdges = trafficDao.trafficExists(trafficToCommit);
        assertThat(returnedEdges, sameBeanAs(expectedTraffics));
        session.getTransaction().rollback();
    }

}
