package Model;

public class Customer
{
    private String customerName;
    private String customerPhoneNumber;
    private String customerLocation;

    public Customer()
    {
        customerName = "";
        customerPhoneNumber = "";
        customerLocation = "";
    }

    public Customer(String customerName, String customerPhoneNumber, String customerLocation) {
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerLocation = customerLocation;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public void setCustomerLocation(String customerLocation) {
        this.customerLocation = customerLocation;
    }
}
