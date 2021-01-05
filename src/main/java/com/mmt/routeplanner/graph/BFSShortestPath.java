package com.mmt.routeplanner.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.BreadthFirstIterator;

final class BFSShortestPath {

  private BFSShortestPath() {} // ensure non-instantiability.

  public static <V, E> List<E> findPathBetween(Graph<V, E> graph, V startVertex, V endVertex) {
    MyBreadthFirstIterator<V, E> iter = new MyBreadthFirstIterator<>(graph, startVertex);
    while (iter.hasNext()) {
      Object vertex = iter.next();
      if (vertex.equals(endVertex)) {
        return createPath(iter, endVertex);
      }
    }
    return null;
  }

  private static <V, E> List<E> createPath(MyBreadthFirstIterator<V, E> iter, V endVertex) {
    List<E> path = new ArrayList<E>();
    while (true) {
      E edge = iter.getSpanningTreeEdge(endVertex);
      if (edge == null) {
        break;
      }
      path.add(edge);
      endVertex = Graphs.getOppositeVertex(iter.getGraph(), edge, endVertex);
    }
    Collections.reverse(path);
    return path;
  }

  private static class MyBreadthFirstIterator<V, E> extends BreadthFirstIterator<V, E> {

    public MyBreadthFirstIterator(Graph<V, E> g, V startVertex) {
      super(g, startVertex);
    }

    @Override
    protected void encounterVertex(V vertex, E edge) {
      super.encounterVertex(vertex, edge);
      putSeenData(vertex, edge);
    }

    @SuppressWarnings("unchecked")
    public E getSpanningTreeEdge(V vertex) {
      return (E) getSeenData(vertex);
    }

  }
}