// Wallet.java
public class Wallet extends PaymentMethod {
    private double balance;

    public Wallet(String accountId, String accountHolderName, double balance) {
        super(accountId, accountHolderName);
        this.balance = balance;
    }

    public void addMoney(double amount) {
        this.balance += amount;
    }

    public void deductMoney(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            System.out.println("Insufficient funds in the wallet.");
        }
    }

    @Override
    public boolean makePayment(double amount) {
        if (this.balance >= amount) {
            this.deductMoney(amount);
            System.out.println("Payment successful using wallet.");
            return true;
        } else {
            System.out.println("Payment failed. Insufficient funds in the wallet.");
            return false;
        }
    }
}
