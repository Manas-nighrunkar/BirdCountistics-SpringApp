package com.example.demo.collection;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "birds")
@Getter
@Setter
public class Birds {
	
	@Id
	private String id;
	private String name;
	private String color;
	private String region;
	
	public Birds(String name, String color, String region) {
		this.name = name;
		this.color = color;
		this.region = region;
	}
	
	public String Getname() {
		return this.name;
	}
}
