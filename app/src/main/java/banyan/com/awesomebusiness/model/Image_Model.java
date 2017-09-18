package banyan.com.awesomebusiness.model;

/**
 * Created by Lincoln on 15/01/16.
 */
public class Image_Model {
    private String title, year;

    public Image_Model() {
    }

    public Image_Model(String title, String year) {
        this.title = title;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
