package Model;

import java.util.ArrayList;
import java.util.List;

public class Purchase
{
    private String purchaseID;
    private String dateOfPurchaseOrder;
    private String dateOfDelivery;
    private String supplierName;
    private List<Product> productList;
    private String purchaseStatus;

    public Purchase()
    {
        purchaseID = "";
        dateOfPurchaseOrder = "";
        dateOfDelivery = "";
        supplierName = "";
        productList = new ArrayList<>();
    }

    public Purchase(String purchaseID, String dateOfPurchaseOrder, String dateOfDelivery, String supplierName, Product product, String purchaseStatus) {
        this.purchaseID = purchaseID;
        this.dateOfPurchaseOrder = dateOfPurchaseOrder;
        this.dateOfDelivery = dateOfDelivery;
        this.supplierName = supplierName;
        this.purchaseStatus = purchaseStatus;
        productList = new ArrayList<>();
        productList.add(product);
    }

    public String getPurchaseID() {
        return purchaseID;
    }

    public void setPurchaseID(String purchaseID) {
        this.purchaseID = purchaseID;
    }

    public String getDateOfPurchase() {
        return dateOfPurchaseOrder;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchaseOrder = dateOfPurchase;
    }

    public String getDateOfDelivery() {
        return dateOfDelivery;
    }

    public void setDateOfDelivery(String dateOfDelivery) {
        this.dateOfDelivery = dateOfDelivery;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProduct(Product product) {
        productList.add(product);
    }

    public String getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(String purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }
}
