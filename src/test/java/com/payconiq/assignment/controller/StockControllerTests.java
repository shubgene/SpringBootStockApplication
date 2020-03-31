package com.payconiq.assignment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.payconiq.assignment.Entity.StockEntity;
import com.payconiq.assignment.Service.StockService;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class StockControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StockService stockService;

	@Test
	@Order(1)
	@WithMockUser(username = "stock",password = "stock")
	public void should_return_empty_list() throws Exception {
		mockMvc.perform(get("/api/stocks")).andExpect(status().isOk()).andExpect(content().string("[]"));
	}

	@Test
	@Order(2)
	@WithMockUser(username = "stock",password = "stock")
	public void should_add_stock() throws Exception {
		String request = "{\"name\": \"stock1\",\"amount\":200}";
		mockMvc.perform(post("/api/stocks").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id", is(1))).andExpect(jsonPath("$.name", is("stock1")))
				.andExpect(jsonPath("$.amount", is(200.0)));
	}

	@Test
	@Order(3)
	@WithMockUser(username = "stock",password = "stock")
	public void should_return_one_entity_without_data() throws Exception {
		mockMvc.perform(get("/api/stocks/1")).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("stock1"))).andExpect(jsonPath("$.amount", is(200.0)))
				.andExpect(jsonPath("$.timestamp").exists());
	}

	@Test
	@Order(4)
	@WithMockUser(username = "stock",password = "stock")
	public void should_update_price() throws Exception {
		String request = "{\"amount\":100}";
		mockMvc.perform(put("/api/stocks/1").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().isNoContent());
	}

	@Test
	@Order(5)
	@WithMockUser(username = "stock",password = "stock")
	public void should_not_found_stock() throws Exception {
		String request = "{\"amount\": \"1\"}";
		mockMvc.perform(put("/api/stocks/10").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(6)
	@WithMockUser(username = "stock",password = "stock")
	public void should_bad_request_incorrect_stock() throws Exception {
		StockEntity stock = new StockEntity();
		stock.setName("stock 1");
		stock.setAmount((double) 0);
		stockService.createNewStock(stock);
		String request = "{\"amount\": \"-1\"}";
		String error = mockMvc.perform(put("/api/stocks/1").contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(status().isBadRequest()).andReturn().getResolvedException().getMessage();

		assertTrue(error.contains("Price of stock cant be less than 0"));
	}

	@Test
	@Order(7)
	@WithMockUser(username = "stock",password = "stock")
	public void should_return_history_without_data() throws Exception {
		mockMvc.perform(get("/api/stocks/1/history").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.[*].amount").isNotEmpty());
	}

}
