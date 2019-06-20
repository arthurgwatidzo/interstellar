package za.co.interstellar.dao;

import java.util.List;

import za.co.interstellar.persistence.Edge;

/**
 * @Author Arthur Gwatidzo -
 * 
 *         Email: arthur.gwatidzo@gmail.com,
 * 
 *         Cell: 076-898-3930
 * 
 */
public interface EdgeDao {

	public void save(Edge edge);

	public void update(Edge edge);

	public int delete(long recordId);

	public Edge selectUnique(long recordId);

	public long selectMaxRecordId();

	public List<Edge> edgeExists(Edge edge);

	public List<Edge> selectAllByRecordId(long recordId);

	public List<Edge> selectAllByEdgeId(String edgeId);

	public List<Edge> selectAll();

}
