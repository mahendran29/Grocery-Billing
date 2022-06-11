import View.LoginView;
import interfaces.LoginViewService;

public class GroceryStoreApp
{
    public static void main(String[] args)
    {
        LoginViewService loginView = new LoginView();
        loginView.checkLogin();
    }
}
