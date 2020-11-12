package com.immfly.api.fights.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.immfly.external.service.response.FlightInformationResponse;
import com.immfly.redis.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import redis.clients.jedis.Jedis;

import javax.annotation.PostConstruct;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = "localtest")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
@RunWith(JUnit4.class)
public class GetFlightHandlerTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    RedisService redisService;
    @Autowired
    GetFlightHandler handler;

    private Jedis jedis;

    @PostConstruct
    public void postConstruct() {
        this.jedis = redisService.getJedisInstance();
    }

    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        jedis.flushAll();
    }

    @Test
    public void getFlight_NoCacheHit() throws JsonProcessingException {
        final String result = (String) handler.handleGetFlight("EC-MYT", "3");
        final FlightInformationResponse response = OBJECT_MAPPER.readValue(result, FlightInformationResponse.class);

        assertThat(response.getFlightnumber()).isEqualTo("3");
    }

    @Test
    public void getFlight_CacheHit() throws JsonProcessingException {
        Map<String, String> map = new HashMap<>();
        map.put("3", "{\n" +
                "    \"ident\": \"IBB653\",\n" +
                "    \"faFlightID\": \"IBB653-1581399936-airline-0136\",\n" +
                "    \"airline\": \"IBB\",\n" +
                "    \"airline_iata\": \"NT\",\n" +
                "    \"flightnumber\": \"3\",\n" +
                "    \"tailnumber\": \"EC-MYT\",\n" +
                "    \"type\": \"Form_Airline\",\n" +
                "    \"codeshares\": \"IBE123\",\n" +
                "    \"blocked\": false,\n" +
                "    \"diverted\": false,\n" +
                "    \"cancelled\": false,\n" +
                "    \"origin\": {\n" +
                "      \"code\": \"GCXO\",\n" +
                "      \"city\": \"Tenerife\",\n" +
                "      \"alternate_ident\": \"TFN\",\n" +
                "      \"airport_name\": \"Tenerife North (Los Rodeos)\"\n" +
                "    },\n" +
                "    \"destination\": {\n" +
                "      \"code\": \"GCGM\",\n" +
                "      \"city\": \"La Gomera\",\n" +
                "      \"alternate_ident\": \"GMZ\",\n" +
                "      \"airport_name\": \"La Gomera\"\n" +
                "    }\n" +
                "  }");

        jedis.hmset("EC-MYT", map);
        final String result = (String) handler.handleGetFlight("EC-MYT", "3");
        final FlightInformationResponse response = OBJECT_MAPPER.readValue(result, FlightInformationResponse.class);

        assertThat(response.getFlightnumber()).isEqualTo("3");
    }

    @Test
    public void getFlight_DoesNotExist() throws JsonProcessingException {
        final String result = (String) handler.handleGetFlight("aeqwfewfew", "ewfewfewfew");

        assertThat(result).isEqualTo("{}");
    }
}