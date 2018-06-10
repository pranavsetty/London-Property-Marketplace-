package viewer;

import data.AirbnbListing;
import data.UserInput;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ComparePanelController {

    @FXML private TableView<AirbnbListing> compareTable;

    /**
     * Initialises the components for the Compare Panel
     * It adds the columns to the table and adds data to those columns as well
     */
    public void initialize(){
        // Creating the columns for the table and adding data to them
        TableColumn name = new TableColumn("Name");
        TableColumn hostName = new TableColumn("Host Name");
        TableColumn price = new TableColumn("Price");
        TableColumn neighbouthood = new TableColumn("Neighbourhood");
        TableColumn noOfNights = new TableColumn("Minimum Nights");
        TableColumn aptType = new TableColumn("Apt Type");
        name.setPrefWidth(300);
        hostName.setPrefWidth(70);
        neighbouthood.setPrefWidth(100);
        price.setPrefWidth(50);
        noOfNights.setPrefWidth(100);
        aptType.setPrefWidth(75);

        // This column contains a button which deletes the particular row from the list
        TableColumn<AirbnbListing, AirbnbListing> removeColumn = new TableColumn<>("");
        removeColumn.setCellValueFactory(
                param -> new ReadOnlyObjectWrapper<>(param.getValue())
        );
        removeColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Remove");

            @Override
            protected void updateItem(AirbnbListing property, boolean empty) {
                super.updateItem(property, empty);

                if (property == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnMouseClicked(
                        event -> getTableView().getItems().remove(property)
                );
            }
        });
        removeColumn.setPrefWidth(75);

        compareTable.getColumns().addAll(name, hostName, neighbouthood , price, aptType, noOfNights, removeColumn);

        ObservableList<AirbnbListing> compareList = FXCollections.observableArrayList(UserInput.getCompareList());

        name.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("name"));
        hostName.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("host_name"));
        neighbouthood.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("neighbourhood"));
        price.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("price"));
        noOfNights.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("minimumNights"));
        aptType.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("room_type"));

        new PropertyDescriptionCreator().propertyDescriptionMouseEvent(compareTable);

        compareTable.setItems(compareList);
    }

    /**
     * The action for the back button. It goes back to the Statistics panel
     * @param event ActionEvent from pressing the 'back' button
     * @throws IOException if StatisticsPanel.fxml isn't found
     */
    @FXML
    public void backButtonAction (ActionEvent event) throws IOException {
        Parent mapPanel = FXMLLoader.load(getClass().getResource("StatisticsPanel.fxml"));
        Stage mapStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mapStage.setScene(new Scene(mapPanel));
    }

}
