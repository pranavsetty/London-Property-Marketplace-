package viewer;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import data.AirbnbListing;
import data.UserInput;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import netscape.javascript.JSObject;

public class PropertyDescriptionController {

    @FXML private Label propertyName;
    @FXML private Label hostName;
    @FXML private Label price;
    @FXML private Label noOfNights;
    @FXML private Label aptType;
    @FXML private Label neighbourhood;
    @FXML private GoogleMapView googleMapView;

    private AirbnbListing currentProperty;
    private GoogleMap map;

    /**
     * Initialises the components for the Property Description Panel
     */
    @FXML
    public void initialize(){
        currentProperty = UserInput.getUserSelection();
        googleMapView.addMapInializedListener(this::configureMap);
        propertyName.setText(propertyName.getText() + "    " + currentProperty.getName());
        hostName.setText(hostName.getText() + "    " + currentProperty.getHost_name());
        price.setText(price.getText() + "    ₤" + currentProperty.getPrice());
        noOfNights.setText(noOfNights.getText() + "    " + currentProperty.getMinimumNights());
        aptType.setText(aptType.getText() + "    " + currentProperty.getRoom_type());
        neighbourhood.setText(neighbourhood.getText() + "    " + currentProperty.getNeighbourhood());
    }

    /**
     * Add the action for the button
     * Once a user clicks on the button, the property should be added to the compare panel
     */
    public void compareButtonAction() {
        UserInput.addToCompareList(UserInput.getUserSelection());
    }

    /**
     * Configures the map on the property description panel.
     * It places a marker on the location of the property based on the longitude and latitude of the property
     */
    private void configureMap() {
        MapOptions mapOptions = new MapOptions();
        LatLong location = new LatLong(currentProperty.getLatitude(), currentProperty.getLongitude());
        mapOptions.center(location)
                .mapType(MapTypeIdEnum.ROADMAP)
                .zoom(14)
                .streetViewControl(false);
        mapOptions.getJSObject().setMember("fullscreenControl", false);
        map = googleMapView.createMap(mapOptions, false);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(location);
        markerOptions.title(currentProperty.getName());
        Marker property = new Marker(markerOptions);
        map.addMarker(property);
        InfoWindowOptions iwo = new InfoWindowOptions();
        iwo.content("Name: " + currentProperty.getName() + "<br />" +
                    "Borough: " + currentProperty.getNeighbourhood() + "<br />" +
                    "Price: £" + currentProperty.getPrice() + "<br />" +
                    "Room Type: " + currentProperty.getRoom_type() + "<br />" +
                    "Last review: " + currentProperty.getLastReview() + "<br />" +
                    "Number of reviews: " + currentProperty.getNumberOfReviews() + "<br />" +
                    "Reviews per month: " + currentProperty.getReviewsPerMonth() + "<br />" +
                    "Minimum number of nights: " + currentProperty.getMinimumNights()
        );
        InfoWindow iw = new InfoWindow(iwo);
        map.addUIEventHandler(property, UIEventType.click, (JSObject obj) -> iw.open(map, property));
    }
}
