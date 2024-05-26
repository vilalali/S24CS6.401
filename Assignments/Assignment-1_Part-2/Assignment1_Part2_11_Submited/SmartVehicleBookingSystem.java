public class SmartVehicleBookingSystem {

    // List to store registered users
    private List<User> users;

    // List to store registered vehicles
    private List<Vehicle> vehicles;

    // List to store available parking lots
    private List<ParkingLot> parkingLots;

    // List to store ongoing trips
    private List<TripManagement> trips;

    // List to store payments made by users
    private List<PaymentManagement> payments;

    // List to store feedbacks received from users
    private List<Feedback> feedbacks;

    // Constructor to initialize the lists
    public SmartVehicleBookingSystem() {
        this.users = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.parkingLots = new ArrayList<>();
        this.trips = new ArrayList<>();
        this.payments = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
    }

    // Method to register a new user
    public void registerUser(User user) {
        users.add(user);
    }

    // Method to add a new vehicle
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    // Method to add a new parking lot
    public void addParkingLot(ParkingLot parkingLot) {
        parkingLots.add(parkingLot);
    }

    // Method to create a new trip for a user with a specific vehicle
    public TripManagement createTrip(User user, Vehicle vehicle) {
        TripManagement trip = new TripManagement(user, vehicle);
        trips.add(trip);
        return trip;
    }

    // Method to process a payment made by a user for a specific amount using a particular payment method
    public void processPayment(User user, double amount, PaymentMethod paymentMethod) {
        payments.add(new PaymentManagement(user, amount, paymentMethod));
    }

    // Method to add a new feedback from a user
    public void addFeedback(Feedback feedback) {
        feedbacks.add(feedback);
    }

    // Getters and setters for the lists
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<ParkingLot> getParkingLots() {
        return parkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
    }

    public List<TripManagement> getTrips() {
        return trips;
    }

    public void setTrips(List<TripManagement> trips) {
        this.trips = trips;
    }

    public List<PaymentManagement> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentManagement> payments) {
        this.payments = payments;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}



