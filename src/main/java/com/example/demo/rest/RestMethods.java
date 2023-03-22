package com.example.demo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.collection.Birds;
import com.example.demo.repository.BirdRepository;

public class RestMethods {

	@Autowired
	BirdRepository repository;
	
	public void deleteAll() {
		System.out.println("Deleting all records..");
		repository.deleteAll();
	}
	
	public void addSampleData() {
		System.out.println("Adding sample data");
		repository.save(new Birds("Parrot", "Green", "India"));
		repository.save(new Birds("Grey Parrot", "Grey", "Soth Asia"));
		repository.save(new Birds("True Parrot", "Red", "USA"));
		repository.save(new Birds("LoveBirds", "Pink", "India"));
	}
	
	public void listAll() {
		System.out.println("Listing sample data");
		repository.findAll().forEach(u -> System.out.println(u.getName()));
	}
	
	public void findFirst() {
		System.out.println("Finding first by Name");
		List<Birds> u = repository.findBirdByName("Parrot");
		System.out.println(u);
	}
	
	public void findByRegex() {
		System.out.println("Finding by Regex - All with address starting with ^Gr ");
		repository.findBirdByRegExColor("^Gr").forEach(u -> System.out.println(u.getName()));
	}
}
