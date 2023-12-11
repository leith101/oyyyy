package finalevent;
import entity.Evenement;
import entity.utilisateur;
import service.Serviceevenement;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import tools.MyDB;
import entity.utilisateur;

public class finalEController {
    
    
    
Evenement evs = new Evenement();
utilisateur userr = new utilisateur(12,"af","hf","jg","admin","hgj","hkd","hkf");



    @FXML
    private Button ParticiperE;

    @FXML
    private TextFlow TextFlowE;

    @FXML
    private ComboBox<String> comboboxsearch;

    @FXML
    private Button controleE;

    @FXML
    private DatePicker datePickersearch;

    @FXML
    private Button guestsE;

    @FXML
    private ImageView imageViewE;

    @FXML
    private Button myeventE;

    @FXML
    private Button searchE;
    
    Path selectedImagePath;
    boolean imageEdited = false;
    
    
   


    @FXML
    void ChangerpageE(ActionEvent event) throws IOException {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader();

            // Adjust the path to load from the JAR
            URL fxmlUrl = getClass().getResource("controlleE.fxml");
            loader.setLocation(fxmlUrl);

              Parent root = loader.load();

            // Create a new scene using the loaded FXML
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) controleE.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        }
    


@FXML
void participer(ActionEvent event) {
    int eventId = evs.getId(); // Obtain the event ID from the Evenement object.
    int userId = userr.getId();
    int username = userr.getId(); // Use the description as the username.
    

    Connection con = MyDB.getinstance().getCon();

    try {
        String insertQuery = "INSERT INTO participant ( id_event , user_id) VALUES ( ?, ?)";

        PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

        preparedStatement.setInt(1, eventId);
        preparedStatement.setInt(2, username);
     

        preparedStatement.executeUpdate();

        preparedStatement.close();

        showsucces("Participation successful");
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Erreur lors de la participation");
    }
}


@FXML
void showMyevent(ActionEvent event) {
    int userId = userr.getId(); // Get the user's ID from the userr object

    // Your database query to retrieve the events where the user has participated
    String query = "SELECT event.* " +
                   "FROM event " +
                   "INNER JOIN participant ON event.id = participant.id_event " +
                   "WHERE participant.user_email = ?";

    Connection con = MyDB.getinstance().getCon();

    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
        preparedStatement.setInt(1, userId); // Set the user's ID in the query

        ResultSet resultSet = preparedStatement.executeQuery();

        // Create a list to store the retrieved events
        List<Evenement> participatedEvents = new ArrayList<>();

        // Process the retrieved events
        while (resultSet.next()) {
            int eventId = resultSet.getInt("id"); // Get the event ID
            String description = resultSet.getString("description");
            String imagePath = resultSet.getString("image");
            // Retrieve other event details as needed

            // Create an Evenement object and set its attributes
            Evenement eventi = new Evenement();
            eventi.setId(eventId);
            eventi.setDescription(description);
            eventi.setImage(imagePath);
            // Set other attributes of the event

            // Add the event to the list
            participatedEvents.add(eventi);
        }

        // Now, the participatedEvents list contains the details of events where the user has participated.
        // You can further process or display these events as needed.
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error while retrieving events");
    }
}



    @FXML
    void showgusest(ActionEvent event) throws IOException {
    // Load the FXML file
            FXMLLoader loader = new FXMLLoader();

            // Adjust the path to load from the JAR
            URL fxmlUrl = getClass().getResource("guestE.fxml");
            loader.setLocation(fxmlUrl);

            Parent root = loader.load();

            // Create a new scene using the loaded FXML
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) guestsE.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
    }
 
private final String etat[] = {"gammarth", "marsa","bizerte","jendouba","carthage"};

public void comboBox(ActionEvent event) {
   List<String> list = new ArrayList<>();

    list.addAll(Arrays.asList(etat));

    ObservableList<String> dataList = FXCollections.observableArrayList(list);

    comboboxsearch.setItems(dataList);
}
public void initialize() {
    if (userr != null && "admin".equals(userr.getRole())) {
        // If the user is an admin, make the buttons visible
        guestsE.setVisible(true);
        controleE.setVisible(true);
    } else {
        // If the user is not an admin, hide the buttons
        guestsE.setVisible(false);
        controleE.setVisible(false);
    }
    List<String> list = new ArrayList<>(Arrays.asList(etat));

    ObservableList<String> dataList = FXCollections.observableArrayList(list);

    comboboxsearch.setItems(dataList);
    
     // Define your database connection details.
    /*String url = "jdbc:mysql://localhost:3306/pidev4se1";
    String username = "root";
    String password = "";

    // Initialize an ObservableList to hold the places.
    ObservableList<String> placesList = FXCollections.observableArrayList();

    // Establish a database connection and retrieve places from the 'event' table.
    try{
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT place FROM event")) {
            
            while (resultSet.next()) {
                String place = resultSet.getString("place");
                placesList.add(place);
            }
            
            // Set the items in the ComboBox.
            comboboxsearch.setItems(placesList);
            
        }
    } catch (SQLException e) {
    }*/
}

    /**
     *
     */
   
public void populateComboBox() {
    // Define your database connection details.
    String url = "jdbc:mysql://localhost:3306/pidev4se1";
    String username = "root";
    String password = "";

    // Initialize an ObservableList to hold the places.
    ObservableList<String> placesList = FXCollections.observableArrayList();

    // Establish a database connection and retrieve places from the 'event' table.
    try{
        try (Connection connection = DriverManager.getConnection(url, username, password); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery("SELECT place FROM event")) {
            
            while (resultSet.next()) {
                String place = resultSet.getString("place");
                placesList.add(place);
            }
            
            // Set the items in the ComboBox.
            comboboxsearch.setItems(placesList);
            
        }
    } catch (SQLException e) {
    }
}
    
    

@FXML
void showsearch(ActionEvent event) throws SQLException {
    LocalDate selectedDate = datePickersearch.getValue();
    
    // Get the selected place from comboboxsearch
    String selectedPlace = comboboxsearch.getValue();
   
    Connection con = MyDB.getinstance().getCon();

    // Use try-with-resources for Statement and ResultSet to ensure proper resource management.
    try (Statement ste = con.createStatement();
         ResultSet resultSet = ste.executeQuery("SELECT * FROM event WHERE date = '" + selectedDate.toString() + "' AND place = '" + selectedPlace + "'")) {

        if (resultSet.next()) {
            // Event found in the database.
            // You can access the data and update your UI components here.
            String description = resultSet.getString("description");
            String imagePath = resultSet.getString("image");
            showsucces("trouvée");
            
            
            evs = new Evenement();
            evs.setId(resultSet.getInt("id")); // Retrieve and set the ID attribute
            evs.setDescription(resultSet.getString("description"));
            evs.setImage(resultSet.getString("image"));

            // You can continue to retrieve and set other attributes as needed
            evs.setPlace(resultSet.getString("place"));
            evs.setDate((resultSet.getString("date")));

            // Update your UI components with the retrieved data.
            // Display the description in TextFlowE.
            Text text = new Text(description);
            TextFlowE.getChildren().setAll(text);
            
         

            try {
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Erreur lors du chargement de l'image.");
            }

            // Show the ParticiperE button when the search is successful.
            ParticiperE.setVisible(true);
        } else {
            showAlert("n'est pas trouvée");
            // Event not found in the database.
            // Handle this case (e.g., display a message).

            // Hide the ParticiperE button when the search is not successful.
            ParticiperE.setVisible(false);
        } }catch (SQLException e) {
        e.printStackTrace();
    }
}





    private void showAlert(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
private void showsucces(String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("OK");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
}


/*
// Assuming you have a User class with a property 'role'.
private User utilisateur;

// In your controller code, you can set the visibility of the button using an if statement.
if ("admin".equals(utilisateur.getRole())) {
    controleE.setVisible(true);
} else {
    controleE.setVisible(false);
}*/




/**
 *
 * @Dhouha
 */




