package View;

import Controller.StockController;
import Enums.StockReport;
import Helper.Helping;
import Model.Product;
import interfaces.ReportsViewService;
import interfaces.StockService;
import interfaces.StockViewService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockView implements StockViewService {

    Scanner scanner = new Scanner(System.in);
    Helping helper = new Helping();
    StockService stockController = new StockController();
    ReportsViewService reportsView = new ReportsView();

    public void getStockDetails() {

        while (true)
        {
            StockReport userChoice = getStockChoice();
            List<Product> productsList = new ArrayList<>();

            switch (userChoice) {
                case ALL -> productsList = stockController.getEntireStock();
                case PRODUCT_ID ->  productsList = stockController.getStockBasedOnID(getProductID());
                case PRODUCT_NAME -> productsList = stockController.getStockBasedOnName(getProductName());
                case BRAND -> productsList = stockController.getStockBasedOnBrand(getProductBrand());
                case EXIT -> {
                    return;
                }
            }

            if(productsList.isEmpty()) {
                reportsView.reportStatus(false);
            }
            displayProductsList(productsList);
            reportsView.reportStatus(true);
        }

    }

    public String getProductID() {
        System.out.println("Enter the product ID: ");
        return scanner.nextLine();
    }

    // Getting the Product name
    public String getProductName() {
        System.out.println("Enter the product name purchased: ");
        return scanner.nextLine();
    }

    // Getting the brand name of the Product
    public String getProductBrand() {
        System.out.println("Enter the brand of the product purchased: ");
        return scanner.nextLine();
    }

    public StockReport getStockChoice() {

        String choice;
        while(true) {
            System.out.println("1.ALL REPORTS 2.BASED ON PRODUCT ID 3.BASED ON PRODUCT NAME 4.BASED ON BRAND 5.EXIT");
            choice = scanner.nextLine();
            boolean isImproperInput = !helper.validatingNumber(choice) || (Integer.parseInt(choice) > StockReport.values().length) || choice.equals("0");

            if (isImproperInput) {
                System.out.println("Give proper input!");
                continue;
            }

            return StockReport.values()[Integer.parseInt(choice) - 1];
        }
    }

    public void displayProductsList(List<Product> productsList) {

        for(Product product : productsList) {
            System.out.println("Product ID: "+product.getProductId());
            System.out.println("Name: "+product.getProductName());
            System.out.println("Description: "+product.getProductDescription());
            System.out.println("Brand: "+product.getProductBrand());
            System.out.println("Count:"+product.getProductCount());
            System.out.println("Cost: "+product.getProductCost());
        }
    }
}
