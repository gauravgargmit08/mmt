# MMT Route Planner

## Pointers
- Layover should not be more than 1 days for a customer.
- Program is searching top 50 routes over maximum of 10 hopes in the route.
- It uses **JGRAPHT** for storing routes of flight and buses. Medium of travel like Bus and Flight is used as releationship property.Database like Neo4J graph based is better candidate for this use case. 
  Data modelling of Neo4j which have more 360 view of relationships between from and to ,flexibility and better performance. Due to time constraint using in-memory graph based library.
- Program is using ConcurrentHashMap to keep track of added route in routeGraph. 
  If already added with same type like Flight no need to add again.We can use distributed Cache like Redis or Aerospike for better performance and features.
- Two kafka topic is being polled for the consumption of events mentioned in the file. These events will be sink into store and in parallel it will also create route graph.
 We can create separate pipeline for the operations
- Program is using in-memory H2 DB for Development. We should use Document based Db like Elastic or MongoDb for scalability and performance or even Oracle could also do the trick.
- It returns the List in ascending order of cheapest and shortest route.
- Send Kafka Payload in same format only as validations are not in place in **MVP**.
- Test cases to be covered in the later stage.
### Graph Diagram
![Graph](Graph.png)


#### Model 
![ApiModel](APIModel.png)

- Swagger API Link : http://127.0.0.1:5052/swagger-ui.html#

#### Kafka Pipeline
- If Environment is not available we can disable in config/application.properties
  >kafka.enable = false 
- Topics:
  - Bus: **route-planner-bus-events**
    >{"from":"B","to":"D","date":"2021-01-08","duration":120,"fare":25,"startTime":"18:00","bus_Id":"2d3bed04-21e7-42ce-99b8-b1038ed02bca"}
  - Flight: **route-planner-flight-events**
    > {"from":"B","to":"D","date":"2021-01-08","duration":120,"fare":25,"startTime":"18:00","flight_Id":"2d3bed04-21e7-42ce-99b8-b1038ed02bca"}

