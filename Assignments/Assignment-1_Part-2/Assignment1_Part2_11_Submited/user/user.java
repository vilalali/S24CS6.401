class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private Wallet wallet;
    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.wallet = new Wallet();
    }
    public void createAccount() {
        // Implement logic to create a user account
    }
    public void uploadId() {
        // Implement logic to upload user ID
    }
    public void addMoneyToWallet(double amount) {
        wallet.addMoney(amount);
    }
    public void viewTripHistory() {
        // Implement logic to view trip history
    }
}