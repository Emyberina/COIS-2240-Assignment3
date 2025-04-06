import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class RentalSystemGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox();

        TextField plateField = new TextField();
        plateField.setPromptText("Enter license plate");

        TextField makeField = new TextField();
        makeField.setPromptText("Make");

        TextField modelField = new TextField();
        modelField.setPromptText("Model");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        Button addVehicle = new Button("Add Vehicle");
        addVehicle.setOnAction(e -> {
            System.out.println("Vehicle added.");
        });

        TextField customerId = new TextField();
        customerId.setPromptText("Customer ID");

        TextField customerName = new TextField();
        customerName.setPromptText("Customer Name");

        Button addCustomer = new Button("Add Customer");
        addCustomer.setOnAction(e -> {
            System.out.println("Customer added.");
        });

        TextArea displayArea = new TextArea();

        ComboBox<String> rentPlateBox = new ComboBox<>();
        ComboBox<String> rentCustomerBox = new ComboBox<>();
        Button rent = new Button("Rent Vehicle");
        rent.setOnAction(e -> {
            System.out.println("Vehicle rented.");
        });

        ComboBox<String> returnPlateBox = new ComboBox<>();
        ComboBox<String> returnCustomerBox = new ComboBox<>();
        Button returnButton = new Button("Return Vehicle");
        returnButton.setOnAction(e -> {
            System.out.println("Vehicle returned.");
        });

        Button showAvailable = new Button("Show Available Vehicles");
        showAvailable.setOnAction(e -> {
            displayArea.setText("Available vehicles listed.");
        });

        Button showHistory = new Button("Show Rental History");
        showHistory.setOnAction(e -> {
            displayArea.setText("Rental history displayed.");
        });

        root.getChildren().addAll(
            plateField, makeField, modelField, yearField, addVehicle,
            customerId, customerName, addCustomer,
            rentPlateBox, rentCustomerBox, rent,
            returnPlateBox, returnCustomerBox, returnButton,
            showAvailable, showHistory,
            displayArea
        );

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
} 


