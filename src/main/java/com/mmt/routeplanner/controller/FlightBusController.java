package com.mmt.routeplanner.controller;

import com.mmt.routeplanner.entity.BaseMedium;
import com.mmt.routeplanner.entity.Bus;
import com.mmt.routeplanner.entity.Flight;
import com.mmt.routeplanner.model.BusEvent;
import com.mmt.routeplanner.model.FlightEvent;
import com.mmt.routeplanner.service.BusService;
import com.mmt.routeplanner.service.EventRouter;
import com.mmt.routeplanner.service.FlightService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/${base.version}/route")
@CrossOrigin
public class FlightBusController {

  @Autowired
  private EventRouter eventRouter;

  @Autowired
  private FlightService flightService;

  @Autowired
  private BusService busService;

  @PostMapping("/flight")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong")})
  public Flight flight(@Valid @RequestBody FlightEvent flightEvent, BindingResult result,
      HttpServletRequest httpServletRequest) {

    return eventRouter.processEvent(flightEvent);
  }


  @PostMapping("/bus")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong")})
  public Bus bus(@Valid @RequestBody BusEvent busEvent, BindingResult result,
      HttpServletRequest httpServletRequest) {
    return eventRouter.processEvent(busEvent);
  }

  @GetMapping("/bus/all")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong")})
  public List<? extends BaseMedium> allBus() {
    return busService.findAll();
  }

  @GetMapping("/flight/all")
  @ApiResponses(value = {
      @ApiResponse(code = 400, message = "Something went wrong")})
  public List<? extends BaseMedium> allFlight() {
    return flightService.findAll();
  }

}
