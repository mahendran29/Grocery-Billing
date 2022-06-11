package payments;

import java.util.Random;

public class Gpay extends Upi
{
    @Override
    public boolean scanQR(double amount,int cashGiven) {
        if(!isQRScanDone) {
            return false;
        }

        return !(cashGiven < amount);
    }

    @Override
    public boolean upiCode(double amount,int cashGiven)
    {
        if(!isUPICodeVerified){
            return false;
        }
        return !(cashGiven < amount);
    }

    public String getRewards() {

        String reward;
        Random random = new Random();
        int rewardChoice = random.nextInt(3);

        if(rewardChoice == 0) {
            reward = "Hard Luck! Better luck next time!";
        }
        else if (rewardChoice == 1) {
            reward = "Cash reward of 3$";
        }
        else {
            reward = "Recharge offer upto 20%";
        }

        return reward;
    }
}
