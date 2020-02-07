package com.example.hazelcast.controller;

import com.example.hazelcast.dto.User;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Vitaliy on 05.01.2020.
 */
@RestController
public class HzEntityController {

    private HazelcastInstance hazelcastInstance;

    public HzEntityController(HazelcastInstance hazelcastInstance){
        this.hazelcastInstance =  hazelcastInstance;
    }

    @GetMapping("/hz/get/distributed/{hzInstanceName}")
    private Map getDistrebutedHZnstance(@PathVariable(name = "hzInstanceName") String instanceName){
        Map map = hazelcastInstance.getMap(instanceName);
        return map;
    }

    @GetMapping("/hz/get/replicated/{hzInstanceName}")
    private Map getReplicatedHZnstance(@PathVariable(name = "hzInstanceName") String instanceName){
        return hazelcastInstance.getReplicatedMap(instanceName);
    }

}
