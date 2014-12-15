package im.ene.androooid.jphacks;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends SensorMonitorActivity implements SensorEventListener, SensorMonitorCallback {
    private static final String TAG = "MainActivity";

    public static final String SENSOR_TYPE_HEART_RATE = "heartRate";
    public static final String SENSOR_TYPE_STEPS = "steps";

    private TextView mHeartRateTextView;
    private TextView mStepsTextView;

    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mHeartRateTextView = (TextView) stub.findViewById(R.id.tvHeartRate);
                mStepsTextView = (TextView) stub.findViewById(R.id.tvSteps);


                ImageView imageView = (ImageView) stub.findViewById(R.id.imgHeart);
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.heart_flash);
                imageView.startAnimation(animation);

                imageView = (ImageView) stub.findViewById(R.id.imgSteps);
                animation.setStartOffset(300);
                imageView.startAnimation(animation);
            }
        });

        setCallback(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onHeartRateChanged(float heartRate, int accuracy) {
        // send to mobile
        sendSensorToMobile(SENSOR_TYPE_HEART_RATE, heartRate);

        // display value
        mHeartRateTextView.setText(""+heartRate);

        Log.d(TAG, "heartRate:"+heartRate);
    }

    @Override
    public void onStepDetected(int sumOfSteps, int accuracy) {
        // send to mobile
        sendSensorToMobile(SENSOR_TYPE_STEPS, sumOfSteps);

        // display value
        mStepsTextView.setText(""+sumOfSteps);

        Log.d(TAG, "steps:"+sumOfSteps);
    }

    private void sendSensorToMobile(String sensorName, String value) {
        sendMessage(sensorName + ':' + value);
    }

    private void sendSensorToMobile(String sensorName, float value) {
        sendSensorToMobile(sensorName, value+"");
    }

    private void sendSensorToMobile(String sensorName, int value) {
        sendSensorToMobile(sensorName, value+"");
    }
}
