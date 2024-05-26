class Teacher extends User {
    private String TeacherId;
    public Teacher(String userId, String name, String email, String password, String TeacherId) {
        super(userId, name, email, password);
        this.TeacherId = TeacherId;
    }
    public String getTeacherId() {
        return TeacherId;
    }
    public Map<String, Object> getUserDetails() {
        // Implement logic to get user details
        return null;
    }
}