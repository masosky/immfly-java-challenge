package com.immfly.external.service.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonIgnoreProperties
public class Destination {

    private  String code;
    private String city;
    private String alternate_ident;
    private String airport_name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAlternate_ident() {
        return alternate_ident;
    }

    public void setAlternate_ident(String alternate_ident) {
        this.alternate_ident = alternate_ident;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_name(String airport_name) {
        this.airport_name = airport_name;
    }
}
