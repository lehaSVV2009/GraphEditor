package graphColoringAlgorithm.model;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class GraphColorerHeuristics implements GraphColorer {

    @Override
    public String getName() {
        return "Rough with sorting";
    }

    @Override
    public HashMap<Vertex, Integer> getColors(Graph graph) {
        HashSet<Vertex> coloredVertices = new HashSet<Vertex>();
        VertexRelativeComparator comparator = new VertexRelativeComparator(coloredVertices);
        HashMap<Vertex, Integer> colors = new HashMap<Vertex, Integer>();
        ArrayList<Vertex> uncoloredVertices = new ArrayList<Vertex>(graph.getOrder());
        for (Vertex vertex : graph.getVertices()) {
            uncoloredVertices.add(vertex);
        }
        int color = 0;
        HashSet<Vertex> toDelete = new HashSet<Vertex>();
        while (0 != uncoloredVertices.size()) {
            ++color;
            for (Vertex vertex : toDelete) {
                uncoloredVertices.remove(vertex);
            }
            toDelete = new HashSet<Vertex>();
            Collections.sort(uncoloredVertices, comparator);
            vertices :
            for (Vertex vertex : uncoloredVertices) {
                if (coloredVertices.contains(vertex))  {
                    continue ;
                }
                for (Vertex incidentVertex : vertex.getIncidentVertices()) {
                    if (colors.containsKey(incidentVertex)) {
                        if (color == colors.get(incidentVertex)) {
                            continue vertices;
                        }
                    }
                }
                colors.put(vertex, color);
                toDelete.add(vertex);
                coloredVertices.add(vertex);
            }
        }
        return colors;
    }
}

