package payments;

public class Cash {

    public boolean payment(double amount,double cashGiven) {

        if(cashGiven<amount) {
            System.out.println("Insufficient amount paid!");
            return false;
        }

        double remainingAmount = cashGiven - amount;
        System.out.println("Remaining balance to be given to the customer: "+remainingAmount);

        return true;
    }
}
