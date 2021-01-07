package com.mmt.routeplanner.graph;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jgrapht.graph.DefaultWeightedEdge;


@Data
@AllArgsConstructor
public class Medium extends DefaultWeightedEdge{

  /**
   * Combination of Src~#~Dest~#~Flight/Bus
   */
  String type;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Medium that = (Medium) o;
    return Objects.equals(type, that.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(type);
  }
}

