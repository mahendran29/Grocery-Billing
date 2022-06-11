package Controller;

import Model.DataProvider;
import Model.Employee;
import interfaces.LogginginService;

import java.io.IOException;

public class LoginController implements LogginginService {

    DataProvider dataProvider = DataProvider.getInstance();

    public boolean authenticate(String employeeID, String password) {
        try {
            dataProvider.getUpdatedEmployeeDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authenticateRole(employeeID, password);
    }

    public boolean authenticateRole(String employeeID,String password) {

        try {
            dataProvider.getUpdatedEmployeeDetailsFromDB();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Employee employeeDetail = dataProvider.getEmployeeDetail(employeeID);
        if(employeeDetail !=  null) {
            return password.equals(employeeDetail.getPassword());
        }
        return false;
    }
}
