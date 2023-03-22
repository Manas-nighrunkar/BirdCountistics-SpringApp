package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.collection.Birds;
import com.example.demo.repository.BirdRepository;
import com.google.gson.Gson;

@RestController
public class RestContoller {

	@Autowired
	BirdRepository repository;

	@GetMapping("/")
	public String sayHello() {
		return "Hello World!";
	}

	@RequestMapping(value = "getAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAll() {
		// repository.findAll().forEach(u -> System.out.println(u.Getname()));
		List<Birds> birds = repository.findAll();
		Gson json = new Gson();

		String response = json.toJson(birds);

		System.out.println(response);

		return response;
		// return new ResponseEntity<List<Birds>>(birds, HttpStatus.OK);
		// return birds;
	}

	@RequestMapping(value = "getByName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getByName(@RequestParam(value = "name", required = true) String name) {

		System.out.println("Finding first by Name");
		List<Birds> birds = repository.findBirdByRegExName("^" + name);
		Gson json = new Gson();

		String response = json.toJson(birds);

		System.out.println(response);

		return response;
		// return new ResponseEntity<List<Birds>>(birds, HttpStatus.OK);
		// return birds;
	}

	@RequestMapping(value = "addBird", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addBird(@RequestBody List<Birds> object) {

//		Gson json = new Gson();
//
//		Birds response = json.fromJson(object, Birds.class);

//		System.out.println("Adding " + response.getName() + " to database");
		repository.saveAll(object);

		return "Success";
		// return new ResponseEntity<List<Birds>>(birds, HttpStatus.OK);
		// return birds;
	}

	@RequestMapping(value = "updateBird", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateBird(@RequestBody List<Birds> object) {

//		Gson json = new Gson();
//
//		Birds response = json.fromJson(object, Birds.class);
//
//		System.out.println("Updating " + response.getId() + " to database");
		repository.saveAll(object);
		// repository.save(null)

		return "Success";
		// return new ResponseEntity<List<Birds>>(birds, HttpStatus.OK);
		// return birds;
	}

	@DeleteMapping("/deleteBird")
	@ResponseBody
	public String deleteBird(@RequestParam(value = "id", required = true) String id) {
//
//		Gson json = new Gson();
//
//        Birds response = json.fromJson(object, Birds.class);

		// System.out.println("Adding " + response.getName() + " to database");
		// repository.save(response);
		repository.deleteById(id);
		return "Success";
		// return new ResponseEntity<List<Birds>>(birds, HttpStatus.OK);
		// return birds;
	}
}
