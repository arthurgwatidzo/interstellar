package za.co.interstellar.dao;

import java.util.List;

import za.co.interstellar.persistence.Traffic;

/**
 * @Author Arthur Gwatidzo -
 * 
 *         Email: arthur.gwatidzo@gmail.com,
 * 
 *         Cell: 076-898-3930
 * 
 */
public interface TrafficDao {

	public void save(Traffic traffic);

	public void update(Traffic traffic);

	public int delete(String routeId);

	public Traffic findUniqueTraffic(String routeId);

	public List<Traffic> findAllTraffic();

	public long findMaxTrafficRecordId();

	public List<Traffic> trafficExists(Traffic traffic);

}
