package interfaces;

import Model.Bill;

import java.util.List;
import java.util.Map;

public interface SalesService
{
    Map<String,Integer>  getEntireSales();
    List<Bill> getSalesBasedOnDate(String date);
    List<String> getSalesBasedOnProductSoldHighest();
    List<String> getSalesBasedOnProductBrandSoldHighest();
    int getMaximum(Map<String,Integer> productsList);
    List<String> getMaximumSoldProducts(int max,Map<String,Integer> productsList);

    Map<String,Integer> getProductAndSoldCount(List<Bill> salesDetails);
}
