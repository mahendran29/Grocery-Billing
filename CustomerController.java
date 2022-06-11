package Controller;

import Model.Customer;
import Model.DataProvider;
import interfaces.CustomerService;

import java.io.IOException;
import java.util.Map;

public class CustomerController implements CustomerService {
    DataProvider dataProvider = DataProvider.getInstance();
    public Map<String, Customer> getAllCustomers()
    {
        try {
            dataProvider.getUpdatedCustomerDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataProvider.getCustomers();
    }
}
