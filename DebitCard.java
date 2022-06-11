package payments;

public class DebitCard extends Card{
    @Override
    public boolean swipe(double amount, int cashGiven) {
        return !(cashGiven < amount);
    }
}
