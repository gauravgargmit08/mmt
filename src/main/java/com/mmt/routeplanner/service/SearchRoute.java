package com.mmt.routeplanner.service;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.entity.Bus;
import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.factory.MediumServiceFactory;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.model.Routes;
import com.mmt.routeplanner.model.SearchResult;
import com.mmt.routeplanner.model.Transfer;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.GraphPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchRoute {

  @Autowired
  private FlightService flightService;

  @Autowired
  private MediumServiceFactory mediumServiceFactory;


  public SearchResult searchRoutes(List<GraphPath<String, Medium>> graphs, Date startDate,
      String src, String dest, String routeSort) {
    SearchResult searchResult = SearchResult.builder().destination(dest).source(src)
        .searchDate(startDate).routeSort(routeSort).build();
    List<Routes> routesList = new ArrayList<>();
    //Traverse each path to check of flights or buses available
    for (GraphPath<String, Medium> stringMediumGraphPath : graphs) {
      BigDecimal totalFare = BigDecimal.ZERO;
      long duration = 0;
      List<Transfer> transfers = new ArrayList<>();
      Routes routes = Routes.builder().build();
      Date nextDate = RouteUtil.atStartOfDay(startDate);
      Date endDate = RouteUtil.atEndOfDay(startDate);
      boolean routeFound = Boolean.TRUE;
      List<Medium> graphWalk = stringMediumGraphPath.getEdgeList();
     log.info("graphWalk: {}",graphWalk.toString());

      for (Medium medium : graphWalk) {
        String[] relProp = medium.getType().split(RouteUtil.DELIMETER);
        IMediumService mediumService =
            mediumServiceFactory.factory(relProp[2]);
       log.info(String
            .format("Fetching Details From %s ----> %s ----- starteDateTime: %s, EndDateTime: %s",
                relProp[0], relProp[1], nextDate, endDate));

        //Fetching type basis relationship property from edge
        Optional<? extends BaseMedium> optionalBaseMedium = find(mediumService, relProp, nextDate,
            endDate, routeSort);

        if (optionalBaseMedium.isPresent()) {
          BaseMedium baseMedium = optionalBaseMedium.get();
          nextDate = baseMedium.getEndDate();
          // Assuming layover should not be more than 1 days for a customer.
          endDate = RouteUtil.addHours(nextDate, 24 * 60L);
          transfers.add(buildRoute(baseMedium, relProp[2]));
          totalFare = totalFare.add(baseMedium.getFare());
          duration += baseMedium.getDuration();
        } else {
          //If nothing is available then possible path will be skipped.
          routeFound = Boolean.FALSE;
        }

      }
      if (routeFound) {
        routes.setTransfers(transfers);
        routes.setTotalFare(totalFare);
        routes.setTotalDuration(duration);
        routesList.add(routes);
      }
    }
    sort(routeSort,routesList);
    searchResult.setRoutes(routesList);
    return searchResult;
  }


  private Optional<? extends BaseMedium> find(IMediumService mediumService, String[] relProp,
      Date startDate, Date endDate, String routeSort) {
    if (RouteUtil.CHEAPEST.equalsIgnoreCase(routeSort)) {
      return mediumService.
          findCheapestBySrcAndByDestAndByDate(relProp[0], relProp[1], startDate, endDate);
    }
    if (RouteUtil.SHORTEST.equalsIgnoreCase(routeSort)) {
      return mediumService.
          findShortestBySourceAndDestinationAndByDate(relProp[0], relProp[1], startDate, endDate);
    } else {
      throw new IllegalArgumentException("Not supported route Criteria");
    }

  }

  private void sort(String routeSort, List<Routes> routesList) {
    if (RouteUtil.CHEAPEST.equalsIgnoreCase(routeSort)) {
      routesList.sort(Comparator.comparing(Routes::getTotalFare));

    }
    if (RouteUtil.SHORTEST.equalsIgnoreCase(routeSort)) {
      routesList.sort(Comparator.comparing(Routes::getTotalDuration));

    } else {
      throw new IllegalArgumentException("Not supported route Criteria");
    }


  }

  private Transfer buildRoute(BaseMedium baseMedium, String type) {
    String code = null;
    if (baseMedium instanceof Flight) {
      code = ((Flight) baseMedium).getFlight_Id();
    } else if (baseMedium instanceof Bus) {
      code = ((Bus) baseMedium).getBus_Id();
    }
    return Transfer.builder().destination(baseMedium.getDestination())
        .endDate(baseMedium.getEndDate()).code(code)
        .fare(baseMedium.getFare()).startDate(baseMedium.getStartDateTime())
        .source(baseMedium.getSource()).type(type).
            startTime(baseMedium.getStartTime()).duration(baseMedium.getDuration()).build();


  }

}
