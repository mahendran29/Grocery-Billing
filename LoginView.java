package View;

import Controller.LoginController;
import Enums.Login;
import Helper.Helping;
import interfaces.GroceryStoreViewService;
import interfaces.LogginginService;
import interfaces.LoginViewService;

import java.util.Scanner;

public class LoginView implements LoginViewService
{
    Scanner scanner = new Scanner(System.in);
    LogginginService loginController = new LoginController();
    Helping helper = new Helping();

    public void checkLogin() {

        while (true) {
            Login login = getLoginDetails();
            if(login == Login.EXIT) {
                return;
            }
            String employeeID = getEmployeeID();
            String password = getPassword();

            boolean isLoginSuccess = loginController.authenticate(employeeID,password);
            if(!isLoginSuccess) {
                System.out.println("Login failed!");
            }
            else {
                System.out.println("\nLogin success!\n");
                GroceryStoreViewService groceryStoreView = new GroceryStoreView();
                groceryStoreView.storeOperations(employeeID);
            }
        }
    }

    public String getEmployeeID() {
        System.out.println("Enter your employee ID: ");
        return scanner.nextLine();
    }

    public String getPassword() {
        System.out.println("Enter your password: ");
        return scanner.nextLine();
    }

    public Login getLoginDetails() {

        String login;
        while(true) {
            System.out.println("1.LOGIN 2.EXIT");
            login = scanner.nextLine();

            boolean isProperInput = !helper.validatingNumber(login) || (Integer.parseInt(login) > Login.values().length);
            if (isProperInput || login.equals("0")) {
                System.out.println("Give proper input!");
                continue;
            }

            return Login.values()[Integer.parseInt(login) - 1];
        }
    }

}
