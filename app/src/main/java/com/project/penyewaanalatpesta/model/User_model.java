package com.project.penyewaanalatpesta.model;

public class User_model {

	String id,fullname, username, photoprofil, address, email, password;

	public User_model() {
	}

	public User_model(String id, String fullname, String username, String photoprofil, String address, String email, String password) {
		this.id = id;
		this.fullname = fullname;
		this.username = username;
		this.photoprofil = photoprofil;
		this.address = address;
		this.email = email;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhotoprofil() {
		return photoprofil;
	}

	public void setPhotoprofil(String photoprofil) {
		this.photoprofil = photoprofil;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
