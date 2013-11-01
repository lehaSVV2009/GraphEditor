package graphColoringAlgorithm.model;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 01.11.13
 * Time: 3:49
 * To change this template use File | Settings | File Templates.
 */
public class GraphColorerSimple implements GraphColorer {

    protected int getColorNum () {
        return 0;
    }

    @Override
    public String getName() {
        return "Simple Colorer";
    }

    @Override
    public Map<Vertex, Integer> getColors(Graph graph) {
        Set<Vertex> vertices = graph.getVertices();
        Map<Vertex, Integer> colors = new HashMap<Vertex, Integer>();
        for (Vertex vertex : vertices) {
            colors.put(vertex, getColorNum());
        }
        return colors;
    }
}
