import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleRentalTest {

    @Test
    void testLicensePlateValidation() {
        Car car = new Car("Toyota", "Corolla", 2020, 4);

        assertDoesNotThrow(() -> car.setLicensePlate("AAA100"));
        assertDoesNotThrow(() -> car.setLicensePlate("ABC567"));
        assertDoesNotThrow(() -> car.setLicensePlate("ZZZ999"));

        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate(""));
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate(null));
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class, () -> car.setLicensePlate("ZZZ99"));
    }

    @Test
    void testRentAndReturnVehicle() {
        Car car = new Car("Toyota", "Corolla", 2020, 4);
        car.setLicensePlate("AAA123");
        Customer customer = new Customer(1, "Test User");

        RentalSystem system = RentalSystem.getInstance();
        system.addVehicle(car);
        system.addCustomer(customer);

        assertEquals(Vehicle.VehicleStatus.AVAILABLE, car.getStatus());
        assertTrue(system.rentVehicle(car, customer, LocalDate.now(), 50));
        assertEquals(Vehicle.VehicleStatus.RENTED, car.getStatus());
        assertFalse(system.rentVehicle(car, customer, LocalDate.now(), 50));
        assertTrue(system.returnVehicle(car, customer, LocalDate.now(), 0));
        assertEquals(Vehicle.VehicleStatus.AVAILABLE, car.getStatus());
        assertFalse(system.returnVehicle(car, customer, LocalDate.now(), 0));
    }

    @Test
    void testSingletonRentalSystem() throws Exception {
        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
        assertEquals(Modifier.PRIVATE, constructor.getModifiers());

        RentalSystem instance = RentalSystem.getInstance();
        assertNotNull(instance);
    }
}
