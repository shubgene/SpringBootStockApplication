package com.payconiq.assignment.Entity;

/**
 * @author User 
 *         Stock History for pojo for mapping the revision of stock table
 *         used for auditing purpose of stock price update
 *
 */
public class StockHistory {
	private Long id;
	private Double amount;
	private Long timestamp;

	public Long getId() {
		return id;
	}

	public StockHistory(Long id, Double amount, Long timestamp) {
		super();
		this.id = id;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public StockHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
