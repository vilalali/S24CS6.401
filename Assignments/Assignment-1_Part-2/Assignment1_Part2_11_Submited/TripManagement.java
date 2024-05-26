import java.util.Date;
import java.util.UUID;

// Class representing the management of a trip taken by a user using a specific vehicle
public class TripManagement {

    // Unique identifier for the trip
    private String tripId;

    // Vehicle used for the trip
    private Vehicle vehicle;

    // User who took the trip
    private User user;

    // Start time of the trip
    private Date startTime;

    // End time of the trip (if completed)
    private Date endTime;

    // Flag indicating whether the trip ended within the allowed time limit
    private boolean endTimeWithinLimit;

    // Distance traveled during the trip
    private double distance;

    // Base rate for the trip
    private double baseRate;

    // Rate charged per 100 meters traveled
    private double ratePer100Meters;

    // Fine charged per day for exceeding the time limit (if applicable)
    private double finePerDay;

    // Total amount due for the trip, including base rate, distance charges, and potential fine
    private double amountDue;

    // Constructor to initialize the trip with user and vehicle
    public TripManagement(User user, Vehicle vehicle) {
        this.tripId = generateTripId(); // Generate a unique trip ID
        this.user = user;
        this.vehicle = vehicle;
        this.startTime = new Date();
        this.endTime = null;
        this.endTimeWithinLimit = false;
        this.distance = 0.0;
        this.baseRate = 10.0; // Set a default base rate (you can adjust as needed)
        this.ratePer100Meters = 2.0; // Set a default rate per 100 meters (you can adjust as needed)
        this.finePerDay = 5.0; // Set a default fine per day for exceeding the time limit
        this.amountDue = 0.0;
    }

    // Method to start the trip
    public void startTrip() {
        // Implement logic to start the trip
        System.out.println("Trip started.");
    }

    // Method to end the trip
    public void endTrip() {
        // Implement logic to end the trip
        System.out.println("Trip ended.");
    }

    // Method to calculate the distance traveled during the trip
    public void calculateDistance() {
        // Implement logic to calculate distance
        System.out.println("Distance calculated.");
    }

    // Method to calculate the amount due for the trip
    public void calculateAmountDue() {
        // Implement logic to calculate the amount due
        System.out.println("Amount due calculated.");
    }

    // Method to apply a fine if the bike is not returned within 8 hours
    public void applyFine() {
        // Implement logic to apply the fine
        System.out.println("Fine applied.");
    }

    // Private method to generate a unique trip ID
    private String generateTripId() {
        return UUID.randomUUID().toString();
    }

    // Getters and setters for the TripManagement class

    public String getTripId() {
        return tripId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public User getUser() {
        return user;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public boolean isEndTimeWithinLimit() {
        return endTimeWithinLimit;
    }

    public double getDistance() {
        return distance;
    }

    public double getBaseRate() {
        return baseRate;
    }

    public double getRatePer100Meters() {
        return ratePer100Meters;
    }

    public double getFinePerDay() {
        return finePerDay;
    }

    public double getAmountDue() {
        return amountDue;
    }

    public void setEndTimeWithinLimit(boolean endTimeWithinLimit) {
        this.endTimeWithinLimit = endTimeWithinLimit;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setAmountDue(double amountDue) {
        this.amountDue = amountDue;
    }
}

