package graphColoringAlgorithm.view;

import graphColoringAlgorithm.listener.NewGraphListener;
import graphColoringAlgorithm.util.Messages;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 01.11.13
 * Time: 4:43
 * To change this template use File | Settings | File Templates.
 */
public class GraphFrame extends JFrame {

    private JButton newGraphButton;
    private JPanel mainPanel;

    public GraphFrame () throws HeadlessException {
        initialize();
        addComponents();
    }

    private void initialize () {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1000, 700));
        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void addPanel (GraphPanel panel) {
        mainPanel.add(panel);
        repaint();
    }

    private void addComponents () {
        addMainPanel();
        addNewGraphButton();
        addDefaultGraphPanel();
        repaint();
    }

    private void addMainPanel () {
        mainPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addNewGraphButton () {
        newGraphButton = new JButton(Messages.ADD_BUTTON);
        newGraphButton.addActionListener(new NewGraphListener(this));
        add(newGraphButton, BorderLayout.NORTH);
        repaint();
    }

    private void addDefaultGraphPanel () {
        mainPanel.add(new GraphPanel());
    }

}
