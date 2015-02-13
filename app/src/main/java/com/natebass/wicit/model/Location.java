package com.natebass.wicit.model;

import java.util.HashMap;
import java.util.Map;

public class Location {

    private Boolean needsRecoding;
    private String longitude;
    private String latitude;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     * The needsRecoding
     */
    public Boolean getNeedsRecoding() {
        return needsRecoding;
    }

    /**
     *
     * @param needsRecoding
     * The needs_recoding
     */
    public void setNeedsRecoding(Boolean needsRecoding) {
        this.needsRecoding = needsRecoding;
    }

    /**
     *
     * @return
     * The longitude
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
