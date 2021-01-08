package com.mmt.routeplanner.graph;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import scala.Int;


@Data
@AllArgsConstructor
class Connection extends DefaultWeightedEdge{
  String type;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Connection that = (Connection) o;
    return Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}

public class JGraph {

  public static void main(String arg[]){





    DirectedGraph<Integer, DefaultEdge> graph2 = new DefaultDirectedGraph<>(DefaultEdge.class);

    graph2.addVertex(7);
    graph2.addVertex(4);
    graph2.addVertex(9);
    graph2.addVertex(3);
    graph2.addVertex(2);
    graph2.addVertex(5);

    graph2.addEdge(7, 4);
    graph2.addEdge(7, 9);
    graph2.addEdge(9, 3);
    graph2.addEdge(3, 2);
    graph2.addEdge(3, 5);
    graph2.addEdge(7, 2);


//    GraphPath<Integer, DefaultEdge> path2 = DijkstraShortestPath.findPathBetween(graph2, 7, 2);
//   log.info(path2);
//
//    KShortestPaths<String, DefaultEdge> pathInspector =
//        new KShortestPaths<String, DefaultEdge>(
//            graph2,
//            7);
//
//
//    List<GraphPath<String, DefaultEdge>> paths = pathInspector.getPaths("D");



    DirectedWeightedMultigraph<String, Connection> graph
        = new DirectedWeightedMultigraph(Connection.class);

    graph.addVertex("vertex1");
    graph.addVertex("vertex2");
    graph.addVertex("vertex3");
    graph.addVertex("vertex3");
    graph.addVertex("vertex4");
    graph.addVertex("vertex5");
    Connection connection = new Connection("vertex1 - vertex2-Flight");
    Connection connection2 = new Connection("vertex1 - vertex2-Bus");

    graph.addEdge("vertex1", "vertex2",connection);
    graph.setEdgeWeight(connection, 2);
    graph.addEdge("vertex1", "vertex2",connection2);
    graph.setEdgeWeight(connection2, 5);


    connection = new Connection("vertex2 - vertex3-Flight");
    connection2 = new Connection("vertex2 - vertex3-Bus");
    graph.addEdge("vertex2", "vertex3",connection);
    graph.setEdgeWeight(connection, 8);
    graph.addEdge("vertex2", "vertex3",connection2);
    graph.setEdgeWeight(connection2, 5);


    connection = new Connection("vertex1 - vertex3-Flight");
    connection2 = new Connection("vertex1 - vertex3-Bus");
    graph.addEdge("vertex1", "vertex3",connection);
    graph.setEdgeWeight(connection, 8);
    graph.addEdge("vertex1", "vertex3",connection2);
    graph.setEdgeWeight(connection2, 8);

    graph.addEdge("vertex1", "vertex3",connection2);
    graph.setEdgeWeight(connection2, 7);

    connection = new Connection("vertex3 - vertex1-Flight");
    graph.addEdge("vertex3", "vertex1",connection);
    graph.setEdgeWeight(connection, 9);

    KShortestPaths<String, Connection> pathInspector =
        new KShortestPaths<String, Connection>(
            graph,10
        );


    List<GraphPath<String, Connection>> paths = pathInspector.getPaths("vertex1", "vertex3");
    System.out.println(paths.toString());

   // List<Connection> all = BFSShortestPath.findPathBetween(graph,"vertex1", "vertex3");

   /* AllDirectedPaths allDirectedPaths = new AllDirectedPaths(graph);
    List< GraphPath<String, Connection> > all =  allDirectedPaths.getAllPaths("vertex1", "vertex3",false,Integer.MAX_VALUE) ;
*/

/*
    DefaultWeightedEdge e1 = graph.addEdge("vertex1", "vertex2");
    graph.setEdgeWeight(e1, 5);

    DefaultWeightedEdge e2 = graph.addEdge("vertex2", "vertex3");
    graph.setEdgeWeight(e2, 3);

    DefaultWeightedEdge e3 = graph.addEdge("vertex4", "vertex5");
    graph.setEdgeWeight(e3, 6);

    DefaultWeightedEdge e4 = graph.addEdge("vertex2", "vertex4");
    graph.setEdgeWeight(e4, 2);

    DefaultWeightedEdge e5 = graph.addEdge("vertex5", "vertex4");
    graph.setEdgeWeight(e5, 4);


    DefaultWeightedEdge e6 = graph.addEdge("vertex2", "vertex5");
    graph.setEdgeWeight(e6, 9);

    DefaultWeightedEdge e7 = graph.addEdge("vertex4", "vertex1");
    graph.setEdgeWeight(e7, 7);

    DefaultWeightedEdge e8 = graph.addEdge("vertex3", "vertex2");
    graph.setEdgeWeight(e8, 2);

    DefaultWeightedEdge e9 = graph.addEdge("vertex1", "vertex3");
    graph.setEdgeWeight(e9, 6);

    DefaultWeightedEdge e10 = graph.addEdge("vertex3", "vertex5");
    graph.setEdgeWeight(e10, 1);

    DefaultWeightedEdge e12 = graph.addEdge("vertex1", "vertex3");
    graph.setEdgeWeight(e12, 5);

    DefaultWeightedEdge e11 = graph.addEdge("vertex2", "vertex1");
    graph.setEdgeWeight(e11, 1);*/

    // Print out the graph to be sure it's really complete
    Iterator<String> iter = new DepthFirstIterator<>(graph);
    while (iter.hasNext()) {
      String vertex = iter.next();
      System.out
          .println(
              "Vertex " + vertex + " is connected to: "
                  + graph.edgesOf(vertex).toString());
    }

    GraphPath<String, Connection> path = DijkstraShortestPath.findPathBetween(graph, "vertex1", "vertex3");
    System.out
        .println(path.toString()+path.getWeight());

    /*AllDirectedPaths<String, DefaultWeightedEdge> allDirectedPaths = new AllDirectedPaths(graph);
    List<GraphPath<String, DefaultWeightedEdge>>  graphPaths =  allDirectedPaths.getAllPaths("vertex1","vertex3",true, Integer.MAX_VALUE);
    graphPaths.forEach(stringDefaultWeightedEdgeGraphPath ->    log.info(path.toString()+path.getWeight()));
*/
  }

}
