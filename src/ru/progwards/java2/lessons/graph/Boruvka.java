package ru.progwards.java2.lessons.graph;

import java.util.*;

public class Boruvka <N,E> {
    class Node<N,E>{
        N info; // информация об узле
        List<Edge<N, E>> in; // массив входящих ребер
        List<Edge<N, E>> out; // массив исходящих ребер
    }

    class Edge<N, E> implements Comparable<Edge<N,E>>{
        E info; // информация о ребре
        Node<N, E> out; // вершина, из которой исходит ребро
        Node<N, E> in; // вершина, в которую можно попасть по этому ребру
        double weight; // стоимость перехода

        @Override
        public int compareTo(Edge<N,E> o) {
            return Double.compare(this.weight, o.weight);
        }
    }

    class Graph<N, E> {
        List<Node<N, E>> nodes;
        List<Edge<N, E>> edges;
    }

    HashMap <Node<N,E>, ArrayList<Node<N,E>>> mapNode = new HashMap<>();  // ключ - вершина, список - множество, 1-й элемент - корень
    LinkedList<Edge<N,E>> minTreeEdges = new LinkedList<>();         // минимальное остовное дерево


    List<Edge<N,E>> minTree(Graph<N,E> graph) {
        for (Node<N,E> node : graph.nodes) {                         // заполненеие mapNode всеми вершинами, в списке корень - сама вершина
            mapNode.put(node, new ArrayList<>(List.of(node)));
        }
        while (minTreeEdges.size()<graph.nodes.size()-1) {            // пока ребер в минимальном дереве меньше чем узлов-1
            for (Node<N, E> node : mapNode.keySet()) {
                if (mapNode.get(node).get(0) == node) {               // если узел корневой - ищем для множества минимальное ребро наружу
                    Edge<N, E> minEdge = findMinEdge(mapNode.get(node));
                    minTreeEdges.add(minEdge);
                    merge(minEdge, node);
                }
            }
        }
        return minTreeEdges;
    }

    // поиск корневого элемента множества
    Node<N,E> find (Node<N,E> node) {
        return mapNode.get(node).get(0);
    }

    // объединение множеств
    private void merge(Edge<N,E> edge, Node<N,E> node) {
        // находим корневой узел у присоединяемого множества
        Node<N,E> oldRoot = node.equals(find(edge.in)) ? find(edge.out) : find(edge.in);
        mapNode.get(node).addAll(mapNode.get(oldRoot));           // добавляем все элементы к общему корневому узлу
        for (int i=mapNode.get(oldRoot).size()-1; i>=0; i--) {
            Node<N,E> tempNode = mapNode.get(oldRoot).get(i);
            mapNode.get(tempNode).clear();      // зачищаем старые множества
            mapNode.get(tempNode).add(node);    // каждому узлу записываем новый корень
        }
    }

    // нахождение минимального ребра для множества ребер
    private Edge<N, E> findMinEdge(ArrayList<Node<N,E>> list) {
        Edge<N, E> minEdgeIn = null;
        Edge<N, E> minEdgeOut;
        for (Node<N, E> nNode : list) {                                    // перебор всего множества узлов
            minEdgeIn = oneDirectMinEdge(nNode.in);
            minEdgeOut = oneDirectMinEdge(nNode.out);
            // поиск минимального между мин входящим и мин исходящим
            if (minEdgeIn == null || (minEdgeOut != null && minEdgeIn.compareTo(minEdgeOut) > 0)) {
                minEdgeIn = minEdgeOut;
            }
        }
        return minEdgeIn;
    }

    //нахождение мин ребра для узла в определенном направлении
    private  Edge<N, E> oneDirectMinEdge (List<Edge<N, E>> listEdge) {
        Edge <N,E> tempMinEdge = null;
        for (Edge<N, E> edge : listEdge) {
            if (!find(edge.in).equals(find(edge.out))) {                       // проверка - принадлежат ли одному множеству
                if (tempMinEdge == null || tempMinEdge.compareTo(edge) > 0) {
                    tempMinEdge = edge;
                }
            }
        }
        return tempMinEdge;
    }
}
