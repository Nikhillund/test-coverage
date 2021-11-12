package com.nikhil.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.nikhil.controllers.EmployeeController;
import com.nikhil.models.Employee;

public class TestEmployee {
	
	@Test
	public void testEquals() {
		Employee e =new Employee(1L, "john", "clerk");
		Employee e1 =new Employee(1L, "john", "clerk");
		assertTrue(e.equals(e1));
		
	}

	@Test
	public void testEqualsSameObject() {
		Employee e =new Employee(1L, "john", "clerk");
		
		assertTrue(e.equals(e));
		
	}
	
	@Test
	public void testEqualsDifferentInstance() {
		Employee e =new Employee(1L, "john", "clerk");
		EmployeeController e1 =new EmployeeController(null);
		assertFalse(e.equals(e1));
		
	}
	
	@Test
	public void testHashCode() {
		Employee e =new Employee(1L, "john", "clerk");
		Employee e1 =new Employee(1L, "john", "clerk");
		assertEquals(e.hashCode(), e1.hashCode());
	}


}
