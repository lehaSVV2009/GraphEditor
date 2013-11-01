package graphColoringAlgorithm.model;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class GraphColorerImplicit implements GraphColorer {

    private Set<Vertex> vertices;

    private Map<Integer, Vertex> numberToVertex;

    private Map<Vertex, Integer> vertexToNumber;

    private int []color;

    private int maxColor;

    private int vertexWithMaxColor;

    @Override
    public String getName() {
        return "Implicit BruteForce";
    }

    private int returnStep (int vertex) {
        int maxVertex = 0;
        int i, j;
        for (Vertex incidentVertex : numberToVertex.get(vertex).getIncidentVertices()) {
            j = vertexToNumber.get(incidentVertex);
            if (j < vertex) {
                maxVertex = j;
            }
        }
        j = maxVertex;
        boolean []goodColor = new boolean[vertices.size() + 1];
        for (i = 0; i < vertices.size(); ++i) {
            goodColor[i] = true;
        }
        goodColor[color[j]] = false;
        for (Vertex incidentVertex : numberToVertex.get(j).getIncidentVertices()) {
            i = vertexToNumber.get(incidentVertex);
            if (0 != color[i]) {
                goodColor[color[i]] = false;
            }
        }
        int newColor = 0;
        for (i = color[j] + 1; i < maxColor; ++i) {
            if (goodColor[i]) {
                newColor = i;
                break;
            }
        }
        if (0 == newColor) {
            if (0 != j) {
                return returnStep(j);
            } else {
                return 0;
            }
        } else {
            color[j] = newColor;
            return j;
        }
    }

    private void simpleColor (int from) {
        boolean []goodColor = new boolean[vertices.size() + 1];
        int j;
        vertexWithMaxColor = 0;
        maxColor = 0;
        for (int i = from; i < vertices.size(); ++i) {
            for (j = 0; j <= vertices.size(); ++j) {
                goodColor[j] = true;
            }
            for (Vertex incidentVertex : numberToVertex.get(i).getIncidentVertices()) {
                j = vertexToNumber.get(incidentVertex);
                if (0 != color[j]) {
                    goodColor[color[j]] = false;
                }
            }
            for (j = 1; j <= vertices.size(); ++j) {
                if (goodColor[j]) {
                    color[i] = j;
                    if (j > maxColor) {
                        maxColor = j;
                        vertexWithMaxColor = i;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public Map<Vertex, Integer> getColors(Graph graph) {
        vertices = graph.getVertices();
        numberToVertex = new HashMap<Integer, Vertex>(vertices.size());
        vertexToNumber = new HashMap<Vertex, Integer>(vertices.size());
        int i = 0;
        for (Vertex vertex : vertices) {
            numberToVertex.put(i, vertex);
            vertexToNumber.put(vertex, i);
            ++i;
        }
        color = new int[vertices.size()];
        simpleColor(0);
        while (
                (0 != vertexWithMaxColor)
                && (0 != (i = returnStep(vertexWithMaxColor)))
                ) {
            for (int j = i + 1; j < vertices.size(); ++j) {
                color[j] = 0;
            }
            simpleColor(i + 1);
        }
        Map<Vertex, Integer> colors = new HashMap<Vertex, Integer>(vertices.size());
        for (Vertex vertex : vertices) {
            colors.put(vertex, color[vertexToNumber.get(vertex)]);
        }
        return colors;
    }
}
