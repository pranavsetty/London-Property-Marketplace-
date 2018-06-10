package data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.OptionalDouble;

/**
 * Class for calculating property based statistics
 */
public class Statistics {

    private AirbnbDataExtractor extractor = new AirbnbDataExtractor();

    private String[][] neighbouthoodPrice = {new String[]{"Barking and Dagenham", "0", "0"}, new String[]{"Barnet", "0", "0"}, new String[]{"Bexley", "0", "0"}, new String[]{"Brent", "0", "0"},
            new String[]{"Bromley", "0", "0"}, new String[]{"Camden", "0", "0"}, new String[]{"City", "0", "0"}, new String[]{"Croydon", "0", "0"}, new String[]{"Ealing", "0", "0"},
            new String[]{"Enfield", "0", "0"}, new String[]{"Greenwich", "0", "0"}, new String[]{"Hackney", "0", "0"}, new String[]{"Hammersmith and Fulham", "0", "0"}, new String[]{"Haringey", "0", "0"},
            new String[]{"Harrow", "0", "0"}, new String[]{"Havering", "0", "0"}, new String[]{"Hillingdon", "0", "0"},new String[]{"Hounslow", "0", "0"}, new String[]{"Islington", "0", "0"},
            new String[]{"Kensington and Chelsea", "0", "0"}, new String[]{"Kingston upon Thames", "0", "0"}, new String[]{"Lambeth", "0", "0"}, new String[]{"Lewisham", "0", "0"}, new String[]{"Merton", "0", "0"},
            new String[]{"Newham", "0", "0"}, new String[]{"Redbridge", "0", "0"}, new String[]{"Richmond upon Thames", "0", "0"}, new String[]{"Southwark", "0", "0"}, new String[]{"Sutton", "0", "0"},
            new String[]{"Tower Hamlets", "0", "0"}, new String[]{"Waltham Forest", "0", "0"}, new String[]{"Wandsworth", "0", "0"}, new String[]{"Westminster", "0", "0"}};

    /**
     * Statistic made by Akshat Sood (1713850)
     * Checks the properties in all the neighbourhoods satisfying the given conditions to calculate the
     * priciest neighbourhood
     * @return the name of the priciest neighbourhood
     */
    public String getPriciestNeighbourhoodStat() {
        ArrayList<AirbnbListing> properties = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "All");

        double highestAvgPrice = 0.0;
        String priciestNeighbourhood = "";

        for (AirbnbListing property : properties){
            for (int i = 0; i < 33; i++){
                if (property.getNeighbourhood().equals(neighbouthoodPrice[i][0])){
                    neighbouthoodPrice[i][1] = Integer.parseInt(neighbouthoodPrice[i][1]) + property.getPrice() * property.getMinimumNights() + "";
                    neighbouthoodPrice[i][2] = Integer.parseInt(neighbouthoodPrice[i][2]) + 1 + "";
                }
            }
        }
        for (int i = 0; i < 33; i++){
            double temp = Double.parseDouble(neighbouthoodPrice[i][1]);
            temp /= Integer.parseInt(neighbouthoodPrice[i][2]);
            if (temp >= highestAvgPrice){
                highestAvgPrice = temp;
                priciestNeighbourhood = neighbouthoodPrice[i][0];
            }
        }
        return priciestNeighbourhood;
    }

    /**
     * Checks for every single house in all the neigbourhoods which is a Entire Home/Apt
     * @return the number of Entire Home/Apt type properties in all the neighbourhoods
     */
    public int getNumberOfEntireHomesApartments() {
        return extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "Entire home/apt").size();
    }

    /**
     * Checks for the least expensive shared room in all of the neighbourhoods
     * @return the name of the least expensive properties in all of the nieghbourhoods
     */
    public String getLeastExpensiveSharedRoom() {
        ArrayList<AirbnbListing> properties = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "Shared room");
        return properties.stream().min(Comparator.comparing(AirbnbListing::getPrice)).get().getName();
    }

    /**
     * Checks for the least expensive private room in all of the neighbourhoods
     * @return the name of the least expensive properties in all of the nieghbourhoods
     * Method made by Pranav Bheemsetty (k1763590
     */
    public String getLeastExpensivePrivateRoom() {
        ArrayList<AirbnbListing> properties = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "Private room");
        return properties.stream().min(Comparator.comparing(AirbnbListing::getPrice)).get().getName();
    }

    /**
     * Get the most expensive Entire Home / Apartment
     * @return the total number of properties available depending on the user specifications
     * Method made by Pranav Bheemsetty (k1763590)
     */

    public String getMostExpensiveEntireHomes() {
        ArrayList<AirbnbListing> properties = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "Entire Home/Apt");
        String highestPricedHome = "No Homes/Apt Available";
        int highestPrice = 0;
        for (AirbnbListing property : properties) {
            if (property.getPrice() > highestPrice) {
                highestPricedHome = property.getName();
            }
        }
        return highestPricedHome;
    }

    /**
     * Calculates the number of available properties depending on the users specifications
     * @return the total number of properties available depending on the user specifications
     */

    public int getNumberOfAvailableProperties() {
        ArrayList<AirbnbListing> properties = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "All");
        return properties.size();
    }

    /**
     * Returns the average number of reviews per property (interpreted to mean average number of reviews in a given list of properties)
     * Method made by Spenser Smart (K1763933)
     * @return The sum of all 'numberOfReviews' divided by the number of listings (Double), if error it returns -1
     */
    public double getAverageNumberOfReviewsPerProperty() {

        // Get all listings for the min price, max price, and number of nights (with room type as any)
        ArrayList<AirbnbListing> listings = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "All");
        // Gets the stream of the listings and maps all 'numberOfReviews' then gets the average of them
        OptionalDouble result = listings.stream().mapToInt(AirbnbListing::getNumberOfReviews).average();
        // If result is present return the double, else return -1
        if (result.isPresent())
            return result.getAsDouble();
        else {
            System.err.println("Unable to return average as double, returning -1");
            return -1;
        }
    }

    /**
     * Statistic made by Akshat Sood (1713850)
     * Returns the closest property to Big Ben
     * @return name The name of the property close to Big Ben
     */
    public String getClosestPropertyToBigBen() {
        ArrayList<AirbnbListing> listings = extractor.getMapListing(UserInput.getMinPrice(), UserInput.getMaxPrice(), UserInput.getNoOfNights(), "All");
        double latBB = 51.500729;
        double longBB = -0.124625;
        double bestDistance = -1;
        AirbnbListing bestProperty = null;

        for (AirbnbListing property : listings) {

            double distance = distance(latBB, longBB, property.getLatitude(), property.getLongitude());
            if (bestDistance == -1 || bestDistance > distance) {
                bestDistance = distance;
                bestProperty = property;
            }
        }
        if(bestProperty != null)
            return "Name: " + bestProperty.getName() + "\n" + "Neighbourhood: " + bestProperty.getNeighbourhood();
        else
            return "Error";
    }

    private double distance(double lat1, double lon1, double lat2, double lon2)
    {
        double R = 6371e3; // metres
        double φ1 = Math.toRadians(lat1);
        double φ2 = Math.toRadians(lat2);
        double Δφ = Math.toRadians(lat2-lat1);
        double Δλ = Math.toRadians(lon2-lon1);

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                        Math.sin(Δλ/2) * Math.sin(Δλ/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }

}
