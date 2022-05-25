package com.hotel.mgmt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Hotelmgmt.
 *
 * <p>Properties are configured in the {@code application.yml} file. See {@link
 * tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String hostName;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
