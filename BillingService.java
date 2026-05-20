import java.time.LocalDateTime;
import java.time.Duration;


class BillingService {
    static final double RATE_PER_MIN = 5.0;

    private static double getBaseRate(VehicleType type) {
        switch (type) {
            case CAR:
                return 100.0;
            case MOTORCYCLE:
                return 50.0;
            case HEAVY:
                return 150.0;
            default:
                return 0.0;
        }
    }

    public static void printBill(ParkingTicket t) {
        LocalDateTime exit = LocalDateTime.now();
        long minutes = Duration.between(t.getEntryTime(), exit).toMinutes();

        if (minutes <= 0) minutes = 1;

        double baseFee = getBaseRate(t.getVehicle().getType());
        double amount = baseFee + minutes * RATE_PER_MIN;

        System.out.println("\n======= BILL =======");
        System.out.println("Reference No : " + t.getVehicle().getReference());
        System.out.println("Driver       : " + t.getVehicle().getDriverName());
        System.out.println("Contact      : " + t.getVehicle().getDriverContact());
        System.out.println("Vehicle No   : " + t.getVehicle().getVehicleNo()); // Print Vehicle No
        System.out.println("Slot No      : " + t.getSlot());
        System.out.println("Time Parked  : " + minutes + " minutes");
        System.out.printf("Base Fee     : Rs.%.2f\n", baseFee); // Display Base Fee
        System.out.printf("Total Amount : Rs.%.2f\n", amount);
        System.out.println("===========================\n");
        System.out.println("Thank You!  Have a nice day.");
    }
}