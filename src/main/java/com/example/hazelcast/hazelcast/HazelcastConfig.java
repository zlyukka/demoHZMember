package com.example.hazelcast.hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.apache.logging.log4j.util.Strings;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Vitaliy on 18.12.2019.
 */
@Configuration
@PropertySource("classpath:hazelcast.properties")
@EnableConfigurationProperties(HazelcastProperties.class)
public class HazelcastConfig {

    private HazelcastProperties hazelcastProperties;

    public HazelcastConfig(HazelcastProperties hazelcastProperties){
        this.hazelcastProperties = hazelcastProperties;
    }

    @Bean
    public Config hazelCastConfig(){
        Config config = new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("user")
                                .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                                //.setTimeToLiveSeconds(25)
                                .setBackupCount(1)
                                .setEvictionPolicy(EvictionPolicy.LRU));
        if (!Strings.isEmpty(hazelcastProperties.getMancenterUrl())) {
            config.getManagementCenterConfig().setEnabled(true);
            config.getManagementCenterConfig().setUrl(hazelcastProperties.getMancenterUrl());
        }
        return config;
    }
}
