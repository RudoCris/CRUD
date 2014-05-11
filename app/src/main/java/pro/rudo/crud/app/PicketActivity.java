package pro.rudo.crud.app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseAnalytics;

import static java.lang.Math.PI;


public class PicketActivity extends ActionBarActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private Sensor senMagnet;

    private EditText azimuthTB;
    private EditText inclineTB;
    private EditText backAzimuthTB;
    private EditText backInclineTB;
    private Button okBtn;
    private Context context = this;
    private float[] orientationData;
    private float[] rotationMatrix;
    private float[] accelerometerData;
    private float[] magnetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picket);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        senMagnet = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        rotationMatrix = new float[16];
        accelerometerData = new float[3];
        magnetData = new float[3];
        orientationData = new float[3];

        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        azimuthTB = (EditText) findViewById(R.id.azimuthTB);
        inclineTB = (EditText) findViewById(R.id.inclineTB);
        backAzimuthTB = (EditText) findViewById(R.id.backAzimuthTB);
        backInclineTB = (EditText) findViewById(R.id.backInclineTB);

        Parse.initialize(this, "jG4p9JnJXW1f8jaYvynOTsB9z1BjZpFThx6MnLBc", "F8JQZoPtkUqYWTptr99vAvcdQnbjd9zh6LCXSTBW");

        okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject testObject = new ParseObject("TestObject");
                testObject.put("foo", "rud");
                testObject.saveInBackground();
                Toast.makeText(context, "SAVED", Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, senMagnet, SensorManager.SENSOR_DELAY_NORMAL);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picket, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private void loadNewSensorData(SensorEvent event){
        final int type = event.sensor.getType();
        switch (type){
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magnetData = event.values.clone();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        loadNewSensorData(event);

        SensorManager.getRotationMatrix(rotationMatrix, null, accelerometerData, magnetData);
        SensorManager.getOrientation(rotationMatrix, orientationData);

        double azimuthDeg = orientationData[0] * 180/PI;
        double inclineDeg = orientationData[1] * 180/PI;
        azimuthDeg = Math.round(azimuthDeg*10)/10;
        inclineDeg = Math.round(inclineDeg*10)/10;

        azimuthTB.setText(Double.toString(azimuthDeg));
        inclineTB.setText(Double.toString(inclineDeg));

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    backAzimuthTB.setText(azimuthTB.getText().toString());
                    backInclineTB.setText(inclineTB.getText().toString());
                    Toast.makeText(this, "UP", Toast.LENGTH_SHORT).show();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    backAzimuthTB.setText(azimuthTB.getText().toString());
                    backInclineTB.setText(inclineTB.getText().toString());
                    Toast.makeText(this, "Down", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
