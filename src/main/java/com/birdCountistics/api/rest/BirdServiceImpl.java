package com.birdCountistics.api.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.birdCountistics.api.collection.Bird;
import com.birdCountistics.api.collection.BirdSighting;
import com.birdCountistics.api.collection.Sightings;
import com.birdCountistics.api.repository.BirdRepository;
import com.birdCountistics.api.repository.BirdSightingRepository;

@Service
public class BirdServiceImpl implements BirdService {

	@Autowired
	private MongoOperations mongoOps;

	@Autowired
	BirdSightingRepository repository;

	@Autowired
	BirdRepository birdRepository;

	@Override
	public List<Sightings> findAll() {

		List<Sightings> birdSighting = repository.findAll();
		return birdSighting;
	}

	@Override
	public List<Bird> findAllBird() {

		List<Bird> birds = birdRepository.findAll();
		return birds;
	}

	@Override
	public Bird getBirdByName(String name) {
		Bird bird = birdRepository.findBirdByName(name);
		return bird;
	}

	@Override
	public List<Sightings> findSightingsByName(String name) {

		List<Sightings> sighting = repository.findSightingByName(name);
		return sighting;
	}

	@Override
	public void saveAllBird(List<Bird> object) {

		birdRepository.saveAll(object);
	}

	@Override
	public void addSighting(Sightings object) {
		repository.save(object);
	}

	@Override
	public List<BirdSighting> findBirdsByTerritory(String territory) {
		return repository.findBirdsByTerritory(territory);
	}

	@Override
	public void deleteById(String id) {

		repository.deleteById(id);
	}

	@Override
	public void deleteAll() {

		repository.deleteAll();
	}

	@Override
	public void deleteAllBird() {

		birdRepository.deleteAll();
	}

	@Override
	public String findIdByName(String name) {
		return birdRepository.findBirdByName(name).getIucn();
	}

	@Override
	public List<BirdSighting> findBirdById(String id) {
		List<BirdSighting> bird = repository.findBirdById(id);
		return bird;
	}

	@Override
	public void updateSpeciesId() {

	}

	@Override
	public List<Sightings> findByCriteria(String name, String territory, String location, String trail, String value) {
		Map<String, String> criteria = new HashMap<>();
		if (name != null) {
			criteria.put("staticBirdFields.speciesName", name);
		}
		if (territory != null) {
			criteria.put("territory", territory);
		}
		if (location != null) {
			criteria.put("location", location);
		}
		if (trail != null) {
			criteria.put("trail", trail);
		}
		if (value != null) {
			criteria.put("value", value);
		}
		Criteria criteriaObj = new Criteria();
		for (Map.Entry<String, String> entry : criteria.entrySet()) {
			criteriaObj.and(entry.getKey()).is(entry.getValue());
		}
		Query query = new Query(criteriaObj);
		return mongoOps.find(query, Sightings.class);
	}
}
