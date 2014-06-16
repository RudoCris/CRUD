package pro.rudo.crud.app;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;


import pro.rudo.crud.app.model.Cave;
import pro.rudo.crud.app.sqlite.CaveSQLiteHelper;

public class MainActivity extends ListActivity{
    private CaveSQLiteHelper db;
    private Button createBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setTitle("Топосъемки");
        createBtn = (Button) findViewById(R.id.createBtn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NewMap.class));
            }
        });
        db = new CaveSQLiteHelper(this);
        List<Cave> books = db.getAllCaves();

        ArrayAdapter<Cave> adapter = new ArrayAdapter<Cave>(this, android.R.layout.simple_list_item_1, books);
        setListAdapter(adapter);

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = getListView().getItemAtPosition(i).toString();
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(title)
                        .setItems(R.array.actions, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (i){
                                    case 0:
                                        Toast.makeText(getApplicationContext(), "EDITED!", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(getApplicationContext(), "DELETED!", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }).create();
                        dialog.show();
                return true;
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);
        startActivity(new Intent(getApplicationContext(), ShowCave.class));
        Toast.makeText(getApplicationContext(), l.getItemAtPosition(position).toString() , Toast.LENGTH_SHORT).show();
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
        if(id == R.id.action_settings){
            startActivity(new Intent(getBaseContext(), EditPrefencesActivity.class));
            return true;
        }
        return  super.onOptionsItemSelected(item);
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
