package graphColoringAlgorithm.model;

import graphColoringAlgorithm.entity.Vertex;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:43
 * To change this template use File | Settings | File Templates.
 */
public class VertexRelativeComparator implements Comparator<Vertex> {

    private Set coloredVertices;

    public VertexRelativeComparator(Set coloredVertices) {
        this.coloredVertices = coloredVertices;
    }

    @Override
    public int compare(Vertex vertex1, Vertex vertex2) {
        int size = 0;
        for (Vertex incidentVertex :vertex1.getIncidentVertices()) {
            if (!coloredVertices.contains(incidentVertex)) {
                --size;
            }
        }
        for (Vertex incidentVertex : vertex2.getIncidentVertices()) {
            if (!coloredVertices.contains(incidentVertex)) {
                ++size;
            }
        }
        return size;
    }
}
