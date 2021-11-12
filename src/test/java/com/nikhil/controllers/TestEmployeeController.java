package com.nikhil.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhil.models.Employee;
import com.nikhil.repository.EmployeeRepository;

@WebMvcTest(EmployeeController.class)
public class TestEmployeeController {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	
	EmployeeController emp =null;
	
	Employee emp1 =new Employee(1L,"Bilbo Baggins", "burglar");
	Employee emp2 =new Employee(2L, "Frodo Baggins", "thief");
	@MockBean
	EmployeeRepository mockRepository;
	

	
	@Test
	public void testAll() throws Exception{
		List<Employee> empList =new ArrayList<>();
		empList.add(emp1);
		empList.add(emp2);
		Mockito.when(mockRepository.findAll()).thenReturn(empList);	
		
		
		mockMvc.perform(MockMvcRequestBuilders
						.get("/employees")
						.contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk())
						.andExpect(jsonPath("$", Matchers.hasSize(2)))
						.andExpect(jsonPath("$[0].name",  Matchers.is("Bilbo Baggins")));
	}
	
	@Test
	public void testNewEmployee() throws Exception {
		
		Employee emp =new Employee(3L, "John Wick", "Ghost");
		when(mockRepository.save(emp)).thenReturn(emp);
		MockHttpServletRequestBuilder mockRequestBuilder = MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(emp));
		
		mockMvc.perform(mockRequestBuilder)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("John Wick")));
		
	}
	
	@Test
	public void testOne() throws Exception {
		List<Employee> empList =new ArrayList<>();
		empList.add(emp1);
		empList.add(emp2);
		
		when(mockRepository.findById(emp1.getId())).thenReturn(Optional.of(emp1));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/employees/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.name", is("Bilbo Baggins")));
	}
	
	@Test
	public void testReplaceEmployee() throws Exception {
		
		Employee updatedEmp =new Employee(1L, "John Wick", "Ghost");
		when(mockRepository.findById(updatedEmp.getId())).thenReturn(Optional.of(emp1));
		when(mockRepository.save(updatedEmp)).thenReturn(updatedEmp);
		
		MockHttpServletRequestBuilder mockRecBuilder = MockMvcRequestBuilders.put("/employees/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedEmp));
		
		mockMvc.perform(mockRecBuilder)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("John Wick")))
		.andExpect(jsonPath("$.role", is("Ghost")));
		
		
	}
	
	@Test
	public void testReplaceEmployeeNotFound() throws Exception {
		
		Employee updatedEmp =new Employee(3L, "John Wick", "Ghost");
		when(mockRepository.findById(updatedEmp.getId())).thenReturn(Optional.of(emp1));
		when(mockRepository.save(updatedEmp)).thenReturn(updatedEmp);
		
		MockHttpServletRequestBuilder mockRecBuilder = MockMvcRequestBuilders.put("/employees/3")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(this.mapper.writeValueAsString(updatedEmp));
		
		mockMvc.perform(mockRecBuilder)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.id", is(3)))
		.andExpect(jsonPath("$.name", is("John Wick")))
		.andExpect(jsonPath("$.role", is("Ghost")));
		
		
	}
	
	@Test
	public void testDeleteEmployee() throws Exception {
		when(mockRepository.findById(emp1.getId())).thenReturn(Optional.of(emp1));
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		
	
	}

	
}
