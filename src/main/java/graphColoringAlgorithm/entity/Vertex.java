package graphColoringAlgorithm.entity;

import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class Vertex {

    private HashSet<Vertex> incidentVertices;

    public Vertex() {
        this.incidentVertices = new HashSet<Vertex>();
    }

    public int getOrder () {
        return incidentVertices.size();
    }

    public void connectVertex (Vertex vertex) {
        incidentVertices.add(vertex);
    }

    public boolean disconnectVertex (Vertex vertex) {
        return incidentVertices.remove(vertex);
    }

    public boolean isConnect (Vertex vertex) {
        return incidentVertices.contains(vertex);
    }

    public HashSet<Vertex> getIncidentVertices() {
        return incidentVertices;
    }
}
