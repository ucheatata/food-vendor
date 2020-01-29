package com.foodvendor.doa;

import com.foodvendor.model.Developer;
import com.foodvendor.model.Order;
import com.foodvendor.model.PaymentOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Class use for seeding mock data for API testing
 */
@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private DeveloperRepository developerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public DataSeeder(DeveloperRepository developerRepository, OrderRepository orderRepository) {
        this.developerRepository = developerRepository;
        this.orderRepository = orderRepository;
    }

    public DataSeeder() {
    }

    private void processDeveloperLine(String line) {

        String parts[] = line.split(",");
        String email = parts[0];
        String firstName = parts[1];
        String lastName = parts[2];
        String password = parts[3];

        Developer developer = new Developer(
                email,
                firstName,
                lastName,
                passwordEncoder.encode(password)
        );

        developer.setRoles(Arrays.asList("ROLE_USER"));

        //developer.setPassword(passwordEncoder.encode(developer.getPassword()));
        //List<Developer> developers = Arrays.asList(developer);
        this.developerRepository.save(developer);
    }

    private void processOrderLine(String line){

        String parts[] = line.split(",");
        String menuItemId = parts[0];
        String developerId = parts[1];
        boolean deliveryStatus = Boolean.parseBoolean(parts[2]);
        PaymentOption paymentOption = PaymentOption.valueOf(parts[3]);

        Order order = new Order(
                this.developerRepository.findAll(),
                menuItemId,
                developerId,
                deliveryStatus,
                paymentOption
        );

        order.confirmOrder();
        this.orderRepository.save(order);
    }

    //@Override
    public void run(String... strings) throws Exception {

        try {
            URL url = DataSeeder.class.getResource("/static/assessment_seed_data.csv");
            File f = new File(url.toURI());
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) {
                    processDeveloperLine(inputLine);
                }
            }
        } catch (FileNotFoundException fnf) {
            System.err.println(fnf);
            System.exit(0);
        }

        try {
            URL url = DataSeeder.class.getResource("/static/order_v2_seed_data.csv");
            File f = new File(url.toURI());
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String inputLine = scanner.nextLine();
                if (inputLine.length() != 0) {
                    processOrderLine(inputLine);
                }
            }
        } catch (FileNotFoundException fnf) {
            System.err.println(fnf);
            System.exit(0);
        }

    }
}
