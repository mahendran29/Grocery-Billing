package Controller;

import Enums.*;
import Helper.Helping;
import Model.Bill;
import Model.Customer;
import Model.DataProvider;
import Model.Product;
import View.BillView;
import interfaces.BillViewService;
import interfaces.BillingService;
import payments.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BillController implements BillingService
 {

    DataProvider dataProvider = DataProvider.getInstance();
    BillViewService billView;
    Helping helper = new Helping();
    Bill bill = new Bill();

    public BillController(BillView view){
        billView=view;
    }

    public void getBill() {

        Helping helper = new Helping();
        bill.setBillDate(helper.getDate());

        String customerPhoneNumber = billView.getCustomerPhoneNumber();
        boolean isCustomer = checkCustomerExists(customerPhoneNumber);
        if(!isCustomer) {
            billView.getCustomerDetails(customerPhoneNumber);
        }
        else{
            getExistingCustomer(customerPhoneNumber);
        }

        List<Product> customerProducts = getProductsFromCustomer();

        int productsAmount = getBilledProductsAmount(customerProducts);

        int cgst = (int)calculateCGST(productsAmount);
        int sgst = (int)calculateSGST(productsAmount);

        int netAmount = calculateBill(bill,productsAmount,cgst,sgst);
        if(netAmount==0)
            return;

        billView.amountToPay(netAmount);

        BillChoice choice = getBillPaymentConfirmation();

        if(choice == BillChoice.CONFIRM)
        {
            List<Product> lessStock = confirmBillPayment(customerProducts);
            boolean isPaymentDone  = paymentProcess(netAmount);
            if(isPaymentDone) {
                int billNumber = getBillNumberFromDB();
                bill.setBillNumber(billNumber);
                addToSalesHistory();
            }
            billView.displayEntireBill(bill,productsAmount,cgst,sgst,lessStock);
        }
        else if(choice == BillChoice.CANCEL) {
            billView.showCancelledStatus();
        }

    }

     public void setCustomerDetails(String customerName,String customerPhoneNumber,String customerLocation){
         addToCustomerDetails(customerName,customerPhoneNumber,customerLocation);
     }

     // Writing the customer details to the customer DB
     public void addToCustomerDetails(String customerName, String customerPhoneNumber, String customerLocation) {

         Customer customer = new Customer(customerName,customerPhoneNumber,customerLocation);
         bill.setCustomer(customer);

         try {
             dataProvider.getUpdatedCustomerDetailsFromDB();
         } catch (IOException e) {
             e.printStackTrace();
         }

         Writer fileWriter;
         PrintWriter printWriter = null;
         try {
             fileWriter = new FileWriter("customers.txt", true);
             printWriter = new PrintWriter(fileWriter);

             printWriter.print(customerName+",");
             printWriter.print(customerPhoneNumber+",");
             printWriter.println(customerLocation);
             printWriter.flush();

             try {
                 dataProvider.getUpdatedCustomerDetailsFromDB();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
         catch (Exception e) {
             e.printStackTrace();
         }
         finally {
             printWriter.close();
         }
     }

     public BillChoice getBillPaymentConfirmation()
     {

         while(true) {
             String choice = billView.getBillPaymentChoice();

             boolean isProperInput = !helper.validatingNumber(choice) || (Integer.parseInt(choice) > BillChoice.values().length);
             if (isProperInput || choice.equals("0")) {
                 billView.showIncorrectInputStatus();
                 continue;
             }

             return BillChoice.values()[Integer.parseInt(choice) - 1];
         }
     }

     public boolean paymentProcess(int netAmount)
     {
         if(netAmount==0)
             return false;

         while(true)
         {
             if(payment(bill,netAmount))
             {
                 return true;
             }
         }
     }

     public boolean payment(Bill bill, double amount)
     {
         boolean isPaymentSuccess;
         Payment operationChosen;
         String modeOfPayment;
         while(true)
         {
             modeOfPayment = billView.getModeOfPayment();

             boolean isProperInput = !helper.validatingNumber(modeOfPayment) || (Integer.parseInt(modeOfPayment) > Payment.values().length);

             if (isProperInput || modeOfPayment.equals("0")) {
                 billView.showIncorrectInputStatus();
                 continue;
             }

             operationChosen =  Payment.values()[Integer.parseInt(modeOfPayment) - 1];
             break;
         }

         setPaymentMode(operationChosen);
         isPaymentSuccess = switch (operationChosen) {
             case CASH -> payThroughCash(amount);
             case UPI -> payThroughUPI(amount);
             case CARD -> payThroughCard(amount);
         };

         return isPaymentSuccess;
     }

     public boolean payThroughCash(double amount)
     {

         int cashGiven = billView.getCustomerCash();
         boolean isPaymentDone = cashPayment(amount,cashGiven);
         billView.paymentStatus(isPaymentDone);

         return isPaymentDone;
     }

     public boolean payThroughUPI(double amount)
     {
         String userInput;
         UPIMode operationChosen;
         String upiType;
         while(true) {

             userInput = billView.getUPIMode();
             upiType = billView.getUPIModeType();

             boolean isProperInput = !helper.validatingNumber(userInput) || (Integer.parseInt(userInput) > UPIMode.values().length);
             if (isProperInput || userInput.equals("0")) {
                 billView.showIncorrectInputStatus();
                 continue;
             }
             operationChosen = UPIMode.values()[Integer.parseInt(userInput) - 1];
             break;
         }

         int cashGiven = billView.getCustomerCash();
         boolean isPaymentDone = upiPayment(amount,cashGiven,operationChosen,upiType);
         billView.paymentStatus(isPaymentDone);

         String reward;
         if(operationChosen == UPIMode.GPAY) {
             reward = checkRewards();
             billView.rewardStatus(reward);
         }

         return isPaymentDone;
     }

     public boolean payThroughCard(double amount)
     {

         String userInput;
         CardType operationChosen;
         while(true) {

             userInput = billView.getCardType();
             boolean isProperInput = !helper.validatingNumber(userInput) || (Integer.parseInt(userInput) > CardType.values().length);
             if (isProperInput || userInput.equals("0")) {
                 System.out.println("Give proper input!");
                 continue;
             }
             operationChosen = CardType.values()[Integer.parseInt(userInput) - 1];
             break;
         }

         int cashGiven = billView.getCustomerCash();
         boolean isPaymentDone = cardPayment(amount,cashGiven,operationChosen);
         billView.paymentStatus(isPaymentDone);

         return isPaymentDone;
     }


    public List<Product> getProductsFromCustomer()
    {
        List<Product> products = new ArrayList<>();
        while(true)
        {
            ProductChoice choice = billView.getProductChoice();
            Product product = null;

            if(choice == ProductChoice.PRODUCT_ID) {
                String productId = billView.getProductID();
                int productCount = billView.getQuantity();
                product = getProductByID(productId,productCount);
            }
            else if (choice == ProductChoice.PRODUCT_NAME) {
                String productBrand = billView.getBrandName();
                int productCount = billView.getQuantity();
                product = getProductByBrand(productBrand,productCount);
            }

            if(product!=null) {

                products.add(product);
                bill.setProduct(product);
            }
            else {
                billView.displayStockStatus();
            }

            if(!billView.isBillingContinue()){
                return products;
            }

        }
    }

    // Check if they are already our customer
    public boolean checkCustomerExists(String phoneNumber) {
        try {
            dataProvider.getUpdatedCustomerDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dataProvider.checkCustomer(phoneNumber);
    }

    // If our customer,get the customer's details
    public void getExistingCustomer(String customerPhoneNumber) {

        Customer customer =  dataProvider.getCustomer(customerPhoneNumber);
        bill.setCustomer(customer);
    }

    // Get the Product details by using the product ID
    public Product getProductByID(String productID,int productCount) {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Product> productDetails = dataProvider.getProductDetails();
        Product orderedProduct =  productDetails.getOrDefault(productID,null);

        if(orderedProduct==null || productCount > orderedProduct.getProductCount())
            return  null;

        Product product = new Product();
        product.setProductId(orderedProduct.getProductId());
        product.setProductName(orderedProduct.getProductName());
        product.setProductBrand(orderedProduct.getProductBrand());
        product.setProductCost(orderedProduct.getProductCost());
        product.setProductDescription(orderedProduct.getProductDescription());
        product.setProductCount(productCount);

        return product;
    }

    // Get the Product details by using the brand name
    public Product getProductByBrand(String brandName,int productCount) {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Product> productDetails = dataProvider.getProductDetails();
        Set<Map.Entry<String, Product>> set = productDetails.entrySet();

        for(Map.Entry<String, Product> me : set) {

            if(me.getValue().getProductBrand().equals(brandName)) {

                if(productCount > me.getValue().getProductCount())
                    return  null;

                Product product = new Product();
                product.setProductId(me.getValue().getProductId());
                product.setProductName(me.getValue().getProductName());
                product.setProductBrand(me.getValue().getProductBrand());
                product.setProductCost(me.getValue().getProductCost());
                product.setProductDescription(me.getValue().getProductDescription());
                product.setProductCount(productCount);
                return product;
            }
        }

        return null;
    }

    // Calculating the bought product's total amount
    public int getBilledProductsAmount(List<Product> customerProducts) {

        int amount = 0;
        for(Product product : customerProducts) {
            amount += product.getProductCost() * product.getProductCount();
        }
        return  amount;
    }

    //@Override
    public double calculateCGST(double amount) {
        return amount*0.09;
    }

    //@Override
    public double calculateSGST(double amount) {
        return amount*0.09;
    }

    // Calculating the net bill
    public int calculateBill(Bill bill, int productsAmount, int cgst, int sgst) {

        int totalAmount = productsAmount+cgst+sgst;
        bill.setTotal(totalAmount);
        return totalAmount;
    }

    // Updating the stock after the payment of bill.
    public List<Product> confirmBillPayment(List<Product> customerProducts) {

        try {
            dataProvider.getUpdatedProductDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Product> productDetails = dataProvider.getProductDetails();
        List<Product> lessStock = new ArrayList<>();

        for (Product customerProduct : customerProducts) {

            Product product = productDetails.get(customerProduct.getProductId());
            product.setProductCount( product.getProductCount() - customerProduct.getProductCount());
            if(product.getProductCount() < 10) {
                lessStock.add(customerProduct);
            }
        }

        Writer fileWriter;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter("stocks.txt");
            printWriter = new PrintWriter(fileWriter);

            Set<Map.Entry<String,Product>> set = productDetails.entrySet();
            for(Map.Entry<String,Product> product : set) {
                printWriter.print(product.getKey()+",");
                printWriter.print(product.getValue().getProductName()+",");
                printWriter.print(product.getValue().getProductDescription()+",");
                printWriter.print(product.getValue().getProductBrand()+",");
                printWriter.print(product.getValue().getProductCost()+",");
                printWriter.println(product.getValue().getProductCount());
            }

            try {
                dataProvider.getUpdatedProductDetailsFromDB();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {
            printWriter.close();
        }

        return lessStock;
    }

    public void setPaymentMode(Payment paymentMode)
    {
        bill.setModeOfPayment(paymentMode);
    }

    public boolean cashPayment(double amount,double cashGiven) {

        Cash cash = new Cash();
        return cash.payment(amount,cashGiven);
    }

    public boolean upiPayment(double amount, int cashGiven, UPIMode upiMode, String upiType) {

        Upi upiPay = null;
        boolean isPaymentSuccessful = false;

        if (upiMode == UPIMode.GPAY) {
            upiPay = new Gpay();
        }
        else if(upiMode == UPIMode.PHONEPE) {
            upiPay = new PhonePe();
        }

        if(upiPay == null) {
            return false;
        }

        if(upiType.equals("1")) {
            upiPay.isQRScanDone = true;
            isPaymentSuccessful = upiPay.scanQR(amount,cashGiven);
        }
        else if (upiType.equals("2")) {
            upiPay.isUPICodeVerified = true;
            isPaymentSuccessful = upiPay.upiCode(amount,cashGiven);
        }

        return isPaymentSuccessful;
    }

    public String checkRewards() {
        Gpay gpay = new Gpay();
        return gpay.getRewards();
    }

    public boolean cardPayment(double amount, int cashGiven, CardType cardType) {
        Card card = null;
        boolean isPaymentSuccessful;

        if(cardType == CardType.DEBITCARD) {
            card = new CreditCard();
        }
        else if(cardType == CardType.CREDITCARD) {
            card = new DebitCard();
        }
        if(card==null){
            return false;
        }

        isPaymentSuccessful = card.swipe(amount,cashGiven);
        return isPaymentSuccessful;
    }

    // Writing the bill details to the sales history DB
    public void addToSalesHistory()
    {
        try {
            dataProvider.getUpdatedSalesDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dataProvider.addToSalesDetails(bill);

        Writer fileWriter;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter("sales.txt", true);
            printWriter = new PrintWriter(fileWriter);

            printWriter.print(bill.getBillNumber()+",");
            printWriter.print(bill.getBillDate()+",");
            printWriter.print(bill.getCustomer().getCustomerName()+",");
            printWriter.print(bill.getCustomer().getCustomerPhoneNumber()+",/");
            for(Product product : bill.getProduct())
            {
                printWriter.print(product.getProductId()+";");
                printWriter.print(product.getProductName()+";");
                printWriter.print(product.getProductDescription()+";");
                printWriter.print(product.getProductBrand()+";");
                printWriter.print(product.getProductCost()+";");
                printWriter.print(product.getProductCount()+";/");
            }
            printWriter.print(","+bill.getModeOfPayment()+",");
            printWriter.println(bill.getTotal());
            printWriter.flush();

            try {
                dataProvider.getUpdatedSalesDetailsFromDB();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch (Exception e) {
            System.out.println(e);
        }
        finally {
            printWriter.close();
        }
    }

    // Getting the bill number from DB
    public int getBillNumberFromDB()
    {
        FileReader fileReader;
        BufferedReader bufferedReader = null;

        Writer fileWriter;
        PrintWriter printWriter;

        int billNumber = 0;

        try {
            File file = new File("GroceryApp.txt");

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String lines = "";
            String line = bufferedReader.readLine();

            while (line != null) {
                String[] each = line.split("=");

                if(each[0].equals("billNumber")) {
                    billNumber = Integer.parseInt(each[1]);
                    ++billNumber;
                    lines += ("billNumber="+billNumber);

                }
                else {
                    lines += each[0]+"="+each[1];
                }

                lines += "\n";
                line = bufferedReader.readLine();
            }

            fileWriter = new FileWriter(file);
            printWriter = new PrintWriter(fileWriter);

            printWriter.append(lines);
            printWriter.flush();

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

        return billNumber;
    }

    public int getParticularProductTotalCost(int count,int cost){
        return  count * cost;
    }
}




