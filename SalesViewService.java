package interfaces;

import Model.Bill;

import java.util.List;
import java.util.Map;

public interface SalesViewService {
    void getSalesHistory();
    String getPurchaseDate();
    void displaySalesReport(List<Bill> salesReport);
    void displayHighestSold(String name,List<String> highestSold);
    void displayEntireSalesReport(Map<String,Integer> productsList);
}
