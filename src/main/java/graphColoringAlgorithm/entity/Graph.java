package graphColoringAlgorithm.entity;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class Graph {

    private HashSet<Vertex> vertices;

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
        HashSet<Vertex> toDelete = new HashSet<Vertex>();
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

    public HashSet<Vertex> getVertices() {
        return vertices;
    }
}
