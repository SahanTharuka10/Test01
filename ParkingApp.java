import java.util.*;

public class ParkingApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingManager manager = new ParkingManager();

        while (true) {
            System.out.println("\n===== ABC Parking System Management =====");
            System.out.println("1. Parking System");
            System.out.println("2. Main Menu");
            System.out.println("3. Exit");

            System.out.print("Enter Option: ");
            String mainLine = sc.nextLine().trim();

            if (mainLine.isEmpty()) continue;
            int mainChoice;
            try {
                mainChoice = Integer.parseInt(mainLine);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (mainChoice == 3) break;

            switch (mainChoice) {

                case 1:
                    while (true) {
                        System.out.println("\n--- Park/Leave Menu ---");
                        System.out.println("1. Park Vehicle");
                        System.out.println("2. Leave Vehicle");
                        System.out.println("3. Back");
                        System.out.print("Select: ");
                        String selLine = sc.nextLine().trim();
                        if (selLine.isEmpty()) continue;
                        int c;
                        try {
                            c = Integer.parseInt(selLine);
                        }
                        catch (NumberFormatException e) { System.out.println("Invalid input.");
                            continue;
                        }

                        if (c == 3) break;

                        if (c == 1) {

                            System.out.print("Do you have a Reference Number? (y/n): ");
                            String haveRef = sc.nextLine().trim();
                            if (haveRef.isEmpty()) {
                                System.out.println("Invalid input.");
                                continue;
                            }

                            String ref = null;
                            VehicleType type = null;
                            String VehicleNo = null;

                            if (haveRef.equalsIgnoreCase("y")) {

                                System.out.print("Enter Reference Number: ");
                                ref = sc.nextLine().trim();

                                if (ref.isEmpty()) { System.out.println("Invalid reference.");
                                    continue;
                                }

                                if (!manager.isReferenceRegistered(ref)) {
                                    System.out.println("Reference not found in registry.");
                                    System.out.print("Do you want to register this reference now? (y/n): ");
                                    String regChoice = sc.nextLine().trim();
                                    if (regChoice.equalsIgnoreCase("y")) {
                                        char prefix = Character.toUpperCase(ref.charAt(0));
                                        if (prefix == 'C') type = VehicleType.CAR;
                                        else if (prefix == 'M') type = VehicleType.MOTORCYCLE;
                                        else if (prefix == 'H') type = VehicleType.HEAVY;
                                        else {
                                            System.out.println("Cannot determine type from reference. Select type:");
                                            System.out.println("1. Car\n2. Motorcycle\n3. Heavy Weight Vehicle");
                                            String tLine = sc.nextLine().trim();
                                            int t;
                                            try { t = Integer.parseInt(tLine); }
                                            catch (NumberFormatException e) { System.out.println("Invalid type."); continue; }
                                            if (t == 1) type = VehicleType.CAR;
                                            else if (t == 2) type = VehicleType.MOTORCYCLE;
                                            else type = VehicleType.HEAVY;
                                        }

                                        System.out.print("Driver Name: ");
                                        String name = sc.nextLine().trim();
                                        System.out.print("Driver Contact: ");
                                        String contact = sc.nextLine().trim();

                                        System.out.print("Vehicle Number: ");
                                        VehicleNo = sc.nextLine().trim();


                                        Driver d = new Driver(name, contact);
                                        Vehicle v;
                                        if (type == VehicleType.CAR) v = new Car(ref, d, VehicleNo);
                                        else if (type == VehicleType.MOTORCYCLE) v = new Motorcycle(ref, d, VehicleNo);
                                        else v = new HeavyVehicle(ref, d, VehicleNo);

                                        manager.registerVehicle(v);
                                        System.out.println("Reference registered successfully.");
                                    } else {
                                        System.out.println("Cannot proceed without registration.");
                                        continue;
                                    }
                                }
                            } else if (haveRef.equalsIgnoreCase("n")) {
                                System.out.println("Select Vehicle Type:");
                                System.out.println("1. Car\n2. Motorcycle\n3. Heavy Vehicle");
                                String tLine = sc.nextLine().trim();
                                int t;
                                try { t = Integer.parseInt(tLine); }
                                catch (NumberFormatException e) { System.out.println("Invalid type.");
                                    continue;
                                }

                                if (t == 1) type = VehicleType.CAR;
                                else if (t == 2) type = VehicleType.MOTORCYCLE;
                                else type = VehicleType.HEAVY;

                                ref = manager.generateRef(type);
                                System.out.println("Generated Reference Number: " + ref);

                                System.out.print("Driver Name: ");
                                String name = sc.nextLine().trim();

                                System.out.print("Vehicle Number: ");
                                VehicleNo = sc.nextLine().trim();
                                String contact="";
                                do {
                                    System.out.print("Driver Contact: ");
                                    contact = sc.nextLine().trim();
                                    if(contact.length()!=10)
                                        System.out.println("Invalid Contact Number.Please Enter contact number with 10 digits.\n");
                                }while(contact.length()!=10);
                                Driver d = new Driver(name, contact);
                                Vehicle v;
                                if (type == VehicleType.CAR) v = new Car(ref, d, VehicleNo);
                                else if (type == VehicleType.MOTORCYCLE) v = new Motorcycle(ref, d, VehicleNo);
                                else v = new HeavyVehicle(ref, d, VehicleNo);
                                manager.registerVehicle(v);
                                System.out.println("Reference and driver details registered.");
                            } else {
                                System.out.println("Please answer 'y' or 'n'.");
                                continue;
                            }

                            Vehicle regVehicle = manager.getRegisteredVehicle(ref);
                            if (regVehicle == null) {
                                System.out.println("Registration error. Aborting park operation.");
                                continue;
                            }

                            manager.showAvailability(regVehicle.getType());
                            System.out.print("Enter Slot Number to park: ");
                            String slotLine = sc.nextLine().trim();
                            int slotNum;
                            try { slotNum = Integer.parseInt(slotLine); }
                            catch (NumberFormatException e) { System.out.println("Invalid slot.");
                                continue;
                            }

                            if (manager.parkVehicle(ref, slotNum))
                                System.out.println("Vehicle Parked Successfully! Reference: " + ref);
                            else
                                System.out.println("Error: Invalid Slot or Slot Occupied!");

                        }

                        else if (c == 2) {
                            System.out.print("Enter Reference Number: ");
                            String ref = sc.nextLine().trim();
                            if (ref.isEmpty()) { System.out.println("Invalid input.");
                                continue;
                            }
                            manager.leaveVehicle(ref);
                        }

                    }
                    break;

                case 2:
                    System.out.print("Enter the PassKey to access to the System: ");
                    String passKeyLine = sc.nextLine().trim(); // Use nextLine() and parse
                    if (passKeyLine.isEmpty()) {
                        System.out.println("Invalid PassKey");
                        break;
                    }

                    int PassKey;
                    try {
                        PassKey = Integer.parseInt(passKeyLine);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid PassKey format.");
                        break;
                    }

                    if(PassKey==1234) {
                        System.out.println("Access Granted to Main Menu");
                        while (true) {
                            System.out.println("\n--- Main Menu ---");
                            System.out.println("1. Vehicle Details");
                            System.out.println("2. Slot Details");
                            System.out.println("3. Back");

                            System.out.print("Select Option: ");
                            String mLine = sc.nextLine().trim();
                            if (mLine.isEmpty()) continue;
                            int m;
                            try {
                                m = Integer.parseInt(mLine);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid input.");
                                continue;
                            }

                            if (m == 3) break;

                            if (m == 1) manager.printVehicleDetails();
                            else if (m == 2) manager.printSlotDetails();
                            else System.out.println("Invalid option.");
                        }
                    }else{
                        System.out.println("Invalid PassKey");
                        break;
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        System.out.println("System Closed.");
        sc.close();
    }
}