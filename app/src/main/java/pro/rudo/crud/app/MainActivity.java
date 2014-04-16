package pro.rudo.crud.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import pro.rudo.crud.app.model.Book;
import pro.rudo.crud.app.sqlite.MySQLiteHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MySQLiteHelper db = new MySQLiteHelper(this);

        db.addBook(new Book("Android Application Development Cookbook", "Wei Meng Lee"));
        db.addBook(new Book("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));
        db.addBook(new Book("Learn Android App Development", "Wallace Jackson"));

        List<Book> books = db.getAllBooks();

        db.deleteBook(books.get(0));

        db.getAllBooks();
    }
}
