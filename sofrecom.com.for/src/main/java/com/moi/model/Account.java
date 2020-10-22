/**
 * 
 */
package com.moi.model;

import java.util.List;

/**
 * @author BEN LAHMAR
 *
 */
public class Account {

	int account_number;
	double balance;
	
	
	
	
	String firstname,lastname;
	int age;
	String gender;
	String address,company,email,city,state,phone;
	
	List<Operation> operations;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	public int getAccount_number() {
		return account_number;
	}
	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public List<Operation> getOperations() {
		return operations;
	}
	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	@Override
	public String toString() {
		return "Account [account_number=" + account_number + ", balance=" + balance + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", age=" + age + ", gender=" + gender + ", address=" + address
				+ ", company=" + company + ", email=" + email + ", city=" + city + ", state=" + state + ", phone="
				+ phone + ", operations=" + operations + "]";
	}
	
	
}
