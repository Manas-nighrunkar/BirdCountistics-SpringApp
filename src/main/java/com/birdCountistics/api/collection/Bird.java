package com.birdCountistics.api.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "sBirdOG")
@Getter
@Setter
public class Bird {

	private String speciesName;
	private String scientificName;
	private String family;
	private String schedule;
	private String iucn;
	private String endemism;

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public void setSpeciesName(String speciesName) {
		this.speciesName = speciesName;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getIucn() {
		return iucn;
	}

	public void setIucn(String iucn) {
		this.iucn = iucn;
	}

	public String getEndemism() {
		return endemism;
	}

	public void setEndemism(String endemism) {
		this.endemism = endemism;
	}

}
