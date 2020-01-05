package com.example.hazelcast.controller;

import com.example.hazelcast.dto.User;
import com.example.hazelcast.services.Crud;
import com.example.hazelcast.services.ReplicatedMapService;
import com.example.hazelcast.services.ReplicatedMapServiceImpl;
import com.example.hazelcast.util.ResponseWithExecutionTime;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vitaliy on 25.12.2019.
 */
@Controller
@RequestMapping("/replicatedMap")
public class ReplicatedMapController {

    Crud<User> userService;

    public ReplicatedMapController(ReplicatedMapService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/get/user/by/id/{id}")
    public ResponseEntity<ResponseWithExecutionTime> getUserById(@PathVariable(value = "id") Long id){
        return processRequest(() -> userService.getById(id).orElse(new User()));
    }

    @GetMapping(value = "/get/user/all")
    public ResponseEntity<ResponseWithExecutionTime> getAllUser(){
        return processRequest(() -> Lists.newArrayList(userService.getAll()));
    }

    @DeleteMapping(value = "/delete/user/{id}")
    public ResponseEntity<ResponseWithExecutionTime> deleteUserById(@PathVariable(value = "id") Long id){
        return processRequest(() -> {
            userService.remove(id);
            return new User();
        });
    }

    @PostMapping(value = "/save/user")
    public ResponseEntity<ResponseWithExecutionTime> saveUser(@RequestBody User user){
        return processRequest(() -> userService.save(user));
    }

    private <T> ResponseEntity<ResponseWithExecutionTime> processRequest(Supplier<T> supplier){
        long startTime = System.currentTimeMillis();
        T object = supplier.get();
        return new ResponseEntity<>(new ResponseWithExecutionTime<T>(object, System.currentTimeMillis()-startTime), HttpStatus.OK);
    }

}
