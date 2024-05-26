public class Feedback {
    private String feedbackId;
    private User user;
    private int rating;
    private String comment;
    private Date timestamp;

    public Feedback(String feedbackId, User user, int rating, String comment) {
        this.feedbackId = feedbackId;
        this.user = user;
        this.rating = validateRating(rating);
        this.comment = comment;
        this.timestamp = new Date(); // Automatically set the timestamp to the current date and time
    }

    private int validateRating(int rating) {
        // Ensure rating is within a valid range (e.g., 1 to 5)
        if (rating < 1) {
            return 1;
        } else if (rating > 5) {
            return 5;
        } else {
            return rating;
        }
    }

    public void provideFeedback() {
        // Implement logic to process and store feedback
        // You may want to store the feedback in a database or perform other actions based on your system's requirements
    }

    public String getFormattedTimestamp() {
        // Convert timestamp to a formatted string (e.g., "yyyy-MM-dd HH:mm:ss")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(timestamp);
    }

    public String toString() {
        // Convert the Feedback object to a formatted string
        return "Feedback ID: " + feedbackId +
                "\nUser: " + user.getName() +
                "\nRating: " + rating +
                "\nComment: " + comment +
                "\nTimestamp: " + getFormattedTimestamp();
    }

    // Getter methods

    public String getFeedbackId() {
        return feedbackId;
    }

    public User getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
