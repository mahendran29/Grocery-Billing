package payments;

public  class PhonePe extends Upi
{

    @Override
    public boolean scanQR(double amount,int cashGiven)
    {
        if(!isQRScanDone) {
            return false;
        }

        return !(cashGiven < amount);
    }

    @Override
    public boolean upiCode(double amount,int cashGiven)
    {
        if(!isUPICodeVerified) {
            return false;
        }

        return !(cashGiven < amount);
    }
}
