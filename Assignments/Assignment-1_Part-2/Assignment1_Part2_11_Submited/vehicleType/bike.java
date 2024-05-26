// Bike  class definition inheriting vehicle type
class Bike extends VehicleType {
    private String bikeSpecific;

    public String getBikeSpecific() {
        return bikeSpecific;
    }

    public void setBikeSpecific(String bikeSpecific) {
        this.bikeSpecific = bikeSpecific;
    }

    @Override
    public Map<String, Object> getVehicleDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("BikeSpecificDetail", bikeSpecific); 
        return details;
    }
}
