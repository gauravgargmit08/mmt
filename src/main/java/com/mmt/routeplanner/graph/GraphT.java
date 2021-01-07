package com.mmt.routeplanner.graph;

import com.mmt.routeplanner.util.RouteUtil;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.KShortestPaths;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * This is class is for neo4J like representation of routes.
 */
@Component
public class GraphT {


  /**
   * This is will have Possible route. For more scalability we need to use
   * data modelling of Neo4j which have more 360 view of relationships between from and to ,flexibility and better performance. Due to time
   * constraint using in-memory graph based library.
   */
  private static DirectedWeightedMultigraph<String, Medium> graph
      = new DirectedWeightedMultigraph(Medium.class);
  /**
   * Map to keep track of added route in routeGraph. If already added with same type like Flight no
   * need add again.
   */
  private static ConcurrentHashMap<String, Integer> routeMap = new ConcurrentHashMap();

  /**
   * Add route with src and destination via type
   *
   * @return if new route via type return true else already exist false.
   */
  public static synchronized boolean addRoute(@NonNull String src, @NonNull String dest, @NonNull String type) {
    String relationShip = String.format(RouteUtil.RELATIONSHIP, src, dest, type);
    if (routeMap.containsKey(relationShip)) {
      return false;
    }

    Medium medium = new Medium(relationShip);
    graph.addVertex(src);
    graph.addVertex(dest);
    graph.addEdge(src, dest, medium);
    graph.setEdgeWeight(medium, 1);
    return true;

  }

  /**
   * Find top 50 routes with maximum 10 hopes
   */
  public static @NonNull
  List<GraphPath<String, Medium>> defaultGetPath(@NonNull String src, @NonNull String dest) {
    if(CollectionUtils.isEmpty(graph.vertexSet()))
      throw new IllegalStateException("Route not Found. No Routes exists in the System. Please constact admininstrator");
    KShortestPaths<String, Medium> pathInspector =
        new KShortestPaths<String, Medium>(
            graph, 50, 10
        );
    return pathInspector.getPaths(src, dest);
  }

  /**
   * Find top k routes with maximum z hopes
   */
  public static @NonNull
  List<GraphPath<String, Medium>> findKPathWithMaxZHopes(@NonNull String src, @NonNull String dest,
      @NonNull int k, @NonNull int z) {
    if(CollectionUtils.isEmpty(graph.vertexSet()))
      throw new IllegalStateException("Route not Found. No Routes exists in the System. Please constact admininstrator");
    
    KShortestPaths<String, Medium> pathInspector =
        new KShortestPaths<String, Medium>(
            graph, k, z
        );
    return pathInspector.getPaths(src, dest);
  }


}
