package com.foodvendor.model;

import com.foodvendor.doa.DeveloperRepository;
import com.foodvendor.doa.MenuDataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Orders")
public class Order  {

    @Id
    private String orderID;
    private Menu_v2 orderDetails;
    private Developer customer;
    private Delivery deliveryStatus;
    private double totalCost;
    private PaymentOption paymentOption;
    private double deliveryFee;
    private double cardDiscount;


    public Order(List<Developer> developerList, String menuItemID, String developerId, boolean deliveryStatus, PaymentOption paymentOption){
        for(Developer customer: developerList){
            if(customer.getEmail().equals(developerId)){
                this.customer = customer;
            }
        }
        this.paymentOption = paymentOption;
        this.orderDetails = MenuDataSeeder.getInstance().findById(menuItemID);
        this.deliveryStatus = Delivery.GetDeliveryStatus(deliveryStatus);
        this.orderID = customer.getEmail() + "_" + orderDetails.getId();
    }

    public Order(){}

    public Menu_v2 getOrderDetails() {
        return orderDetails;
    }

    public Developer getCustomer() {
        return customer;
    }

    public String getOrderID() {
        return orderID;
    }

    public Delivery getDeliveryStatus() {
        return deliveryStatus;
    }

    public double getTotalCost() { return totalCost; }

    public PaymentOption getPaymentOption() { return paymentOption; }

    public void confirmOrder(){

        //Check delivery status and add delivery fee if status is true
        if(getDeliveryStatus().equals(Delivery.DELIVERY_TRUE)){
            deliveryFee = Delivery.DELIVERY_TRUE.getDeliveryDistance()*10;
            //totalCost = orderDetails.getCost()+(Delivery.DELIVERY_TRUE.getDeliveryDistance()*10);
        }
        else{
            deliveryFee = 0;
        }

        //Check payment option and add card discount if option is card
        if(getPaymentOption().equals(PaymentOption.CARD)){
            cardDiscount = (2.5/100)*orderDetails.getCost();
        }
        else if(getPaymentOption().equals(PaymentOption.CASH)){
            cardDiscount = 0;
        }

        totalCost = orderDetails.getCost() + deliveryFee + cardDiscount;
    }
}

