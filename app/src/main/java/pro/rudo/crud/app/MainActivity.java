package pro.rudo.crud.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;


import java.util.List;
import java.util.Random;

import pro.rudo.crud.app.model.Book;
import pro.rudo.crud.app.sqlite.MySQLiteHelper;

public class MainActivity extends ListActivity {
    private MySQLiteHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MySQLiteHelper(this);
        List<Book> books = db.getAllBooks();

        ArrayAdapter<Book> adapter = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, books);
        setListAdapter(adapter);

        Button createBtn = (Button) findViewById(R.id.createBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewMap.class));
            }
        });
    }

    public void onClick(View view){
        @SuppressWarnings("unchecked")
        ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) getListAdapter();
        Book book;
        switch (view.getId()){
            case R.id.addBtn:
                Book[] books = new Book[] {
                        new Book("Romeo And Julieta", "William Shekspir"),
                        new Book("Code complete", "Steve McConnell"),
                        new Book("Patterns and practice", "Band of four")
                };
                int nextInt = new Random().nextInt(3);
                book = books[nextInt];
                db.addBook(book);
                adapter.add(book);
                break;
            case R.id.deleteBtn:
                if(getListAdapter().getCount() > 0) {
                    book = (Book) getListAdapter().getItem(0);
                    db.deleteBook(book);
                    adapter.remove(book);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected  void onPause(){
        super.onPause();
    }
}
