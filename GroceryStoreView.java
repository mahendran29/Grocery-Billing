package View;

import Controller.EmployeeController;
import Enums.AdminStoreOperation;
import Enums.BillerStoreOperation;
import Helper.Helping;
import interfaces.*;

import java.util.Scanner;

public class GroceryStoreView implements GroceryStoreViewService
{
    Helping helper = new Helping();
    Scanner scanner = new Scanner(System.in);

    EmployeeService employeeController = new EmployeeController();

    public void storeOperations(String empID)
    {
        boolean isAdmin = employeeController.authenticateEmployee(empID);
        if (isAdmin) {
            adminStoreOperations();
        }
        else {
            billerStoreOperations();
        }
    }

    public void adminStoreOperations() {

        displayShopDetails();
        boolean isAppRunning = true;
        while (isAppRunning) {
            // Getting user input
            AdminStoreOperation operationChosen = getAdminOperationUserInput();

            switch (operationChosen) {
                case PURCHASE -> {
                    PurchaseViewService purchaseView = new PurchaseView();
                    purchaseView.purchaseProducts();
                }
                case BILL -> {
                    BillViewService billView = new BillView();
                    billView.billingOperation();
                }
                case REPORTS -> {
                    ReportsViewService reportsView = new ReportsView();
                    reportsView.showReports();
                }
                case CREATEEMPLOYEE ->{
                    EmployeeViewService employeeView = new EmployeeView();
                    employeeView.createEmployee();
                }
                case EXIT -> {
                    isAppRunning = false;
                    System.out.println("Exiting!");
                }
            }
        }
    }

    public void billerStoreOperations() {

        displayShopDetails();
        boolean isAppRunning = true;
        while (isAppRunning) {
            // Getting user input
            BillerStoreOperation operationChosen = getBillerOperationUserInput();
            switch (operationChosen) {
                case BILL -> {
                    BillViewService billView = new BillView();
                    billView.billingOperation();
                }
                case REPORTS -> {
                    ReportsViewService reportsView = new ReportsView();
                    reportsView.showReports();
                }
                case EXIT -> {
                    isAppRunning = false;
                    System.out.println("Exiting!");
                }
            }
        }
    }

    public AdminStoreOperation getAdminOperationUserInput() {

        String storeOperation;
        while(true) {

            storeOperation = getAdminUserInput();
            boolean isProperInput = !helper.validatingNumber(storeOperation) || (Integer.parseInt(storeOperation) > AdminStoreOperation.values().length) || storeOperation.equals("0");

            if (isProperInput) {
                System.out.println("Give proper input!");
                continue;
            }

            return AdminStoreOperation.values()[Integer.parseInt(storeOperation) - 1];
        }
    }

    private String getAdminUserInput() {
        System.out.println("""
                Enter the choice:
                1.Purchase
                2.Bill
                3.Reports
                4.Create Employee
                5.Exit""");

        return scanner.nextLine();
    }

    public BillerStoreOperation getBillerOperationUserInput() {

        String storeOperation;
        while(true) {

            storeOperation = getBillerUserInput();
            boolean isProperInput = !helper.validatingNumber(storeOperation) || (Integer.parseInt(storeOperation) > BillerStoreOperation.values().length) || storeOperation.equals("0");

            if (isProperInput) {
                System.out.println("Give proper input!");
                continue;
            }

            return BillerStoreOperation.values()[Integer.parseInt(storeOperation) - 1];
        }
    }

    private String getBillerUserInput() {
        System.out.println("""
                Enter the choice:
                1.Bill
                2.Reports
                3.Exit""");

        return scanner.nextLine();
    }

    public void displayShopDetails() {
        String shopName = "Nilgiris";
        String location = "Ayanavaram";
        System.out.println("Shop name: " + shopName + "\nLocation: " + location+"\n");
    }
}
