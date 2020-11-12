package com.immfly.external.service;

import com.immfly.external.service.response.FlightInformationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = "localtest")
@ContextConfiguration
@ExtendWith(MockitoExtension.class)
public class ExternalFlightServiceTest {
    @Autowired
    ExternalFlightService service;

    @Test
    public void getFlightEC_MYT() {
        List<FlightInformationResponse> flightInformationResponseList = service.getFlights("EC-MYT");

        assertThat(flightInformationResponseList.get(0).getFlightnumber()).isEqualTo("3");
        assertThat(flightInformationResponseList.get(1).getFlightnumber()).isEqualTo("4");
    }

    @Test
    public void getFlightEC_CGS() {
        List<FlightInformationResponse> flightInformationResponseList = service.getFlights("EC-CGS");

        assertThat(flightInformationResponseList.get(0).getFlightnumber()).isEqualTo("1");
        assertThat(flightInformationResponseList.get(1).getFlightnumber()).isEqualTo("2");
    }

    @Test
    public void getFlightEC_AIN() {
        List<FlightInformationResponse> flightInformationResponseList = service.getFlights("EC-AIN");

        assertThat(flightInformationResponseList.get(0).getFlightnumber()).isEqualTo("653");
        assertThat(flightInformationResponseList.get(1).getFlightnumber()).isEqualTo("654");
    }
}