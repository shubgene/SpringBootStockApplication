package com.payconiq.assignment.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payconiq.assignment.Entity.StockEntity;
import com.payconiq.assignment.Entity.StockHistory;
import com.payconiq.assignment.Exception.ParamInvalitExceptionHandler;
import com.payconiq.assignment.Exception.StockNotPresentExceptionHandler;
import com.payconiq.assignment.Repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepo;

	@PersistenceContext
	private EntityManager em;

	public StockService(StockRepository stockRepository) {
		this.stockRepo = stockRepository;
	}

	public List<StockEntity> getAllStocks() {
		return stockRepo.findAll();
	}

	public StockEntity getStocksById(long id) {
		Optional<StockEntity> st = stockRepo.findById(id);
		if (st.isPresent()) {
			return stockRepo.findById(id).get();
		} else {
			throw new StockNotPresentExceptionHandler("Stock with given Stock Id not present");
		}

	}

	public StockEntity updateStockPrice(Long id, Double currentPrice) {
		StockEntity stEntity = getStocksById(id);
		if (currentPrice < 0) {
			throw new ParamInvalitExceptionHandler("Price of stock cant be less than 0");
		}
		if (stEntity != null) {
			stEntity.setAmount(currentPrice);
		}
		stockRepo.save(stEntity);
		return stEntity;
	}

	public StockEntity createNewStock(StockEntity stock) {
		if ( stock.getName() == null || stock.getName().isEmpty()) {
			throw new ParamInvalitExceptionHandler("Name of stock cant be blank");
		}
		if (stock.getAmount() < 0) {
			throw new ParamInvalitExceptionHandler("Price of stock cant be less than 0");
		}
		return stockRepo.save(stock);
	}

	public List<StockHistory> getStockHistotyById(long id) {
		Optional<StockEntity> st = stockRepo.findById(id);
		if (st.isPresent()) {
			AuditReader reader = AuditReaderFactory.get(em);
			AuditQuery auditQuery = reader.createQuery().forRevisionsOfEntity(StockEntity.class, false, true)
					.add(AuditEntity.id().eq(id));
			List<?> results = auditQuery.getResultList();
			if (results == null) {
				results = new ArrayList<>();
			}
			List<StockHistory> entityHistories = results.stream().filter(x -> x instanceof Object[])
					.map(x -> (Object[]) x)
					.map(x -> getCustomerHistory(x)).collect(Collectors.toList());
			return entityHistories;
		} else {
			throw new StockNotPresentExceptionHandler("Stock with given Stock Id not present");
		}

	}

	private static StockHistory getCustomerHistory(Object[] auditQueryResult) {
		StockEntity entity = (StockEntity) auditQueryResult[0];
		return new StockHistory(entity.getId(), entity.getAmount(), entity.getTimestamp());
	}
}
