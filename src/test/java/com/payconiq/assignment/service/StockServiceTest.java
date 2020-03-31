package com.payconiq.assignment.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.payconiq.assignment.Entity.StockEntity;
import com.payconiq.assignment.Entity.StockHistory;
import com.payconiq.assignment.Exception.ParamInvalitExceptionHandler;
import com.payconiq.assignment.Exception.StockNotPresentExceptionHandler;
import com.payconiq.assignment.Repository.StockRepository;
import com.payconiq.assignment.Service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

	@Autowired
	private StockRepository stockRepository;

	@Test
	@WithMockUser(username = "stock",password = "stock")
	public void should_create_stock_with_id() {
		String stockName = "payconiq";
		StockEntity entity = new StockEntity();
		entity.setName(stockName);
		entity.setAmount(100.00);

		StockService stockSer = new StockService(stockRepository);
		StockEntity stock = stockSer.createNewStock(entity);

		Assert.assertEquals(1, stock.getId(), 0.0);
		Assert.assertEquals(stockName, stock.getName());
		Assert.assertEquals(100.00, stock.getAmount().doubleValue(), 0.0);
		Assert.assertNotNull(stock.getTimestamp());
	}

	@Test(expected = ParamInvalitExceptionHandler.class)
	@WithMockUser(username = "stock",password = "stock")
	public void should_not_create_stock_empty_name() {
		StockEntity stockEntity = new StockEntity();

		StockService stockService = new StockService(stockRepository);
		stockService.createNewStock(stockEntity);
	}

	@Test
	@WithMockUser(username = "stock",password = "stock")
	public void should_get_stock_by_id() {
		String stockName = "payconiq";
		StockEntity stockEntity = new StockEntity();
		stockEntity.setName(stockName);
		stockEntity.setAmount(100.00);
		StockService stockService = new StockService(stockRepository);
		Long id = stockService.createNewStock(stockEntity).getId();
		StockEntity existedStock = stockService.getStocksById(id);
		Assert.assertEquals(id, existedStock.getId(), 0.0);
		Assert.assertEquals(stockName, existedStock.getName());
	}

	@Test
	@WithMockUser(username = "stock",password = "stock")
	public void should_return_collection_with_one_element() {
		String stockName = "payconiq";
		StockEntity stockEntity = new StockEntity();
		stockEntity.setName(stockName);
		stockEntity.setAmount(100.00);
		StockService stockService = new StockService(stockRepository);
		stockService.createNewStock(stockEntity);

		List<StockEntity> stocks = stockService.getAllStocks();

		Assert.assertEquals(3, stocks.size());
		Assert.assertEquals("payconiq", stocks.iterator().next().getName());
		Assert.assertEquals(1, stocks.iterator().next().getId(), 0.0);
	}

	@Test
	@WithMockUser(username = "stock",password = "stock")
	public void should_update_price_stock_by_id() {
		String stockName = "payconiq";
		StockEntity stockEntity = new StockEntity();
		stockEntity.setName(stockName);
		stockEntity.setAmount(100.00);
		StockService stockService = new StockService(stockRepository);
		Long storedId = stockService.createNewStock(stockEntity).getId();

		StockEntity updatedStock = stockService.updateStockPrice(storedId, 10.00);
		Assert.assertEquals(storedId, updatedStock.getId(), 0.0);
		Assert.assertEquals(stockName, updatedStock.getName());
		Assert.assertEquals(10.00, updatedStock.getAmount().doubleValue(), 0.0);
		OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
		Date d = new Date(updatedStock.getTimestamp());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(d);
		int year = calendar.get(Calendar.YEAR);
		Assert.assertEquals(now.getYear(), year);
	}

	@Test(expected = ParamInvalitExceptionHandler.class)
	@WithMockUser(username = "stock",password = "stock")
	public void should_not_update_invalid_price() {
		StockService stockService = new StockService(stockRepository);
		double price = -10;
		stockService.updateStockPrice(1L, price);
	}

	@Test(expected = StockNotPresentExceptionHandler.class)
	public void should_not_found_get_by_id() {
		StockService stockService = new StockService(stockRepository);
		stockService.getStocksById(10);
	}
	
	/*
	 * @Test public void should_get_history_by_id() { String stockName = "payconiq";
	 * StockEntity stockEntity = new StockEntity(); stockEntity.setName(stockName);
	 * stockEntity.setAmount(100.00); StockService stockService = new
	 * StockService(stockRepository); Long id =
	 * stockService.createNewStock(stockEntity).getId();
	 * stockService.updateStockPrice(id, 50.00); List<StockHistory>
	 * his=stockService.getStockHistotyById(id); Assert.assertEquals(id,
	 * his.get(0).getId(), 0.0); Assert.assertEquals(50.00,
	 * his.get(0).getAmount(),0.0); }
	 */
}
