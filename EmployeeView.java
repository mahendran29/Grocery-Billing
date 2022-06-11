package View;

import Controller.EmployeeController;
import Enums.EmployeeType;
import Helper.*;
import interfaces.EmployeeService;
import interfaces.EmployeeViewService;

import java.util.Scanner;

public class EmployeeView implements EmployeeViewService {
    Scanner scanner = new Scanner(System.in);
    EmployeeService employeeController = new EmployeeController();
    Helping helper = new Helping();

    public void createEmployee() {
        employeeCreation();
    }

    public void employeeCreation() {
        String empID;
        do {
            empID = getEmployeeID();
            if(employeeController.authenticateEmployee(empID))
            {
                System.out.println("You are trying to assign an Admin Employee ID");
                continue;
            }
            if(employeeController.checkIfEmployeeIdExists(empID))
            {
                System.out.println("Employee ID already exists!");
                continue;
            }
            break;
        }while (true);

        String name = getEmployeeName();
        String email = getEmployeeMail();
        int age = getEmployeeAge();
        String password = getPassword();
        EmployeeType employeeType = getEmployeeType();

        boolean isEmployeeAdded = employeeController.addEmployee(empID,name,email,age,password,employeeType);
        employeeCreationStatus(isEmployeeAdded);
    }

    private String getEmployeeID() {
        System.out.println("Enter the Employee ID: ");
        return scanner.nextLine();
    }

    private String getEmployeeName() {
        System.out.println("Enter the Employee Name: ");
        return scanner.nextLine();
    }

    private String getEmployeeMail() {
        System.out.println("Enter the Employee Mail: ");
        return scanner.nextLine();
    }

    private int getEmployeeAge() {
        while (true)
        {
            System.out.println("Enter the Employee Age: ");
            String age = scanner.nextLine();
            if(helper.validatingNumber(age))
            {
                int employeeAge = Integer.parseInt(age);
                if(employeeAge > 0 && employeeAge < 100){
                    return employeeAge;
                }
            }

            System.out.println("Enter proper age!");
        }
    }

    private String getPassword() {

        while (true)
        {
            System.out.println("Enter password: ");
            String password =  scanner.nextLine();
            if(password.length() > 4){
                return password;
            }

            System.out.println("Password must not be less than 5 characters");
        }

    }

    private EmployeeType getEmployeeType()
    {
        return EmployeeType.BILLER;
    }

    public void employeeCreationStatus(boolean isEmployeeAdded) {

        if(isEmployeeAdded) {
            System.out.println("Employee added successfully!");
        }
        else {
            System.out.println("Employee creation failed!");
        }
    }
}
