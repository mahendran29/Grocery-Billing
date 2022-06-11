package View;

import Enums.Report;
import Helper.Helping;
import interfaces.*;

import java.util.Scanner;

public class ReportsView implements ReportsViewService {

    Scanner scanner = new Scanner(System.in);
    Helping helper = new Helping();

    public void showReports(){

        boolean showReportsContinuously = true;
        while (showReportsContinuously) {
            Report operationChosen = getOperationUserInput();

            switch (operationChosen) {
                case STOCK -> {
                    StockViewService stockView = new StockView();
                    stockView.getStockDetails();
                }
                case PURCHASE -> {
                    PurchaseViewService purchaseView = new PurchaseView();
                    purchaseView.getPurchaseDetails();
                }
                case SALES -> {
                    SalesViewService salesView = new SalesView();
                    salesView.getSalesHistory();
                }
                case CUSTOMER -> {
                    CustomerViewService customerView = new CustomerView();
                    customerView.getCustomerDetails();
                }
                case EXIT -> {
                    System.out.println("Exiting");
                    showReportsContinuously = false;
                }
            }
        }
    }

    public Report getOperationUserInput() {


        String userInput;
        while(true) {
            userInput = getUserInput();

            boolean isProperInput = !helper.validatingNumber(userInput) || (Integer.parseInt(userInput) > Report.values().length);
            if (isProperInput || userInput.equals("0")) {
                System.out.println("Give proper input!");
                continue;
            }

            return Report.values()[Integer.parseInt(userInput) - 1];
        }
    }

    public String getUserInput() {
        System.out.println("1.Stock Details 2.Purchase Details 3.Sales Details 4.Customer Details 5.EXIT");
        return scanner.nextLine();
    }

    public void reportStatus(boolean isReportAvailable) {
        if(isReportAvailable)
            System.out.println("********************** Reports End ****************************");
        else
            System.out.println("*************** No Report available! ****************");
    }
}
