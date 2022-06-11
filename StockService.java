package interfaces;

import Model.Product;

import java.util.List;

public interface StockService
{
    List<Product> getEntireStock();
    List<Product> getStockBasedOnID(String productID);
    List<Product> getStockBasedOnName(String productName);
    List<Product> getStockBasedOnBrand(String productBrand);
}
