class PaymentMethod {
    private UUID transactionId;
    private String amount;
    
    // Constructor
    public  PaymentMethod(UUID transactionId) {
        this.transactionId = transactionId;
    }

    // Implementation of processPayment method
    @Override
    public boolean processPayment(UUID transactionId) {
        // implement processPayment method
    }

}