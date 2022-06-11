package Model;

import Enums.EmployeeType;
import Enums.Payment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider
{
    private Map<String, Employee> employeeDetails;
    private List<Purchase> purchaseDetails;
    private Map<String,Product> productDetails;
    private Map<String,Customer> customerDetails;
    private List<Bill> salesHistory;

    static DataProvider dataProvider = null;

    private DataProvider()
    {
        try {
            getUpdatedEmployeeDetailsFromDB();
            getUpdatedCustomerDetailsFromDB();
            getUpdatedPurchaseDetailsFromDB();
            getUpdatedSalesDetailsFromDB();
            getUpdatedProductDetailsFromDB();
        }
        catch (Exception e) {
           e.printStackTrace();
        }
    }


    public static DataProvider getInstance() {
        if(dataProvider == null)
            dataProvider = new DataProvider();

        return dataProvider;
    }

    public void getUpdatedEmployeeDetailsFromDB() throws IOException {
        employeeDetails = new HashMap<>();
        getEmployeeDetailsFromDB(employeeDetails);
    }

    public void getUpdatedCustomerDetailsFromDB() throws IOException {
        customerDetails = new HashMap<>();
        getCustomerDetailsFromDB(customerDetails);
    }

    public void getUpdatedPurchaseDetailsFromDB() throws IOException {
        purchaseDetails = new ArrayList<>();
        getPurchaseDetailsFromDB(purchaseDetails);
    }

    public void getUpdatedProductDetailsFromDB() throws IOException {
        productDetails = new HashMap<>();
        getProductDetailsFromDB(productDetails);
    }

    public void getUpdatedSalesDetailsFromDB() throws IOException {
        salesHistory = new ArrayList<>();
        getSalesDetailsFromDB(salesHistory);
    }

    private void getProductDetailsFromDB(Map<String, Product> productDetails)throws IOException {

        FileReader fileReader;
        BufferedReader bufferedReader = null;

        try {

            File file = new File("stocks.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String lines = bufferedReader.readLine();
            if(lines == null){
                return;
            }

            String[] line = lines.split(",");
            while (true)
            {
                String productID = line[0];
                String productName = line[1];
                String productDescription = line[2];
                String productBrand = line[3];
                int productCost = Integer.parseInt(line[4]);
                int productCount = Integer.parseInt(line[5]);


                Product product = new Product(productID,productName,productDescription,productBrand,productCost,productCount);
                productDetails.put(productID,product);
                lines = bufferedReader.readLine();
                if(lines!=null) {
                    line = lines.split(",");
                }else {
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            bufferedReader.close();
        }
    }

    private void getSalesDetailsFromDB(List<Bill> salesHistory) throws IOException{

        FileReader fileReader;
        BufferedReader bufferedReader = null;

        try {

            File file = new File("sales.txt");
            if(!file.exists())
            {
                file.createNewFile();
            }

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String lines = bufferedReader.readLine();
            if(lines == null){
                return;
            }

            String[] line = lines.split(",");

            while (true) {
                int billNumber = Integer.parseInt(line[0]);
                String billDate = line[1];
                String customerName = line[2];
                String customerPhoneNumber = line[3];

                ArrayList<Product> productList = new ArrayList<>();
                String[] products = lines.split("/");
                for(int i=1;i<products.length-1;i++)
                {

                    String[] productDetails = products[i].split(";");
                    String productID = productDetails[0];
                    String productName = productDetails[1];
                    String productDescription = productDetails[2];
                    String productBrand = productDetails[3];
                    int productCost = Integer.parseInt(productDetails[4]);
                    int productCount = Integer.parseInt(productDetails[5]);

                    Product product = new Product(productID, productName, productDescription, productBrand, productCost, productCount);
                    productList.add(product);

                }
                Payment modeOfPayment = Payment.valueOf(line[5]);
                int totalBill = Integer.parseInt(line[6]);

                Customer customer = new Customer();
                customer.setCustomerName(customerName);
                customer.setCustomerPhoneNumber(customerPhoneNumber);

                Bill bill = new Bill();
                bill.setBillNumber(billNumber);
                bill.setBillDate(billDate);
                bill.setCustomer(customer);
                bill.setModeOfPayment(modeOfPayment);
                bill.setTotal(totalBill);


                for(Product prod : productList) {
                    bill.setProduct(prod);
                }

                salesHistory.add(bill);
                lines = bufferedReader.readLine();
                if(lines!=null) {
                    line = lines.split(",");
                }else {
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            bufferedReader.close();
        }
    }

    private void getCustomerDetailsFromDB(Map<String, Customer> customerDetails) throws IOException {

        FileReader fileReader;
        BufferedReader bufferedReader = null;


        try {

            File file = new File("customers.txt");
            if(!file.exists())
            {
                file.createNewFile();
            }
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String lines = bufferedReader.readLine();
            if(lines == null){
                return;
            }

            String[] line = lines.split(",");

            while (true) {
                String customerName = line[0];
                String customerPhoneNumber = line[1];
                String customerLocation = line[2];

                Customer customer = new Customer(customerName,customerPhoneNumber,customerLocation);
                customerDetails.put(customerPhoneNumber,customer);
                lines = bufferedReader.readLine();
                if(lines!=null) {
                    line = lines.split(",");
                }else {
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            bufferedReader.close();
        }
    }


    private void getPurchaseDetailsFromDB(List<Purchase> purchaseDetails) throws IOException {

        FileReader fileReader;
        BufferedReader bufferedReader = null;

        try
        {
            File file = new File("purchase.txt");
            if(!file.exists())
            {
               file.createNewFile();
            }

            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

            String lines = bufferedReader.readLine();
            if(lines == null){
                return;
            }

            String[] line = lines.split(",");
            while (true)
            {
                String purchaseID = line[0];
                String dateOfPurchase = line[1];
                String supplierName = line[2];
                String productID = line[3];
                String productName = line[4];
                String productDescription = line[5];
                String productBrand = line[6];
                int productCost = Integer.parseInt(line[7]);
                int productCount = Integer.parseInt(line[8]);
                String dateOfDelivery = line[9];
                String purchaseStatus = line[10];

                Product product = new Product(productID,productName,productDescription,productBrand,productCost,productCount);
                Purchase purchase = new Purchase(purchaseID,dateOfPurchase,dateOfDelivery,supplierName,product,purchaseStatus);
                purchaseDetails.add(purchase);
                lines = bufferedReader.readLine();
                if(lines!=null) {
                    line = lines.split(",");
                }else {
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            bufferedReader.close();
        }
    }


    public void getEmployeeDetailsFromDB(Map<String, Employee> employeeDetails) {
        FileReader fileReader;
        BufferedReader bufferedReader = null;

        try {

            File file = new File("employees.txt");
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String lines = bufferedReader.readLine();
            if(lines == null){
                return;
            }

            String[] line = lines.split(",");
            while (true)
            {
                String employeeId = line[0];
                String name = line[1];
                String email = line[2];
                int age = Integer.parseInt(line[3]);
                String password = line[4];
                EmployeeType employeeType = EmployeeType.valueOf(line[5]);

                Employee employee = new Employee(employeeId, name,email,age,password,employeeType);
                employeeDetails.put(employeeId,employee);

                lines = bufferedReader.readLine();
                if(lines!=null) {
                    line = lines.split(",");
                }else {
                    break;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    public Employee getEmployeeDetail(String empId) {
        return employeeDetails.getOrDefault(empId, null);
    }

    public List<Purchase> getPurchaseDetails() {
        return purchaseDetails;
    }

    public Map<String,Product> getProductDetails() {
        return productDetails;
    }

    public boolean checkCustomer(String phoneNumber) {return customerDetails.containsKey(phoneNumber);}

    public Customer getCustomer(String phoneNumber) {return customerDetails.get(phoneNumber);}

    public Map<String,Customer>  getCustomers(){ return customerDetails;}

    public List<Bill> getEntireSalesHistory()
    {
        return salesHistory;
    }


    public void addToEmployees(String empID,Employee employee) {
        employeeDetails.put(empID,employee);
    }

    public void addToSalesDetails(Bill bill) {
        salesHistory.add(bill);
    }
}
