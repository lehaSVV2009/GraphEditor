package graphColoringAlgorithm.listener;

import graphColoringAlgorithm.util.Messages;
import graphColoringAlgorithm.view.GraphFrame;
import graphColoringAlgorithm.view.GraphPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 01.11.13
 * Time: 5:11
 * To change this template use File | Settings | File Templates.
 */
public class NewGraphListener implements ActionListener {

    private GraphFrame graphFrame;

    public NewGraphListener(GraphFrame graphFrame) {
        this.graphFrame = graphFrame;
    }

    private String inputGraphName () {
        return JOptionPane.showInputDialog(graphFrame, Messages.INPUT_GRAPH_NAME);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GraphPanel graphPanel = new GraphPanel(inputGraphName());
        graphFrame.addPanel(graphPanel);
    }
}
