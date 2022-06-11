package View;

import Controller.CustomerController;
import Model.Customer;
import interfaces.CustomerService;
import interfaces.CustomerViewService;

import java.util.Map;
import java.util.Set;

public class CustomerView implements CustomerViewService {

    CustomerService customerController = new CustomerController();

    public boolean getCustomerDetails() {
        Map<String,Customer> customerDetails = customerController.getAllCustomers();
        displayCustomerDetails(customerDetails);
        return true;
    }

    public void displayCustomerDetails(Map<String,Customer> customerDetails) {
        System.out.println("********************** Customers ***********************");
        Set<Map.Entry<String, Customer>> set = customerDetails.entrySet();
        for(Map.Entry<String, Customer> me : set)
        {
            System.out.println("Customer Name: "+me.getValue().getCustomerName());
            System.out.println("Customer Phone Number: "+me.getValue().getCustomerPhoneNumber());
            System.out.println("Customer Location: "+me.getValue().getCustomerLocation());
            System.out.println("*********************************************************");
        }
    }
}
