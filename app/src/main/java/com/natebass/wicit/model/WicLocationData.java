package com.natebass.wicit.model;

import java.util.HashMap;
import java.util.Map;

public class WicLocationData {
    private String zipCode;
    private Location location;
    private String county;
    private String secondAddress;
    private String address;
    private String vendor;
    private String city;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode The zip_code
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * @return The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * @return The county
     */
    public String getCounty() {
        return county;
    }

    /**
     * @param county The county
     */
    public void setCounty(String county) {
        this.county = county;
    }

    /**
     * @return The secondAddress
     */
    public String getSecondAddress() {
        return secondAddress;
    }

    /**
     * @param secondAddress The second_address
     */
    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    /**
     * @return The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return The vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * @param vendor The vendor
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

