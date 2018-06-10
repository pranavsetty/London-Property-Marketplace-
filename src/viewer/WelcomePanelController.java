package viewer;

import data.UserInput;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WelcomePanelController {

    // Allows the user to select an room type
    @FXML private ChoiceBox aptType;

    // Allows the user to select a price range for the properties
    @FXML private ChoiceBox priceRange;

    // Goes to the next panel
    @FXML private Button goButton;

    // Allows the user to enter the minimum number of nights
    @FXML private TextField noOfNightsInput;

    // Shows information about the application
    @FXML private Button infoButton;

    /**
     * Creates the Welcome Panel using the WelcomePanel.fxml
     */
    @FXML
    public void createWelcomePanel(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("WelcomePanel.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);   // So that the window cannot be resized
        primaryStage.show();
    }

    /**
     * Initialises the components for the Welcome Panel
     * @throws FileNotFoundException
     */
    @FXML
    private void initialize() throws FileNotFoundException {
        noOfNightsInput.setTooltip(new Tooltip("Minimum Number of Nights for Stay"));

        aptType.setItems(FXCollections.observableArrayList(
                "All", new Separator(), "Private Room", "Entire Home/Apt", "Shared Room")
        );

        aptType.setTooltip(new Tooltip("Select the type of apartment"));

        priceRange.setItems(FXCollections.observableArrayList(
                "Upto 25", "25 - 50", "50 - 75", "75 - 100", "100 - 150", "Above 150")
        );
        priceRange.setTooltip(new Tooltip("Select the per night price range for the properties"));

        ImageView infoImage = new ImageView(new Image(new FileInputStream("res/infoButton.png")));
        infoImage.setFitWidth(30);
        infoImage.setFitHeight(30);
        infoButton.setGraphic(infoImage);
        infoButton.setTooltip(new Tooltip("About the App"));

        goButton.setTooltip(new Tooltip("Preview the results on a map"));
        goButton.setDisable(true);

        // Setting the default values for the inputs so that if the user has already selected items and
        // reopens the panel, the inputs have the user selections as the selected items
        if(!UserInput.getPriceRange().equals("default"))
            priceRange.setValue(UserInput.getPriceRange());
        if(UserInput.getNoOfNights() != -1)
            noOfNightsInput.setText(UserInput.getNoOfNights() + "");
        aptType.setValue(UserInput.getAptType());
    }

    /**
     * The action which takes place when the "GO Button is pressed"
     * @param event ActionEvent resulting from pressing the 'go' button
     * @throws IOException if AlertBox.fxml isn't found
     */
    @FXML
    public void goButtonAction(ActionEvent event) throws IOException{
        if(isValidInt(noOfNightsInput.getText())){
            if(!noOfNightsInput.getText().isEmpty())
                UserInput.setNoOfNights(Integer.parseInt(noOfNightsInput.getText()));
            else
                UserInput.setNoOfNights(1);
            UserInput.setPriceRange(priceRange.getValue().toString());
            UserInput.setAptType(aptType.getValue().toString());

            Parent mapPanel = FXMLLoader.load(getClass().getResource("MapPanel.fxml"));
            Stage mapStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            mapStage.setScene(new Scene(mapPanel));

        } else {
            createAlertBox();
            noOfNightsInput.clear();
        }
    }

    /**
     * Creates the info box when the user clicks on the info button
     * @throws IOException if InfoBox.fxml isn't found
     */
    @FXML
    public void infoButtonAction() throws IOException{
        createInfoBox();
    }

    /**
     * As soon as the user selects a price range the GO button is enabled
     */
    @FXML
    public void priceRangeSelectAction(){
        goButton.setDisable(false);
    }

    /**
     * Checks whether the user input is a valid integer input
     *
     * @param input, the user input
     * @return true if the input is a valid integer input
     */
    private boolean isValidInt(String input){
        // Since the selection is optional, the input is valid if the user has not made one
        if(input.isEmpty())
            return true;

        try {
            int number = Integer.parseInt(input);
            return (number >= 0);
        } catch (NumberFormatException e){
            return false;
        }
    }

    /**
     * Creates and displays the alert box
     * @throws IOException if AlertBox.fxml isn't found
     */
    private void createAlertBox() throws IOException{
        Parent alert = FXMLLoader.load(getClass().getResource("AlertBox.fxml"));
        Stage alertBox = new Stage();
        alertBox.setTitle("Error!");
        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setScene(new Scene(alert));
        alertBox.setResizable(false);
        alertBox.show();
    }

    /**
     * Creates and displays the info box
     * @throws IOException if InfoBox.fxml isn't found
     */
    private void createInfoBox() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("InfoBox.fxml"));
        Stage infoBox = new Stage();
        infoBox.setScene(new Scene(root));
        infoBox.initModality(Modality.APPLICATION_MODAL);
        infoBox.setTitle("About");
        infoBox.setResizable(false);   // So that the window cannot be resized
        infoBox.show();
    }
}
