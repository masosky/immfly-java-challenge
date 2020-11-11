# immfly-java-challenge

## Dev
```docker-compose -f docker-compose-local.yml up```

```mvn spring-boot:run -Plocaltest```

## Test
```docker-compose -f docker-compose-local.yml up```

```mvn clean test -Plocaltest```


## Production
1

```mvn clean package -Pproduction```

2

```docker-compose build --no-cache```

3

```docker-compose up```