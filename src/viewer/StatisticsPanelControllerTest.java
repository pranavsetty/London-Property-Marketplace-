package viewer;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsPanelControllerTest {

    @Test
    public void testGetNextStat() {
        StatisticsPanelController test1 = new StatisticsPanelController();
        boolean[] isDisplayed = new boolean[8];
        for (int i = 0; i < 8; i++){
            if(i <= 3)
                isDisplayed[i] = true;
            else
                isDisplayed[i]= false;
        }
        int result = test1.getNextStat(1, isDisplayed);
        assertEquals(4 ,result);
    }

    @Test
    public void testgetPrevStat() {

        StatisticsPanelController test2 = new StatisticsPanelController();
        boolean[] isDisplayed = new boolean[8];
        for (int i = 0; i < 8; i++){
            if(i <= 3)
                isDisplayed[i] = true;
            else
                isDisplayed[i]= false;
        }
        int result2 = test2.getPrevStat(3, isDisplayed);
        assertEquals(7, result2);

    }
}