package data;

import java.util.ArrayList;

public class AirbnbDataExtractor {
    private static ArrayList<AirbnbListing> listings;

    public AirbnbDataExtractor(){
        listings = new AirbnbDataLoader().load();
    }

    /**
     * Returns the list of all the properties in the neighbourhood after checking for the given conditions
     * @param neighbourhood the neighbourhood which the user has selectied
     * @param minPrice the minimum price of the property
     * @param maxPrice the maximum price of the property
     * @param noOfNights minimum number of nights
     * @param aptType the type of apartment it is
     * @return the list of all the properties satisfying the given conditions
     */
    public ArrayList<AirbnbListing> getNeighbourhoodListing(String neighbourhood, int minPrice, int maxPrice, int noOfNights, String aptType){
        ArrayList<AirbnbListing> list = new ArrayList<>();
        for(AirbnbListing listing: listings) {
            if (listing.getNeighbourhood().equals(neighbourhood) && listing.getPrice() >= minPrice && (listing.getPrice() < maxPrice || maxPrice == -1) &&
                    listing.getMinimumNights() >= noOfNights && (aptType.equalsIgnoreCase("All") || listing.getRoom_type().equalsIgnoreCase(aptType))){
                list.add(listing);
            }
        }
        return list;
    }

    /**
     * Returns the list of all the properties in all the neighbourhoods depending on the given conditions
     * @param minPrice the minimum price of the property
     * @param maxPrice the maximum price of the property
     * @param noOfNights the mimimum number of nights of stay at the property
     * @param aptType the type of apartment
     * @return the list of all the properties satisfying the given conditions
     */
    public ArrayList<AirbnbListing> getMapListing(int minPrice, int maxPrice, int noOfNights, String aptType){
        ArrayList<AirbnbListing> list = new ArrayList<>();
        for(AirbnbListing listing: listings) {
            if (listing.getPrice() >= minPrice && (listing.getPrice() < maxPrice || maxPrice == -1) && listing.getMinimumNights() >= noOfNights &&
                    (aptType.equalsIgnoreCase("All") || listing.getRoom_type().equalsIgnoreCase(aptType))){
                list.add(listing);
            }
        }
        return list;
    }
}
