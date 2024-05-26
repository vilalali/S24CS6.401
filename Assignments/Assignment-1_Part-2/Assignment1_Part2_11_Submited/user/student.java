
class Student extends User {
    private String studentId;
    public Student(String userId, String name, String email, String password, String studentId) {
        super(userId, name, email, password);
        this.studentId = studentId;
    }
    public String getStudentId() {
        return studentId;
    }
    public Map<String, Object> getUserDetails() {
        // Implement logic to get user details
        return null;
    }
}