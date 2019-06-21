package za.co.interstellar.impl.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import za.co.interstellar.dao.VertexDao;
import za.co.interstellar.persistence.Vertex;

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
@Component
public class VertexDaoImpl implements  VertexDao, Serializable{
	
	private static final Log log = LogFactory.getLog(VertexDaoImpl.class);
	

	@Autowired
    private SessionFactory sessionFactory;
	

    @Autowired
    public VertexDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Vertex vertex) {
    	log.debug("saving a vertex");
        Session session = sessionFactory.getCurrentSession();
        session.save(vertex);
    }

    @Override
    public void update(Vertex vertex) {
    	log.debug("updating a vertex");
        Session session = sessionFactory.getCurrentSession();
        session.merge(vertex);
    }

    @Override
    public int delete(String vertexId) {
    	log.debug("deleting a vertex");
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM vertex AS V WHERE V.vertexId = :vertexIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("vertexIdParameter", vertexId);

        return query.executeUpdate();
    }

    @Override
    public Vertex findUniqueVertex(String vertexId) {
    	log.debug("finding a vertex by vetexId");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        criteria.add(Restrictions.eq("vertexId", vertexId));

        return (Vertex) criteria.uniqueResult();
    }

    @Override
    public Vertex findUniqueVertexByName(String name) {
    	log.debug("finding a vertex by vertex name");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        criteria.add(Restrictions.eq("name", name));

        return (Vertex) criteria.uniqueResult();
    }

    @Override
    public List<Vertex> findAllVertices() {
    	log.debug("finding all vertices");
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        List<Vertex> vertices = (List<Vertex>) criteria.list();

        return vertices;
    }
    
}
