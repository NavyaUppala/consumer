package com.capitalone.dept.consumer.model;

import java.io.Serializable;

public class Phone implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String type;
	private String phoneNumber;

	public Phone() {
	}

	public Phone(Long id, String type, String phoneNumber) {
		this.id = id;
		this.type = type;
		this.phoneNumber = phoneNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Phone [id=" + id + ", type=" + type + ", phoneNumber=" + phoneNumber + "]";
	}

}
