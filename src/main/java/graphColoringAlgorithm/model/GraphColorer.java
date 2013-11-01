package graphColoringAlgorithm.model;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:15
 * To change this template use File | Settings | File Templates.
 */
public interface GraphColorer {

    public String getName ();
    public Map<Vertex, Integer> getColors (Graph graph);

}
