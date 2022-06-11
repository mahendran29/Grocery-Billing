package Model;

public class Product
{
    String productId;
    String productName;
    String productDescription;
    String productBrand;
    int productCost;
    int productCount;

    public Product()
    {
        productId = "";
        productName = "";
        productDescription = "";
        productBrand = "";
        productCost = 0;
        productCount = 0;
    }

    public Product(String productId, String productName, String productDescription, String productBrand, int productCost, int productCount) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productBrand = productBrand;
        this.productCost = productCost;
        this.productCount = productCount;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public int getProductCost() {
        return productCost;
    }

    public void setProductCost(int productCost) {
        this.productCost = productCost;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }


}
