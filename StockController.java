package Controller;

import Model.DataProvider;
import Model.Product;
import interfaces.StockService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StockController implements StockService {

    DataProvider dataProvider = DataProvider.getInstance();

    public List<Product> getEntireStock() {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products = new ArrayList<>();

        Set<Map.Entry<String, Product>> set = dataProvider.getProductDetails().entrySet();
        for(Map.Entry<String, Product> me : set)
        {
            products.add(me.getValue());
        }

        return  products;
    }

    public List<Product> getStockBasedOnID(String productID){

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products = new ArrayList<>();
        Set<Map.Entry<String, Product>> set = dataProvider.getProductDetails().entrySet();

        for(Map.Entry<String, Product> me : set) {

           Product product = me.getValue();
           if(product.getProductId().equals(productID)) {
               products.add(product);
           }
        }

        return products;
    }

    public List<Product> getStockBasedOnName(String productName) {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products = new ArrayList<>();
        Set<Map.Entry<String, Product>> set = dataProvider.getProductDetails().entrySet();

        for(Map.Entry<String, Product> me : set) {

            Product product = me.getValue();
            if(product.getProductName().equals(productName)) {
                products.add(product);
            }
        }

        return products;
    }

    public List<Product> getStockBasedOnBrand(String productBrand) {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Product> products = new ArrayList<>();
        Set<Map.Entry<String, Product>> set = dataProvider.getProductDetails().entrySet();

        for(Map.Entry<String, Product> me : set) {

            Product product = me.getValue();
            if(product.getProductBrand().equals(productBrand)) {
                products.add(product);
            }
        }

        return products;
    }
}
