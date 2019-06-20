package za.co.interstellar.dao;

import java.util.List;

import za.co.interstellar.persistence.Vertex;

public interface VertexDao {

	public List<Vertex> selectAll();

	public Vertex selectUniqueByName(String name);

	public Vertex selectUnique(String vertexId);

	public int delete(String vertexId);

	public void update(Vertex vertex);

	public void save(Vertex vertex);

}
