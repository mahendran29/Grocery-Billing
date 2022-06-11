package interfaces;

import Model.Purchase;

import java.io.IOException;
import java.util.List;

public interface PurchaseService
{
    boolean setStockInventory(String productID,String productName,String productDescription,String productBrand,int productCount,int productCost);
    boolean setPurchaseInventory(String purchaseID,String supplierName,String date,String productID,String productName,String productBrand,String productDescription,int productCount,int productCost);
    boolean setPurchaseDetails(String purchaseId,String supplierName,String productID,String productName,String productDescription,String productBrand,int productCount,int productCost);
    boolean changeParticularPurchaseOrder(String purchaseID) throws IOException;
    List<Purchase> getEntirePurchase();
    List<Purchase> getPurchaseBasedOnPurchaseID(String purchaseID);
    List<Purchase> getPurchaseBasedOnProductID(String productID);
    List<Purchase> getPurchaseBasedOnProductName(String productName);
    List<Purchase> getPurchaseBasedOnProductBrand(String productBrand);
    List<Purchase> getPurchaseBasedOnDate(String date);
    List<Purchase> getPurchaseBasedOnSupplier(String supplier);
    String getPurchaseIDFromDB();

}
