package com.birdCountistics.api.collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "user")
@Getter
@Setter
public class User {

	public String userName;
	public String email;
	public String API_KEY;
	public String status;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAPI_KEY() {
		return API_KEY;
	}

	public void setAPI_KEY(String API_KEY) {
		this.API_KEY = API_KEY;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
