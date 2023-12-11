package finalevent;

import entity.Evenement;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import service.Serviceevenement;

public class ControlleEController {

    @FXML
    private Button ajouterE;

    @FXML
    private Button backmainE;

    @FXML
    private DatePicker date_picker_ajout;

    @FXML
    private DatePicker date_picker_modifier;

    @FXML
    private Button modifierE;

    @FXML
    private Button supprimerE;

    @FXML
    private TextArea text_field_discription_ajout;

    @FXML
    private TextArea text_field_discription_modifier;

    @FXML
    private TextField textfield_ajout_image;

    @FXML
    private TextField textfield_ajout_name;

    @FXML
    private TextField textfield_ajout_place;

    @FXML
    private TextField textfield_ajout_price;

    @FXML
    private TextField textfield_modifier_id;

    @FXML
    private TextField textfield_modifier_image;

    @FXML
    private TextField textfield_modifier_name;

    @FXML
    private TextField textfield_modifier_place;

    @FXML
    private TextField textfield_modifier_price;

    @FXML
    void ActivateSupprimerE(ActionEvent event) {
    // Check if the ID field is empty
    if (textfield_modifier_id.getText().isEmpty()) {
        showAlert("Please enter an event ID to delete.");
        return;
    }

    // Check if the event ID exists in the database
    try {
        int eventId = Integer.parseInt(textfield_modifier_id.getText());
        Serviceevenement sp = new Serviceevenement();
        List<Evenement> events = sp.affihcerevenement();
        boolean eventExists = false;

        for (Evenement i: events) {
            if (i.getId() == eventId) {
                eventExists = true;
                break;
            }
        }

        if (!eventExists) {
            showAlert("Event with ID " + eventId + " does not exist.");
            return;
        }
    } catch (NumberFormatException e) {
        showAlert("Event ID must be a valid integer.");
        return;
    }
    Evenement eventToDelete = new Evenement();
    eventToDelete.setId(Integer.parseInt(textfield_modifier_id.getText()));
    int eventId = Integer.parseInt(textfield_modifier_id.getText());
    Serviceevenement sp = new Serviceevenement();
    sp.supprimerevenement(eventToDelete);
    showsucces("Event with ID " + eventId + " has been deleted");


  }

    @FXML
    void activateAjouterE(ActionEvent event) {
    String name = textfield_ajout_name.getText().trim();
    String priceText = textfield_ajout_price.getText().trim();
    String date = date_picker_ajout.getValue() != null ? date_picker_ajout.getValue().toString() : "";
    String description = text_field_discription_ajout.getText().trim();
    String image = textfield_ajout_image.getText().trim();
    String place = textfield_ajout_place.getText().trim();

    if (name.isEmpty() || priceText.isEmpty() || date.isEmpty() || description.isEmpty() || image.isEmpty() || place.isEmpty()) {
        showAlert("Erreur");
        return;
    }

    double price;
    try {
        price = Double.parseDouble(priceText);
    } catch (NumberFormatException e) {
        showAlert("Erreur");
        return;
    }

    Evenement newEvent = new Evenement(name, price, date, description, image,place);

    Serviceevenement sp = new Serviceevenement();
    sp.ajouterevenement(newEvent);

    showsucces("Succès");
    
    javafx.scene.image.Image img = new javafx.scene.image.Image(getClass().getResourceAsStream("/b.png"));

    // Définir les dimensions de l'image réduite
    double newWidth = 100; // Remplacez 100 par la largeur souhaitée en pixels
    double newHeight = 100; // Remplacez 100 par la hauteur souhaitée en pixels

    // Créer un ImageView avec les dimensions réduites
    ImageView imageView = new ImageView(img);
    imageView.setFitWidth(newWidth);
    imageView.setFitHeight(newHeight);

    // Créer la notification avec l'image réduite
    Notifications notificationBuilder = Notifications.create()
            .title("Succès!")
            .text("Un nouvel événement est bien ajouté dans la base de données")
            .graphic(imageView)  
            //.hideAfter(Duration.seconds(10))
            .position(Pos.BOTTOM_RIGHT);
    notificationBuilder.darkStyle();
    notificationBuilder.show();
    
    showQRCodeStage();
    
    
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
void activatemodifierE(ActionEvent event) {
    if (textfield_modifier_id.getText().isEmpty() ||
            textfield_modifier_name.getText().isEmpty() ||
            textfield_modifier_price.getText().isEmpty() ||
            date_picker_modifier.getValue() == null ||
            text_field_discription_modifier.getText().isEmpty() ||
            textfield_modifier_image.getText().isEmpty()) {
        showAlert("Please fill in all fields.");
        return;
    }

    try {
        int eventId = Integer.parseInt(textfield_modifier_id.getText());
        Serviceevenement sp = new Serviceevenement();
        List<Evenement> events = sp.affihcerevenement();
        boolean eventExists = false;

        for (Evenement i : events) {
            if (i.getId() == eventId) {
                eventExists = true;
                break;
            }
        }

        if (!eventExists) {
            showAlert("Event with ID " + eventId + " does not exist.");
            return;
        }
    } catch (NumberFormatException e) {
        showAlert("Event ID must be a valid integer.");
        return;
    }

    // Get the modified data from the input fields
    int eventId = Integer.parseInt(textfield_modifier_id.getText());
    String eventName = textfield_modifier_name.getText();
    double eventPrice = Double.parseDouble(textfield_modifier_price.getText());
    String eventDate = date_picker_modifier.getValue().toString();
    String eventDescription = text_field_discription_modifier.getText();
    String eventImage = textfield_modifier_image.getText();
    String eventPlace = textfield_modifier_place.getText();

    // Create a new Evenement instance with the modified data
    Evenement modifiedEvent = new Evenement(eventId, eventName, eventPrice, eventDate, eventDescription, eventImage,eventPlace);
    Serviceevenement sp = new Serviceevenement();
    sp.modifierevenement(modifiedEvent);

    showsucces("Event modified successfully.");
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
    
    private void showQRCodeStage() {
    JavaFX_QRCodeWriterController qrCodeWriterController = new JavaFX_QRCodeWriterController();
    qrCodeWriterController.start(new Stage());
    }
}


