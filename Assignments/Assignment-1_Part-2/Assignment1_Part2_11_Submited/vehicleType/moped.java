// Moped class definition inheriting vehicle type
class Moped extends VehicleType {
    private String mopedSpecific;

    public String getMopedSpecific() {
        return mopedSpecific;
    }

    public void setMopedSpecific(String mopedSpecific) {
        this.mopedSpecific = mopedSpecific;
    }

    @Override
    public Map<String, Object> getVehicleDetails() {
        Map<String, Object> details = new HashMap<>();
        details.put("MopedSpecificDetail", mopedSpecific); 
        return details;
    }
}
