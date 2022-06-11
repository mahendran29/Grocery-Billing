package interfaces;

import Model.Customer;

import java.util.Map;

public interface CustomerService
{
    Map<String, Customer> getAllCustomers();
}
