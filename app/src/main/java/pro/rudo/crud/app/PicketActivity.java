package pro.rudo.crud.app;

import android.content.Context;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import pro.rudo.crud.app.model.Picket;
import pro.rudo.crud.app.model.PicketBuilder;

import static java.lang.Math.PI;


public class PicketActivity extends ActionBarActivity  implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor senAccelerometer;
    private Sensor senMagnet;
    private CheckBox isRay;
    private EditText lengthTB;
    private EditText azimuthTB;
    private EditText inclineTB;
    private EditText backAzimuthTB;
    private EditText backInclineTB;
    private EditText leftTB;
    private EditText rigthTB;
    private EditText upTB;
    private EditText downTB;
    private EditText fromTB;
    private EditText toTB;
    private EditText commentTB;
    private Button okBtn;
    private Button cancelBtn;
    private TextView currentAzimuthLbl;
    private TextView currentInclineLbl;
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
        lengthTB = (EditText) findViewById(R.id.lengthTB);
        azimuthTB = (EditText) findViewById(R.id.azimuthTB);
        inclineTB = (EditText) findViewById(R.id.inclineTB);
        backAzimuthTB = (EditText) findViewById(R.id.backAzimuthTB);
        backInclineTB = (EditText) findViewById(R.id.backInclineTB);
        leftTB = (EditText) findViewById(R.id.leftTB);
        rigthTB = (EditText) findViewById(R.id.rightTB);
        upTB = (EditText) findViewById(R.id.upTB);
        downTB = (EditText) findViewById(R.id.downTB);
        currentAzimuthLbl = (TextView) findViewById(R.id.currentAzimuthLbl);
        currentInclineLbl = (TextView) findViewById(R.id.currentInacleLbl);
        fromTB = (EditText) findViewById(R.id.fromTB);
        toTB = (EditText) findViewById(R.id.toTB);
        isRay = (CheckBox) findViewById(R.id.isRay);
        commentTB = (EditText) findViewById(R.id.commentTB);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowCave.class));
            }
        });

        okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Picket picket = new PicketBuilder()
                        .withRay(isRay.isChecked())
                        .withFrom(fromTB.getText().toString())
                        .withTo(toTB.getText().toString())
                        .withLength(Double.parseDouble(lengthTB.getText().toString()))
                        .withAzimuth(Double.parseDouble(azimuthTB.getText().toString()))
                        .withIncline(Double.parseDouble(inclineTB.getText().toString()))
                        .withBackAzimuth(Double.parseDouble(backAzimuthTB.getText().toString()))
                        .withBackIncline(Double.parseDouble(backInclineTB.getText().toString()))
                        .withLeft(Double.parseDouble(leftTB.getText().toString()))
                        .withRight(Double.parseDouble(rigthTB.getText().toString()))
                        .withUp(Double.parseDouble(upTB.getText().toString()))
                        .withDown(Double.parseDouble(downTB.getText().toString()))
                        .withComment(commentTB.getText().toString())
                        .createPicket();

                final int caveId = (Integer)getIntent().getExtras().get("caveId");
                picket.setCaveId(caveId);
                picket.save(new Picket.SavePicketCallback() {
                    @Override
                    public void onSavePicket() {
                        Toast.makeText(getApplicationContext(), "SAVED", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ShowCave.class).putExtra("caveId", caveId));
                    }
                });
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
        return  super.onOptionsItemSelected(item);
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

        currentAzimuthLbl.setText(Double.toString(azimuthDeg));
        currentInclineLbl.setText(Double.toString(inclineDeg));

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    azimuthTB.setText(currentAzimuthLbl.getText().toString());
                    inclineTB.setText(currentInclineLbl.getText().toString());
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    azimuthTB.setText(currentAzimuthLbl.getText().toString());
                    inclineTB.setText(currentInclineLbl.getText().toString());
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
