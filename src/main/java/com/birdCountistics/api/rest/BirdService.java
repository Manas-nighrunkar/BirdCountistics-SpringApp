package com.birdCountistics.api.rest;

import java.util.List;

import com.birdCountistics.api.collection.Bird;
import com.birdCountistics.api.collection.BirdSighting;
import com.birdCountistics.api.collection.Sightings;

public interface BirdService {
	public boolean validateUser(String apiKey);

	public void deleteAllBird();

	public void deleteAll();

	public List<Sightings> findAll();

	public List<Bird> findAllBird();

	public Bird getBirdByName(String name);

	public List<Sightings> findSightingsByName(String domain);

	public void saveAllBird(List<Bird> object);

	public void deleteById(String id);

	public String findIdByName(String name);

	public List<BirdSighting> findBirdById(String id);

	public void addSighting(Sightings object);

	public List<BirdSighting> findBirdsByTerritory(String territory);

	public List<Sightings> findByCriteria(List<String> name, List<String> family, List<String> schedule,
			List<String> iucn, List<String> endemism, List<String> year, List<String> territory, List<String> location,
			List<String> trail, List<String> value);

	public void updateSpeciesId();
}
