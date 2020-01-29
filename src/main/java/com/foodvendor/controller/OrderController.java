
package com.foodvendor.controller;

import com.foodvendor.doa.OrderRepository;
import com.foodvendor.model.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class exposes API rest points for querying Order mongo database collection.
 * Add cross origin addresses to enable access; eg @CrossOrigin(origins = {"http://localhost:3000"}).
 */
@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    /**
     * Exposes API rest point for querying order database by order id variable
     * @param id Order id to be queried
     * @return Full order details
     */
    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") String id) {
        Order order = orderRepository.findByOrderID(id);
        return order;
    }

    /**
     * Exposes API rest point for listing all orders in order mongo database collection
     * @return Full details of all orders
     */
    @GetMapping("/all")
    public List<Order> getAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by menu item id or description
     * @param idOrDescription Menu item id or description to be queried
     * @return List of orders of specified menu item
     */
    @GetMapping("/menu/{idOrDescription}")
    public List<Order> getByMenuItemId(@PathVariable("idOrDescription") String idOrDescription){
        //Uses QueryDSL generated classes
        QOrder qOrder = new QOrder("idOrDescription");

        //QueryDsl mongo query
        BooleanExpression filterByMenuItemId = qOrder.orderDetails.id.eq(idOrDescription)
                .or(qOrder.orderDetails.description.containsIgnoreCase(idOrDescription));

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByMenuItemId);
        return orders;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by delivery status
     * @param status Delivery status (DELIVERY_TRUE or DELIVERY_FALSE)
     * @return List of orders filtered  by specified status
     */
    @GetMapping("/delivery/{status}")
    public List<Order> getByDeliveryStatus(@PathVariable("status") Delivery status){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("status");

        //QueryDsl mongo query
        BooleanExpression filterByDeliveryStatus = qOrder.deliveryStatus.eq(status);

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByDeliveryStatus);
        return orders;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by payment option
     * @param option Payment option (CASH or CARD)
     * @return List of orders filtered by specified payment option
     */
    @GetMapping("/payment/{option}")
    public List<Order> getByPaymentOption(@PathVariable("option") PaymentOption option){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("option");

        //QueryDsl mongo query
        BooleanExpression filterByPaymentOption = qOrder.paymentOption.eq(option);

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByPaymentOption);
        return orders;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by customer id
     * @param id Customer id to be queried
     * @return List of orders filtered by specified customer id
     */
    @GetMapping("/customer/id/{id}")
    public List<Order> getByCustomerId(@PathVariable("id") String id){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("id");

        //QueryDsl mongo query
        BooleanExpression filterByCustomerId = qOrder.customer.email.eq(id);

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByCustomerId);
        return orders;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by customer first or last name
     * @param name Customer first of last name to be queried
     * @return List of orders filtered by specified customer name
     */
    @GetMapping("/customer/name/{name}")
    public List<Order> getByCustomerName(@PathVariable("name") String name){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("name");

        //QueryDsl mongo query
        BooleanExpression filterByCustomerName = qOrder.customer.firstName.equalsIgnoreCase(name)
                .or(qOrder.customer.lastName.equalsIgnoreCase(name));

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByCustomerName);
        return orders;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by menu item id or description
     * and getting total cost of orders by specified id or description
     * @param idOrDescription Menu item id
     * @return Filtered map with <Menu item id, total cost>
     */
    @GetMapping("/cost/menuId/{idOrDescription}")
    public Map<String, Double> getTotalCostByMenuItemId(@PathVariable("idOrDescription") String idOrDescription){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("idOrDescription");

        //QueryDsl mongo query
        BooleanExpression filterByMenuItemId = qOrder.orderDetails.id.eq(idOrDescription)
                .or(qOrder.orderDetails.description.containsIgnoreCase(idOrDescription));

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByMenuItemId);

        //Maps menu item id or description with total cost
        Map<String, Double> orderMap = new HashMap<>();
        orderMap.put(idOrDescription,
                Double.parseDouble(new DecimalFormat("#0.00").format(orders.stream().mapToDouble(order -> order.getTotalCost()).sum())));
        return orderMap;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by delivery status
     * and getting total cost of orders by specified delivery status parameter
     * @param status Delivery status (DELIVERY_TRUE or DELIVERY_FALSE)
     * @return Filtered map with <Delivery status, total cost>
     */
    @GetMapping("/cost/delivery/{status}")
    public Map<String, Double> getTotalCostByDeliveryStatus(@PathVariable("status") Delivery status){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("status");

        //QueryDsl mongo query
        BooleanExpression filterByDeliveryStatus = qOrder.deliveryStatus.eq(status);

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByDeliveryStatus);

        //Maps delivery status with total cost
        Map<String, Double> orderMap = new HashMap<>();
        orderMap.put(String.valueOf(status),
                Double.parseDouble(new DecimalFormat("#0.00").format(orders.stream().mapToDouble(order -> order.getTotalCost()).sum())));
        return orderMap;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by payment option
     * and getting total cost of orders by specified payment option parameter
     * @param option Payment option (CASH or CARD)
     * @return Filtered map with <Payment option, total cost>
     */
    @GetMapping("/cost/payment/{option}")
    public Map<String, Double> getTotalCostByPaymentOption(@PathVariable("option") PaymentOption option){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("option");

        //QueryDsl mongo query
        BooleanExpression filterByPaymentOption = qOrder.paymentOption.eq(option);

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByPaymentOption);

        //Maps payment option with total cost
        Map<String, Double> orderMap = new HashMap<>();
        orderMap.put(String.valueOf(option),
                Double.parseDouble(new DecimalFormat("#0.00").format(orders.stream().mapToDouble(order -> order.getTotalCost()).sum())));
        return orderMap;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by customer id
     * and getting total cost of orders by specified customer parameter
     * @param id Customer id
     * @return Filtered map with <Customer id, total cost>
     */
    @GetMapping("/cost/customer/id/{id}")
    public Map<String, Double> getTotalCostByCustomerId(@PathVariable("id") String id){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("id");

        //QueryDsl mongo query
        BooleanExpression filterByCustomerId = qOrder.customer.email.eq(id);

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByCustomerId);

        //Maps customer id with total cost
        Map<String, Double> orderMap = new HashMap<>();
        orderMap.put(id,
                Double.parseDouble(new DecimalFormat("#0.00").format(orders.stream().mapToDouble(order -> order.getTotalCost()).sum())));
        return orderMap;
    }

    /**
     * Exposes API rest point for querying order mongo database collection by customer first and last name
     * and getting total cost of orders by specified customer parameter
     * @param name Customer name
     * @return Filtered map with <Customer first or last name, total cost>
     */
    @GetMapping("/cost/customer/name/{name}")
    public Map<String, Double> getTotalCostByCustomerName(@PathVariable("name") String name){
        //Uses QueryDsl generated classes
        QOrder qOrder = new QOrder("name");

        //QueryDsl mongo query
        BooleanExpression filterByCustomerName = qOrder.customer.firstName.equalsIgnoreCase(name)
                .or(qOrder.customer.lastName.equalsIgnoreCase(name));

        List<Order> orders = (List<Order>) this.orderRepository.findAll(filterByCustomerName);

        //Maps customer name with total cost
        Map<String, Double> orderMap = new HashMap<>();
        orderMap.put(name,
                Double.parseDouble(new DecimalFormat("#0.00").format(orders.stream().mapToDouble(order -> order.getTotalCost()).sum())));
        return orderMap;
    }
}

