/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Shatha Suliman
 */
public abstract class ReferenceDocument {
    
    protected String Title;
    protected String Author;
    protected String Publisher;
    protected String PublishYear;
    protected String Pages;

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
    public String getPublishYear() {
        return PublishYear;
    }

    /**
     * @param PublishYear the PublishYear to set
     */
    public void setPublishYear(String PublishYear) {
        this.PublishYear = PublishYear;
    }

    /**
     * @return the PagesNo
     */
    public String getPages() {
        return Pages;
    }

    /**
     * @param Pages the PagesNo to set
     */
    public void setPages(String Pages) {
        this.Pages = Pages;
    }
    
}
