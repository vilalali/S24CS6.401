class Staff extends User {
    private String staffId;
    public Staff(String userId, String name, String email, String password, String staffId) {
        super(userId, name, email, password);
        this.staffId = staffId;
    }
    public String getStaffId() {
        return staffId;
    }
    public Map<String, Object> getUserDetails() {
        // Implement logic to get user details
        return null;
    }
}