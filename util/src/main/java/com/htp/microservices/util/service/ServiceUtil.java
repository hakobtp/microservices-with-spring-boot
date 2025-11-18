package com.htp.microservices.util.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@Component
public class ServiceUtil {

    private final String port;

    @Getter
    private final String serviceAddress;

    public ServiceUtil(@Value("${server.port}") String port) {
        this.port = port;
        this.serviceAddress = buildServiceAddress();
    }

    private String buildServiceAddress() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostName = localHost.getHostName();
            String hostAddress = localHost.getHostAddress();

            return hostName + "/" + hostAddress + ":" + port;

        } catch (UnknownHostException e) {
            log.warn("Could not determine host address, using defaults.", e);
            return "unknown-host/unknown-ip:" + port;
        }
    }
}
