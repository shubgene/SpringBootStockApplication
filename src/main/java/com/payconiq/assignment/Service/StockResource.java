package com.payconiq.assignment.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payconiq.assignment.Entity.StockEntity;
import com.payconiq.assignment.Entity.StockHistory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Stocks Rest Api")
@RestController
@RequestMapping("/api/stocks")
public class StockResource {

	@Autowired
	private StockService stockService;

	@ApiOperation("Reste Api to get all the stocks")
	@GetMapping
	public List<StockEntity> getStocks() {
		return stockService.getAllStocks();
	}

	@ApiOperation("Rest Api to get stocks by stock id")
	@GetMapping("/{id}")
	public StockEntity getStocksById(@PathVariable final long id) {
		return stockService.getStocksById(id);
	}

	@ApiOperation("Rest Api to get stocks hisorical prices by stock id")
	@GetMapping("/{id}/history")
	public List<StockHistory> getStockHistoryById(@PathVariable final long id) {
		return stockService.getStockHistotyById(id);
		
	}
	
	@ApiOperation(value = "Rest Api to create a new Stock")
	@PostMapping
	public ResponseEntity<StockEntity> createNewStock(@Valid @RequestBody final StockEntity stock) {
		final StockEntity stockCreated = stockService.createNewStock(stock);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(linkTo(StockResource.class).slash(stockCreated.getId()).toUri());
		return new ResponseEntity<>(stockCreated, headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Rest Api to update current price of existing stock")
	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCurrentPrice(@PathVariable final Long id,
			@Valid @RequestBody final StockEntity stock) {
		stockService.updateStockPrice(id, stock.getAmount());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	
}
