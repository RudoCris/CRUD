package pro.rudo.crud.app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class PicketActivity extends ActionBarActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private Sensor senMagnet;

    private long lastUpdate = 0;
    private float last_x, last_y, last_z;

    private EditText azimuthTB;
    private EditText inclineTB;
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
        azimuthTB.setText(Float.toString(orientationData[0]));
        inclineTB.setText(Float.toString(orientationData[1]));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
