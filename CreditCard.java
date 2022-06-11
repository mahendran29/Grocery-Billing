package payments;

public class CreditCard extends Card
{
    @Override
    public boolean swipe(double amount, int cashGiven) {
        return !(cashGiven < amount);
    }
}
