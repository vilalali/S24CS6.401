public class ParkingLot {

    // Unique identifier for the parking lot
    private String parkingLotId;

    // Total capacity of the parking lot
    private int capacity;

    // Number of currently available parking spaces
    private int availability;

    // Location of the parking lot
    private String location;

    // Maintenance status of the parking lot (e.g., available, undergoing repairs)
    private String maintenanceStatus;

    // Available security features of the parking lot (e.g., CCTV, security personnel)
    private String securityFeatures;

    // Constructor to initialize the parking lot with ID, capacity, and location
    public ParkingLot(String parkingLotId, int capacity, String location) {
        this.parkingLotId = parkingLotId;
        this.capacity = capacity;
        this.availability = capacity; // Initially, all spaces are available
        this.location = location;
    }

    // Method to get the parking lot ID
    public String getParkingLotId() {
        return parkingLotId;
    }

    // Method to get the total capacity of the parking lot
    public int getCapacity() {
        return capacity;
    }

    // Method to get the number of available parking spaces
    public int getAvailability() {
        return availability;
    }

    // Method to update the availability when a car parks or leaves
    public void updateAvailability(int change) {
        availability += change;
    }

    // Method to get the location of the parking lot
    public String getLocation() {
        return location;
    }

    // Method to get the maintenance status of the parking lot
    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }

    // Method to set the maintenance status of the parking lot
    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    // Method to get the security features of the parking lot
    public String getSecurityFeatures() {
        return securityFeatures;
    }

    // Method to set the security features of the parking lot
    public void setSecurityFeatures(String securityFeatures) {
        this.securityFeatures = securityFeatures;
    }
}

