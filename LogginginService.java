package interfaces;

public interface LogginginService
{
    boolean authenticate(String employeeID, String password);
    boolean authenticateRole(String employeeID,String password);

}
