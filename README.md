# immfly-java-challenge

## Local
```docker-compose -f docker-compose-local.yml up```

```mvn spring-boot:run -Plocaltest```

Now you can access a URL like ```http://localhost:8080/v1/flight-information/1/2```

## Test
```docker-compose -f docker-compose-local.yml up```

Now run the tests:

```mvn clean test -Plocaltest```

## Coverage
```mvn clean test && mvn jacoco:report```


## Production
1

```mvn clean package -Pproduction```

2

```docker-compose build --no-cache```

3

```docker-compose up```