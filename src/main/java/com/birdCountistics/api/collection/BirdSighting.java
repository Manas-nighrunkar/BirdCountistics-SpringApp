package com.birdCountistics.api.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "birdsNew2")
@Getter
@Setter
public class BirdSighting {

	@Id
	private String id;

	Sightings sightings;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Sightings getSightings() {
		return this.sightings;
	}

	public void setSightings(Sightings sightings) {
		this.sightings = sightings;
	}
}
