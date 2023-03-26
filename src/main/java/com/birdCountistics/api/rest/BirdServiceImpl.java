package com.birdCountistics.api.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.birdCountistics.api.collection.Birds;
import com.birdCountistics.api.collection.Sightings;
import com.birdCountistics.api.repository.BirdRepository;

@Service
public class BirdServiceImpl implements BirdService {
	@Autowired
	private MongoOperations mongoOps;
	@Autowired
	BirdRepository repository;

	@Override
	public List<Birds> findAll() {

		List<Birds> birds = repository.findAll();
		return birds;
	}

	@Override
	public List<Birds> findBirdByRegExName(String domain) {

		List<Birds> birds = repository.findBirdByRegExName(domain);
		return birds;
	}

	@Override
	public void saveAll(List<Birds> object) {

		repository.saveAll(object);
	}

	@Override
	public void addSightings(List<Sightings> object) {
		String id = findIdByName(object.get(0).getSpeciesName());
		Birds birdDocument = repository.findById(id).orElse(null);
		birdDocument.getSightings().addAll(object);
		repository.save(birdDocument);
	}

	@Override
	public List<Birds> findBirdsByTerritory(String territory) {
		return repository.findBirdsByTerritory(territory);
	}

	@Override
	public void deleteById(String id) {

		repository.deleteById(id);
	}

	@Override
	public String findIdByName(String name) {
		return repository.findBirdByName(name).getId();
	}

	@Override
	public List<Birds> findBirdById(String id) {
		List<Birds> bird = repository.findBirdById(id);
		return bird;
	}

	@Override
	public List<Birds> findByCriteria(String territory, String location, String trail, String value) {
		Map<String, String> criteria = new HashMap<>();
		if (territory != null) {
			criteria.put("sightings.territory", territory);
		}
		if (location != null) {
			criteria.put("sightings.location", location);
		}
		if (trail != null) {
			criteria.put("sightings.trail", trail);
		}
		if (value != null) {
			criteria.put("sightings.value", value);
		}
		Criteria criteriaObj = new Criteria();
		for (Map.Entry<String, String> entry : criteria.entrySet()) {
			criteriaObj.and(entry.getKey()).is(entry.getValue());
		}
		Query query = new Query(criteriaObj);
		return mongoOps.find(query, Birds.class);
	}
}
