package ru.progwards.java2.lessons.graph;

import java.util.*;

public class Dijkstra {

    class Mark implements Comparable<Mark> {
        Integer distance;                                       // кратчайшее расстояние до вершины
        LinkedList<Mark.Edge> edgeList = new LinkedList<>();    // список инцидентных ребер

        @Override
        public int compareTo(Mark o) {
            return Integer.compare(this.distance,o.distance);
        }

        class Edge {
            int b;
            int weight;

            public Edge(int b, int weight) {
                this.b = b;
                this.weight = weight;
            }
        }
    }

    int[][] graph;      // входной граф
    int [][] route;     // матрица выходная - вершина []/расстояние и путь (последняя вершина) []
    Map<Integer, Mark> mark = new HashMap<>();

    Dijkstra(int[][] graph) {
        this.graph = graph;
        if (graph != null) {
            route = new int[graph.length][2];
            Mark tempMark;
            for (int i = 0; i < graph.length; i++) {
                tempMark = new Mark();
                tempMark.distance = Integer.MAX_VALUE;              // заполняем все метки большим значением
                for (int j = 0; j < graph[i].length; j++) {
                    if (graph[i][j] != 0) {
                        tempMark.edgeList.add(tempMark.new Edge(j, graph[i][j]));    // составляем список ребер инцидентных данной вершине
                    }
                }
                mark.put(i, tempMark);
            }
        }
    }

    public int[][] find(int n) {
        mark.get(n).distance = 0;
        route[n][0] = 0;
        route[n][1] = 0;
        int start = n;

        while (!mark.isEmpty()) {
            while (!mark.get(start).edgeList.isEmpty()) {              // пока список ребер не пуст - есть пути из этой вершины
                Mark.Edge tempEdge = mark.get(start).edgeList.poll();
                // если она окончательно не обработана и (не обрабатывается первый раз или новый путь меньше текущего)
                if (mark.containsKey(tempEdge.b) && (mark.get(tempEdge.b).distance == null || mark.get(start).distance +tempEdge.weight < mark.get(tempEdge.b).distance)) {
                    mark.get(tempEdge.b).distance = mark.get(start).distance +tempEdge.weight;  // расчитываем более короткий путь
                    route[tempEdge.b][0] = mark.get(tempEdge.b).distance;                       // пишем в выходной массив путь и из какой вершины пришли
                    route[tempEdge.b][1] = start;
                }
            }
            mark.remove(start);      // удаляем обработанную вершину

            if (!mark.isEmpty()) {
                Mark minMark = Collections.min(mark.values());   // находим следующую минимальную метку
                for (Map.Entry<Integer, Mark> entry : mark.entrySet()) {
                    if (minMark.equals(entry.getValue())) {
                        start = entry.getKey();                   // и ее вершину
                    }
                }
            }
        }
        return route;
    }

    public static void main(String[] args) {
        int [][] graph = {{0,7,9,0,0,14}, {7,0,10,15,0,0}, {9,10,0,11,0,2},{0,15,11,0,6,0},{0,0,0,6,0,9},{14,0,2,0,9,0}};
        Dijkstra dijkstra = new Dijkstra(graph);
        System.out.println(Arrays.deepToString(dijkstra.find(0)));
    }
}

