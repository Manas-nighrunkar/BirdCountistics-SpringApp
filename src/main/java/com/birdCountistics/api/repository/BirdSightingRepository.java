package com.birdCountistics.api.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.birdCountistics.api.collection.BirdSighting;
import com.birdCountistics.api.collection.Sightings;

public interface BirdSightingRepository extends MongoRepository<Sightings, String> {

	@Query("{'sightings.staticBirdFields.speciesName':'?0'}")
	BirdSighting findBirdByName(String name);

	@Query("{'_id':'?0'}")
	List<BirdSighting> findBirdById(String id);

	@Query("{'staticBirdFields.speciesName' : '?0' }")
	List<Sightings> findSightingByName(String domain);

	@Query("{color:'?0'}")
	List<BirdSighting> findBirdByColor(String color);

	@Query("{color : { $regex: ?0 } }")
	List<BirdSighting> findBirdByRegExColor(String domain);

	@Query("{'sightings.territory': '?0'}")
	List<BirdSighting> findBirdsByTerritory(String territory);

//	@Query(value = "{'staticBirdFields.speciesName': '?0'}")
//	List findIdByName(String name);
}
