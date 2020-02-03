package com.example.hazelcast.controller;

import com.example.hazelcast.dto.User;
import com.example.hazelcast.services.Crud;
import com.example.hazelcast.services.UserService;
import com.example.hazelcast.services.UserServicesImpl;
import com.example.hazelcast.util.ResponseWithExecutionTime;
import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitaliy on 17.12.2019.
 */
@RestController
public class StartController {

    UserService userService;

    public StartController(UserService userService){
        this.userService = userService;
    }

    @PostMapping(value = "/get/user/add/{count}")
    private String fillElastic(@PathVariable(value = "count") Long count){
        for(long i =0; i<count; i++) {
            userService.save(new User(i, "Kolya", "666666"+i, "s1"));
        }
        return count+" users were added";
    }

    @GetMapping(value = "/get/user/by/id/{id}")
    public ResponseEntity<ResponseWithExecutionTime> getUserById(@PathVariable(value = "id") Long id){
        return processRequest(() -> userService.getById(id).orElse(new User()));
    }

    @GetMapping(value = "/get/user/all")
    public ResponseEntity<ResponseWithExecutionTime> getAllUser(){
        return processRequest(() -> Lists.newArrayList(userService.getAll()));
    }

    @GetMapping(value = "/get/user/by/ids/{idsList}")
    public ResponseEntity<ResponseWithExecutionTime> getUsersByIds(@PathVariable(value = "idsList") List<Long> ids){
        return processRequest(() -> Lists.newArrayList(userService.getUserByIdsList(ids)));
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

    @PostMapping(value = "/save/userWithoutReturn")
    public ResponseEntity<String> saveUserWithoutReturn(@RequestBody User user){
        userService.saveWithoutReturn(user);
        return new ResponseEntity("user was save", HttpStatus.OK);
    }

    @PostMapping(value = "/save/users")
    public ResponseEntity<ResponseWithExecutionTime> saveUsers(@RequestBody List<User> users){
        return processRequest(() -> userService.saveAll(users));
    }

    private <T> ResponseEntity<ResponseWithExecutionTime> processRequest(Supplier<T> supplier){
        long startTime = System.currentTimeMillis();
        T object = supplier.get();
        return new ResponseEntity<>(new ResponseWithExecutionTime<T>(object, System.currentTimeMillis()-startTime), HttpStatus.OK);
    }
}
