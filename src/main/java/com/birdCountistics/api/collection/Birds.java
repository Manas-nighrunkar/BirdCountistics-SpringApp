package com.birdCountistics.api.collection;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "birdsNew")
@Getter
@Setter
public class Birds {

	@Id
	private String id;

	StaticBirdFields staticBirdFields;
	List<Sightings> sightings;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StaticBirdFields getStaticBirdFields() {
		return this.staticBirdFields;
	}

	public void setStaticBirdFields(StaticBirdFields staticBirdFields) {
		this.staticBirdFields = staticBirdFields;
	}

	public List<Sightings> getSightings() {
		return this.sightings;
	}

	public void setSightings(List<Sightings> sightings) {
		this.sightings = sightings;
	}
}
