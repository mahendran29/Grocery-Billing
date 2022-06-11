package View;

import Controller.PurchaseController;
import Enums.CustomReport;
import Enums.PurchaseChoice;
import Helper.Helping;
import Model.Product;
import Model.Purchase;
import interfaces.PurchaseService;
import interfaces.PurchaseViewService;
import interfaces.ReportsViewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PurchaseView implements PurchaseViewService {

    Scanner scanner = new Scanner(System.in);

    PurchaseService purchaseController = new PurchaseController();
    ReportsViewService reportsView = new ReportsView();
    Helping helper = new Helping();

    public void purchaseProducts() {

        String purchaseID = getPurchaseID();
        String supplierName = getSupplierName();
        String productID = getProductID();
        String productName = getProductName();
        String productDescription = getProductDescription();
        String productBrand = getProductBrand();
        int productCount = getProductCount();
        int productCost = getProductCost();

        boolean isPurchaseDone = purchaseController.setPurchaseDetails(purchaseID,supplierName,productID,productName,productDescription,productBrand,productCount,productCost);

        if(isPurchaseDone) {
            displayPurchaseStatus(isPurchaseDone);
            displayPurchaseOrderDetails(purchaseID,supplierName,productID,productName,productDescription,productBrand,productCount,productCost);
        }
        else {
            displayPurchaseStatus(isPurchaseDone);
        }

    }

    public void displayPurchaseOrderDetails(String purchaseID,String supplierName,String productId,String productName,String productDescription,String productBrand,int productCount,int productCost) {

        System.out.println("----------------- ORDER DETAILS -----------------");
        System.out.println("PURCHASE ID: " +purchaseID);
        System.out.println("ORDER PLACED DATE: "+helper.getDate());
        System.out.println("ORDER DELIVERED DATE: -");
        System.out.println("SUPPLIER NAME: "+supplierName);
        System.out.println("PURCHASE STATUS: ORDERED");
        System.out.println("PRODUCTS ORDERED: ");
        System.out.println("    Product ID: "+productId);
        System.out.println("    Name: "+productName);
        System.out.println("    Description: "+productDescription);
        System.out.println("    Brand: "+productBrand);
        System.out.println("    Count:"+productCount);
        System.out.println("    Cost: "+productCost);
        System.out.println("----------------- ORDER DETAILS -----------------");
    }

    public void getPurchaseDetails() {

        while (true)
        {
            PurchaseChoice choice = getUserInput();
            if(choice == PurchaseChoice.CHANGE_STATUS) {

                boolean isPurchaseStatusChanged = false;
                String purchaseID = getPurchaseIDFromUser();

                try {
                    isPurchaseStatusChanged = purchaseController.changeParticularPurchaseOrder(purchaseID);
                }catch (Exception e) {
                    e.printStackTrace();
                    reportsView.reportStatus(false);
                }

                changePurchaseOrderStatus(isPurchaseStatusChanged);
                reportsView.reportStatus(true);
            }
            else if(choice == PurchaseChoice.REPORTS) {

                while (true) {
                    CustomReport reportChoice = getReportChoice();
                    List<Purchase> purchaseList = new ArrayList<>();

                    switch (reportChoice) {
                        case ALL -> purchaseList = purchaseController.getEntirePurchase();
                        case PURCHASE_ID ->  purchaseList = purchaseController.getPurchaseBasedOnPurchaseID(getPurchaseIDFromUser());
                        case PRODUCT_ID -> purchaseList = purchaseController.getPurchaseBasedOnProductID(getProductID());
                        case PRODUCT_NAME -> purchaseList = purchaseController.getPurchaseBasedOnProductName(getProductName());
                        case BRAND -> purchaseList = purchaseController.getPurchaseBasedOnProductBrand(getProductBrand());
                        case DATE -> purchaseList = purchaseController.getPurchaseBasedOnDate(getPurchaseDate());
                        case SUPPLIER -> purchaseList = purchaseController.getPurchaseBasedOnSupplier(getSupplierName());
                        case EXIT -> {
                            return;
                        }
                    }
                    if(purchaseList.isEmpty()){
                        reportsView.reportStatus(false);
                    }
                    displayPurchaseDetails(purchaseList);
                    reportsView.reportStatus(true);
                }

            }
            else {
                return;
            }
        }
    }

    public void displayPurchaseDetails(List<Purchase> purchaseList) {

        if(purchaseList.isEmpty()) {
            return;
        }

        for(Purchase purchase : purchaseList) {
            System.out.println("\nPURCHASE ID: " +purchase.getPurchaseID());
            System.out.println("ORDER PLACED DATE: "+purchase.getDateOfPurchase());
            System.out.println("ORDER DELIVERED DATE: "+purchase.getDateOfDelivery());
            System.out.println("SUPPLIER NAME: "+purchase.getSupplierName());
            System.out.println("PURCHASE STATUS: "+purchase.getPurchaseStatus());
            System.out.println("PRODUCTS BOUGHT: ");

            for(Product product : purchase.getProductList()) {
                System.out.println("    Product ID: "+product.getProductId());
                System.out.println("    Name: "+product.getProductName());
                System.out.println("    Description: "+product.getProductDescription());
                System.out.println("    Brand: "+product.getProductBrand());
                System.out.println("    Count:"+product.getProductCount());
                System.out.println("    Cost: "+product.getProductCost());
            }
        }
    }


    // Getting the supplier name
    public String getPurchaseID() {
        String purchaseID = purchaseController.getPurchaseIDFromDB();
        return purchaseID;
    }

    public String getPurchaseIDFromUser() {
        System.out.println("Enter the purchase ID:");
        return scanner.nextLine();
    }

    // Getting the supplier name
    public String getSupplierName() {
        System.out.println("Enter the supplier name: ");
        return scanner.nextLine();
    }

    // Getting the Product ID of the product
    public String getProductID() {
        System.out.println("Enter the product ID: ");
        return scanner.nextLine();
    }

    public String getPurchaseDate() {
        System.out.println("Enter the Purchase Date: ");
        return scanner.nextLine();
    }

    // Getting the Product name
    public String getProductName() {
        System.out.println("Enter the product name purchased: ");
        return scanner.nextLine();
    }

    // Getting the description of the Product
    public String getProductDescription() {
        System.out.println("Enter a short description for the product: ");
        return scanner.nextLine();
    }

    // Getting the brand name of the Product
    public String getProductBrand() {
        System.out.println("Enter the brand of the product purchased: ");
        return scanner.nextLine();
    }

    // Getting the quantity of the Products bought
    public int getProductCount() {
        String count;
        do {
            System.out.println("Enter the count of Products: ");
            count = scanner.nextLine();
        } while (!helper.validatingNumber(count));

        return Integer.parseInt(count);
    }

    // Getting the cost of each Product bought
    public int getProductCost() {
        String cost;
        do {
            System.out.println("Enter the cost of each product purchased: ");
            cost = scanner.nextLine();
        } while (!helper.validatingNumber(cost));

        return Integer.parseInt(cost);
    }

    public PurchaseChoice getUserInput() {

        String choice;
        while(true) {
            System.out.println("1.CHANGE PURCHASE ORDER STATUS 2.DISPLAY REPORTS 3.EXIT");
            choice = scanner.nextLine();

            boolean isProperInput = !helper.validatingNumber(choice) || (Integer.parseInt(choice) > PurchaseChoice.values().length);
            if (isProperInput || choice.equals("0")) {
                System.out.println("Give proper input!");
                continue;
            }

            return PurchaseChoice.values()[Integer.parseInt(choice) - 1];
        }
    }

    public CustomReport getReportChoice() {

        String choice;
        while(true) {
            System.out.println("1.ALL REPORTS 2. BASED ON PURCHASE ID 3.BASED ON PRODUCT ID 4.BASED ON PRODUCT NAME 5.BASED ON BRAND  6.BASED ON DATE 7.BASED ON SUPPLIER 8.EXIT");
            choice = scanner.nextLine();
            boolean isProperInput = !helper.validatingNumber(choice) || (Integer.parseInt(choice) > CustomReport.values().length) || choice.equals("0");

            if (isProperInput) {
                System.out.println("Give proper input!");
                continue;
            }
            return CustomReport.values()[Integer.parseInt(choice) - 1];
        }
    }

    public void changePurchaseOrderStatus(boolean isChanged)
    {
        if(isChanged) {
            System.out.println("Order status changed!Products added to the stock!");
        }
        else {
            System.out.println("Sorry! Could not change the order status!");
        }
    }

    public void displayPurchaseStatus(boolean isPurchaseDone)
    {
        if(isPurchaseDone) {
            System.out.println("Order placed successfully!");
        }
        else {
            System.out.println("Oops! Could not place order.Try again!");
        }
    }
}
