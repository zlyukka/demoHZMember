package com.example.hazelcast.services;

import com.example.hazelcast.dto.User;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ReplicatedMap;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Vitaliy on 25.12.2019.
 */
@Service
public class ReplicatedMapServiceImpl implements ReplicatedMapService{

    private ReplicatedMap<Long, User> userConcurrentHashMap;

    public ReplicatedMapServiceImpl(HazelcastInstance hazelcastInstance){
        userConcurrentHashMap = hazelcastInstance.getReplicatedMap("userReplicated");
    }

    @PostConstruct
    private void init(){
        List<User> users = Arrays.asList(
                new User(4l, "Vasya", "4444444", "k1"),
                new User(5l, "Vova", "55555555", "p2"),
                new User(6l, "Kolya", "666666666", "s1")
        );
        saveAll(users);
    }

    @Override
    public Iterable<User> getAll() {
        return userConcurrentHashMap.values();
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.ofNullable(userConcurrentHashMap.get(id));
    }

    @Override
    public User save(User value) {
        userConcurrentHashMap.put(value.getId(), value);
        return value;
    }

    @Override
    public void saveWithoutReturn(User value) {

    }

    @Override
    public Iterable<User> saveAll(List<User> values) {
        Map<Long, User> userMap = values.stream()
                .collect(Collectors.toMap(k -> k.getId(), v -> v));
        userConcurrentHashMap.putAll(userMap);
        return values;
    }

    @Override
    public void remove(Long id) {
        userConcurrentHashMap.remove(id);
    }
}
