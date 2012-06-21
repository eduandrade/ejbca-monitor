package com.ejbcamonitor.model;

public class CaInfo {

    private String name;
    
    private String healthCheckUrl;

    private CaInfo(String name, String healthCheckUrl) {
        this.name = name;
        this.healthCheckUrl = healthCheckUrl;
    }

    public String getName() {
        return name;
    }

    public String getHealthCheckUrl() {
        return healthCheckUrl;
    }

    @Override
    public String toString() {
        return "CaInfo [name=" + name + ", healthCheckUrl=" + healthCheckUrl + "]";
    }
    
}
