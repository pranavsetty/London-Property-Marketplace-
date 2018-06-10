package viewer;

import data.AirbnbDataExtractor;
import data.AirbnbListing;
import data.UserInput;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PropertyListViewerController implements Initializable {

    // List of the options available in the choice box for sorting the results
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("By Name: A-Z","By Name: Z-A",
            "Host Name: A-Z", "Host Name: Z-A", "Price: Low to High", "Price: High to Low", "No Of Nights: High to Low",
            "No of Nights: Low to High", "No of Reviews: Low to High", "No of Reviews: High to Low", "Room Type");
    private ArrayList<AirbnbListing> propertyArrayList;
    private ObservableList<AirbnbListing> propertyList;
    private SortedList<AirbnbListing> sortedList;

    AirbnbDataExtractor extractor = new AirbnbDataExtractor();

    @FXML private TableView<AirbnbListing> table;

    @FXML private ChoiceBox choiceBox;

    @FXML private TextField searchField;

    /**
     * Initialises the components for the Property List Panel
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        choiceBox.setItems(choiceBoxList);

        // Creating columns for the table view
        TableColumn name = new TableColumn("Name");
        TableColumn hostName = new TableColumn("Host Name");
        TableColumn price = new TableColumn("Price");
        TableColumn noOfNights = new TableColumn("Minimum Nights");
        TableColumn aptType = new TableColumn("Apt Type");
        TableColumn noOfReviews = new TableColumn("No of Reviews");
        name.setPrefWidth(300);
        hostName.setPrefWidth(70);
        price.setPrefWidth(50);
        noOfNights.setPrefWidth(100);
        aptType.setPrefWidth(75);
        noOfReviews.setPrefWidth(75);

        // Adding the columns to the table view
        table.getColumns().addAll(name, hostName, aptType, price, noOfNights, noOfReviews);

        // Gets the list of the properties which needs to be displayed
        propertyArrayList = extractor.getNeighbourhoodListing(UserInput.getNeighbourhood(),UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), UserInput.getAptType());
        propertyList = FXCollections.observableArrayList(propertyArrayList);

        name.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("name"));
        hostName.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("host_name"));
        price.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("price"));
        noOfNights.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("minimumNights"));
        aptType.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("room_type"));
        noOfReviews.setCellValueFactory(new PropertyValueFactory<AirbnbListing, String>("numberOfReviews"));

        new PropertyDescriptionCreator().propertyDescriptionMouseEvent(table);

        table.setItems(createSortedList(new FilteredList<>(propertyList, p -> true)));
    }

    /**
     * Sorted the list in specified order
     * @param event
     */
    @FXML
    public void sortingAction(ActionEvent event){
        ObservableList<AirbnbListing> tempList = FXCollections.observableArrayList(sortedList);
        if(choiceBox.getValue() != null){
            switch(choiceBox.getValue().toString()){
                // Sorts the list in an alphabetical order of the property names
                case "By Name: A-Z": Collections.sort(tempList, Comparator.comparing(AirbnbListing::getName));
                    break;
                // Sorts the list in a reverse alphabetical order of the property names
                case "By Name: Z-A" : {
                    Collections.sort(tempList, Comparator.comparing(AirbnbListing::getName));
                    Collections.reverse(tempList);
                } break;
                // Sorts the list in ascending order of prices
                case "Price: Low to High": Collections.sort(tempList, Comparator.comparing(AirbnbListing::getPrice));
                    break;
                // Sorts the list in descending order of prices
                case "Price: High to Low" : {
                    Collections.sort(tempList, Comparator.comparing(AirbnbListing::getPrice));
                    Collections.reverse(tempList);
                } break;
                // Sorts the list in an ascending order of minimum number of nights
                case "No of Nights: Low to High": Collections.sort(tempList, Comparator.comparing(AirbnbListing::getMinimumNights));
                    break;
                // Sorts the list in descending order of minimum number of nights
                case "No Of Nights: High to Low" : {
                    Collections.sort(tempList, Comparator.comparing(AirbnbListing::getMinimumNights));
                    Collections.reverse(tempList);
                } break;
                // Sorts the list according to room types
                case "Room Type": Collections.sort(tempList, Comparator.comparing(AirbnbListing::getRoom_type));
                    break;
                // Sorts the list in alphabetical order of the host name
                case "Host Name: A-Z" : Collections.sort(tempList, Comparator.comparing(AirbnbListing::getHost_name));
                break;
                // Sorts the list in reverse alphabetical order of the host name
                case "Host Name: Z-A" : {
                    Collections.sort(tempList, Comparator.comparing(AirbnbListing::getHost_name));
                    Collections.reverse(tempList);
                } break;
                // Sorts the list in ascending order of number of reviews
                case "No of Reviews: Low to High" : tempList.sort(Comparator.comparing(AirbnbListing::getNumberOfReviews));
                break;
                // Sorts the list in descending order of number of reviews
                case "No of Reviews: High to Low" : {
                    tempList.sort(Comparator.comparing(AirbnbListing::getNumberOfReviews));
                    Collections.reverse(tempList);
                } break;
                default: System.out.println("Error, Invalid Input");
            }
        }
        table.setItems(createSortedList(new FilteredList<>(tempList, p -> true)));
    }

    /**
     * Creates a sorted list from the given filtered list
     * It allows users to search for strings contained in the name of the properties
     * @param filteredList
     * @return
     */
    private SortedList<AirbnbListing> createSortedList(FilteredList<AirbnbListing> filteredList){
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(AirbnbListing -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare name and room type of the property
                String lowerCaseFilter = newValue.toLowerCase();
                if (String.valueOf(AirbnbListing.getName()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                    // Filter matches name of the property
                } else if (String.valueOf(AirbnbListing.getRoom_type()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches the room type of the property
                }
                return false; // Does not match to either one of them
            });
        });
        sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        return sortedList;
    }

    /**
     * Refreshes the results in the table view
     * @param event
     */
    @FXML
    public void refreshButtonAction(ActionEvent event) {
        choiceBox.getSelectionModel().clearSelection();
        searchField.clear();
        table.setItems(createSortedList(new FilteredList<>(propertyList, p -> true)));
        event.consume();
    }
}
