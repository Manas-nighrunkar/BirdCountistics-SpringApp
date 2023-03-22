package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.collection.Birds;

public interface BirdRepository extends MongoRepository<Birds, String>{

    @Query("{name:'?0'}")
    List<Birds> findBirdByName(String name); 
    
    @Query("{name : { $regex: ?0 } }")
    List<Birds> findBirdByRegExName(String domain);
 
    @Query("{color:'?0'}")
    List<Birds> findBirdByColor(String color);
 
    @Query("{color : { $regex: ?0 } }")
    List<Birds> findBirdByRegExColor(String domain);
}
