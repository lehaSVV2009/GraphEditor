package graphColoringAlgorithm.listener;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 01.11.13
 * Time: 6:35
 * To change this template use File | Settings | File Templates.
 */
public class SaveAsGraphListener implements ActionListener {

    private Graph graph;

    public SaveAsGraphListener(Graph graph) {
        this.graph = graph;
    }

    private boolean validatePath (String path) {
        return path != null && !"".equals(path.trim());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION && validatePath(fileChooser.getSelectedFile().getAbsolutePath())) {

        }
    }
}
