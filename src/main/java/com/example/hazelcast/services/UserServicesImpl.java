package com.example.hazelcast.services;

import com.example.hazelcast.dao.UserDao;
import com.example.hazelcast.dto.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Vitaliy on 17.12.2019.
 */
@Service
public class UserServicesImpl implements UserService {

    private UserDao userDao;

    public UserServicesImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @PostConstruct
    private void init(){

    }

    @Override
    @Cacheable(value = "user", key = "#root.methodName")
    public Iterable<User> getAll() {
        return userDao.findAll();
    }

    @Override
    @Cacheable("user")
    public Optional<User> getById(Long id) {
        return userDao.findById(id);
    }

    @Override
    @CachePut(value = "user", key = "#value.id")
    public User save(User value) {
        return userDao.save(value);
    }

    @Override
    @CachePut(value = "user", key = "#values.![id]")
    public Iterable<User> saveAll(List<User> values) {
        return userDao.saveAll(values);
    }

    @Override
    @CacheEvict(value = "user", beforeInvocation = true)
    public void remove(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Cacheable(value = "user")
    public Iterable<User> getUserByIdsList(List<Long> ids) {
        return userDao.findAllById(ids);
    }
}
