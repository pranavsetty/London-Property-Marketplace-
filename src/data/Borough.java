package data;

import javafx.scene.image.ImageView;

public class Borough {

    private ImageView iv;
    private int count;
    private String shortName;

    public Borough(ImageView iv, String shortName)
    {
        this.iv = iv;
        this.shortName = shortName;
    }

    /**
     * Returns the number of listings for a borough
     * @return
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Returns the image view for the borough
     * @return
     */
    public ImageView getImageView()
    {
        return iv;
    }

    /**
     * Returns the short name of the borough
     * @return
     */
    public String getShortName()
    {
        return shortName;
    }

    /**
     * Increments the amount of listings in the borough.
     */
    public void increaseCount()
    {
        count++;
    }

}
