// PaymentManagement.java
public class PaymentManagement {
    private String paymentId;
    private User user;
    private double amount;
    private PaymentMethod paymentMethod;

    public PaymentManagement(String paymentId, User user, double amount, PaymentMethod paymentMethod) {
        this.paymentId = paymentId;
        this.user = user;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    public void makePayment() {
        this.paymentMethod.makePayment(this.amount);
    }
}
