# MMT Route Planner

## Pointers
- RoutePlanner is a Spring Boot Application with Endpoints for searching or adding Bus and Flight into Database.
- Assuming Layover between two flights or Bus should not be more than 1 days for customer convenience .
- Program is searching top 50 routes over maximum of 10 hopes in the routes graph.
  - We can create dynamic config as per business and technical feasibility.
- It uses **JGRAPHT** for storing routes of flight and buses. Medium of travel like Bus and Flight is used as releationship property.Database like Neo4J graph based is better candidate for this use case. 
  Data modelling of Neo4j which have more 360 view of relationships between from and to ,flexibility and better performance. Due to time constraint using in-memory graph based library.
- Program is using ConcurrentHashMap to keep track of added route in routeGraph. 
  If already added with same type like Flight no need to add again.We can use distributed Cache like Redis or Aerospike for better performance and features.
- Two kafka topic is being polled for the consumption of events mentioned in the file. These events will be sink into store and in parallel it will also create route graph.
 We can create separate pipeline for the operations.
  - One Pipeline or Stream for Sink into Db or may be kafka connect into elastic
  - Second pipeline to sink into our Graph route Store.
- Program is using in-memory H2 DB for Development. We should use Document based Db like Elastic or MongoDb for scalability and performance.
- It returns the List in ascending order of cheapest and shortest route.
- Send Kafka Payload in same format only as validations are not in place in **MVP**.
- JUNIT for main Business case is covered.
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
    
    
##### Test Data
- flight: From [A] -> [B]----dateTime: [Jan 08 12:00:00] --endTime: [Jan 08 14:00:00]----Fare:[10]---Duration:[120]
- flight: From [B] -> [C]----dateTime: [Jan 08 15:00:00] --endTime: [Jan 08 17:00:00]----Fare:[20]---Duration:[120]
- flight: From [C] -> [D]----dateTime: [Jan 08 18:00:00] --endTime: [Jan 08 20:00:00]----Fare:[120]---Duration:[120]
- flight: From [A] -> [D]----dateTime: [Jan 08 18:00:00] --endTime: [Jan 08 21:00:00]----Fare:[30]---Duration:[180]
- flight: From [B] -> [D]----dateTime: [Jan 08 18:00:00] --endTime: [Jan 08 20:00:00]----Fare:[25]---Duration:[120]
- bus:    From [A] -> [B]----dateTime: [Jan 08 12:00:00] --endTime: [Jan 08 16:00:00]----Fare:[5]---Duration:[240]
- bus:    From [B] -> [D]----dateTime: [Jan 08 13:00:00] --endTime: [Jan 09 01:00:00]----Fare:[1]---Duration:[720]

- Cheapest Route from A --> D 
  ```json
  {
    "destination": "D",
    "source": "A",
    "routeSort": "CHEAPEST",
    "searchDate": "2021-01-08T00:00:00.000+0000",
    "routes": [
      {
        "totalFare": 30,
        "totalDuration": 180,
        "transfers": [
          {
            "source": "A",
            "destination": "D",
            "code": "6414a249-7f1f-46ce-8a1c-39384a21fa94",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 18:00:00",
            "endDateTime": "2021-01-08 21:00:00",
            "duration": 180,
            "fare": 30,
            "startTime": "18:00",
            "type": "FLIGHT"
          }
        ]
      },
      {
        "totalFare": 30,
        "totalDuration": 360,
        "transfers": [
          {
            "source": "A",
            "destination": "B",
            "code": "bd755b5f-f59a-4d71-8fe3-37d987aa3099",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 12:00:00",
            "endDateTime": "2021-01-08 16:00:00",
            "duration": 240,
            "fare": 5,
            "startTime": "12:00",
            "type": "BUS"
          },
          {
            "source": "B",
            "destination": "D",
            "code": "72f6a63e-f639-49c5-96fd-05c23a7a61e3",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 18:00:00",
            "endDateTime": "2021-01-08 20:00:00",
            "duration": 120,
            "fare": 25,
            "startTime": "18:00",
            "type": "FLIGHT"
          }
        ]
      },
      {
        "totalFare": 35,
        "totalDuration": 240,
        "transfers": [
          {
            "source": "A",
            "destination": "B",
            "code": "268b059d-fff7-443d-a628-4b2c8499de57",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 12:00:00",
            "endDateTime": "2021-01-08 14:00:00",
            "duration": 120,
            "fare": 10,
            "startTime": "12:00",
            "type": "FLIGHT"
          },
          {
            "source": "B",
            "destination": "D",
            "code": "72f6a63e-f639-49c5-96fd-05c23a7a61e3",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 18:00:00",
            "endDateTime": "2021-01-08 20:00:00",
            "duration": 120,
            "fare": 25,
            "startTime": "18:00",
            "type": "FLIGHT"
          }
        ]
      },
      {
        "totalFare": 150,
        "totalDuration": 360,
        "transfers": [
          {
            "source": "A",
            "destination": "B",
            "code": "268b059d-fff7-443d-a628-4b2c8499de57",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 12:00:00",
            "endDateTime": "2021-01-08 14:00:00",
            "duration": 120,
            "fare": 10,
            "startTime": "12:00",
            "type": "FLIGHT"
          },
          {
            "source": "B",
            "destination": "C",
            "code": "9f864d2c-0838-473d-be86-82748df2869b",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 15:00:00",
            "endDateTime": "2021-01-08 17:00:00",
            "duration": 120,
            "fare": 20,
            "startTime": "15:00",
            "type": "FLIGHT"
          },
          {
            "source": "C",
            "destination": "D",
            "code": "1ca55591-2664-4ff2-80e6-b94252f824ec",
            "startDate": "2021-01-08",
            "startDateTime": "2021-01-08 18:00:00",
            "endDateTime": "2021-01-08 20:00:00",
            "duration": 120,
            "fare": 120,
            "startTime": "18:00",
            "type": "FLIGHT"
          }
        ]
      }
    ]
  }
  
  ```
- Shortest Duration Route from A --> D 
```json
{
  "destination": "D",
  "source": "A",
  "routeSort": "SHORTEST",
  "searchDate": "2021-01-08T00:00:00.000+0000",
  "routes": [
    {
      "totalFare": 30,
      "totalDuration": 180,
      "transfers": [
        {
          "source": "A",
          "destination": "D",
          "code": "6414a249-7f1f-46ce-8a1c-39384a21fa94",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 18:00:00",
          "endDateTime": "2021-01-08 21:00:00",
          "duration": 180,
          "fare": 30,
          "startTime": "18:00",
          "type": "FLIGHT"
        }
      ]
    },
    {
      "totalFare": 35,
      "totalDuration": 240,
      "transfers": [
        {
          "source": "A",
          "destination": "B",
          "code": "268b059d-fff7-443d-a628-4b2c8499de57",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 12:00:00",
          "endDateTime": "2021-01-08 14:00:00",
          "duration": 120,
          "fare": 10,
          "startTime": "12:00",
          "type": "FLIGHT"
        },
        {
          "source": "B",
          "destination": "D",
          "code": "72f6a63e-f639-49c5-96fd-05c23a7a61e3",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 18:00:00",
          "endDateTime": "2021-01-08 20:00:00",
          "duration": 120,
          "fare": 25,
          "startTime": "18:00",
          "type": "FLIGHT"
        }
      ]
    },
    {
      "totalFare": 30,
      "totalDuration": 360,
      "transfers": [
        {
          "source": "A",
          "destination": "B",
          "code": "bd755b5f-f59a-4d71-8fe3-37d987aa3099",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 12:00:00",
          "endDateTime": "2021-01-08 16:00:00",
          "duration": 240,
          "fare": 5,
          "startTime": "12:00",
          "type": "BUS"
        },
        {
          "source": "B",
          "destination": "D",
          "code": "72f6a63e-f639-49c5-96fd-05c23a7a61e3",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 18:00:00",
          "endDateTime": "2021-01-08 20:00:00",
          "duration": 120,
          "fare": 25,
          "startTime": "18:00",
          "type": "FLIGHT"
        }
      ]
    },
    {
      "totalFare": 150,
      "totalDuration": 360,
      "transfers": [
        {
          "source": "A",
          "destination": "B",
          "code": "268b059d-fff7-443d-a628-4b2c8499de57",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 12:00:00",
          "endDateTime": "2021-01-08 14:00:00",
          "duration": 120,
          "fare": 10,
          "startTime": "12:00",
          "type": "FLIGHT"
        },
        {
          "source": "B",
          "destination": "C",
          "code": "9f864d2c-0838-473d-be86-82748df2869b",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 15:00:00",
          "endDateTime": "2021-01-08 17:00:00",
          "duration": 120,
          "fare": 20,
          "startTime": "15:00",
          "type": "FLIGHT"
        },
        {
          "source": "C",
          "destination": "D",
          "code": "1ca55591-2664-4ff2-80e6-b94252f824ec",
          "startDate": "2021-01-08",
          "startDateTime": "2021-01-08 18:00:00",
          "endDateTime": "2021-01-08 20:00:00",
          "duration": 120,
          "fare": 120,
          "startTime": "18:00",
          "type": "FLIGHT"
        }
      ]
    }
  ]
}
```
# Configuring Route Planner

##       Prerequisite to run
           1. JDK - 8
##        Build instructions
	git clone https://github.com/gauravgargmit08/routeplanner.git
	cd routeplanner/
	git checkout master
	mvn clean install
	cd target
##         Run application
    mkdir 
    mkdir config
    cp routeplanner/config .
    nohup java -jar routeplanner 1.0-SNAPSHOT.jar



