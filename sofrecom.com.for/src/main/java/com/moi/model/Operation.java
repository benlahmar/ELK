/**
 * 
 */
package com.moi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * @author BEN LAHMAR
 *
 */
public class Operation {

	double amount;
	String type;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
	LocalDate operationDate;
	
	
	
	@Override
	public String toString() {
		return "Operation [amount=" + amount + ", type=" + type + ", operationDate=" + operationDate + "]";
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(LocalDate operationDate) {
		this.operationDate = operationDate;
	}
	
	
}
