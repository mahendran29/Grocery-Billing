package interfaces;

import Enums.BillChoice;
import Enums.CardType;
import Enums.Payment;
import Enums.UPIMode;
import Model.Bill;
import Model.Product;
import java.util.List;

public interface BillingService extends Cgst, Sgst
{
    void getBill();
    BillChoice getBillPaymentConfirmation();
    boolean paymentProcess(int netAmount);
    boolean payment(Bill bill, double amount);
    boolean payThroughCash(double amount);
    boolean payThroughUPI(double amount);
    boolean payThroughCard(double amount);
    List<Product> getProductsFromCustomer();
    void setCustomerDetails(String customerName,String customerPhoneNumber,String customerLocation);
    void addToCustomerDetails( String customerName, String customerPhoneNumber, String customerLocation);
    boolean checkCustomerExists(String phoneNumber);
    void getExistingCustomer(String customerPhoneNumber);
    Product getProductByID(String productID,int productCount);
    Product getProductByBrand(String brandName,int productCount);
    int getBilledProductsAmount(List<Product> customerProducts);
    double calculateCGST(double amount) ;
    double calculateSGST(double amount);
    int calculateBill(Bill bill, int productsAmount, int cgst, int sgst);
    List<Product> confirmBillPayment(List<Product> customerProducts);
    void setPaymentMode(Payment paymentMode);
    boolean cashPayment(double amount,double cashGiven) ;
    boolean upiPayment(double amount, int cashGiven, UPIMode upiMode, String upiType);
    String checkRewards();
    boolean cardPayment(double amount, int cashGiven, CardType cardType);
    void addToSalesHistory();
    int getBillNumberFromDB();
    int getParticularProductTotalCost(int count,int cost);

}
