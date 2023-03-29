package com.birdCountistics.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.birdCountistics.api.collection.AddSightingPOJO;
import com.birdCountistics.api.collection.Bird;
import com.birdCountistics.api.collection.Sightings;
import com.birdCountistics.api.collection.StaticBirdFields;

@RestController
public class RestContoller {

	@Autowired
	BirdServiceImpl birdServiceImpl;

	@GetMapping("/api/healthCheck")
	public String sayHello() {
		return "API is up and running";
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/birdSighting/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAll() {

		System.out.println("Fetching all birds");

		List<Sightings> birdSighting = birdServiceImpl.findAll();

		return new ResponseEntity<Object>(birdSighting, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/birdSighting/getBy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getByName(@RequestParam(value = "name", required = false) String name) {

		System.out.println("Fetching bird by name");
		List<Sightings> sightings = null;
		if (name == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name or ID Accepted, Both not allowed");
		} else if (name != null) {
			sightings = birdServiceImpl.findSightingsByName(name);
		}

		return new ResponseEntity<Object>(sightings, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/bird/getBy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getBirdByName(@RequestParam(value = "name", required = false) String name) {

		System.out.println("Fetching bird by name");
		Bird bird = birdServiceImpl.getBirdByName(name);

		return new ResponseEntity<Object>(bird, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/birdSighting/getByParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getByParams(@RequestParam(value = "speciesName", required = false) String name,
			@RequestParam(value = "territory", required = false) String territory,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "trail", required = false) String trail,
			@RequestParam(value = "value", required = false) String value) {

		List<Sightings> sighting = null;
		System.out.println("Fetching bird by params territory = " + territory);
		if (trail != null) {
			if (trail.equals("day")) {
				sighting = birdServiceImpl.findByCriteria(name, territory, location, "T2", value);
				List<Sightings> sighting1 = birdServiceImpl.findByCriteria(name, territory, location, "T3", value);
				sighting.addAll(sighting1);
			} else if (trail.equals("night")) {
				sighting = birdServiceImpl.findByCriteria(name, territory, location, "T1", value);
				List<Sightings> sighting1 = birdServiceImpl.findByCriteria(name, territory, location, "T4", value);
				sighting.addAll(sighting1);
			}
			return new ResponseEntity<Object>(sighting, HttpStatus.OK);
		}
		sighting = birdServiceImpl.findByCriteria(name, territory, location, trail, value);
//		List<Birds> birds = null;
//		if (territory == null && location == null && trail == null && value == null) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//					.body("AtLeast one Parameter required (territory, location, trail, value)");
//		} else if (territory != null) {
//			birds = birdServiceImpl.findBirdsByTerritory(territory);
//		}
//		else {
//			birds = birdServiceImpl.findBirdById(id);
//		}

		return new ResponseEntity<Object>(sighting, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/birdSighting/addSightings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addSightings(@RequestBody List<AddSightingPOJO> object) {

		Sightings sightings = null;
		for (AddSightingPOJO obj : object) {

			System.out.println("in for loop");
			System.out.println(obj.getSpeciesName());
			Bird bird = birdServiceImpl.getBirdByName(obj.getSpeciesName());
			StaticBirdFields staticBirdField = new StaticBirdFields(bird.getSpeciesName(), bird.getScientificName(),
					bird.getFamily(), bird.getSchedule(), bird.getIucn(), bird.getEndemism());
			sightings = new Sightings(staticBirdField, obj.getMeta(), obj.getTerritory(), obj.getLocation(),
					obj.getTrail(), obj.getValue());
			birdServiceImpl.addSighting(sightings);
		}
		return new ResponseEntity<Object>(sightings, HttpStatus.OK);
//
//		try {
//			birdServiceImpl.findIdByName(object.get(0).getSpeciesName());
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SpecieName Entry not available");
//		}
//		birdServiceImpl.addSightings(object);
//
//		String message = "Add Sightings successful";
//		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@RequestMapping(value = "/api/bird/addBird", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addBird(@RequestBody List<Bird> object) {

		birdServiceImpl.saveAllBird(object);
		birdServiceImpl.updateSpeciesId();
		String message = "Add Bird successful";
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@RequestMapping(value = "/api/bird/getIdByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getIdByName(@RequestParam(value = "name", required = true) String name) {

		System.out.println("Finding Id by Name");
		String id = birdServiceImpl.findIdByName(name);

		return id;
	}

//	@RequestMapping(value = "/api/updateBird", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseEntity<String> updateBird(@RequestBody List<BirdSighting> object) {
//
//		birdServiceImpl.saveAll(object);
//
//		String message = "Update Bird successful";
//		return ResponseEntity.status(HttpStatus.OK).body(message);
//	}
//
//	@RequestMapping(value = "/api/deleteBird", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public ResponseEntity<String> deleteBird(@RequestParam(value = "id", required = true) String id) {
//
//		birdServiceImpl.deleteById(id);
//
//		String message = "Delete Bird successful";
//		return ResponseEntity.status(HttpStatus.OK).body(message);
//	}

	@RequestMapping(value = "/api/bird/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAllSBF() {
		List<Bird> bird = birdServiceImpl.findAllBird();

		return new ResponseEntity<Object>(bird, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/birdSighting/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteBirdSighting() {

		birdServiceImpl.deleteAll();

		String message = "Delete Bird successful";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@RequestMapping(value = "/api/bird/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteBird() {

		birdServiceImpl.deleteAllBird();

		String message = "Delete Bird successful";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
