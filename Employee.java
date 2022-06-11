package Model;

import Enums.EmployeeType;

public class Employee {

    private String empId;

    private String name;
    private String email;
    private int age;
    private String password;

    private EmployeeType employeeType;

    public Employee() {

    }

    public Employee(String empId, String name, String email, int age, String password, EmployeeType employeeType) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.employeeType = employeeType;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }
}
