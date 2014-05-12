package pro.rudo.crud.app.model;

import com.google.gson.Gson;

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

    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
