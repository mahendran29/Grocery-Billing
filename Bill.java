package Model;

import Enums.Payment;

import java.util.ArrayList;
import java.util.List;

public class Bill
{
    private int billNumber;
    private String billDate;
    private Customer customer;
    private List<Product> product = new ArrayList<>();
    private Payment modeOfPayment;
    private int total;

    public Bill()
    {
        billNumber = 0;
        billDate = "";
        customer = null;
        modeOfPayment = null;
        total = 0;
    }

    public Bill(int billNumber, String billDate, Customer customer, Product product, Payment modeOfPayment, int total) {
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.customer = customer;
        this.product.add(product);
        this.modeOfPayment = modeOfPayment;
        this.total = total;
    }

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product.add(product);
    }

    public Payment getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(Payment modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
