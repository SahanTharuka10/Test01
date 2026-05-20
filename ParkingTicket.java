import java.time.LocalDateTime;


class ParkingTicket {
    public Vehicle vehicle;
    public int slot;
    public LocalDateTime entryTime;

    public ParkingTicket(Vehicle v, int slot) {
        this.vehicle = v;
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
    public int getSlot() {

        return slot;
    }
    public LocalDateTime getEntryTime() {

        return entryTime;
    }
}
