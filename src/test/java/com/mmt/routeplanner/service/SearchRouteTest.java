package com.mmt.routeplanner.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.mmt.routeplanner.factory.MediumServiceFactory;
import com.mmt.routeplanner.graph.GraphT;
import com.mmt.routeplanner.graph.Medium;
import com.mmt.routeplanner.helper.Helper;
import com.mmt.routeplanner.model.SearchResult;
import com.mmt.routeplanner.util.MediumType;
import com.mmt.routeplanner.util.RouteUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.jgrapht.GraphPath;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SearchRouteTest {

  @MockBean
  private MediumServiceFactory mediumServiceFactory;

  @MockBean
  private FlightService flightService;

  @MockBean
  private BusService busService;

  @Autowired
  private SearchRoute searchRoute;

  private Date date = RouteUtil.getDateWithoutTimeUsingCalendar(new Date());

  /**
   * Init .
   */
  @Before
  public void setup() {
    //A - B do not have flight on that day Hence it will be ommitted from route List.
    // This will increase conditions coverage in the business logic
    // Routes are A-D FLight
    // A-D Bus
    GraphT.addRoute("A", "B", MediumType.MEDIUM_FLIGHT);
    GraphT.addRoute("B", "C", MediumType.MEDIUM_FLIGHT);
    GraphT.addRoute("C", "D", MediumType.MEDIUM_FLIGHT);
    GraphT.addRoute("A", "D", MediumType.MEDIUM_FLIGHT);
    GraphT.addRoute("A", "D", MediumType.MEDIUM_BUS);
    when(mediumServiceFactory.factory(MediumType.MEDIUM_FLIGHT)).thenReturn(flightService);
    when(mediumServiceFactory.factory(MediumType.MEDIUM_BUS)).thenReturn(busService);

    //Mock db
    when(flightService
        .findCheapestBySrcAndByDestAndByDate(eq("A"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("A", "D", BigDecimal.TEN, new Date(), "18:00", 60L));
    when(flightService
        .findCheapestBySrcAndByDestAndByDate(eq("B"), eq("C"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("B", "C", BigDecimal.ONE, new Date(), "20:00", 60L));
    when(flightService
        .findCheapestBySrcAndByDestAndByDate(eq("C"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("C", "D", BigDecimal.valueOf(2), new Date(), "22:00", 60L));
    when(flightService
        .findCheapestBySrcAndByDestAndByDate(eq("A"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("A", "D", BigDecimal.valueOf(5), new Date(), "22:15", 60L));
    when(busService
        .findCheapestBySrcAndByDestAndByDate(eq("A"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.bus("A", "D", BigDecimal.valueOf(5), new Date(), "22:15", 120L));


    //Mock db
    when(flightService
        .findShortestBySourceAndDestinationAndByDate(eq("A"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("A", "D", BigDecimal.TEN, new Date(), "18:00", 60L));
    when(flightService
        .findShortestBySourceAndDestinationAndByDate(eq("B"), eq("C"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("B", "C", BigDecimal.ONE, new Date(), "20:00", 60L));
    when(flightService
        .findShortestBySourceAndDestinationAndByDate(eq("C"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("C", "D", BigDecimal.valueOf(2), new Date(), "22:00", 60L));
    when(flightService
        .findShortestBySourceAndDestinationAndByDate(eq("A"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.flight("A", "D", BigDecimal.valueOf(5), new Date(), "22:15", 60L));
    when(busService
        .findShortestBySourceAndDestinationAndByDate(eq("A"), eq("D"), any(Date.class), any(Date.class)))
        .thenReturn(Helper.bus("A", "D", BigDecimal.valueOf(5), new Date(), "22:15", 120L));

  }


  @Test
  public void searchRoutesCheapest() {
    List<GraphPath<String, Medium>> paths = GraphT.defaultGetPath("A", "D");
    Date date = RouteUtil.getDateWithoutTimeUsingCalendar(new Date());
    SearchResult searchResult = searchRoute.searchRoutes(paths, date, "A", "D", RouteUtil.CHEAPEST);
    org.junit.Assert.assertEquals(2,searchResult.getRoutes().size());
  }

  @Test
  public void searchRoutesShortest() {
    List<GraphPath<String, Medium>> paths = GraphT.defaultGetPath("A", "D");
    Date date = RouteUtil.getDateWithoutTimeUsingCalendar(new Date());
    SearchResult searchResult = searchRoute.searchRoutes(paths, date, "A", "D", RouteUtil.SHORTEST);
    org.junit.Assert.assertEquals(2,searchResult.getRoutes().size());
  }



  @Test(expected = IllegalArgumentException.class)
  public void wrongRouteSort() {
    List<GraphPath<String, Medium>> paths = GraphT.defaultGetPath("A", "D");
    Date date = RouteUtil.getDateWithoutTimeUsingCalendar(new Date());
    searchRoute.searchRoutes(paths, date, "A", "D", "N");
  }

  @TestConfiguration
  static class SearchRouteTestContextConfiguration {

    @Bean
    public SearchRoute searchRoute() {
      return new SearchRoute();
    }

    @Bean
    public GraphT graphT() {
      return new GraphT();
    }


  }
}
