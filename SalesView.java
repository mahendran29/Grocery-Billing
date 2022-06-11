package View;

import Controller.SalesController;
import Enums.SalesReport;
import Helper.Helping;
import Model.Bill;
import Model.Product;
import interfaces.ReportsViewService;
import interfaces.SalesService;
import interfaces.SalesViewService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SalesView implements SalesViewService
{
    Scanner scanner = new Scanner(System.in);
    Helping helper = new Helping();
    SalesService salesController = new SalesController();
    ReportsViewService reportsView = new ReportsView();

    public void getSalesHistory() {

        while (true) {
            SalesReport stockChoice = getReportChoice();

            switch (stockChoice) {
                case ALL ->{
                    Map<String,Integer> productsList = salesController.getEntireSales();
                    displayEntireSalesReport(productsList);
                }
                case DATE -> {
                    List<Bill> salesReport = salesController.getSalesBasedOnDate(getPurchaseDate());
                    displaySalesReport(salesReport);
                }
                case HIGHEST_BRAND -> {
                    List<String> highestSold  = salesController.getSalesBasedOnProductBrandSoldHighest();
                    displayHighestSold("brand", highestSold);

                }
                case HIGHEST_PRODUCT -> {
                    List<String> highestSold = salesController.getSalesBasedOnProductSoldHighest();
                    displayHighestSold("product", highestSold);
                }
                case EXIT -> {
                    return;
                }
            }
            reportsView.reportStatus(true);
        }

    }

    public SalesReport getReportChoice() {

        String choice;
        while(true) {

            System.out.println("1.ALL REPORTS 2. BASED ON DATE 3.BASED ON HIGHEST BRAND SOLD 4.BASED ON HIGHEST PRODUCT 5.EXIT");
            choice = scanner.nextLine();
            boolean isProperInput = !helper.validatingNumber(choice) || (Integer.parseInt(choice) > SalesReport.values().length) || choice.equals("0");

            if (isProperInput) {
                System.out.println("Give proper input!");
                continue;
            }

            return SalesReport.values()[Integer.parseInt(choice) - 1];
        }
    }

    public String getPurchaseDate() {
        System.out.println("Enter the Date: ");
        return scanner.nextLine();
    }

    public void displaySalesReport(List<Bill> salesReport) {
        for(Bill bill : salesReport) {
            System.out.println("\n------------------------- Bill -------------------------");
            System.out.println("Bill number: " + bill.getBillNumber());
            System.out.println("Billing date: " + bill.getBillDate());
            System.out.println("Customer name: " + bill.getCustomer().getCustomerName());
            System.out.println("Customer phone number: " + bill.getCustomer().getCustomerPhoneNumber());

            System.out.println("Product Name       Quantity     Rate        Cost");
            for(Product product : bill.getProduct()) {
                int cost =  product.getProductCount() * product.getProductCost();
                System.out.println(product.getProductBrand() +"               " + product.getProductCount() +"            "+ product.getProductCost() +"         "+cost);
            }

            System.out.println("Mode of Payment: " + bill.getModeOfPayment());
            System.out.println("Net amount: " + bill.getTotal());

            System.out.println("--------------------------------------------------------\n");
        }
    }

    public void displayHighestSold(String name,List<String> highestSold) {

        System.out.println("\nHighest sold "+name+"(s) are:\n");
        for(String productName : highestSold) {
            System.out.print(productName+"\n");
        }
    }

    public void displayEntireSalesReport(Map<String,Integer> productsList) {

        Set<Map.Entry<String, Integer>> set = productsList.entrySet();
        for(Map.Entry<String, Integer> product : set) {
            System.out.println(product.getKey()+" "+product.getValue());
        }
    }
}
