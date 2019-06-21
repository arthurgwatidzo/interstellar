package za.co.interstellar.impl.dao;


import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import za.co.interstellar.dao.EdgeDao;
import za.co.interstellar.persistence.Edge;


/**
 * @Author Arthur Gwatidzo - 
 * 
 * Email: arthur.gwatidzo@gmail.com, 
 * 
 * Cell: 076-898-3930
 *         
 */
@Repository
@Transactional
public class EdgeDaoImpl implements EdgeDao,Serializable{
	
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 private static final Log log = LogFactory.getLog(EdgeDaoImpl.class);

	    @Autowired
	    public EdgeDaoImpl(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }

	    @Override
	    public void save(Edge edge) {
	    	log.debug("start saving an edge");
	        Session session = sessionFactory.getCurrentSession();
	        session.save(edge);
	    }

	    @Override
	    public void update(Edge edge) {
	    	log.debug("start updating an edge");
	        Session session = sessionFactory.getCurrentSession();
	        session.merge(edge);
	    }

	    @Override
	    public int delete(long recordId) {
	    	log.debug("start deleting an edge");
	        Session session = sessionFactory.getCurrentSession();
	        String qry = "DELETE FROM edge AS E WHERE E.recordId = :recordIdParameter";
	        Query query = session.createQuery(qry);
	        query.setParameter("recordIdParameter", recordId);

	        return query.executeUpdate();
	    }
	    
	    @Override
	    public Edge selectUnique(long recordId) {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria criteria = session.createCriteria(Edge.class);
	        criteria.add(Restrictions.eq("recordId", recordId));

	        return (Edge) criteria.uniqueResult();
	    }

	    @Override
	    public long selectMaxRecordId() {
	        long maxId = (Long) sessionFactory.getCurrentSession()
	                .createCriteria(Edge.class)
	                .setProjection(Projections.max("recordId")).uniqueResult();

	        return maxId;
	    }

	    @Override
	    public List<Edge> edgeExists(Edge edge) {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria criteria = session.createCriteria(Edge.class);
	        criteria.add(Restrictions.ne("recordId", edge.getRecordId()));
	        criteria.add(Restrictions.eq("source", edge.getSource()));
	        criteria.add(Restrictions.eq("destination", edge.getDestination()));
	        List<Edge> edges = (List<Edge>) criteria.list();

	        return edges;
	    }

	    @Override
	    public List<Edge> selectAllByRecordId(long recordId) {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria criteria = session.createCriteria(Edge.class);
	        criteria.add(Restrictions.eq("recordId", recordId));
	        List<Edge> edges = (List<Edge>) criteria.list();

	        return edges;
	    }

	    @Override
	    public List<Edge> selectAllByEdgeId(String edgeId) {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria criteria = session.createCriteria(Edge.class);
	        criteria.add(Restrictions.eq("edgeId", edgeId));
	        List<Edge> edges = (List<Edge>) criteria.list();

	        return edges;
	    }

	    @Override
	    public List<Edge> selectAll() {
	        Session session = sessionFactory.getCurrentSession();
	        Criteria criteria = session.createCriteria(Edge.class);
	        List<Edge> edges = (List<Edge>) criteria.list();
	        return edges;
	    }

}
