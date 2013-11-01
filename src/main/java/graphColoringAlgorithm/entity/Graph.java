package graphColoringAlgorithm.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class Graph {

    private String name;

    private Set<Vertex> vertices;

    public Graph() {
        this.vertices = new HashSet<Vertex>();
    }

    public Vertex addVertex () {
        Vertex vertex = new Vertex();
        vertices.add(vertex);
        return vertex;
    }

    public void deleteEdge (Vertex from, Vertex to) {
        from.disconnectVertex(to);
        to.disconnectVertex(from);
    }

    public void setEdge (Vertex from, Vertex to) {
        from.connectVertex(to);
        to.connectVertex(from);
    }

    public void deleteVertex (Vertex vertex) {
        Set<Vertex> toDelete = new HashSet<Vertex>();
        for (Vertex incidentVertex : vertex.getIncidentVertices()) {
            toDelete.add(incidentVertex);
        }
        for (Vertex incidentVertex : toDelete) {
            deleteEdge(vertex, incidentVertex);
        }
        vertices.remove(vertex);
    }

    public int getOrder () {
        return vertices.size();
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
