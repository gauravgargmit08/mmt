# MMT Route Planner

Assignment: Design and implement Route Planner
Assumptions:
1. Real time event stream of flights with (FROM, TO, flight_id, date,  duration, fare, startTime, FROM_loc<long, lat>,  TO_loc<long, lat>)
• This event comes, when user searches for flights for a given date and  for a source and destination pair.
2. A real time event stream of buses with (FROM, TO, bus_id, date, duration, fare, startTime, FROM_loc<long, lat>,  TO_loc<long, lat>)
• This event comes, when user searches for buses for a given date and  for a source and a destination pair.
3. A real time event stream of flights with (FROM, TO, flight_id,  date, is_booked).
• This event comes when user books a flight.
4. A real time event stream of buses with (FROM, TO, bus_id, date,  is_booked)
• This event comes when user books a bus.
Glossary
FROM: source station code,
TO: destination station code,
startTime: Start time of the bus/flight from its source.
date: Date of journey user is searching,
fare: Fare details from that source to the destination for the given date starting at  startTime.
FROM_loc: longitude, latitude of the source station code.
TO_loc: longitude, latitude of the destination station code.
duration: time taken to travel from a source to a destination
is_booked: this flag is true if booking is done.
You have to design and implement at least one of the module as per your choice and  make sure the design is scalable to accommodate the other module.
Module1: Cheapest Route
 Develop a service to recommend cheapest routes for a customer looking to go  from a source to a destination for a given date.
 Following pointers to be considered:
• Reachability has to be considered. If route is not possible between source and  the destination, then you can choose to show routes not found or any  suggestion is welcome. We are open for ideas.
• Recommended routes could be a combination of different flights and buses.
Module2: Shortest duration
 Develop a service to recommend shortest routes for a customer looking to go  from a source to a destination for a given date.
• Reachability has to be considered. If route is not possible between source and  the destination, then you can choose to show routes not found or any  suggestion is welcome. We are open for ideas.
• Recommended routes could be a combination of different flights and buses.


#Assuming layover should not be more than 1 days for a customer.