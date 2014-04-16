package pro.rudo.crud.app.model;

/**
 * Created by rudolf on 17.04.14.
 */
public class Book {
    private int id;
    private String title;
    private String author;

    public Book(){}

    public Book(String title, String author){
        super();
        this.title = title;
        this.author = author;
    }

    public String toString(){
        return "Book [id=" + id + ", title=" + title + ", autor=" + author + "]";
    }
}
