package data;

import java.util.ArrayList;

public class UserInput {

    private static int minPrice = 0; // The lower bound of the price range selected by the user
    private static int maxPrice = 0; // The upper bound of the price range selected by the user
    private static int noOfNights = -1; // The minimum number nights the user would be staying for
    private static String priceRange = "default"; // The price range which the user has selected
    private static String aptType = "All"; // The type of apartment which the user selected
    private static String neighbourhood = ""; // Stores the name of the neighbourhood which the user has selected
    private static String propertyID = ""; // The ID of the property which the user has selected
    private static AirbnbListing userSelection = null; // The property which the user clicks on from the property list panel

    private static ArrayList<AirbnbListing> compareList = new ArrayList<>();

    private static void setMinPrice(int minPrice) {

        UserInput.minPrice = minPrice;
    }

    private static void setMaxPrice(int maxPrice) {

        UserInput.maxPrice = maxPrice;
    }

    public static void setNoOfNights(int noOfNights) {

        UserInput.noOfNights = noOfNights;
    }

    public static void setAptType(String aptType) {

        UserInput.aptType = aptType;
    }

    public static void setNeighbourhood(String neighbourhood) {

        UserInput.neighbourhood = neighbourhood;
    }

    public static int getMinPrice() {

        return minPrice;
    }

    public static int getMaxPrice() {

        return maxPrice;
    }

    public static int getNoOfNights() {

        return noOfNights;
    }

    public static String getAptType() {

        return aptType;
    }

    public static String getNeighbourhood() {

        return neighbourhood;
    }

    public static String getPriceRange(){
        return priceRange;
    }

    public static AirbnbListing getUserSelection() {
        return userSelection;
    }

    public static void setUserSelection(AirbnbListing userSelection) {
        UserInput.userSelection = userSelection;
    }

    public static ArrayList<AirbnbListing> getCompareList() {
        return compareList;
    }

    public static void addToCompareList(AirbnbListing newCompare) {
        for (AirbnbListing property : compareList){
            if(property.equals(newCompare))
                return;
        }
        compareList.add(newCompare);
    }

    /**
     * Gets the price range as a string from the user
     * and then initialises the max and the min prices accordingly
     * @param userPriceRange
     */
    public static void setPriceRange(String userPriceRange){
        priceRange = userPriceRange;
        switch(userPriceRange){
            case "Upto 25" :
                setMinPrice(0);
                setMaxPrice(25);
                break;
            case "25 - 50":
                setMinPrice(25);
                setMaxPrice(50);
                break;
            case "50 - 75":
                setMinPrice(50);
                setMaxPrice(75);
                break;
            case "75 - 100":
                setMinPrice(75);
                setMaxPrice(100);
                break;
            case "100 - 150":
                setMinPrice(100);
                setMaxPrice(150);
                break;
            case "Above 150":
                setMinPrice(150);
                setMaxPrice(-1);
                break;
            default: System.out.println("Invalid Input");
                break;
        }
    }
}
