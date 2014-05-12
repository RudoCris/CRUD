package pro.rudo.crud.app;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditPrefencesActivity extends Activity {

    private EditText name;
    private Button saveBtn;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prefences);
        name = (EditText) findViewById(R.id.nameTB);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePreferences();
            }
        });
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("author", name.getText().toString());
        editor.commit();

        Toast.makeText(getApplicationContext(), "Настройки сохранены", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_prefences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_save) {
            savePreferences();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
