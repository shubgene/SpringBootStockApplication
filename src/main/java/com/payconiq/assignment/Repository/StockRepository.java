package com.payconiq.assignment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payconiq.assignment.Entity.StockEntity;

/**
 * @author User Interface using jparepository to implement basic crud operation
 *         on stock
 */
public interface StockRepository extends JpaRepository<StockEntity, Long> {

}
