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

import com.birdCountistics.api.collection.Birds;
import com.birdCountistics.api.collection.Sightings;

@RestController
public class RestContoller {

	@Autowired
	BirdServiceImpl birdServiceImpl;

	@GetMapping("/api/healthCheck")
	public String sayHello() {
		return "API is up and running";
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAll() {

		System.out.println("Fetching all birds");

		List<Birds> birds = birdServiceImpl.findAll();

		return new ResponseEntity<Object>(birds, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/getByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getByName(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "id", required = false) String id) {

		System.out.println("Fetching bird by name");
		List<Birds> birds = null;
		if (name != null && id != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Name or ID Accepted, Both not allowed");
		} else if (name != null) {
			birds = birdServiceImpl.findBirdByRegExName("^" + name);
		} else {
			birds = birdServiceImpl.findBirdById(id);
		}

		return new ResponseEntity<Object>(birds, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping(value = "/api/getByParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getByParams(@RequestParam(value = "territory", required = false) String territory,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "trail", required = false) String trail,
			@RequestParam(value = "value", required = false) String value) {

		System.out.println("Fetching bird by params territory = " + territory);
		List<Birds> birds = birdServiceImpl.findByCriteria(territory, location, trail, value);
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

		return new ResponseEntity<Object>(birds, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/addSightings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addSightings(@RequestBody List<Sightings> object) {

		try {
			birdServiceImpl.findIdByName(object.get(0).getSpeciesName());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SpecieName Entry not available");
		}
		birdServiceImpl.addSightings(object);

		String message = "Add Sightings successful";
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@RequestMapping(value = "/api/addBird", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> addBird(@RequestBody List<Birds> object) {

		birdServiceImpl.saveAll(object);

		String message = "Add Bird successful";
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@RequestMapping(value = "/api/getIdByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getIdByName(@RequestParam(value = "name", required = true) String name) {

		System.out.println("Finding Id by Name");
		String id = birdServiceImpl.findIdByName(name);

		return id;
	}

	@RequestMapping(value = "/api/updateBird", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> updateBird(@RequestBody List<Birds> object) {

		birdServiceImpl.saveAll(object);

		String message = "Update Bird successful";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@RequestMapping(value = "/api/deleteBird", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<String> deleteBird(@RequestParam(value = "id", required = true) String id) {

		birdServiceImpl.deleteById(id);

		String message = "Delete Bird successful";
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

}
