package graphColoringAlgorithm.view;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;
import graphColoringAlgorithm.listener.SaveAsGraphListener;
import graphColoringAlgorithm.model.GraphColorer;
import graphColoringAlgorithm.model.GraphColorerHeuristics;
import graphColoringAlgorithm.model.GraphColorerImplicit;
import graphColoringAlgorithm.model.GraphColorerSimple;
import graphColoringAlgorithm.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class GraphPanel extends JInternalFrame implements MouseMotionListener, ItemListener, MouseListener {

    public static int graphCount = 0;

    private final static int xSize = 400;
    private final static int ySize = 400;


    /**
     * Граф, который раскрашивается
     */
    private Graph graph;

    /*
     *
     * Координаты вершин (Вершина => [x, y])
     */
    private Map<Vertex, Point> position;

    /*
     * Цвета вершин (Вершина => цвет)
     */
    private Map<Vertex, Integer> colors;
    /**
     * Вершина, которую сейчас перетаскивают, иначе null
     */
    private Vertex movingVertex;
    /**
     * Вершина, из которой сейчас создается дуга, иначе null
     */
    private Vertex creatingEdgeFrom;
    /**
     * Координаты, где находиться мышка, создающая новую дугу
     */
    private Point creatingEdgePosition;

    /**
     * Вершина, над которым находиться курсор, иначе null
     */
    private Vertex selectedVertex;
    /**
     * 2 вершины, между которым есть дуга с наведенной на ней курсором
     */
    private Vertex[] selectedEdge;
    /**
     * Вершина, помеченная на удаление
     */
    private Vertex vertexToDelete;
    /**
     * Объект, который раскрашивает граф
     */
    private GraphColorer colorer;

    /**
     * Выбор метода раскраски
     */
    private Choice method;

    private Choice colorsMaxNum;

    public GraphPanel() {
       this(Messages.STANDARD_GRAPH_NAME + graphCount);
        ++graphCount;
    }

    public GraphPanel(String graphName) {
        super(graphName,
                false,
                true,
                false,
                false);
        setVisible(true);
        setPreferredSize(new Dimension(xSize, ySize));
        init();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /*
         * Иницилизаци аплета, сразу после того как он был загружен
         */
    public void init() {
        ++graphCount;
        setLayout(new BorderLayout());
        this.position = new HashMap();
        this.graph = new Graph();
        this.movingVertex = null;
        this.creatingEdgeFrom = null;
        this.vertexToDelete = null;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        addNorthPanel();
        addSouthPanel();
        // Граф по-умолчанию
        graph.setName(
                getTitle() == null ? Messages.STANDARD_GRAPH_NAME + graphCount : getTitle()
        );
        Vertex Vertex1 = this.addVertex(new Point(200, 200));
        Vertex Vertex2 = this.addVertex(new Point(160, 270));
        Vertex Vertex3 = this.addVertex(new Point(240, 270));
        this.graph.setEdge(Vertex1, Vertex2);
        this.graph.setEdge(Vertex2, Vertex3);
        this.graph.setEdge(Vertex3, Vertex1);
        this.colorer = new GraphColorerImplicit();
    }

    private Choice createMaxNumChoice () {
        Choice choice = new Choice();
        choice.add("2");
        choice.add("3");
        choice.add("4");
        choice.add("5");
        choice.add("6");
        choice.add("7");
        choice.add("8");
        choice.select(5);
        return choice;
    }

    private Choice createMethodChoice () {
        Choice choice = new Choice();
        choice.add("Неявный перебор");
        choice.add("Эвристический метод");
        choice.add("Простая раскраска");
        choice.addItemListener(this);
        return choice;
    }

    private void addNorthPanel () {
        Panel northPanel = new Panel();
        northPanel.setLayout(new GridLayout(3, 0));
        Panel titlePanel = new Panel();
        titlePanel.add(new Label(getTitle()));
        northPanel.add(titlePanel);
        Panel colorsMaxNumPanel = new Panel();
        Label label = new Label(Messages.COLORS_MAX_NUM);
        colorsMaxNumPanel.add(label);
        colorsMaxNum = createMaxNumChoice();
        colorsMaxNumPanel.add(colorsMaxNum);
        northPanel.add(colorsMaxNumPanel);
        method = createMethodChoice();
        northPanel.add(this.method);
        add(northPanel, BorderLayout.NORTH);
    }

    private void addSouthPanel () {
        Panel southPanel = new Panel();
        Button saveAsButton = new Button(Messages.SAVE_AS_BUTTON);
        saveAsButton.addActionListener(new SaveAsGraphListener(graph));
        southPanel.add(saveAsButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private boolean badColorsNum (int maxColorNum, Map<Vertex, Integer> colors) {
        for (Integer color : colors.values()) {
            if (maxColorNum <= color) {
                return true;
            }
        }
        return false;
    }

    /**
     * Рисует интерфейс апплета
     */
    public void paint(Graphics Graphics) {
        Color[] colorValues = new Color[9];
        colorValues[0] = Color.black;
        colorValues[1] = Color.red;
        colorValues[2] = Color.green;
        colorValues[3] = Color.blue;
        colorValues[4] = Color.orange;
        colorValues[5] = Color.cyan;
        colorValues[6] = Color.magenta;
        colorValues[7] = Color.yellow;
        colorValues[8] = Color.gray;
        Map<Vertex, Integer> colorsMap = this.colorer.getColors(this.graph);
        if (badColorsNum(colorsMaxNum.getSelectedIndex() + 1, colorsMap)) {
            colorer = new GraphColorerSimple();
            colors = colorer.getColors(graph);
            JOptionPane.showMessageDialog(this, Messages.ALGORITHM_FAILED);
            colorsMaxNum.select(5);
//            method.select(2);
        } else {
            colors = colorsMap;
        }
        Graphics.setColor(Color.white);
        Graphics.fillRect(0, 100, 400, 300);


        Graphics.setColor(Color.black);
        for (Vertex Vertex1 : this.graph.getVertices()) {
            for (Vertex Vertex2 : Vertex1.getIncidentVertices()) {
                if (
                        (Vertex1 != this.vertexToDelete) &&
                                (Vertex2 != this.vertexToDelete)
                        ) {
                    Graphics.drawLine(this.position.get(Vertex1).x,
                            this.position.get(Vertex1).y, this.position.get(Vertex2).x,
                            this.position.get(Vertex2).y);
                    Graphics.setColor(Color.white);
                    Graphics.fillOval(this.position.get(Vertex1).x - 7,
                            this.position.get(Vertex1).y - 7, 14, 14);
                    Graphics.fillOval(this.position.get(Vertex2).x - 7,
                            this.position.get(Vertex2).y - 7, 14, 14);
                    Graphics.setColor(Color.black);
                }
            }
        }
        if (null != this.selectedEdge) {
            Vertex Vertex1 = this.selectedEdge[0];
            Vertex Vertex2 = this.selectedEdge[1];
            Graphics.drawLine(this.position.get(Vertex1).x + 1,
                    this.position.get(Vertex1).y, this.position.get(Vertex2).x + 1,
                    this.position.get(Vertex2).y);
            Graphics.drawLine(this.position.get(Vertex1).x - 1,
                    this.position.get(Vertex1).y, this.position.get(Vertex2).x - 1,
                    this.position.get(Vertex2).y);
            Graphics.drawLine(this.position.get(Vertex1).x,
                    this.position.get(Vertex1).y + 1, this.position.get(Vertex2).x,
                    this.position.get(Vertex2).y + 1);
            Graphics.drawLine(this.position.get(Vertex1).x,
                    this.position.get(Vertex1).y - 1, this.position.get(Vertex2).x,
                    this.position.get(Vertex2).y - 1);
            Graphics.setColor(Color.white);
            Graphics.fillOval(this.position.get(Vertex1).x - 6,
                    this.position.get(Vertex1).y - 6, 12, 12);
            Graphics.fillOval(this.position.get(Vertex2).x - 6,
                    this.position.get(Vertex2).y - 6, 12, 12);
            Graphics.setColor(Color.black);
        }
        if ((null != this.creatingEdgeFrom) && (null == this.movingVertex)) {
            Vertex Vertex = this.underVertex(this.creatingEdgePosition);
            Point Position;
            if (null == Vertex) {
                Position = this.creatingEdgePosition;
            } else {
                Position = this.position.get(Vertex);
            }
            Graphics.setColor(Color.gray);
            Graphics.drawLine(this.position.get(this.creatingEdgeFrom).x,
                    this.position.get(this.creatingEdgeFrom).y, Position.x,
                    Position.y);
            Graphics.setColor(Color.white);
            Graphics.fillOval(this.position.get(this.creatingEdgeFrom).x - 6,
                    this.position.get(this.creatingEdgeFrom).y - 6, 12, 12);
            Vertex = this.underVertex(this.creatingEdgePosition);
            if (null == Vertex) {
                Graphics.fillOval(this.creatingEdgePosition.x - 6,
                        this.creatingEdgePosition.y - 6, 12, 12);
            } else {
                Graphics.fillOval(this.position.get(Vertex).x - 6,
                        this.position.get(Vertex).y - 6, 12, 12);
            }
            Graphics.setColor(Color.black);
        }
        for (Vertex Vertex : this.graph.getVertices()) {
            if (Vertex != this.vertexToDelete) {
                Graphics.setColor(colorValues[this.colors.get(Vertex)]);
                Graphics.fillOval(this.position.get(Vertex).x - 4,
                        this.position.get(Vertex).y - 4, 8, 8);
            }
        }
        if ((null != this.selectedVertex) &&
                        (this.vertexToDelete != this.selectedVertex)
                ) {
            Graphics.setColor(colorValues[this.colors.get(this.selectedVertex)]);
            Graphics.fillOval(this.position.get(selectedVertex).x - 6,
                    this.position.get(selectedVertex).y - 6, 12, 12);
        }
        if ((null != this.creatingEdgeFrom) && (null == this.movingVertex)) {
            Vertex Vertex = this.underVertex(this.creatingEdgePosition);
            if (null == Vertex) {
                Graphics.setColor(Color.gray);
                Graphics.fillOval(this.creatingEdgePosition.x - 4,
                        this.creatingEdgePosition.y - 4, 8, 8);
                Graphics.setColor(Color.black);
            }
        }
    }

    /**
     * Вставляет вершину
     *
     * @param Point Координата x - x вершины, y - y вершины
     * @return Созданная вершина
     */
    private Vertex addVertex(Point Point) {

        Vertex Vertex = this.graph.addVertex();
        this.position.put(Vertex, Point);
        repaint();
        return Vertex;
    }

    /**
     * Удаляет вершину
     *
     * @param Vertex Вершина для удаления
     */
    private void deleteVertex(Vertex Vertex) {
        this.position.remove(Vertex);
        this.graph.deleteVertex(Vertex);
        repaint();
    }

    /**
     * Создает дугу
     *
     * @param From Первая вершина
     * @param To   Вторая вершина
     */
    private void createEdge(Vertex From, Vertex To) {
        this.graph.setEdge(From, To);
        repaint();
    }

    /**
     * Определяет, произошло ли действие над определенной вершиной
     *
     * @param cursor Точка, над которой находиться курсор
     * @param vertex Вершина, которую проверяем
     * @return Находиться ли курсор над определенной вершиной
     */
    private boolean underVertex(Point cursor, Vertex vertex) {
        if (position == null) {
            return false;
        } else if (position.get(vertex) == null) {
            return false;
        } else if (10 >= this.position.get(vertex).distance(cursor)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Определяет, произошло ли действие на изображении вершины
     *
     * @param Cursor Точка, над которой находиться курсор
     * @return Вершина, над которой курсор, иначе null
     */
    private Vertex underVertex(Point Cursor) {
        for (Vertex Vertex : this.graph.getVertices()) {
            if (this.underVertex(Cursor, Vertex)) {
                return Vertex;
            }
        }
        return null;
    }

    /**
     * Определяем, произошло ли действие на изображении определенной дуги
     *
     * @param Cursor Точка, над которой находиться курсор
     * @param Edge   Дуга, которую проверяем
     * @return Произошло ли действие на изображении определенной дуги
     */
    private boolean underEdge(Point Cursor, Vertex[] Edge) {
        Point Position1 = this.position.get(Edge[0]);
        Point Position2 = this.position.get(Edge[1]);
        double edgeLength = Position1.distance(Position2) - 10;
        if (
                edgeLength > Position1.distance(Cursor) &
                        edgeLength > Position2.distance(Cursor)
                ) {
            int edgeX = Position1.x - Position2.x;
            int edgeY = Position1.y - Position2.y;
            int cursorX = Position1.x - Cursor.x;
            int cursorY = Position1.y - Cursor.y;
            double edgeIncline;
            double curosorIncline;
            if (edgeX > edgeY) {
                edgeIncline = (double) edgeX / edgeY;
                curosorIncline = (double) cursorX / cursorY;
            } else {
                edgeIncline = (double) edgeY / edgeX;
                curosorIncline = (double) cursorY / cursorX;
            }
            if (0.1 > Math.abs(edgeIncline - curosorIncline)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Определяет, произошло ли действие на изображении дуг и
     *
     * @param Cursor Точка, над которой находиться курсор
     * @return 2 Вершины, между которыми находиться дуга, иначе null
     */
    private Vertex[] underEdge(Point Cursor) {
        Vertex[] Edge = new Vertex[2];
        for (Vertex Vertex1 : this.graph.getVertices()) {
            for (Vertex Vertex2 : Vertex1.getIncidentVertices()) {
                if (Vertex1.isConnect(Vertex2)) {
                    Edge[0] = Vertex1;
                    Edge[1] = Vertex2;


                    if (this.underEdge(Cursor, Edge)) {
                        return Edge;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Действия при переключении способа раскраски
     */
    public void itemStateChanged(ItemEvent Event) {
        int selectedIndex = method.getSelectedIndex();
        if (0 == selectedIndex) {
            this.colorer = new GraphColorerImplicit();
        } else if (1 == selectedIndex) {
            this.colorer = new GraphColorerHeuristics();
        } else if (2 == selectedIndex) {
            this.colorer = new GraphColorerSimple();
        }
        repaint();
    }


    /**
     * Действия при нажатии кнопки мыши
     */
    public void mouseClicked(MouseEvent Event) {
        if (null != this.movingVertex) {
            // Если выбрали место, куда переместить вершины
            this.movingVertex = null;
            this.creatingEdgeFrom = null;
        } else {
            if (1 == Event.getClickCount()) {
                // Если кликнули 1 раз
                Vertex[] Edge = this.underEdge(Event.getPoint());
                if (null != Edge) {
                    // Если по дуге - удалить е е
                    this.graph.deleteEdge(Edge[0], Edge[1]);
                } else {
                    Vertex Result = this.underVertex(Event.getPoint());
                    if (null == Result) {
                        // Если по пустому месту - создать новую вершин у
                        this.addVertex(Event.getPoint());
                    } else {
                        // Если по вершине - удалить вершину
                        this.vertexToDelete = Result;
                    }
                }
            } else if (2 == Event.getClickCount()) {
                this.vertexToDelete = null;
                Vertex Vertex = this.underVertex(Event.getPoint());
                if (null != Vertex) {
                    this.movingVertex = Vertex;
                }
            }
        }
    }


    /**
     * Действия, когда кнопку мыши нажали
     */
    public void mousePressed(MouseEvent Event) {
        Vertex Vertex = this.underVertex(Event.getPoint());
        if (null != Vertex) {
            this.creatingEdgeFrom = Vertex;
        }
    }

    /**
     * Действия, когда кнопку мыши отпустили
     */
    public void mouseReleased(MouseEvent Event) {
        if (null != this.creatingEdgeFrom) {
            Vertex Vertex = this.underVertex(Event.getPoint());
            if (Vertex != this.creatingEdgeFrom) {
                if (null == Vertex) {
                    Vertex = this.addVertex(Event.getPoint());
                }
                this.createEdge(this.creatingEdgeFrom, Vertex);
                this.creatingEdgeFrom = null;
            } else {
                this.creatingEdgeFrom = null;
            }
        }
    }

    /**
     * Действия, когда кнопку мыши зажали и "тащят" какой-то объект
     */
    public void mouseDragged(MouseEvent Event) {
        if (null != this.selectedVertex) {
            this.selectedVertex = null;
        }
        if (null != this.creatingEdgeFrom) {
            this.creatingEdgePosition = Event.getPoint();
            repaint();
        }
    }

    /**
     * Действия, когда курсор сдвинули с места
     */
    public void mouseMoved(MouseEvent Event) {
        boolean needRepaint = false;
        if (null != this.vertexToDelete) {
            this.deleteVertex(this.vertexToDelete);
            this.selectedVertex = null;
        }
        if (null != this.movingVertex) {
            this.selectedVertex = null;
            this.position.get(this.movingVertex).setLocation(Event.getPoint());
            needRepaint = true;
        } else {
            if (null != this.selectedEdge) {
                if (!this.underEdge(Event.getPoint(), this.selectedEdge)) {
                    this.selectedEdge = null;


                    needRepaint = true;
                }
            }
            if (null != this.selectedVertex) {
                if (!this.underVertex(Event.getPoint(), this.selectedVertex)) {
                    this.selectedVertex = null;
                    needRepaint = true;
                }
            }
            Vertex[] Edge = this.underEdge(Event.getPoint());
            if (null != Edge) {
                this.selectedEdge = Edge;
                needRepaint = true;
            } else {
                Vertex Vertex = this.underVertex(Event.getPoint());
                if (null != Vertex) {
                    this.selectedVertex = Vertex;
                    needRepaint = true;
                }
            }
        }
        if (needRepaint) {
            repaint();
        }
    }

    /**
     * Действия, когда курсор зашел в область аплета
     */
    public void mouseEntered(MouseEvent Event) {
    }

    /**
     * Действия, когда курсор покинул область аплета
     */
    public void mouseExited(MouseEvent Event) {
    }

}
