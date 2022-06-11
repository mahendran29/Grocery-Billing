package interfaces;

import Enums.ProductChoice;
import Model.Bill;
import Model.Product;

import java.util.List;

public interface BillViewService {

    void billingOperation();
    boolean isBillingContinue();
    void displayStockStatus() ;
    void getCustomerDetails(String customerPhoneNumber);
    String getCustomerName();
    String getCustomerLocation();
    String getCustomerPhoneNumber();
    ProductChoice getProductChoice();
    String getProductID();
    String getBrandName();
    int getQuantity();
    String getBillPaymentChoice();
    void showIncorrectInputStatus();
    String getModeOfPayment();
    String getUPIMode();
    String getUPIModeType();
    int getCustomerCash();
    String getCardType();
    void rewardStatus(String reward);
    void paymentStatus(boolean isPaymentDone);
    void displayLessStock(List<Product> lessStock );
    void displayEntireBill(Bill bill, int productAmount, int cgst, int sgst,List<Product> lessStock);
    void amountToPay(int netAmount);
    void showCancelledStatus();
}
