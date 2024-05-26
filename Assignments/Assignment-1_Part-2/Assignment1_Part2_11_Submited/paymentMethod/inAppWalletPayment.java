import java.util.UUID
import co


class InAppWalletPayment extends PaymentMethod {
    private UUID walletId;
    private double walletBalance;

    // constructor
    public InAppWalletPayment(UUID walletId, double walletBalance) {
        this.walletId = walletId;
        this.walletBalance = walletBalance;
    }

    @Override
    public void processPayment(UUID walletId, double amount, ) {
        // Implement the payment logic specific to InAppWalletPayment
        
    }

    public String getWalletId() {
        return walletId;
    }

    public double getBalance() {
        return balance;
    }


}