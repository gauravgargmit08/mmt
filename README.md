# MMT Route Planner

## Pointers
- Layover should not be more than 1 days for a customer.
- Program is searching top 50 routes over maximum of 10 hopes in the route.
- It uses **JGRAPHT** for storing routes of flight and buses.Medium of travel like Bus and Flight is used as releationship property. We can use Database like Neo4J graph based Database as well. 
  Data modelling of Neo4j which have more 360 view of relationships between from and to ,flexibility and better performance. Due to time constraint using in-memory graph based library.
- Program is using ConcurrentHashMap to keep track of added route in routeGraph. 
  If already added with same type like Flight no need to add again.We can use distributed Cache like Redis or Aerospike for better performance and feautures
- Two kafka topic is being polled for the consumption of events mentioned in the file. These topic will be insert data into store and parllely it will also create route graph.
- Program is using in-memory H2 DB for Development. We should use Document based Db like Elastic or MongoDb for scalability and performance or even Oracle could also do the trick.
- It returns the List in ascending order of cheapest and shortest route.

