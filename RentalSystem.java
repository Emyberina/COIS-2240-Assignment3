import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class RentalSystem {
	private static RentalSystem instance;
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();

    
    private RentalSystem() {
    	loadData();
    }
    
    private void loadData() {
        try (java.util.Scanner vScanner = new java.util.Scanner(new java.io.File("vehicles.txt"))) {
            while (vScanner.hasNextLine()) {
                vehicles.add(new Car("Loaded", "Vehicle", 2000, 4)); 
            }
        } catch (Exception e) {
            System.out.println("Could not load vehicles.");
        }

        try (java.util.Scanner cScanner = new java.util.Scanner(new java.io.File("customers.txt"))) {
            while (cScanner.hasNextLine()) {
                customers.add(new Customer(0, "Loaded Customer"));
            }
        } catch (Exception e) {
            System.out.println("Could not load customers.");
        }

        try (java.util.Scanner rScanner = new java.util.Scanner(new java.io.File("rental_records.txt"))) {
            while (rScanner.hasNextLine()) {
                rentalHistory.addRecord(new RentalRecord(null, null, java.time.LocalDate.now(), 0, "RENT")); 
            }
        } catch (Exception e) {
            System.out.println("Could not load rental records.");
        }
    }


    public static RentalSystem getInstance() {
        if (instance == null) {
            instance = new RentalSystem();
        }
        return instance;
    }
    
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        saveVehicle(vehicle);
    }
    
    private void saveVehicle(Vehicle vehicle) {
        try (java.io.FileWriter writer = new java.io.FileWriter("vehicles.txt", true)) {
            writer.write(vehicle.getInfo() + System.lineSeparator());
        } catch (Exception e) {
            System.out.println("Error saving vehicle: " + e.getMessage());
        }
    }

    private void saveCustomer(Customer customer) {
        try (java.io.FileWriter writer = new java.io.FileWriter("customers.txt", true)) {
            writer.write(customer.toString() + System.lineSeparator());
        } catch (Exception e) {
            System.out.println("Error saving customer: " + e.getMessage());
        }
    }
    
    
    public void addCustomer(Customer customer) {
        customers.add(customer);
        saveCustomer(customer);
    }
    
 
    private void saveRecord(RentalRecord record) {
        try (java.io.FileWriter writer = new java.io.FileWriter("rental_records.txt", true)) {
            writer.write(record.toString() + System.lineSeparator());
        } catch (Exception e) {
            System.out.println("Error saving rental record: " + e.getMessage());
        }
    }



    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
            vehicle.setStatus(Vehicle.VehicleStatus.RENTED);
            RentalRecord record = new RentalRecord(vehicle, customer, date, amount, "RENT");
            rentalHistory.addRecord(record);
            saveRecord(record);

            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.RENTED) {
            vehicle.setStatus(Vehicle.VehicleStatus.AVAILABLE);
            RentalRecord record = new RentalRecord(vehicle, customer, date, extraFees, "RETURN");
            rentalHistory.addRecord(record);
            saveRecord(record);
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(boolean onlyAvailable) {
    	System.out.println("|     Type         |\tPlate\t|\tMake\t|\tModel\t|\tYear\t|");
    	System.out.println("---------------------------------------------------------------------------------");
    	 
        for (Vehicle v : vehicles) {
            if (!onlyAvailable || v.getStatus() == Vehicle.VehicleStatus.AVAILABLE) {
                System.out.println("|     " + (v instanceof Car ? "Car          " : "Motorcycle   ") + "|\t" + v.getLicensePlate() + "\t|\t" + v.getMake() + "\t|\t" + v.getModel() + "\t|\t" + v.getYear() + "\t|\t");
            }
        }
        System.out.println();
    }
    
    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }
    
    public void displayRentalHistory() {
        for (RentalRecord record : rentalHistory.getRentalHistory()) {
            System.out.println(record.toString());
        }
    }
    
    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(String id) {
        for (Customer c : customers)
            if (c.getCustomerId() == Integer.parseInt(id))
                return c;
        return null;
    }
}