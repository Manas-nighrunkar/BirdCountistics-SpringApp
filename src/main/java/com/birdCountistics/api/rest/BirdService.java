package com.birdCountistics.api.rest;

import java.util.List;

import com.birdCountistics.api.collection.Bird;
import com.birdCountistics.api.collection.BirdSighting;
import com.birdCountistics.api.collection.Sightings;

public interface BirdService {
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

	List<Sightings> findByCriteria(String name, String territory, String location, String trail, String value);

	public void updateSpeciesId();
}
