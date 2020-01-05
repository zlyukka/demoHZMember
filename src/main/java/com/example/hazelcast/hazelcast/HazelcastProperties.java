package com.example.hazelcast.hazelcast;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Created by Vitaliy on 18.12.2019.
 */
@Data
@ConfigurationProperties("hazelcast")
public class HazelcastProperties {
    private String host;

    private Integer port;

    private String members;

    private int queueTtl;

    private String groupName;

    private String mancenterUrl;

    private Map<String, String> config;
}
