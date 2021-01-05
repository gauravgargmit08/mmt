package com.mmt.routeplanner.graph;

import java.util.concurrent.ConcurrentHashMap;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.stereotype.Component;

@Component
public class GraphT {

  private static final String RELATIONSHIP = "%s~#~%s~#~%s";
  /**
   * This is will have Possible route with weight as duration. For more scalability we need to use
   * data modelling of Neo4j. This implementation will more flexibility and performance. Due to time
   * constraint I am in memory graph based library.
   */
  private static DirectedWeightedMultigraph<String, Medium> graph
      = new DirectedWeightedMultigraph(Medium.class);
  /**
   * Map to keep track of added route in routeGraph. If already added with same type like Flight no
   * need add again.
   */
  private static ConcurrentHashMap<String, Integer> routeMap = new ConcurrentHashMap();


  private static KShortestPaths<String, Medium> pathInspector =
      new KShortestPaths<String, Medium>(
          graph,50,10
      );


  public static boolean addRoute(String src, String dest, String type, long duration) {
    String relationShip = String.format(RELATIONSHIP, src, dest, type);
    if (routeMap.containsKey(relationShip)) {
      return false;
    }

    Medium medium = new Medium(relationShip);
    graph.addEdge(src, dest, medium);
    graph.setEdgeWeight(medium, 1);
    return true;

  }


}
