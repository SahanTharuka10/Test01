class ParkingSlot {
    private int slotId;
    private VehicleType allowedType;
    boolean Reserved;

    public ParkingSlot(int id, VehicleType type) {
        this.slotId = id;
        this.allowedType = type;
        this.Reserved = false;
    }

    public boolean canPark(Vehicle v) {
        return !Reserved && v.getType() == allowedType;
    }

    public void Reserve() {

        Reserved = true;
    }
    public void free() {

        Reserved = false;
    }

    public int getSlotId() {

        return slotId;
    }
    public boolean isReserved() {

        return Reserved;
    }
    public VehicleType getAllowedType() {

        return allowedType;
    }
}
