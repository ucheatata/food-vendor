package com.foodvendor.controller;

import com.foodvendor.doa.DeveloperRepository;
import com.foodvendor.model.Developer;
import com.foodvendor.model.QDeveloper;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Class exposes API rest points for querying developer customer mongo database collection.
 * Add cross origin addresses to enable access; eg @CrossOrigin(origins = {"http://localhost:3000"}).
 */
@RequestMapping("/developer")
@RestController
public class DeveloperController {

    @Autowired
    private DeveloperRepository developerRepository;

    /**
     * Method exposes API rest point for searching for developer by id
     * @param id Developer id required
     * @return Developer full details
     */
    @GetMapping("/{id}")
    public Developer getById(@PathVariable("id") String id) {
        Developer developer = developerRepository.findByEmail(id);
        return developer;
    }

    /**
     * Method exposes API rest point for viewing all developers in customer database collection
     * @return List of all customers
     */
    @GetMapping("/all")
    public List<Developer> getAllCustomers(){
        List<Developer> customers = developerRepository.findAll();
        return customers;
    }

    /**
     * Method exposes API rest point for searching for developers by first or last name
     * @param name Customer first or last name
     * @return Customer full details
     */
    @GetMapping("/customer/{name}")
    public Optional<Developer> getByFirstName(@PathVariable("name") String name){
        //Uses QueryDSL generated class
        QDeveloper qDeveloper = new QDeveloper("name");

        //QueryDSL mongo query
        BooleanExpression filterByFirstName = qDeveloper.firstName.equalsIgnoreCase(name).or(qDeveloper.lastName.equalsIgnoreCase(name));

        Optional<Developer> customer = this.developerRepository.findOne(filterByFirstName);
        return customer;
    }
}

