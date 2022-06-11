package Controller;

import Helper.Helping;
import Model.DataProvider;
import Model.Product;
import Model.Purchase;
import interfaces.PurchaseService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PurchaseController implements PurchaseService {

    DataProvider dataProvider = DataProvider.getInstance();
    Helping helping = new Helping();

    public boolean setStockInventory(String productID,String productName,String productDescription,String productBrand,int productCount,int productCost) {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Product> products = dataProvider.getProductDetails();
        if(products.get(productID) == null){
            Product product = new Product(productID,productName,productDescription,productBrand,productCost,productCount);
            products.put(productID,product);
            return true;
        }

        Product product = products.get(productID);
        product.setProductName(productName);
        product.setProductDescription(productDescription);
        product.setProductBrand(productBrand);
        product.setProductCost(productCost);
        product.setProductCount(product.getProductCount() + productCount);
        products.put(productID,product);

        return true;
    }

    public boolean setPurchaseInventory(String purchaseID,String supplierName,String date,String productID,String productName,String productBrand,String productDescription,int productCount,int productCost) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseDetails = dataProvider.getPurchaseDetails();
        Product product = new Product(productID,productName,productDescription,productBrand,productCost,productCount);
        Purchase purchase = new Purchase(purchaseID,date,"Yet to deliver",supplierName,product,"ORDERED");
        purchaseDetails.add(purchase);

        Writer fileWriter;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter("purchase.txt",true);
            printWriter = new PrintWriter(fileWriter);

            printWriter.print(purchaseID+",");
            printWriter.print(date+",");
            printWriter.print(supplierName+",");
            printWriter.print(productID+",");
            printWriter.print(productName+",");
            printWriter.print(productDescription+",");
            printWriter.print(productBrand+",");
            printWriter.print(productCost+",");
            printWriter.print(productCount+",");
            printWriter.print("Yet to deliver,");
            printWriter.println("ORDERED");
            printWriter.flush();

            try {
                dataProvider.getUpdatedPurchaseDetailsFromDB();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        finally {
            printWriter.close();
        }

        return true;

    }

    public boolean setPurchaseDetails(String purchaseId,String supplierName,String productID,String productName,String productDescription,String productBrand,int productCount,int productCost) {
        return setPurchaseInventory(purchaseId,supplierName,helping.getDate(),productID,productName,productBrand,productDescription,productCount,productCost);
    }

    public boolean changeParticularPurchaseOrder(String purchaseID) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        boolean isPurchaseStatusChanged = false;
        for(Purchase purchase : purchaseList)
        {
            if(purchase.getPurchaseID().equals(purchaseID) && purchase.getPurchaseStatus().equals("ORDERED"))
            {
                isPurchaseStatusChanged = true;
                purchase.setPurchaseStatus("DELIVERED");
                purchase.setDateOfDelivery(helping.getDate());
                for(Product product : purchase.getProductList())
                {
                    setStockInventory(product.getProductId(),product.getProductName(),product.getProductDescription(),product.getProductBrand(),product.getProductCount(),product.getProductCost());
                }
            }

        }

        if(!isPurchaseStatusChanged)
            return false;

        Writer fileWriter;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter("purchase.txt");
            printWriter = new PrintWriter(fileWriter);

            for(Purchase purchase : purchaseList) {

                printWriter.print(purchase.getPurchaseID()+",");
                printWriter.print(purchase.getDateOfPurchase()+",");
                printWriter.print(purchase.getSupplierName()+",");

                for(Product product : purchase.getProductList()) {

                    printWriter.print(product.getProductId()+",");
                    printWriter.print(product.getProductName()+",");
                    printWriter.print(product.getProductDescription()+",");
                    printWriter.print(product.getProductBrand()+",");
                    printWriter.print(product.getProductCost()+",");
                    printWriter.print(product.getProductCount()+",");
                }
                printWriter.print(purchase.getDateOfDelivery()+",");
                printWriter.println(purchase.getPurchaseStatus());
                printWriter.flush();
            }

            Map<String, Product> productDetails = dataProvider.getProductDetails();

            fileWriter = new FileWriter("stocks.txt");
            printWriter = new PrintWriter(fileWriter);

            Set<Map.Entry<String,Product>> set = productDetails.entrySet();
            for(Map.Entry<String,Product> product : set) {
                printWriter.print(product.getKey()+",");
                printWriter.print(product.getValue().getProductName()+",");
                printWriter.print(product.getValue().getProductDescription()+",");
                printWriter.print(product.getValue().getProductBrand()+",");
                printWriter.print(product.getValue().getProductCost()+",");
                printWriter.println(product.getValue().getProductCount()+",");
            }

            try {
                dataProvider.getUpdatedPurchaseDetailsFromDB();
                dataProvider.getUpdatedProductDetailsFromDB();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            printWriter.close();
        }
        return true;
    }

    public List<Purchase> getEntirePurchase()
    {
        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataProvider.getPurchaseDetails();
    }

    public List<Purchase> getPurchaseBasedOnPurchaseID(String purchaseID) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        List<Purchase> purchaseListBasedOnSupplier = new ArrayList<>();

        for(Purchase purchase : purchaseList) {
            if(purchase.getPurchaseID().equals(purchaseID)) {
                purchaseListBasedOnSupplier.add(purchase);
            }
        }

        return purchaseListBasedOnSupplier;
    }

    public List<Purchase> getPurchaseBasedOnProductID(String productID) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        List<Purchase> purchaseListBasedOnID = new ArrayList<>();

        for(Purchase purchase : purchaseList) {
            for(Product product : purchase.getProductList()) {
                if(product.getProductId().equals(productID)) {
                    purchaseListBasedOnID.add(purchase);
                }
            }
        }

        return purchaseListBasedOnID;
    }

    public List<Purchase> getPurchaseBasedOnProductName(String productName) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        List<Purchase> purchaseListBasedOnName = new ArrayList<>();

        for(Purchase purchase : purchaseList) {
            for(Product product : purchase.getProductList()) {
                if(product.getProductName().equals(productName)) {
                    purchaseListBasedOnName.add(purchase);
                }
            }
        }

        return purchaseListBasedOnName;
    }

    public List<Purchase> getPurchaseBasedOnProductBrand(String productBrand) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        List<Purchase> purchaseListBasedOnBrand = new ArrayList<>();

        for(Purchase purchase : purchaseList) {
            for(Product product : purchase.getProductList()) {
                if(product.getProductBrand().equals(productBrand)) {
                    purchaseListBasedOnBrand.add(purchase);
                }
            }
        }

        return purchaseListBasedOnBrand;
    }

    public List<Purchase> getPurchaseBasedOnDate(String date) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        List<Purchase> purchaseListBasedOnDate = new ArrayList<>();

        for(Purchase purchase : purchaseList) {
            if(purchase.getDateOfPurchase().equals(date)) {
                purchaseListBasedOnDate.add(purchase);
            }
        }

        return purchaseListBasedOnDate;
    }

    public List<Purchase> getPurchaseBasedOnSupplier(String supplier) {

        try {
            dataProvider.getUpdatedPurchaseDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Purchase> purchaseList = dataProvider.getPurchaseDetails();
        List<Purchase> purchaseListBasedOnSupplier = new ArrayList<>();

        for(Purchase purchase : purchaseList) {
            if(purchase.getSupplierName().equals(supplier)) {
                purchaseListBasedOnSupplier.add(purchase);
            }
        }

        return purchaseListBasedOnSupplier;
    }


    public String getPurchaseIDFromDB()
    {
        FileReader fileReader;
        BufferedReader bufferedReader = null;

        Writer fileWriter;
        PrintWriter printWriter = null;

        int purchaseId = 0;

        try {
            File file = new File("GroceryApp.txt");
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            File file2 = new File("dummyTemp.txt");
            fileWriter = new FileWriter(file2);
            printWriter = new PrintWriter(fileWriter);

            String lines = "";
            String[] line = bufferedReader.readLine().split("=");
            while (true)
            {
                if(line[0].equals("purchaseID"))
                {
                    purchaseId = Integer.parseInt(line[1]);
                    ++purchaseId;
                    printWriter.println(line[0]+"="+purchaseId);
                }
                else {
                    printWriter.println(line[0]+"="+line[1]);
                }

                printWriter.flush();

                lines = bufferedReader.readLine();
                if(lines!=null) {
                    line = lines.split("=");
                }else {
                    break;
                }
            }

            file.delete();
            file2.renameTo(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return String.valueOf(purchaseId);
    }

}
