package Controller;

import Enums.EmployeeType;
import Model.DataProvider;
import Model.Employee;
import interfaces.EmployeeService;

import java.io.*;

public class EmployeeController implements EmployeeService {

    DataProvider dataProvider = DataProvider.getInstance();


    public boolean authenticateEmployee(String empID) {

        try {
            dataProvider.getUpdatedEmployeeDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Employee employeeDetail = dataProvider.getEmployeeDetail(empID);
        if(employeeDetail == null)
            return false;
        return employeeDetail.getEmployeeType() == EmployeeType.ADMIN;
    }

    public boolean checkIfEmployeeIdExists(String empID)
    {
        try {
            dataProvider.getUpdatedEmployeeDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Employee employeeDetail = dataProvider.getEmployeeDetail(empID);
        return employeeDetail != null;
    }

    public boolean addEmployee(String empID, String name, String email, int age, String password, EmployeeType employeeType) {

        try {
            dataProvider.getUpdatedEmployeeDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Writer fileWriter;
        PrintWriter printWriter = null;
        try {
            fileWriter = new FileWriter("employees.txt",true);
            printWriter = new PrintWriter(fileWriter);

            printWriter.print(empID+",");
            printWriter.print(name+",");
            printWriter.print(email+",");
            printWriter.print(age+",");
            printWriter.print(password+",");
            printWriter.println(employeeType);

            printWriter.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        finally {
            if (printWriter != null) {
                printWriter.close();
            }
            try {
                dataProvider.getUpdatedEmployeeDetailsFromDB();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
