package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.collection.Birds;
import com.example.demo.repository.BirdRepository;

@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {
	
	@Autowired
	BirdRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		deleteAll();
		addSampleData();
		listAll();
		findFirst();
		findByRegex();
	}

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
		repository.findAll().forEach(u -> System.out.println(u.Getname()));
	}
	
	public void findFirst() {
		System.out.println("Finding first by Name");
		List<Birds> u = repository.findBirdByName("Parrot");
		System.out.println(u);
	}
	
	public void findByRegex() {
		System.out.println("Finding by Regex - All with address starting with ^Gr");
		repository.findBirdByRegExColor("^Gr").forEach(u -> System.out.println(u.Getname()));
	}
}
