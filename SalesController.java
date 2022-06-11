package Controller;

import Model.Bill;
import Model.DataProvider;
import Model.Product;
import interfaces.SalesService;
import interfaces.StockService;

import java.io.IOException;
import java.util.*;

public class SalesController implements SalesService {

    DataProvider dataProvider = DataProvider.getInstance();

    public Map<String,Integer> getEntireSales() {

        try {
            dataProvider.getUpdatedSalesDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Bill> salesDetails = dataProvider.getEntireSalesHistory();
        return getProductAndSoldCount(salesDetails);
    }

    public List<Bill> getSalesBasedOnDate(String date) {

        try {
            dataProvider.getUpdatedSalesDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Bill> sales = new ArrayList<>();
        List<Bill> salesDetails = dataProvider.getEntireSalesHistory();

        for(Bill bill : salesDetails) {
            if(bill.getBillDate().equals(date)) {
                sales.add(bill);
            }
        }

        return sales;
    }

    public List<String> getSalesBasedOnProductSoldHighest() {

        try {
            dataProvider.getUpdatedSalesDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Integer> productsList = new HashMap<>();
        List<Bill> salesDetails = dataProvider.getEntireSalesHistory();

        for(Bill bill : salesDetails) {
            List<Product> products = bill.getProduct();

            for(Product product : products) {
                if(productsList.containsKey(product.getProductName())) {
                    int count = productsList.get(product.getProductName());
                    productsList.put(product.getProductName(),count + product.getProductCount());
                }
                else{
                    productsList.put(product.getProductName(),product.getProductCount());
                }
            }
        }

        int max = getMaximum(productsList);
        return getMaximumSoldProducts(max,productsList);
    }

    public List<String> getSalesBasedOnProductBrandSoldHighest() {

        try {
            dataProvider.getUpdatedSalesDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Bill> salesDetails = dataProvider.getEntireSalesHistory();
        Map<String,Integer> productsList = getProductAndSoldCount(salesDetails);
        int max = getMaximum(productsList);

        return getMaximumSoldProducts(max,productsList);
    }

    public int getMaximum(Map<String,Integer> productsList) {

        int max = 0;
        Set<Map.Entry<String, Integer>> set = productsList.entrySet();
        for(Map.Entry<String, Integer> me : set) {
            if(me.getValue() >= max) {
                max = me.getValue();
            }
        }

        return max;
    }

    public List<String> getMaximumSoldProducts(int max,Map<String,Integer> productsList) {

        List<String> highestSoldProducts = new ArrayList<>();
        Set<Map.Entry<String, Integer>> set = productsList.entrySet();

        for(Map.Entry<String, Integer> me : set) {
            if(me.getValue() == max) {
                highestSoldProducts.add(me.getKey());
            }
        }

        return highestSoldProducts;
    }

    public Map<String,Integer> getProductAndSoldCount(List<Bill> salesDetails) {

        Map<String,Integer> productsList = new HashMap<>();

        for(Bill bill : salesDetails) {
            List<Product> products = bill.getProduct();

            for(Product product : products) {
                if(productsList.containsKey(product.getProductBrand())) {

                    int count = productsList.get(product.getProductBrand());
                    productsList.put(product.getProductBrand(),count + product.getProductCount());
                }
                else {
                    productsList.put(product.getProductBrand(),product.getProductCount());
                }
            }
        }

        return productsList;
    }
}
