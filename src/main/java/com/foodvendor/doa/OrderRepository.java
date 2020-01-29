package com.foodvendor.doa;

import com.foodvendor.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Mongo database order repository
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String>, QuerydslPredicateExecutor<Order> {
    Order findByOrderID(String id);
}

