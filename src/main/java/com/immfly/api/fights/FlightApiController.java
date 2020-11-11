package com.immfly.api.fights;

import com.immfly.redis.RedisService;
import com.immfly.redis.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping(path = "/v1/flight-information")
public class FlightApiController implements FlightApiContract {
    @Autowired
    RedisService redisService;
    @Override
    @RequestMapping(value = "/{tail-number}/{flight-number}", method = RequestMethod.GET)
    @ResponseBody
    public String getFlight(@PathVariable("tail-number") String tailNumber, @PathVariable("flight-number") String flightNumber) {
        Jedis jedis = redisService.getJedisInstance();
        return tailNumber + flightNumber;
    }
}
