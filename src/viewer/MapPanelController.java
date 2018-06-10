package viewer;

import data.AirbnbDataExtractor;
import data.AirbnbListing;
import data.Borough;
import data.UserInput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class MapPanelController {

    @FXML private Label noOfNightsLabel;
    @FXML private Label aptTypeLabel;
    @FXML private Label priceRangeLabel;

    @FXML
    ImageView barkingAndDagenham, barnet, bexley, brent, bromley, camden, city, croydon, ealing, enfield, greenwich, hackney, hammersmithAndFullham,
            haringey, harrow, havering, hillingdon, hounslow, islington, kensingtonAndChelsea, kingstonUponThames, lambeth, lewisham, merton, newham,
            redbridge, richmondUponThames, southwark, sutton, towerHamlets, walthamForest, wandsworth, westminster;

    @FXML
    public void initialize(){
        ArrayList<ImageView> neighbourhoods;
        ArrayList<Borough> boroughs = new ArrayList<>();

        noOfNightsLabel.setText(UserInput.getNoOfNights() + "");
        aptTypeLabel.setText(UserInput.getAptType());
        priceRangeLabel.setText(UserInput.getPriceRange());

        neighbourhoods = new ArrayList<>(Arrays.asList(barkingAndDagenham, barnet, bexley, brent, bromley, camden, city, croydon, ealing, enfield, greenwich, hackney, hammersmithAndFullham,
                haringey, harrow, havering, hillingdon, hounslow, islington, kensingtonAndChelsea, kingstonUponThames, lambeth, lewisham, merton, newham,
                redbridge, richmondUponThames, southwark, sutton, towerHamlets, walthamForest, wandsworth, westminster));
        // Set houses to all the same size
        int iconSize = 20;
        final int baseSize = iconSize;
        neighbourhoods.forEach(i -> setSize(i, baseSize, baseSize));
        // Get variable names of all ImageView fields and create a new Borough object from them
        for(Field f: this.getClass().getDeclaredFields())
        {
            try {
                if (f.getType().equals(ImageView.class))
                    boroughs.add(new Borough((ImageView) f.get(this), f.getName()));
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        // Get the data set that the user has selected
        AirbnbDataExtractor ext = new AirbnbDataExtractor();
        ArrayList<AirbnbListing> listings = ext.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), UserInput.getAptType());
        //System.out.println(listings.size() + " number of filtered listings");
        // For each listings, get its borough and increment that boroughs count to determine the number of listings for each borough
        for(AirbnbListing listing: listings)
        {
            String boroughName = listing.getNeighbourhood().replaceAll("\\s+","");
            boroughs.stream().filter(i -> i.getShortName().equalsIgnoreCase(boroughName)).forEach(Borough::increaseCount);
        }
        // Sort the boroughs by number of listings (smallest number first in array)
        boroughs.sort(Comparator.comparing(Borough::getCount));
        // Adjust icon size depending on where the borough is in the sorted list
        for(Borough b : boroughs)
        {
            // Do not display borough icon if there are no listings for it
            if(b.getCount() == 0)
                b.getImageView().setVisible(false);
            else
            {
                setSize(b.getImageView(), iconSize, iconSize);
                iconSize+=2;
            }
        }

        for (ImageView neighbourhood : neighbourhoods){
            neighbourhood.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                try {
                    displayPropertyList(event);
                }catch (IOException e)
                {
                    e.printStackTrace();
                }finally {
                    event.consume();
                }
            });
            neighbourhood.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
                neighbourhood.setOpacity(0.95);
                event.consume();
            });
            neighbourhood.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                neighbourhood.setOpacity(0.5);
                event.consume();
            });
        }
    }

    /**
     * Updates the size of a specified ImageView with the new width and height
     * Move the image view according to new dimensions as well to attempt to keep image centred
     * @param iv ImageView that you want to change the size of
     * @param width the new width of the image
     * @param height the new height of the image
     */
    private void setSize(ImageView iv, double width, double height)
    {
        // Calculate the difference between the new and old size
        double difHeight = height-iv.getFitHeight();
        double difWidth = width-iv.getFitWidth();
        // Get the existing location
        double x = iv.getX();
        double y = iv.getY();
        // Move to new location based off new size
        x = x-(difWidth/2);
        y = y-(difHeight/2);
        // Update size and location
        iv.setX(x);
        iv.setY(y);
        iv.setFitWidth(width);
        iv.setFitHeight(height);
    }

    @FXML
    public void backButtonAction(ActionEvent event)throws IOException{
        Parent mapPanel = FXMLLoader.load(getClass().getResource("WelcomePanel.fxml"));
        Stage mapStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mapStage.setScene(new Scene(mapPanel));
    }

    @FXML
    public void statisticsButtonAction(ActionEvent event) throws IOException{
        Parent mapPanel = FXMLLoader.load(getClass().getResource("StatisticsPanel.fxml"));
        Stage mapStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mapStage.setScene(new Scene(mapPanel));
    }

    /**
     * Displays the properties in the a particular neighbourhood
     * @param event mouse event that has taken place
     * @throws IOException thrown if FXML is unavailable to load
     */
    @FXML
    private void displayPropertyList(MouseEvent event) throws IOException{
        String name = getName(event.getSource().toString());
        UserInput.setNeighbourhood(name);
        Stage propertyListStage = new Stage();
        Parent propertyListScene = FXMLLoader.load(getClass().getResource("PropertyListViewer.fxml"));
        propertyListStage.setScene(new Scene(propertyListScene));
        propertyListStage.setTitle(name);
        propertyListStage.initModality(Modality.APPLICATION_MODAL);
        propertyListStage.show();
    }

    /**
     * Returns the name of the neighbourhood from which the image was clicked     *
     * @param source the name of the object of the image
     * @return the name of the nieghbourhood
     */
    private String getName(String source){
        switch(source.substring(source.indexOf('=') + 1, source.indexOf(','))){
            case "barkingAndDagenham" : return "Barking and Dagenham";
            case "barnet" : return "Barnet";
            case "bexley" : return "Bexley";
            case "brent" : return "Brent";
            case "bromley" : return "Bromley";
            case "camden" : return "Camden";
            case "city" : return "City";
            case "croydon" : return "Croydon";
            case "ealing" : return "Ealing";
            case "enfield" : return "Enfield";
            case "greenwich" : return "Greenwich";
            case "hackney" : return "Hackney";
            case "hammersmithAndFullham" : return "Hammersmith and Fulham";
            case "haringey" : return "Haringey";
            case "harrow" : return "Harrow";
            case "havering" : return "Havering";
            case "hillingdon" : return "Hillingdon";
            case "hounslow" : return "Hounslow";
            case "islington" : return "Islington";
            case "kensingtonAndChelsea" : return "Kensington and Chelsea";
            case "kingstonUponThames" : return "Kingston upon Thames";
            case "lambeth" : return "Lambeth";
            case "lewisham" : return "Lewisham";
            case "merton" : return "Merton";
            case "newham" :  return "Newham";
            case "redbridge" : return "Redbridge";
            case "richmondUponThames" : return "Richmond upon Thames";
            case "southwark" : return "Southwark";
            case "sutton" : return "Sutton";
            case "towerHamlets" : return "Tower Hamlets";
            case "walthamForest" : return "Waltham Forest";
            case "wandsworth" : return "Wandsworth";
            case "westminster" : return "Westminster";
            default: return "";
        }
    }
}
