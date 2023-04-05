package com.birdCountistics.api.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "birdSightingsPROD")
public class Sightings {

	StaticBirdFields staticBirdFields;
	Meta meta;
	private String territory;
	private String location;
	private String trail;
	private String value;

	public Sightings(StaticBirdFields staticBirdFields, Meta meta, String territory, String location, String trail,
			String value) {
		this.staticBirdFields = staticBirdFields;
		this.meta = meta;
		this.territory = territory;
		this.location = location;
		this.trail = trail;
		this.value = value;
	}

	public StaticBirdFields getStaticBirdFields() {
		return this.staticBirdFields;
	}

	public void setStaticBirdFields(StaticBirdFields staticBirdFields) {
		this.staticBirdFields = staticBirdFields;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTrail() {
		return trail;
	}

	public void setTrail(String trail) {
		this.trail = trail;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
