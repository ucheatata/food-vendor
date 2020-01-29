package com.foodvendor.controller;


import com.foodvendor.doa.DeveloperRepository;
import com.foodvendor.doa.OrderRepository;
import com.foodvendor.model.Developer;
import com.foodvendor.model.Order;
import com.foodvendor.model.RoleName;
import com.foodvendor.payload.ApiResponse;
import com.foodvendor.payload.OrderRequest;
import com.foodvendor.payload.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Arrays;

/**
 * Class exposes API rest points for user sign up and making orders.
 * Add cross origin addresses to enable access; eg @CrossOrigin(origins = {"http://localhost:3000"}).
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Method exposes rest point for user sign up
     * @param signUpRequest Hold variables for user sign up values
     * @return Response confirming successful user profile creation
     */
    @PostMapping(value = "/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        // Creating user's account
        Developer customer = new Developer(
                signUpRequest.getEmail(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );

        //Set role for access control
        customer.setRoles(Arrays.asList(String.valueOf(RoleName.ROLE_USER)));

        //customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        this.developerRepository.save(customer);
        return ResponseEntity.ok("User profile created successfully.");
    }

    /**
     * Method exposes rest points for placing orders
     * @param orderRequest Holds variables for placing order
     * @return Api response that can enable vendor notification when an order is confirmed
     */
    @PostMapping(value = "/order")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderRequest orderRequest){

        //Make order and confirm
        Order order = new Order(
                developerRepository.findAll(),
                orderRequest.getMenuItemId(),
                orderRequest.getCustomerId(),
                orderRequest.isDeliveryStatus(),
                orderRequest.getPaymentOption()
                );
        order.confirmOrder();
        this.orderRepository.save(order);
        return ResponseEntity.ok().body(new ApiResponse(true,"Order placed."));
    }

}
