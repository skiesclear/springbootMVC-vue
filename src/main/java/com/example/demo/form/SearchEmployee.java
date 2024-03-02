package com.example.demo.form;

public class SearchEmployee {
	private String firstName="";
	private String lastName="";
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Override
	public String toString() {
		return "SearchEmployee [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	

}
