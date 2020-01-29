package com.foodvendor.doa;

import com.foodvendor.model.Developer;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Mongo database developer repository
 */
@Repository
public interface DeveloperRepository extends MongoRepository<Developer, String>, QuerydslPredicateExecutor<Developer> {
    Developer findByEmail(String id);
}
