class ParkingManager {

    private ParkingSlot[] slots = new ParkingSlot[20];
    private ParkingTicket[] activeTickets = new ParkingTicket[100];
    private Vehicle[] registeredVehicles = new Vehicle[100];

    private int regCount = 0, ticketCount = 0;

    private int carCounter = 1, bikeCounter = 1, heavyCounter = 1;

    public ParkingManager() {
        initializeSlots();
    }

    private void initializeSlots() {
        int index = 0;

        for (int i = 1; i <= 10; i++) {
            slots[index++] = new ParkingSlot(i, VehicleType.CAR);
        }
        for (int i = 11; i <= 15; i++) {
            slots[index++] = new ParkingSlot(i, VehicleType.MOTORCYCLE);
        }
        for (int i = 16; i <= 20; i++) {
            slots[index++] = new ParkingSlot(i, VehicleType.HEAVY);
        }
    }

    public String generateRef(VehicleType type) {
        if (type == VehicleType.CAR) return "C" + (1000 + carCounter++);
        if (type == VehicleType.MOTORCYCLE) return "M" + (2000 + bikeCounter++);
        return "H" + (3000 + heavyCounter++);
    }

    public void registerVehicle(Vehicle v) {
        registeredVehicles[regCount++] = v;
    }

    public boolean isReferenceRegistered(String ref) {
        for (int i = 0; i < regCount; i++) {
            if (registeredVehicles[i].getReference().equals(ref)) {
                return true;
            }
        }
        return false;
    }

    public Vehicle getRegisteredVehicle(String ref) {
        for (int i = 0; i < regCount; i++) {
            if (registeredVehicles[i].getReference().equals(ref)) {
                return registeredVehicles[i];
            }
        }
        return null;
    }

    public void showAvailability(VehicleType type) {
        System.out.print("Available " + type + " Slots: ");
        boolean found = false;

        for (ParkingSlot s : slots) {
            if (!s.isReserved() && s.getAllowedType() == type) {
                System.out.print("[" + s.getSlotId() + "] ");
                found = true;
            }
        }
        if (!found) System.out.print("None");

        System.out.println();
    }

    public boolean parkVehicle(String ref, int slotId) {

        Vehicle v = getRegisteredVehicle(ref);
        if (v == null) {
            System.out.println("Reference not registered!");
            return false;
        }

        for (ParkingSlot s : slots) {
            if (s.getSlotId() == slotId) {

                if (!s.canPark(v)) {
                    System.out.println("Not allowed / Reserved!");
                    return false;
                }

                s.Reserve();
                activeTickets[ticketCount++] = new ParkingTicket(v, slotId);
                return true;
            }
        }

        return false;
    }

    public boolean leaveVehicle(String ref) {

        for (int i = 0; i < ticketCount; i++) {

            if (activeTickets[i].getVehicle().getReference().equals(ref)) {

                int slot = activeTickets[i].getSlot();

                for (ParkingSlot s : slots) {
                    if (s.getSlotId() == slot) {
                        s.free();
                        break;
                    }
                }

                BillingService.printBill(activeTickets[i]);

                activeTickets[i] = activeTickets[ticketCount - 1];
                ticketCount--;

                return true;
            }
        }

        System.out.println("Invalid reference!");
        return false;
    }

    public void printVehicleDetails() {
        System.out.println("\n================= PARKED VEHICLES =================");
        System.out.printf("%-10s %-10s %-6s %-10s %-10s\n", "Ref", "Type", "Slot", "Driver","Vehicle Number\n---------------------------------------------------------");

        for (int i = 0; i < ticketCount; i++) {
            ParkingTicket t = activeTickets[i];

            System.out.printf("%-10s %-10s %-6d %-10s %-10s \n", t.getVehicle().getReference(), t.getVehicle().getType(), t.getSlot(), t.getVehicle().getDriverName(),t.vehicle.getVehicleNo());
        }
        System.out.println("========================================================\n");
    }

    public void printSlotDetails() {
        System.out.println("\n=== SLOT DETAILS ===");
        for (ParkingSlot s : slots) {
            System.out.println("Slot " + s.getSlotId() + " (" + s.getAllowedType() + ") : " +
                    (s.isReserved() ? "Reserved" : "Free"));
        }
        System.out.println("====================\n");
    }
}
