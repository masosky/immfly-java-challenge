package com.immfly.api.fights;

import com.immfly.api.fights.handlers.GetFlightHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/flight-information")
public class FlightApiController implements FlightApiContract {
    @Autowired
    GetFlightHandler getFlightHandler;

    @Override
    @RequestMapping(value = "/{tail-number}/{flight-number}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Object getFlight(@PathVariable("tail-number") final String tailNumber, @PathVariable("flight-number") final String flightNumber) {
        return getFlightHandler.handleGetFlight(tailNumber, flightNumber);
    }
}
