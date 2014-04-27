package pro.rudo.crud.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pro.rudo.crud.app.model.Book;
import pro.rudo.crud.app.sqlite.MySQLiteHelper;


public class NewMap extends Activity {
    private MySQLiteHelper db;
    private EditText title;
    private EditText author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);

        db = new MySQLiteHelper(this);

        title = (EditText) findViewById(R.id.titleTextBox);
        author = (EditText) findViewById(R.id.authorTextBox);
    }

    public void saveMap(View view){
        Book book = new Book(title.getText().toString(), author.getText().toString());
        db.addBook(book);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
