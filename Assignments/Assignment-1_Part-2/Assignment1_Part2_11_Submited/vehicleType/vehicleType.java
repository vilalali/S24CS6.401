// VehicleType abstract class definition

abstract class VehicleType {
    private String vehicleTypeId;

    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    public void setVehicleTypeId(String vehicleTypeId) {
        this.vehicleTypeId = vehicleTypeId;
    }
	
    // Abstract method
    public abstract Map<String, Object> getVehicleDetails();
}
