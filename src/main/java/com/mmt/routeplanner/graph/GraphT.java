package com.mmt.routeplanner.graph;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * This is class is for neo4J like representation of routes.
 */
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
          graph, 50, 10
      );

  /**
   * Add route with src and destination via type
   *
   * @return if new route via type return true else already exist false.
   */
  public static boolean addRoute(@NonNull String src, @NonNull String dest, @NonNull String type) {
    String relationShip = String.format(RELATIONSHIP, src, dest, type);
    if (routeMap.containsKey(relationShip)) {
      return false;
    }

    Medium medium = new Medium(relationShip);
    graph.addEdge(src, dest, medium);
    graph.setEdgeWeight(medium, 1);
    return true;

  }

  /**
   * Find top 50 routes with maximum 10 hopes
   */
  public static @NonNull
  List<GraphPath<String, Medium>> defaultGetPath(@NonNull String src, @NonNull String dest) {
    return pathInspector.getPaths(src, dest);
  }

  /**
   * Find top k routes with maximum z hopes
   */
  public static @NonNull
  List<GraphPath<String, Medium>> findKPathWithMaxZHopes(@NonNull String src, @NonNull String dest,
      @NonNull int k, @NonNull int z) {
    KShortestPaths<String, Medium> pathInspector =
        new KShortestPaths<String, Medium>(
            graph, k, z
        );
    return pathInspector.getPaths(src, dest);
  }


}
