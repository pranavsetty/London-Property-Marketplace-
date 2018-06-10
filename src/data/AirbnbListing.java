package data;

import javafx.beans.property.SimpleStringProperty;

import javax.lang.model.util.SimpleElementVisitor6;

/**
 * Represents one listing of a property for rental on Airbnb.
 * This is essentially one row in the data table. Each column
 * has a corresponding field.
 */

public class AirbnbListing {
    /**
     * The id and name of the individual property
     */
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    /**
     * The id and name of the host for this listing.
     * Each listing has only one host, but one host may
     * list many properties.
     */
    private SimpleStringProperty host_id;
    private SimpleStringProperty host_name;

    /**
     * The grouped location to where the listed property is situated.
     * For this data set, it is a london borough.
     */
    private SimpleStringProperty neighbourhood;

    /**
     * The location on a map where the property is situated.
     */
    private SimpleStringProperty latitude;
    private SimpleStringProperty longitude;

    /**
     * The type of property, either "Private room" or "Entire Home/apt".
     */
    private SimpleStringProperty room_type;

    /**
     * The price per night's stay
     */
    private SimpleStringProperty price;

    /**
     * The minimum number of nights the listed property must be booked for.
     */
    private SimpleStringProperty minimumNights;
    private SimpleStringProperty numberOfReviews;

    /**
     * The date of the last review, but as a String
     */
    private SimpleStringProperty lastReview;
    private SimpleStringProperty reviewsPerMonth;

    /**
     * The total number of listings the host holds across AirBnB
     */
    private SimpleStringProperty calculatedHostListingsCount;
    /**
     * The total number of days in the year that the property is available for
     */
    private SimpleStringProperty availability365;

    public AirbnbListing(String id, String name, String host_id,
                         String host_name, String neighbourhood, double latitude,
                         double longitude, String room_type, int price,
                         int minimumNights, int numberOfReviews, String lastReview,
                         double reviewsPerMonth, int calculatedHostListingsCount, int availability365) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.host_id = new SimpleStringProperty(host_id);
        this.host_name = new SimpleStringProperty(host_name);
        this.neighbourhood = new SimpleStringProperty(neighbourhood);
        this.latitude = new SimpleStringProperty("" + latitude);
        this.longitude = new SimpleStringProperty("" + longitude);
        this.room_type = new SimpleStringProperty(room_type);
        this.price = new SimpleStringProperty("" + price);
        this.minimumNights = new SimpleStringProperty("" + minimumNights);
        this.numberOfReviews = new SimpleStringProperty("" + numberOfReviews);
        this.lastReview = new SimpleStringProperty(lastReview);
        this.reviewsPerMonth = new SimpleStringProperty("" + reviewsPerMonth);
        this.calculatedHostListingsCount = new SimpleStringProperty("" + calculatedHostListingsCount);
        this.availability365 = new SimpleStringProperty("" + availability365);
    }

    public String getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }

    public String getHost_id() {
        return host_id.get();
    }

    public String getHost_name() {
        return host_name.get();
    }

    public String getNeighbourhood() {
        return neighbourhood.get();
    }

    public double getLatitude() {
        return Double.parseDouble(latitude.get());
    }

    public double getLongitude() {
        return Double.parseDouble(longitude.get());
    }

    public String getRoom_type() {
        return room_type.get();
    }

    public int getPrice() {
        return Integer.parseInt(price.get());
    }

    public int getMinimumNights() {
        return Integer.parseInt(minimumNights.get());
    }

    public int getNumberOfReviews() {
        return Integer.parseInt(numberOfReviews.get());
    }

    public String getLastReview() {
        return lastReview.get();
    }

    public double getReviewsPerMonth() {
        return Double.parseDouble(reviewsPerMonth.get());
    }

    public int getCalculatedHostListingsCount() {
        return Integer.parseInt(calculatedHostListingsCount.get());
    }

    public int getAvailability365() {
        return Integer.parseInt(availability365.get());
    }

    @Override
    public String toString() {
        return "data.AirbnbListing{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", host_id='" + host_id + '\'' +
                ", host_name='" + host_name + '\'' +
                ", neighbourhood='" + neighbourhood + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", room_type='" + room_type + '\'' +
                ", price=" + price +
                ", minimumNights=" + minimumNights +
                ", numberOfReviews=" + numberOfReviews +
                ", lastReview='" + lastReview + '\'' +
                ", reviewsPerMonth=" + reviewsPerMonth +
                ", calculatedHostListingsCount=" + calculatedHostListingsCount +
                ", availability365=" + availability365 +
                '}';
    }
}
