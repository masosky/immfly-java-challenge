# immfly-java-challenge

## Introduction
I know there are several ways to improve this, but I have dedicated a lot of time in order to make it professional.
Please read the instructions carefully and if you have question do not hesitate to contact me. Developer information has been set on pom.xml

## Setup
- Java 8
- Docker
- Maven 3

## Architecture
### Main.java
Starts the Spring Boot Application
### RedisService.java
Provides a Singleton Instances that contains a Jedis Client
### ExternalFlightService.java
This class simulates a Real API that provides information of FLights. In order to make it simpler the class is loading the information of some properties files and store it on its cache.
I could have implemented MockServer but with these we can use it in tests and in local.
### FlightInformationResponse.java
This class has been annotated with @JsonSerialize in order to use it with ObjectMapper from Jackson.
It emulates the Serializer of a API JSON Response
Also, this class has been marked with @JsonIgnoreProperties that makes the serialization success if extra properties are added.
### FlightApiController.java
Rest Controller for Spring Boot that sets the endpoint ```/v1/flight-information/{tail-number}/{flight-number}```
### GetFlightHandler.java
Handler class that is implementing all the logic for the endpoint ```/v1/flight-information/{tail-number}/{flight-number}```
1. Search for a Flight in Redis Cache. Flights are stored as Hash <key, <field, value>>. This archytecture allows us to retrieve easily a Flight using a TailNumber and FlightNumber
2. If Flight is not Found in Cache we Call the External Service and we try to update the Cache if the Data is valid.
3. If no Flight is found we return Empty Object ````{}````
## Logging
Log4j2 with the corresponding ```log4j2.xml```

## Javadoc
I do not like to use too many comments on code because normally developers forgot to update the Javadoc.
What usually happens is that the Javadoc is giving outdated information.
That is why I did not add comments on the code.

The code should be self-explanatory or contain a really few lines of comments.
I just added some lines in order to make it more explicit.

## Api Error Handling
Error handling has not been applied due to a lack of time.
Using @ControllerAdvice for error handling is a good solution in Spring.

## Environments

### Local
Redis and Redis Commander are dockerized and SrpingBoot Api is outside docker for faster developing.
Due to the ExternalFLightService.java we can query the endpoint ``````/v1/flight-information/{tail-number}/{flight-number}`````` with these fake data TailNumbers ```["EC-AIN", "EC-CGS", "EC-MYT"]```

```docker-compose -f docker-compose-local.yml up```

```mvn spring-boot:run -Plocaltest```


Now you can access a URL like :

```http://localhost:8080/v1/flight-information/{tail-number}/{flight-number}```

You can access Redis Commander and check all the Redis keys through:

```http://localhost:8081/```

### Production All Dockerized
This environment simulates all the services inside Docker.

Steps:

```mvn clean package -Pproduction```


```docker-compose build --no-cache```


```docker-compose up```

## Test and Coverage
### Test
We must run the docker-compose-local environment. Tests are using a dockerized Redis instance.

Redis has not been mocked in order to make the tests closer to a production environment.

```mvn clean test -Plocaltest```
### Coverage
```mvn clean test && mvn jacoco:report```


