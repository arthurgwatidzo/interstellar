package za.co.interstellar.impl.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import za.co.interstellar.dao.TrafficDao;
import za.co.interstellar.persistence.Traffic;



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
public class TrafficDaoImpl implements TrafficDao,Serializable{
	
	@Autowired(required = true)
	private SessionFactory sessionFactory;

    @Autowired
    public TrafficDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        session.save(traffic);
    }

    public void update(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(traffic);
    }

    public int delete(String routeId) {
        Session session = sessionFactory.getCurrentSession();
        String qry = "DELETE FROM traffic AS T WHERE T.routeId = :routeIdParameter";
        Query query = session.createQuery(qry);
        query.setParameter("routeIdParameter", routeId);

        return query.executeUpdate();
    }

    public Traffic findUniqueTraffic(String routeId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        criteria.add(Restrictions.eq("routeId", routeId));

        return (Traffic) criteria.uniqueResult();
    }

    public List<Traffic> findAllTraffic() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        List<Traffic> trafficList = (List<Traffic>) criteria.list();

        return trafficList;
    }

    public long findMaxTrafficRecordId() {
        long maxId = (Long) sessionFactory.getCurrentSession()
                .createCriteria(Traffic.class)
                .setProjection(Projections.rowCount()).uniqueResult();

        return maxId;
    }

    public List<Traffic> trafficExists(Traffic traffic) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Traffic.class);
        criteria.add(Restrictions.ne("routeId", traffic.getRouteId()));
        criteria.add(Restrictions.eq("source", traffic.getSource()));
        criteria.add(Restrictions.eq("destination", traffic.getDestination()));
        List<Traffic> trafficList = (List<Traffic>) criteria.list();

        return trafficList;
    }

}
