package com.birdCountistics.api.rest;

import java.util.List;

import com.birdCountistics.api.collection.Birds;
import com.birdCountistics.api.collection.Sightings;

public interface BirdService {

	public List<Birds> findAll();

	public List<Birds> findBirdByRegExName(String domain);

	public void saveAll(List<Birds> object);

	public void deleteById(String id);

	public String findIdByName(String name);

	public List<Birds> findBirdById(String id);

	public void addSightings(List<Sightings> object);

	public List<Birds> findBirdsByTerritory(String territory);

	List<Birds> findByCriteria(String territory, String location, String trail, String value);
}
