package com.sachuk.keu.entities.payload;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String ipn;
	private String firstname;
	private String surname;
	private String thirdname;
	private List<String> roles;

	public JwtResponse(String token, Long id, String ipn, String firstname, String surname, String thirdname, List<String> roles) {
		this.token = token;
		this.id = id;
		this.ipn = ipn;
		this.firstname = firstname;
		this.surname = surname;
		this.thirdname = thirdname;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIpn() {
		return ipn;
	}

	public void setIpn(String ipn) {
		this.ipn = ipn;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getThirdname() {
		return thirdname;
	}

	public void setThirdname(String thirdname) {
		this.thirdname = thirdname;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
