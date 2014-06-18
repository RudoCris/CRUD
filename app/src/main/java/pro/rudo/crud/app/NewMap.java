package pro.rudo.crud.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.ParseObject;

import pro.rudo.crud.app.model.Cave;
import pro.rudo.crud.app.sqlite.CaveSQLiteHelper;


public class NewMap extends Activity {
    private EditText name;
//    private CaveSQLiteHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);
//        db = new CaveSQLiteHelper(this);
        name = (EditText) findViewById(R.id.titleTextBox);
    }

    public void saveMap(View view){
        String mapName = name.getText().toString();
        Cave cave = new Cave(mapName, getApplicationContext());
//        db.addCave(cave);
//        ParseObject maps = new ParseObject("Cave");
//        maps.put(mapName, mapName);

        cave.save(new Cave.SaveCaveCallback() {
            @Override
            public void onSaveCave() {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
        
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
