package viewer;

import data.Statistics;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class StatisticsPanelController {

    // Label for the first statistics box on the panel
    @FXML private Label label1;

    // Label for the second statistics box on the panel
    @FXML private Label label2;

    // Label for the third statistics box on the panel
    @FXML private Label label3;

    // Label for the fourth statistics box on the panel
    @FXML private Label label4;

    // To store the 8 statistics which we have to display
    private String[] statistics = new String[8];
    // Tells us which one of the 8 statistics are being displayed, it is true for the statistics which are being displayed
    private boolean[] isDisplayed = new boolean[8];
    // Stores the index of the 4 statistics boxes on our statistics panel
    private int[] statBoxIndex = new int[4];

    private Statistics stats = new Statistics();

    /**
     * Action for the back button
     * Opens the Map Panel
     * @param event
     * @throws IOException
     */
    @FXML
    public void backButtonAction(ActionEvent event)throws IOException {
        Parent mapPanel = FXMLLoader.load(getClass().getResource("MapPanel.fxml"));
        Stage mapStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        mapStage.setScene(new Scene(mapPanel));
    }

    @FXML
    public void initialize(){
        for (int i = 0; i < 8; i++){
            isDisplayed[i] = false;
        }

        statistics[0] = "Priciest Neighbourhood: " + "\n" + "\n" + stats.getPriciestNeighbourhoodStat();
        statistics[1] = "Number of Entire Homes/Apartments: " + "\n" + "\n" + stats.getNumberOfEntireHomesApartments();
        statistics[2] = "Least Expensive Shared Room:" + "\n" + "\n" + stats.getLeastExpensiveSharedRoom();
        statistics[3] = "Least Expensive Private Room:" + "\n" + "\n" + stats.getLeastExpensivePrivateRoom();
        statistics[4] = "Most Expensive Entire Home/Apartment: " + "\n" + "\n" + stats.getMostExpensiveEntireHomes();
        statistics[5] = "Number of Available Properties:" + "\n" + "\n" + stats.getNumberOfAvailableProperties();
        statistics[6] = "Average Number of Reviews Per Property" + "\n" + "\n" + stats.getAverageNumberOfReviewsPerProperty();
        statistics[7] = "Closest Property to the Big Ben: " + "\n" + "\n" + stats.getClosestPropertyToBigBen();

        label1.setText(statistics[0]);
        statBoxIndex[0] = 0;
        isDisplayed[0] = true;
        label2.setText(statistics[1]);
        isDisplayed[1] = true;
        statBoxIndex[1] = 1;
        label3.setText(statistics[2]);
        isDisplayed[2] = true;
        statBoxIndex[2] = 2;
        label4.setText(statistics[3]);
        isDisplayed[3] = true;
        statBoxIndex[3] = 3;
    }

    /**
     * Actions for the next button for each statistics box
     * @param event
     */
    @FXML
    public void nextButtonAction(ActionEvent event){
        String source = event.getSource().toString();
        source = source.substring(source.indexOf('=') + 1, source.indexOf(','));

        switch (source){
            case "n1" : label1.setText(statistics[getNextStat(0, isDisplayed)]);
            break;
            case "n2" : label2.setText(statistics[getNextStat(1, isDisplayed)]);
            break;
            case "n3" : label3.setText(statistics[getNextStat(2, isDisplayed)]);
            break;
            case "n4" : label4.setText(statistics[getNextStat(3, isDisplayed)]);
            break;
        }
    }

    /**
     * Gets the index to the next statistic which isnt being displayed on any other
     * statistics box
     * @param statBoxNumber
     * @param isDisplayed
     * @return
     */
    public int getNextStat(int statBoxNumber, boolean[] isDisplayed){
        int statIndex = statBoxIndex[statBoxNumber];
        isDisplayed[statIndex] = false;
        statIndex = (statIndex + 1) % 8;
        while(isDisplayed[statIndex]){
            statIndex = (statIndex + 1) % 8;
        }
        statBoxIndex[statBoxNumber] = statIndex;
        isDisplayed[statIndex] = true;
        return statIndex;
    }

    /**
     * Action for the previous button on each one of the statistics boxes
     * @param event
     */
    @FXML
    public void previousButtonAction(ActionEvent event){
        String source = event.getSource().toString();
        source = source.substring(source.indexOf('=') + 1, source.indexOf(','));

        switch (source){
            case "p1" : label1.setText(statistics[getPrevStat(0, isDisplayed)]);
                break;
            case "p2" : label2.setText(statistics[getPrevStat(1, isDisplayed)]);
                break;
            case "p3" : label3.setText(statistics[getPrevStat(2, isDisplayed)]);
                break;
            case "p4" : label4.setText(statistics[getPrevStat(3, isDisplayed)]);
                break;
        }
    }


    /**
     * Returns the index of the statistic which is previous to the current one
     * and which is not being displayed
     * @param statBoxNumber
     * @param isDisplayed
     * @return
     */
    public int getPrevStat(int statBoxNumber, boolean[] isDisplayed){
        int statIndex = statBoxIndex[statBoxNumber];
        isDisplayed[statIndex] = false;

        if (statIndex == 0)
            statIndex = 7;
        else
            --statIndex;

        while(isDisplayed[statIndex]){
            if (statIndex == 0)
                statIndex = 7;
            else
                --statIndex;
        }
        statBoxIndex[statBoxNumber] = statIndex;
        isDisplayed[statIndex] = true;
        return statIndex;
    }

    /**
     * Goes to the next Panel in the app
     * Loads the Compare Panel
     * @param event
     * @throws IOException
     */
    @FXML
    public void compareButtonAction(ActionEvent event) throws IOException {
        Parent comparePanel = FXMLLoader.load(getClass().getResource("ComparePanel.fxml"));
        Stage compareStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        compareStage.setScene(new Scene(comparePanel));
    }
}
