package View;

import Controller.BillController;
import Enums.*;
import Helper.Helping;
import Model.Bill;
import Model.Product;
import interfaces.BillViewService;
import interfaces.BillingService;

import java.util.List;
import java.util.Scanner;

public class BillView implements BillViewService
{
    Scanner scanner = new Scanner(System.in);
    BillingService billController = new BillController(this);
    Helping helper = new Helping();

    public void billingOperation() {
        billController.getBill();
    }

    public String getCustomerPhoneNumber()
    {
        while(true)
        {
            System.out.println("Enter the customer's phone number: ");
            String phoneNumber = scanner.nextLine();

            // Validate if the given number is valid or not
            boolean isValidNumber = helper.validateMobileNumber(phoneNumber);
            if(!isValidNumber)
            {
                System.out.println("Invalid Phone number");
                continue;
            }
            return phoneNumber;
        }
    }


    public void getCustomerDetails(String customerPhoneNumber)
    {
        String customerName = getCustomerName();
        String customerLocation = getCustomerLocation();
        billController.setCustomerDetails(customerName,customerPhoneNumber,customerLocation);
    }

    public String getCustomerName()
    {
        System.out.println("Enter the customer's name: ");
        return scanner.nextLine();
    }

    public String getCustomerLocation()
    {
        System.out.println("Enter the customer's location: ");
        return scanner.nextLine();
    }

    public boolean isBillingContinue() {

        while (true)
        {
            System.out.println("Continue to add products? 1.YES 2.NO");
            String billDone = scanner.nextLine();
            if(helper.validatingNumber(billDone)) {
                return Integer.parseInt(billDone) != 2;
            }

            System.out.println("Enter proper!");
        }
    }

    public void displayStockStatus(){
        System.out.println("Product out of stock!");
    }

    public ProductChoice getProductChoice()
    {
        while(true) {
            System.out.println("1.Product ID 2.Product brand name");
            String choice = scanner.nextLine();

            boolean isProperInput = !helper.validatingNumber(choice) || (Integer.parseInt(choice) > ProductChoice.values().length);
            if (isProperInput || choice.equals("0")) {
                System.out.println("Give proper input!");
                continue;
            }

            return ProductChoice.values()[Integer.parseInt(choice) - 1];
        }
    }

    public String getProductID()
    {
        System.out.println("Enter the product ID: ");
        return scanner.nextLine();
    }

    public String getBrandName()
    {
        System.out.println("Enter the brand Name: ");
        return scanner.nextLine();
    }

    public int getQuantity()
    {
        while (true)
        {
            System.out.println("Enter the Quantity: ");
            String quantity = scanner.nextLine();
            if(helper.validatingNumber(quantity)) {
                return Integer.parseInt(quantity);
            }

            System.out.println("Enter proper!");
        }
    }


    public String getBillPaymentChoice(){

        while (true)
        {
            System.out.println("Confirm? 1.YES 2.NO");
            String choice = scanner.nextLine();
            if(helper.validatingNumber(choice)) {
                return choice;
            }

            System.out.println("Enter proper!");
        }
    }

    public void showIncorrectInputStatus() {
        System.out.println("Give proper input!");
    }

    public String getModeOfPayment()
    {
        while (true)
        {
            System.out.println("Enter your mode of payment: 1.Cash 2.UPI 3.Card");
            String paymentMode = scanner.nextLine();
            if(helper.validatingNumber(paymentMode)) {
                return paymentMode;
            }

            System.out.println("Enter proper!");
        }
    }

    public String getUPIMode()
    {
        while (true)
        {
            System.out.println("1.Google Pay  2.PhonePe?");
            String paymentMode = scanner.nextLine();
            if(helper.validatingNumber(paymentMode)) {
                return paymentMode;
            }

            System.out.println("Enter proper!");
        }
    }

    public String getUPIModeType()
    {
        while (true)
        {
            System.out.println("1.QR Code or 2.UPI ID");
            String paymentMode = scanner.nextLine();
            if(helper.validatingNumber(paymentMode)) {
                return paymentMode;
            }

            System.out.println("Enter proper!");
        }
    }

    public int getCustomerCash()
    {
        while (true)
        {
            System.out.println("Enter the Cash given by the customer: ");
            String quantity = scanner.nextLine();
            if(helper.validatingNumber(quantity)) {
                return Integer.parseInt(quantity);
            }

            System.out.println("Enter proper!");
        }
    }

    public String getCardType()
    {
        while (true)
        {
            System.out.println("1.Credit Card 2.Debit Card");
            String paymentMode = scanner.nextLine();
            if(helper.validatingNumber(paymentMode)) {
                return paymentMode;
            }

            System.out.println("Enter proper!");
        }
    }

    public void rewardStatus(String reward)
    {
        System.out.println("Google Pay rewards:\n" +reward);
    }


    public void paymentStatus(boolean isPaymentDone) {
        if(isPaymentDone)
            System.out.println("Payment successful!");
        else
            System.out.println("Payment failed");
    }

    public void displayLessStock(List<Product> lessStock ) {

        for(Product product : lessStock) {
            System.out.println("WARNING: "+product.getProductBrand() + " has less count in stock");
        }
    }

    public void displayEntireBill(Bill bill, int productAmount, int cgst, int sgst,List<Product> lessStock)
    {

        displayLessStock(lessStock);
        System.out.println("\n------------------------- Bill -------------------------");
        System.out.println("Bill number: " + bill.getBillNumber());
        System.out.println("Billing date: " + bill.getBillDate());
        System.out.println("Customer name: " + bill.getCustomer().getCustomerName());
        System.out.println("Customer phone number: " + bill.getCustomer().getCustomerPhoneNumber());

        System.out.println("Product Name       Quantity     Rate        Cost");
        for(Product product : bill.getProduct())
        {
            int cost = billController.getParticularProductTotalCost(product.getProductCount(), product.getProductCost());
            System.out.println(product.getProductBrand() +"               " + product.getProductCount() +"            "+ product.getProductCost() +"         "+cost);
        }
        System.out.println("Amount: "+productAmount);
        System.out.println("CGST Tax: "+cgst);
        System.out.println("SGST Tax: "+sgst);
        System.out.println("Mode of Payment: " + bill.getModeOfPayment());
        System.out.println("Net amount: " + bill.getTotal());

        System.out.println("--------------------------------------------------------\n");

    }

    public void amountToPay(int netAmount)
    {
        System.out.println("Amount to be paid: "+netAmount);
    }

    public void showCancelledStatus(){
        System.out.println("Bill cancelled");
    }
}
