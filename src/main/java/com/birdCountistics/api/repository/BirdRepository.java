package com.birdCountistics.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.birdCountistics.api.collection.Birds;

public interface BirdRepository extends MongoRepository<Birds, String> {

	@Query("{'staticBirdFields.speciesName':'?0'}")
	Birds findBirdByName(String name);

	@Query("{'_id':'?0'}")
	List<Birds> findBirdById(String id);

	@Query("{'staticBirdFields.speciesName' : { $regex: ?0 } }")
	List<Birds> findBirdByRegExName(String domain);

	@Query("{color:'?0'}")
	List<Birds> findBirdByColor(String color);

	@Query("{color : { $regex: ?0 } }")
	List<Birds> findBirdByRegExColor(String domain);

	@Query("{'sightings.territory': '?0'}")
	List<Birds> findBirdsByTerritory(String territory);

//	@Query(value = "{'staticBirdFields.speciesName': '?0'}")
//	List findIdByName(String name);
}
