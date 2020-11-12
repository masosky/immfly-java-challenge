package com.immfly.api.flights;
import static org.assertj.core.api.Assertions.assertThat;

import com.immfly.api.fights.FlightApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles(profiles = "localtest")
@ContextConfiguration
public class FlightApiControllerTest {

    @Autowired
    private FlightApiController flightApiController;

    @Test
    public void getFlight() {
        assertThat(flightApiController.getFlight("1", "2")).isEqualTo("12");
    }
}
