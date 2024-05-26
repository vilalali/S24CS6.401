// Bicycle Class definition inheriting vehicle type
class Bicycle extends VehicleType {
    private String bicycleSpecific;

    public String getBicycleSpecific() {
        return bicycleSpecific;
    }

    public void setBicycleSpecific(String bicycleSpecific) {
        this.bicycleSpecific = bicycleSpecific;
    }

    @Override
    public Map<String, Object> getVehicleDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("BicycleSpecificDetail", bicycleSpecific); 
        return details;
    }
}

