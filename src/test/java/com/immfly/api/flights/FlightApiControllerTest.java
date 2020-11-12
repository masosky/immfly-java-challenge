package com.immfly.api.flights;
import static org.assertj.core.api.Assertions.assertThat;

import com.immfly.api.fights.FlightApiController;
import com.immfly.api.fights.handlers.GetFlightHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ActiveProfiles(profiles = "localtest")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
public class FlightApiControllerTest {

    @Autowired
    private FlightApiController flightApiController;

    @MockBean
    private GetFlightHandler getFlightHandler;

    @Test
    public void getFlight() {
        Mockito.when(getFlightHandler.handleGetFlight("1", "2")).thenReturn("12");

        final String result = (String)flightApiController.getFlight("1", "2");

        assertThat(result).isEqualTo("12");
    }
}
