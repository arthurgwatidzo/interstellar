package za.co.interstellar.impl.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

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
	

	@Autowired
    private SessionFactory sessionFactory;
	

    @Autowired
    public VertexDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Vertex vertex) {
        Session session = sessionFactory.getCurrentSession();
        session.save(vertex);
    }

    @Override
    public void update(Vertex vertex) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(vertex);
    }

    @Override
    public int delete(String vertexId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM vertex AS V WHERE V.vertexId = :vertexIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("vertexIdParameter", vertexId);

        return query.executeUpdate();
    }

    @Override
    public Vertex selectUnique(String vertexId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        criteria.add(Restrictions.eq("vertexId", vertexId));

        return (Vertex) criteria.uniqueResult();
    }

    @Override
    public Vertex selectUniqueByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        criteria.add(Restrictions.eq("name", name));

        return (Vertex) criteria.uniqueResult();
    }

    @Override
    public List<Vertex> selectAll() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Vertex.class);
        List<Vertex> vertices = (List<Vertex>) criteria.list();

        return vertices;
    }
    
}
