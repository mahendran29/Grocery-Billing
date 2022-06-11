package interfaces;

import Enums.EmployeeType;

public interface EmployeeService
{
    boolean authenticateEmployee(String empID);
    boolean addEmployee(String empID, String name, String email, int age, String password, EmployeeType employeeType);
    boolean checkIfEmployeeIdExists(String empID);
}
