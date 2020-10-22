/**
 * 
 */
package com.moi.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.moi.model.Account;
import com.moi.model.Operation;

/**
 * @author BEN LAHMAR
 *
 */
public interface IDao {

	public Account findById(String id);
	
	public Account findByNumber(int nb);
	
	public List<Account> findByage(int age);
	
	public List<Account> findAll();
	
	public List<Operation> findOperation4Account(int num);
	
	List<Account> findByageAndGendre(int age, String gendre);
	
	List<Account> findbetweendate(LocalDate dd, LocalDate df);
	
	
	public double moyByAge();
	
	public  Map<String, Long>  bugendre();
	
}
