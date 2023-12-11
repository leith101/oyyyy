package finalevent;
import entity.Evenement;
import service.Serviceevenement;
import entity.Participant;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tools.MyDB;

public class GuestEController {

    @FXML
    private Button backmainE;

    @FXML
    private AnchorPane tableviewE;
    @FXML
    private TextArea texteramyguest;

    @FXML
    private TableView<Participant> table; // Define a TableView for displaying the data
    @FXML
    private TableColumn<Participant, Integer> ID;
    @FXML
    private TableColumn<Participant, Integer> eventIdColumn;
    @FXML
    private TableColumn<Participant, String> userNameColumn;
    @FXML
    private TableColumn<Participant, String> userEmailColumn;

    /*public void initialize() {
        Serviceevenement sv = new Serviceevenement();
        List<Participant> participantList = sv.affihcerparticiapnt();
        // Initialize the columns with the correct property names
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_event"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("user_Name"));
        userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("user_Email"));

        ObservableList<Participant> data = FXCollections.observableArrayList(participantList);

        table.setItems(data); // Set the data in the table
    }*/

public void initialize() {
    // ... Votre code d'initialisation existant ...

    // Compter le nombre de participants par événement
    int maxParticipants = 0;
    String eventName = "";

    try (Connection con = MyDB.getinstance().getCon();
         Statement ste = con.createStatement()) {

        String query = "SELECT id_event, COUNT(*) AS participantCount " +
                       "FROM participant " +
                       "GROUP BY id_event " +
                       "ORDER BY participantCount DESC " +
                       "LIMIT 1";

        ResultSet resultSet = ste.executeQuery(query);

        if (resultSet.next()) {
            maxParticipants = resultSet.getInt("participantCount");
            int eventId = resultSet.getInt("id_event");

            // Maintenant, obtenir le nom de l'événement associé à eventId
            String eventNameQuery = "SELECT name FROM event WHERE id = " + eventId;
            ResultSet eventNameResult = ste.executeQuery(eventNameQuery);

            if (eventNameResult.next()) {
                eventName = eventNameResult.getString("name");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // Afficher le résultat dans la TextArea
    texteramyguest.setText(" les participants : " + maxParticipants +
                          "\nNom de l'événement le plus participé : " + eventName);
    
        // Initialize the columns
    //ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    //eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_event"));
    //loadParticipantData();
}

    
/*    public void initialize() {
    // Initialize the columns
    ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_event"));
    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
    userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));

    // Load data from the "participant" table and populate the TableView
    loadParticipantData();
}*/





/*public void showData() {
    Serviceevenement sv = new Serviceevenement();
    List<Participant> evenementList = sv.affihcerparticiapnt(); // Make sure the afficherEvenement method returns a list of Evenement objects

    // Use PropertyValueFactory with the correct property names
    //ID.setCellValueFactory(new PropertyValueFactory<>("id"));
    //eventIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_event"));
    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name")); // Assuming Evenement has a userName property
    userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email")); // Assuming Evenement has a userEmail property

    ObservableList<Participant> data = FXCollections.observableArrayList(evenementList); // Convert the list to an observable list

    table.setItems(data); // Set the data in the table
}*/

public void showData() {
        Serviceevenement sv = new Serviceevenement();
        List<Participant> veloList = sv.affihcerparticiapnt(); // Assurez-vous que la méthode afficher retourne une liste de vélos

    userNameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
    // Assuming Evenement has a userName property
    userEmailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email")); // Assuming Evenement has a userEmail property

        ObservableList<Participant> data = FXCollections.observableArrayList(veloList); // Convertir la liste en une liste observable

        table.setItems(data); // Définir les données dans la table
    }































private void loadParticipantData() {
    // Define your database connection variables
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    try {
        // Establish a database connection
        connection = MyDB.getinstance().getCon(); // Replace this with your actual database connection method

        // Define your SQL query to retrieve data from the "participant" table
        String query = "SELECT * FROM participant";

        // Create a PreparedStatement to execute the query
        preparedStatement = connection.prepareStatement(query);

        // Execute the query and get the result set
        resultSet = preparedStatement.executeQuery();

        // Create a list to store the retrieved participants
        List<Participant> participants = new ArrayList<>();

        // Iterate through the result set and populate the list
        while (resultSet.next()) {
            int id_event = resultSet.getInt("id_event");
            String userName = resultSet.getString("user_name");
            String userEmail = resultSet.getString("user_email");

            participants.add(new Participant(id_event, userName, userEmail));
        }

        // Populate the TableView with the retrieved participants
        table.getItems().setAll(participants);
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Error while loading participant data");
    } finally {
        // Close database resources
        try {
            if (resultSet != null) resultSet.close();
            if (preparedStatement != null) preparedStatement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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


@FXML
    void back_change_page(ActionEvent event) throws IOException {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader();

            // Adjust the path to load from the JAR
            URL fxmlUrl = getClass().getResource("finalE.fxml");
            loader.setLocation(fxmlUrl);

            Parent root = loader.load();

            // Create a new scene using the loaded FXML
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) backmainE.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        }


}
