package viewer;

import data.AirbnbListing;
import data.UserInput;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PropertyDescriptionCreator {

    /**
     * Creates the panel featuring details about the property
     * @throws IOException if PropertyDescriptionPanel.fxml isn't found
     */
    private void createPropertyDescriptionPanel() throws IOException {
        Parent description = FXMLLoader.load(getClass().getResource("PropertyDescriptionPanel.fxml"));
        Stage propertyDescriptionStage = new Stage();
        propertyDescriptionStage.setTitle("Property Description");
        propertyDescriptionStage.initModality(Modality.APPLICATION_MODAL);
        propertyDescriptionStage.setScene(new Scene(description));
        propertyDescriptionStage.setResizable(false);
        propertyDescriptionStage.show();
    }

    /**
     * Add a setOnMousePressed event for a TableView (tv) which when clicked will open a Property Description Panel for the selected Property
     * @param tv The TableView that the user will click an element of.
     */
    protected void propertyDescriptionMouseEvent(TableView<AirbnbListing> tv) {
        tv.setOnMousePressed((MouseEvent event) -> {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                UserInput.setUserSelection(tv.getSelectionModel().getSelectedItem());
                try {
                    new PropertyDescriptionCreator().createPropertyDescriptionPanel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
