package pro.rudo.crud.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

import pro.rudo.crud.app.model.Picket;
import pro.rudo.crud.app.sqlite.PicketSQLiteHelper;


public class PicketsList extends ListActivity {
    private PicketSQLiteHelper db;
    private Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickets_list);
        addBtn = (Button) findViewById(R.id.addBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PicketActivity.class));
            }
        });

        db = new PicketSQLiteHelper(this);
        List<Picket> pickets = db.getAllPickets();

        ArrayAdapter<Picket> adapter = new ArrayAdapter<Picket>(this, android.R.layout.simple_list_item_1, pickets);
        setListAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picket, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), EditPrefencesActivity.class));
                return true;
            case R.id.goToCaves:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
