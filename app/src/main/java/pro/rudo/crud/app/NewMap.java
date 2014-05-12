package pro.rudo.crud.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.ParseObject;

import pro.rudo.crud.app.model.Map;


public class NewMap extends Activity {
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);

        name = (EditText) findViewById(R.id.titleTextBox);
    }

    public void saveMap(View view){
        String mapName = name.getText().toString()
        Map map = new Map(mapName, getApplicationContext());

        ParseObject maps = new ParseObject("Map");
        maps.put(mapName, mapName);

        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
