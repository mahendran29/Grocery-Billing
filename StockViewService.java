package interfaces;

import Model.Product;
import java.util.List;

public interface StockViewService {
    void getStockDetails();
    void displayProductsList(List<Product> productsList);
}
