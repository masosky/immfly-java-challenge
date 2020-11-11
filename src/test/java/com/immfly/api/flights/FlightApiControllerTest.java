package com.immfly.main;
import static org.assertj.core.api.Assertions.assertThat;

import com.immfly.api.fights.FlightApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "localtest")
public class MainTest {

    @Autowired
    private FlightApiController flightApiController;

    @Test
    public void testEnv() {
        assertThat(flightApiController.getFlight("1", "2")).isEqualTo("12");
    }
}
