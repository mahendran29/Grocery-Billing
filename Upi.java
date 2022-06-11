package payments;

public abstract  class Upi
{
    public boolean isQRScanDone;
    public boolean isUPICodeVerified;

    public abstract boolean scanQR(double amount,int cashGiven);
    public abstract boolean upiCode(double amount,int cashGiven);
}
