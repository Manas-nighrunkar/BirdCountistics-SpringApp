package com.birdCountistics.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.birdCountistics.api.collection.User;

public interface UserRepository extends MongoRepository<User, String> {

	@Query("{'API_KEY':'?0'}")
	User findUserByKey(String API_KEY);
}
