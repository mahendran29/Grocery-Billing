package interfaces;

import Model.Purchase;

import java.util.List;

public interface PurchaseViewService {
    void purchaseProducts();
    void displayPurchaseOrderDetails(String purchaseID,String supplierName,String productId,String productName,String productDescription,String productBrand,int productCount,int productCost);
    void getPurchaseDetails();
    void displayPurchaseDetails(List<Purchase> purchaseList);
    void changePurchaseOrderStatus(boolean isChanged);
    void displayPurchaseStatus(boolean isPurchaseDone);
}
