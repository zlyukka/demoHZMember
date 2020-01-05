package com.example.hazelcast.services;

import com.example.hazelcast.dto.User;

import java.util.List;

/**
 * Created by Vitaliy on 25.12.2019.
 */
public interface UserService extends Crud<User>{

    Iterable<User> getUserByIdsList(List<Long> ids);

}
