package pro.rudo.crud.app.model;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by rudolf on 12.05.14.
 */
public class Map {
    private int _id;
    private String name;
    private String author;

    public Map(String name, Context context) {
        this.name = name;
        SharedPreferences pref = context.getSharedPreferences(context.getPackageName() + "_preferences", Context.MODE_PRIVATE);
        this.author = pref.getString("author", "");
    }

    public int get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
