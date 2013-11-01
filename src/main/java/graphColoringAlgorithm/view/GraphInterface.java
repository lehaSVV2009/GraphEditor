package graphColoringAlgorithm.view;

import graphColoringAlgorithm.entity.Graph;
import graphColoringAlgorithm.entity.Vertex;
import graphColoringAlgorithm.model.GraphColorer;
import graphColoringAlgorithm.model.GraphColorerHeuristics;
import graphColoringAlgorithm.model.GraphColorerImplicit;
import graphColoringAlgorithm.model.GraphColorerSimple;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Кадет
 * Date: 31.10.13
 * Time: 22:56
 * To change this template use File | Settings | File Templates.
 */
public class GraphInterface extends Applet implements MouseMotionListener, ItemListener, MouseListener {

    /**
     * Граф, который раскрашивается
     */
    private Graph currentGraph;
    /*
     *
     * Координаты вершин (Вершина => [x, y])
     */
    private HashMap<Vertex, Point> position;

    /*
     * Цвета вершин (Вершина => цвет)
     */
    private HashMap<Vertex, Integer> colors;
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

    /*
     * Иницилизаци аплета, сразу после того как он был загружен
     */
    public void init() {
        this.position = new HashMap();
        this.currentGraph = new Graph();
        this.movingVertex = null;
        this.creatingEdgeFrom = null;
        this.vertexToDelete = null;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.method = new Choice();
        this.method.add("Неявный перебор");
        this.method.add("Эвристический метод");
        this.method.add("Простая раскраска");
        this.method.addItemListener(this);
        add(this.method);
        // Граф по-умолчанию
        Vertex Vertex1 = this.addVertex(new Point(200, 100));
        Vertex Vertex2 = this.addVertex(new Point(160, 170));
        Vertex Vertex3 = this.addVertex(new Point(240, 170));
        this.currentGraph.setEdge(Vertex1, Vertex2);
        this.currentGraph.setEdge(Vertex2, Vertex3);
        this.currentGraph.setEdge(Vertex3, Vertex1);
        this.colorer = new GraphColorerImplicit();
    }

    /**
     * Рисует интерфейс апплета
     */
    public void paint(Graphics Graphics) {
        Color[] colors = new Color[9];
        colors[0] = Color.black;
        colors[1] = Color.red;
        colors[2] = Color.green;
        colors[3] = Color.blue;
        colors[4] = Color.orange;
        colors[5] = Color.cyan;
        colors[6] = Color.magenta;
        colors[7] = Color.yellow;
        colors[8] = Color.gray;
        this.colors = this.colorer.getColors(this.currentGraph);
        Graphics.setColor(Color.white);
        Graphics.fillRect(0, 0, 400, 300);


        Graphics.setColor(Color.black);
        for (Vertex Vertex1 : this.currentGraph.getVertices()) {
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
        for (Vertex Vertex : this.currentGraph.getVertices()) {
            if (Vertex != this.vertexToDelete) {
                Graphics.setColor(colors[this.colors.get(Vertex)]);
                Graphics.fillOval(this.position.get(Vertex).x - 4,
                        this.position.get(Vertex).y - 4, 8, 8);
            }
        }
        if ((null != this.selectedVertex) &&
                        (this.vertexToDelete != this.selectedVertex)
                ) {
            Graphics.setColor(colors[this.colors.get(this.selectedVertex)]);
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

        //TODO: insert addName
        //TODO: insert addContent
        Vertex Vertex = this.currentGraph.addVertex();
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
        this.currentGraph.deleteVertex(Vertex);
        repaint();
    }

    /**
     * Создает дугу
     *
     * @param From Первая вершина
     * @param To   Вторая вершина
     */
    private void createEdge(Vertex From, Vertex To) {
        this.currentGraph.setEdge(From, To);
        repaint();
    }

    /**
     * Определяет, произошло ли действие над определенной вершиной
     *
     * @param Cursor Точка, над которой находиться курсор
     * @param Vertex Вершина, которую проверяем
     * @return Находиться ли курсор над определенной вершиной
     */
    private boolean underVertex(Point Cursor, Vertex Vertex) {
        if (10 >= this.position.get(Vertex).distance(Cursor)) {
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
        for (Vertex Vertex : this.currentGraph.getVertices()) {
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
        for (Vertex Vertex1 : this.currentGraph.getVertices()) {
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
                    this.currentGraph.deleteEdge(Edge[0], Edge[1]);
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
