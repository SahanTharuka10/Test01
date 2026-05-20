abstract class Vehicle {
    private String refNo;
    private VehicleType type;
    private Driver driver;
    private String VehicleNo;


    public Vehicle(String refNo, VehicleType type, Driver driver, String VehicleNo) {
        this.refNo = refNo;
        this.type = type;
        this.driver = driver;
        this.VehicleNo = VehicleNo;
    }

    public Vehicle(String refNo, VehicleType type, Driver driver) {
        this.refNo = refNo;
        this.type = type;
        this.driver = driver;
        this.VehicleNo = null;
    }

    public void setVehicleNo(String vehicleNo) {
        this.VehicleNo = vehicleNo;
    }


    public String getReference() {
        return refNo;
    }
    public VehicleType getType() {
        return type;
    }
    public String getDriverName() {
        return driver.getName();
    }

    public String getVehicleNo() {
        return VehicleNo;
    }
    public String getDriverContact() {
        return driver.getContact();
    }
}