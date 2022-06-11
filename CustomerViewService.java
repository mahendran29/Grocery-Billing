package interfaces;

import Model.Customer;
import java.util.Map;

public interface CustomerViewService {
    boolean getCustomerDetails();
    void displayCustomerDetails(Map<String, Customer> customerDetails);
}
