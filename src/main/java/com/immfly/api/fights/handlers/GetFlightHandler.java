package com.immfly.api.fights.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.immfly.external.service.ExternalFlightService;
import com.immfly.external.service.response.FlightInformationResponse;
import com.immfly.redis.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
public class GetFlightHandler {
    private static final String EMPTY_RESPONSE = "{}";
    private static final Logger logger = LogManager.getLogger(GetFlightHandler.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final Jedis jedis;
    private final ExternalFlightService externalFlightService;

    public GetFlightHandler(@Autowired RedisService redisService, @Autowired ExternalFlightService externalFlightService) {
        this.jedis = redisService.getJedisInstance();
        this.externalFlightService = externalFlightService;
    }

    public Object handleGetFlight(final String tailNumber, final String flightNumber) {
        // We are getting the flight in Redis (Hash Type). <String, Map<String, String>>. Stored as <TailNumber, <FlightNumber, FlightInformation>>
        String flightInformation = jedis.hget(tailNumber, flightNumber);
        if (flightInformation == null) {
            // Flight is not in Redis, we call the external service and try to store it in Redis Cache.
            List<FlightInformationResponse> flightInformationResponseList = externalFlightService.getFlights(tailNumber);
            if (flightInformationResponseList != null) {
                saveFlightsToCache(tailNumber, flightInformationResponseList);
                return getSpecificFlight(flightNumber, flightInformationResponseList);
            } else {
                // This Combination of TailNumber and FlightNumber does not exist.
                logger.debug("No match found for: " + tailNumber + " " + flightNumber);
                return EMPTY_RESPONSE;
            }
        }
        return flightInformation;
    }

    /**
     * Returns a Specific Flight as JSON
     * @param flightNumber
     * @param flightInformationResponseList
     * @return Flight Information as JSON or EMPTY JSON if not found.
     */
    private String getSpecificFlight(String flightNumber, List<FlightInformationResponse> flightInformationResponseList) {
        final List<FlightInformationResponse> flightToReturn = flightInformationResponseList
                .stream()
                .filter(flightInfo -> flightNumber.equals(flightInfo.getFlightnumber()))
                .limit(1)
                .collect(Collectors.toList());
        if (flightToReturn.size() == 1) {
            try {
                return OBJECT_MAPPER.writeValueAsString(flightToReturn.get(0));
            } catch (JsonProcessingException e) {
                logger.error("Error when parsing Flight", e);
            }
        }
        return EMPTY_RESPONSE;
    }

    private void saveFlightsToCache(final String tailNumber, final List<FlightInformationResponse> flightInformationResponseList) {
        AtomicBoolean error = new AtomicBoolean(false);
        // We have to transform the List of FLights to a Map<String, String> for Redis Cache.
        Map<String, String> flightInformationMap = flightInformationResponseList.stream().collect(Collectors.toMap(FlightInformationResponse::getFlightnumber, flightInfo -> {
            try {
                return OBJECT_MAPPER.writeValueAsString(flightInfo);
            } catch (JsonProcessingException e) {
                logger.error("Error when parsing FlightInformation", e);
                error.set(true);
                return "{}";
            }
        }));

        // If no error when parsing we update the Redis Cache
        if (!error.get()) {
            jedis.hmset(tailNumber, flightInformationMap);
        }
    }
}
