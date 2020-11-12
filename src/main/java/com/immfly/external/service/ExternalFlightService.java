package com.immfly.external.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.immfly.external.service.response.FlightInformationResponse;
import com.immfly.redis.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * This class simulates an API Service that returns information of Flights given a TailNumber
 */
@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ExternalFlightService {
    private static final Logger logger = LogManager.getLogger(ExternalFlightService.class);

    private static final List<String> TAIL_NUMBER_LIST = Arrays.asList("EC-AIN", "EC-CGS", "EC-MYT");
    // Save all flight information in Cache
    private static final Map<String, List<FlightInformationResponse>> FLIGHTS_INFORMATION = new HashMap();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        for (final String tailNumber:TAIL_NUMBER_LIST) {
            try {
                Resource resource = new ClassPathResource(tailNumber + ".json");
                File file = resource.getFile();
                FlightInformationResponse[] flightInformationList = OBJECT_MAPPER.readValue(file, FlightInformationResponse[].class);
                FLIGHTS_INFORMATION.put(tailNumber, Arrays.asList(flightInformationList));
            } catch (Exception e) {
                logger.error("Error when loading FlightInformation Resources in Cache");
            }
        }
        logger.debug("All FlightInformation Resources ahs been loaded successfully in Cache");
        logger.debug("This Service emulates a real API Service");
    }

    public List<FlightInformationResponse> getFlights(final String tailNumber) {
        return FLIGHTS_INFORMATION.get(tailNumber);
    }
}
