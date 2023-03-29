package com.birdCountistics.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.birdCountistics.api.collection.Bird;

public interface BirdRepository extends MongoRepository<Bird, String> {

	@Query("{'speciesName':'?0'}")
	Bird findBirdByName(String name);
}
