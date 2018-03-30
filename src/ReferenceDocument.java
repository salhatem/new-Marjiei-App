/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shatha Suliman
 */
public class ReferenceDocument {
    
    private int ID;
    protected String Title;
    protected String Author;
    protected String Publisher;
    protected int PublishYear;
    private String DateAdded;

    public ReferenceDocument (int ID, String Title, String Author, String Publisher, int PublishYear, String DateAdded)
    {
        this.ID = ID;
        this.Title = Title; 
        this.Author = Author; 
        this.Publisher = Publisher;
        this.PublishYear = PublishYear;
        this.DateAdded = DateAdded;
    }
    /**
     * @return the Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     * @param Title the Title to set
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     * @return the Author
     */
    public String getAuthor() {
        return Author;
    }

    /**
     * @param Author the Author to set
     */
    public void setAuthor(String Author) {
        this.Author = Author;
    }

    /**
     * @return the Publisher
     */
    public String getPublisher() {
        return Publisher;
    }

    /**
     * @param Publisher the Publisher to set
     */
    public void setPublisher(String Publisher) {
        this.Publisher = Publisher;
    }

    /**
     * @return the PublishYear
     */
    public int getPublishYear() {
        return PublishYear;
    }

    /**
     * @param PublishYear the PublishYear to set
     */
    public void setPublishYear(int PublishYear) {
        this.PublishYear = PublishYear;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @return the DateAdded
     */
    public String getDateAdded() {
        return DateAdded;
    }

}
