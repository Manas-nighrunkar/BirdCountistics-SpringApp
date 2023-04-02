package com.birdCountistics.api.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

	@RequestMapping(value = "/api/birdSighting/getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getAll() {

		System.out.println("Fetching all birds");

		List<Sightings> birdSighting = birdServiceImpl.findAll();

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

		return ResponseEntity.ok().headers(headers).body(birdSighting);
	}

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

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");

		return ResponseEntity.ok().headers(headers).body(sightings);
	}

	@RequestMapping(value = "/api/bird/getBy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getBirdByName(@RequestParam(value = "name", required = false) String name) {

		System.out.println("Fetching bird by name");
		Bird bird = birdServiceImpl.getBirdByName(name);

		return new ResponseEntity<Object>(bird, HttpStatus.OK);
	}

	@RequestMapping(value = "/api/birdSighting/getByParams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> getByParams(@RequestParam(value = "speciesName", required = false) String name,
			@RequestParam(value = "family", required = false) String family,
			@RequestParam(value = "schedule", required = false) String schedule,
			@RequestParam(value = "iucn", required = false) String iucn,
			@RequestParam(value = "endemism", required = false) String endemism,
			@RequestParam(value = "year", required = false) String year,
			@RequestParam(value = "territory", required = false) String territory,
			@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "trail", required = false) String trail,
			@RequestParam(value = "value", required = false) String value) {

		List<Sightings> sighting = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		System.out.println("Fetching bird by params speciesName = " + name + ", family= " + family + ", schedule= "
				+ schedule + ", iucn= " + iucn + ", endemism= " + endemism + ", year= " + year + ", territory= "
				+ territory + ", location= " + location + ", trail= " + trail + ", value= " + value);

		if (trail != null) {
			if (trail.equals("day") | trail.equals("night")) {
				if (territory == null) {
					return ResponseEntity.badRequest().headers(headers)
							.body("If trail equals day or night, territory cannot be null");
				}
			}
			if (trail.equals("day")) {
				if (territory.equals("Karnala")) {
					sighting = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism, year, territory,
							location, "T2", value);
					List<Sightings> sighting1 = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism,
							year, territory, location, "T3", value);
					sighting.addAll(sighting1);

				} else if (territory.equals("Phansad")) {
					sighting = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism, year, territory,
							location, "T2", value);
					List<Sightings> sighting1 = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism,
							year, territory, location, "T4", value);
					sighting.addAll(sighting1);

				}

				return ResponseEntity.ok().headers(headers).body(sighting);
			} else if (trail.equals("night")) {
				if (territory.equals("Karnala")) {
					sighting = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism, year, territory,
							location, "T1", value);
					List<Sightings> sighting1 = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism,
							year, territory, location, "T4", value);
					sighting.addAll(sighting1);

				} else if (territory.equals("Phansad")) {

					sighting = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism, year, territory,
							location, "T1", value);
					List<Sightings> sighting1 = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism,
							year, territory, location, "T3", value);
					sighting.addAll(sighting1);

				}
				return ResponseEntity.ok().headers(headers).body(sighting);
			}
		}
		sighting = birdServiceImpl.findByCriteria(name, family, schedule, iucn, endemism, year, territory, location,
				trail, value);
		return ResponseEntity.ok().headers(headers).body(sighting);
	}

	@RequestMapping(value = "/api/birdSighting/addSightings", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin("*")
	@ResponseBody
	public ResponseEntity<Object> addSightings(@RequestBody List<AddSightingPOJO> object) {

		if (object == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("POST body should not be Null");
		Sightings sightings = null;
		for (AddSightingPOJO obj : object) {

			System.out.println("adding bird: " + obj.getSpeciesName());
			Bird bird;
			try {
				bird = birdServiceImpl.getBirdByName(obj.getSpeciesName());
			} catch (Exception e) {
				System.out.println("Error in Adding Sighting to database: " + e.getMessage()
						+ ": Either there are mulitple entries of same bird or normalization issue");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error in Adding Sighting to database " + e.getMessage());

			}
			StaticBirdFields staticBirdField = new StaticBirdFields(bird.getSpeciesName(), bird.getScientificName(),
					bird.getFamily(), bird.getSchedule(), bird.getIucn(), bird.getEndemism());
			sightings = new Sightings(staticBirdField, obj.getMeta(), obj.getTerritory(), obj.getLocation(),
					obj.getTrail(), obj.getValue());
			try {
				birdServiceImpl.addSighting(sightings);
			} catch (Exception e) {
				System.out.println("Error in Adding Sighting to database " + e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Error in Adding Sighting to database " + e.getMessage());
			}
		}

		return ResponseEntity.status(HttpStatus.CREATED).body("Added SucessFully");
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
